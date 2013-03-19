package game.ship;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.opengl.GL3bc;

import objectManager.EntityHashMap;
import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import sceneManager.SceneNode;
import sectionManager.Section;
import sectionManager.SectionObject;
import textureManager.TextureLoader;
import utilityManager.MathBox;
import collisionManager.Collidable;
import factionManager.Faction;
import gameManager.GameManager;

public class TestShipFragment extends SectionObject {

	public TestShipFragment(GameObject source, Hashtable<String, Double> values, ArrayList<Section> sections, ArrayList<ArrayList<Integer>> adjacencyList, Faction faction) {
		super(ObjectType.FRAGMENT, source, values, sections, adjacencyList, faction);
	}

	@Override
	public void collide(Collidable collider) {
		Section section = getClosestSection(collider.getCollidablePosition());

		try {
			section.setHealth(section.getHealth() - 1);
		}
		catch (NullPointerException e) {
			System.err.println("Attempted to collide collider: " + collider + " into " + this + " however the closest section to the collider position was null.");
		}
	}

	@Override
	public double getRadius() {
		if (sections.isEmpty()) {
			return 0.0;
		}

		Section max = sections.get(0);
		for (Section section : sections) {
			if (section.getSectionPosition().getNorm() > max.getSectionPosition().getNorm()) {
				max = section;
			}
		}

		return Math.max(max.getSectionPosition().getX(), max.getSectionPosition().getY()) + 
				   Math.max(TextureLoader.getTexture(max.getTextureName()).getWidth(), TextureLoader.getTexture(max.getTextureName()).getHeight())/2;
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			SceneNode root = new SceneNode(null) {

				@Override
				public void update(GL3bc gl) {
					gl.glPushMatrix();

					// translate and rotate
					gl.glTranslatef((float) objectPosition.getX(), (float) objectPosition.getY(), 0);
					gl.glRotatef((float) (orientation * 180 / Math.PI), 0, 0, 1);

					for (int i = 0; i < sections.size(); i++) {
						Section section = sections.get(i);
						section.updateView();

						for (SceneNode brickView : section.getView()) {
							brickView.update(gl);
						}
					}

					gl.glPopMatrix();
				}
			};
			sceneNodes.put("root", root);
		}
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		if (getSectionFromIndex(0) == null) {
			setAlive(false);
		}

		updateSections();
	}

	@Override
	protected PhysicalObject createFragment(ArrayList<Section> fragmentBricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		// get average position of fragment sections
		Vector2D averagePosition = new Vector2D(0.0, 0.0);
		for (Section section : fragmentBricks) {
			averagePosition = averagePosition.add(section.getSectionPosition());
		}
		averagePosition = averagePosition.scalarMultiply(1.0 / fragmentBricks.size());

		// adjust position of sections to be relative to new average position
		for (Section section : fragmentBricks) {
			section.setPosition(section.getSectionPosition().subtract(averagePosition));
		}

		// update section indices
		for (int i = 0; i < fragmentBricks.size(); i++) {
			// change adjacency list
			for (int j = 0; j < adjacencyList.size(); j++) {
				if (adjacencyList.get(j).contains(new Integer(fragmentBricks.get(i).getIndex()))) {
					adjacencyList.get(j).remove(new Integer(fragmentBricks.get(i).getIndex()));
					adjacencyList.get(j).add(new Integer(i));
				}
			}
			// change section index
			fragmentBricks.get(i).setIndex(i);
		}

		// set averagePosition of sections to an absolute position
		averagePosition = MathBox.rotatePoint(averagePosition, orientation).add(objectPosition);

		Hashtable<String, Double> values = new Hashtable<String, Double>();
		values.put("mass", 5000.0);
		values.put("positionX", averagePosition.getX());
		values.put("positionY", averagePosition.getY());
		values.put("orientation", orientation);
		values.put("forceX", Math.random() * MathBox.nextSign());
		values.put("forceY", Math.random() * MathBox.nextSign());
		values.put("forceMagnitude", Math.random() * 100.0);
		values.put("turningForce", Math.random() * 0.1 * MathBox.nextSign());
		values.put("velocityX", 0.0);
		values.put("velocityY", 0.0);
		values.put("turningVelocity", 0.0);
		values.put("maxVelocity", 0.5);
		values.put("maxTurningVelocity", 0.1);
		values.put("maxForce", 1.0);
		values.put("maxTurningForce", 1.0);

		TestShipFragment fragment = new TestShipFragment(this, values, fragmentBricks, adjacencyList, faction);
		fragment.addObserver(GameManager.getObjectManager());
		return fragment;
	}

	@Override
	public boolean canCollide() {
		if (alive) {
			return true;
		}
		else {
			return false;
		}
	}
}
