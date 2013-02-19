package game.ship;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.opengl.GL3bc;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;
import aiManager.AgentInput.AgentInputType;
import aiManager.AgentInputAttack;
import aiManager.AgentInputMove;
import brickManager.Brick;
import collisionManager.Collidable;
import factionManager.Faction;

public class TestShip extends Ship {
	public TestShip(Hashtable<String, Double> values, ArrayList<Brick> bricks, ArrayList<ArrayList<Integer>> adjacencyList, Faction faction) {
		super(values, bricks, adjacencyList, faction);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			sceneNodes.put("root", new TestShipSceneNode());
		}
	}

	@Override
	public void collide(Collidable collider) {
		Brick closest = getClosestBrick(collider.getPosition());

		closest.setHealth(closest.getHealth() - 10);
	}
	
	private class TestShipSceneNode extends SceneNode { 
		public TestShipSceneNode() {
			super(null);
		}

		@Override
		public void update(GL3bc gl) {
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

			if (selected && currentOrder != null) {
				Vector2D target = null;
				if (currentOrder.getAgentInputType() == AgentInputType.MOVE) {
					gl.glColor4f(0.0f, 1.0f, 0.0f, 0.4f);
					target = ((AgentInputMove) currentOrder).getDestination();
				}
				else if (currentOrder.getAgentInputType() == AgentInputType.ATTACK) {
					gl.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
					target = ((AgentInputAttack) currentOrder).getTarget().getPosition();
				}

				gl.glEnable(GL3bc.GL_BLEND);
				gl.glLineWidth(1.0f);

				gl.glPushMatrix();
				gl.glTranslatef(0.0f, 0.0f, 0.0f);
				gl.glBegin(GL3bc.GL_LINES);
				gl.glVertex3d(position.getX(), position.getY(), 0.0d);
				gl.glVertex3d(target.getX(), target.getY(), 0.0d);
				gl.glEnd();
				gl.glPopMatrix();

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
			}
			
			if (selected) {
				gl.glEnable(GL3bc.GL_BLEND);
				gl.glLineWidth(3f);
				
				gl.glPushMatrix();
				
				gl.glTranslatef((float) position.getX(), (float) position.getY(), 0.0f);
				gl.glColor4f(0.0f, 0.0f, 1.0f, 0.5f);
				
				double r = 0;
				gl.glBegin(GL3bc.GL_LINES);
				for (int i = 0; i < 360; i++) {
					r = i * Math.PI / 180;
					gl.glVertex3d(Math.cos(r) * (getRadius() + 5), Math.sin(r) * (getRadius() + 5), 1.0);
				}
				gl.glEnd();
				
				gl.glPopMatrix();

				gl.glDisable(GL3bc.GL_BLEND);
			}
		}
	};
}