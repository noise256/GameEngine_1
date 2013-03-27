package game.ship;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.opengl.GL3bc;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;
import sectionManager.Section;
import aiManager.AgentInput.AgentInputType;
import aiManager.AgentInputAttack;
import aiManager.AgentInputMove;
import collisionManager.Collidable;
import factionManager.Faction;
import game.subsystem.SubSystem;

public class TestShip extends Ship {
	public TestShip(Hashtable<String, Double> values, ArrayList<Section> sections, ArrayList<ArrayList<Integer>> adjacencyList, Faction faction) {
		super(values, sections, adjacencyList, faction);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			sceneNodes.put("root", new TestShipSceneNode());
		}
	}

	@Override
	public void collide(Collidable collider) {
		Section section = getClosestSection(collider.getCollidablePosition());

		try {
//			section.setHealth(section.getHealth() - 2);
			
			if (section.getHealth() <= 0) {
				ArrayList<Section> adjacentSections = getAdjacentSections(section.getIndex());
				for (Section adjacent : adjacentSections) {
					adjacent.setHealth(-1);
				}
			}
		}
		catch (NullPointerException e) {
			System.err.println("Attempted to collide collider: " + collider + " into " + this + " however the closest section to the collider position was null.");
		}
	}
	
	private class TestShipSceneNode extends SceneNode { 
		public TestShipSceneNode() {
			super(null);
		}

		@Override
		public void update(GL3bc gl) {
			for (SubSystem subSystem : subSystems) {
				subSystem.updateView();
				
				for (SceneNode systemView : subSystem.getView()) {
					systemView.update(gl);
				}
			}
			
			gl.glPushMatrix();

			// translate and rotate
			gl.glTranslatef((float) objectPosition.getX(), (float) objectPosition.getY(), 0);
			gl.glRotatef((float) (orientation * 180 / Math.PI), 0, 0, 1);
			
			for (int i = 0; i < sections.size(); i++) {
				Section section = sections.get(i);
				section.updateView();

				for (SceneNode sectionView : section.getView()) {
					sectionView.update(gl);
				}
			}

			
			for (int i = 0; i < subSystems.size(); i++) {
				SubSystem system = subSystems.get(i);
				system.updateView();
				
				for (SceneNode systemView : system.getView()) {
					systemView.update(gl);
				}
			}
			
			gl.glPopMatrix();

			if (selected && currentOrder != null) {
				Vector2D target = null;
				if (currentOrder.getAgentInputType() == AgentInputType.MOVE) {
					gl.glColor4f(0.0f, 1.0f, 0.0f, 0.4f);
					target = ((AgentInputMove) currentOrder).getDestination();
				}
				else if (currentOrder.getAgentInputType() == AgentInputType.ATTACK) {
					gl.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
					target = ((AgentInputAttack) currentOrder).getSectionTarget().getAbsolutePosition();
				}

				gl.glDisable(GL3bc.GL_LIGHTING);
				gl.glEnable(GL3bc.GL_BLEND);
				gl.glEnable(GL3bc.GL_LINE_SMOOTH);
				gl.glHint(GL3bc.GL_LINE_SMOOTH_HINT, GL3bc.GL_NICEST);
				gl.glLineWidth(1.0f);

				gl.glPushMatrix();
				gl.glTranslatef(0.0f, 0.0f, 0.0f);
				gl.glBegin(GL3bc.GL_LINES);
				gl.glVertex3d(objectPosition.getX(), objectPosition.getY(), 0.0d);
				gl.glVertex3d(target.getX(), target.getY(), 0.0d);
				gl.glEnd();
				gl.glPopMatrix();

				gl.glDisable(GL3bc.GL_LINE_SMOOTH);
				gl.glDisable(GL3bc.GL_BLEND);
				
				//TODO change this to a cross
				gl.glEnable(GL3bc.GL_BLEND);
				gl.glEnable(GL3bc.GL_POLYGON_SMOOTH);
				gl.glHint(GL3bc.GL_POLYGON_SMOOTH_HINT, GL3bc.GL_NICEST);
				
				gl.glPushMatrix();
				gl.glTranslated(target.getX(), target.getY(), 0.0d);
				double r = 0;
				gl.glBegin(GL3bc.GL_POLYGON);
				for (int i = 0; i < 360; i++) {
					r = i * Math.PI / 180;
					gl.glVertex3d(Math.cos(r) * 5, Math.sin(r) * 5, 1.0);
				}
				gl.glEnd();
				gl.glPopMatrix();

				gl.glDisable(GL3bc.GL_BLEND);
				gl.glDisable(GL3bc.GL_POLYGON_SMOOTH);
				gl.glEnable(GL3bc.GL_LIGHTING);
			}
			
			if (selected) {
				gl.glDisable(GL3bc.GL_LIGHTING);
				gl.glEnable(GL3bc.GL_BLEND);
				gl.glEnable(GL3bc.GL_LINE_SMOOTH);
				gl.glHint(GL3bc.GL_LINE_SMOOTH_HINT, GL3bc.GL_NICEST);
				gl.glLineWidth(3f);
				
				gl.glPushMatrix();
				
				gl.glTranslatef((float) objectPosition.getX(), (float) objectPosition.getY(), 0.0f);
				gl.glColor4f(0.0f, 0.0f, 1.0f, 0.5f);
				
				double r = 0;
				gl.glBegin(GL3bc.GL_LINES);
				for (int i = 0; i < 360; i++) {
					r = i * Math.PI / 180;
					gl.glVertex3d(Math.cos(r) * (getRadius() + 5), Math.sin(r) * (getRadius() + 5), 1.0);
				}
				gl.glEnd();
				
				gl.glPopMatrix();

				gl.glDisable(GL3bc.GL_LINE_SMOOTH);
				gl.glDisable(GL3bc.GL_BLEND);
				gl.glEnable(GL3bc.GL_LIGHTING);
			}
		}
	}
}
