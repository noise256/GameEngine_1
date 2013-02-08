package game;

import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.opengl.GL3bc;

import modelManager.TextureLoader;
import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import sceneManager.SceneNode;
import utilityManager.MathBox;
import brickManager.Brick;
import brickManager.Brick.BrickType;
import brickManager.BrickObject;
import collisionManager.Collidable;

import com.jogamp.opengl.util.texture.Texture;

public class TestShipFragment extends BrickObject {

	public TestShipFragment(GameObject source, Hashtable<String, Double> values, ArrayList<Brick> bricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		super(ObjectType.FRAGMENT, source, values, bricks, adjacencyList);
	}

	@Override
	public void collide(Collidable collider) {
		Brick brick = getClosestBrick(collider.getPosition());
		
		brick.setHealth(brick.getHealth() - 1);
		
		if (bricks.get(0).getHealth() <= 0) {
			setAlive(false);
		}
	}

	@Override
	public double getRadius() {
		if (bricks.isEmpty()) {
			return 0.0;
		}
		
		Brick max = bricks.get(0);
		for (Brick brick : bricks) {
			if (brick.getPosition().getNorm() > max.getPosition().getNorm()) {
				max = brick;
			}
		}
		
		if (max.getBrickType() == BrickType.SQUARE) {
			return max.getPosition().getNorm() + + Math.sqrt(max.getEdgeLength()*2 + max.getEdgeLength()*2);
		}
		else if (max.getBrickType() == BrickType.TRIANGLE) {
			return max.getPosition().getNorm();
		}
		else {
			return -1;
		}
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			SceneNode root = new SceneNode(null) {
				private Texture metalTexture;
				
				@Override
				public void update(GL3bc gl) {
					if (metalTexture == null) {
						TextureLoader.loadTexture(gl, "metal1", "data/border.png");
						metalTexture = TextureLoader.getTexture("metal1");
					}
					metalTexture.enable(gl);
					metalTexture.bind(gl);
					
					gl.glPushMatrix();

					// translate and rotate
					gl.glTranslatef((float) position.getX(), (float) position.getY(), 0);
					gl.glRotatef((float) (orientation * 180 / Math.PI), 0, 0, 1);
					
					for (int i = 0; i < bricks.size(); i++) {
						Brick brick = bricks.get(i);
						brick.updateView();
						
						for (SceneNode brickView : brick.getView()) {
							gl.glPushMatrix();
							if (brick.isExploding()) {
								gl.glRotatef((float) -(orientation * 180 / Math.PI), 0, 0, 1);
							}
							brickView.update(gl);
							gl.glPopMatrix();
						}
					}
					
					gl.glPopMatrix();
				}
			};
			sceneNodes.put("root", root);
		}
	}

	@Override
	public void update() {
		updateBricks();
	}

	@Override
	protected PhysicalObject createFragment(ArrayList<Brick> fragmentBricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		//get average position of fragment bricks
		Vector2D averagePosition = new Vector2D(0.0, 0.0);
		for (Brick brick : fragmentBricks) {
			averagePosition = averagePosition.add(brick.getPosition());
		}
		averagePosition = averagePosition.scalarMultiply(1.0/fragmentBricks.size());
		
		//adjust position of bricks to be relative to new average position
		for (Brick brick : fragmentBricks) {
			brick.setPosition(brick.getPosition().subtract(averagePosition));
		}
		
		//update brick indices
		for (int i = 0; i < fragmentBricks.size(); i++) {
			//change adjacency list
			for (int j = 0; j < adjacencyList.size(); j++) {
				if (adjacencyList.get(j).contains(new Integer(fragmentBricks.get(i).getIndex()))) {
					adjacencyList.get(j).remove(new Integer(fragmentBricks.get(i).getIndex()));
					adjacencyList.get(j).add(new Integer(i));
				}
			}
			//change brick index
			fragmentBricks.get(i).setIndex(i);
		}
		
		//set averagePosition of bricks to an absolute position
		averagePosition = MathBox.rotatePoint(averagePosition, orientation).add(position);
				
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
		
		TestShipFragment fragment = new TestShipFragment(this, values, fragmentBricks, adjacencyList);
		fragment.addObserver(GameManager.getObjectManager());
		return fragment;
	}

	@Override
	public boolean canCollide() {
		return true;
	}
}
