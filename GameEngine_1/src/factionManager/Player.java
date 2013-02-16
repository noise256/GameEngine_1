package factionManager;

import java.util.ArrayList;

import game.ui.InterfaceBox;
import graphicsManager.Constants;

import javax.media.opengl.GL3bc;

import objectManager.EntityHashMap;
import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;

import sceneManager.SceneNode;

/**
 * The player needs to represented. For example many visual variables might depend on the players fleet, e.g. radar strength etc.
 * 
 * @author noise
 *
 */
public class Player extends GameObject {
	//TODO radar should probably be defined in a separate clas
	private ArrayList<Float[]> radarFriendlyLocations = new ArrayList<Float[]>();
	private ArrayList<Float[]> radarEnemyLocations = new ArrayList<Float[]>();
	
	public Player(ObjectType objectType, GameObject source, Faction faction) {
		super(objectType, source, faction);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			sceneNodes.put("root", new PlayerSceneNode());
		}
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		ArrayList<PhysicalObject> nearbyObjects = entityHashMap.getEntities(
				ObjectType.AGENT, 
				Constants.cameraX - Constants.viewWidth * 5, Constants.cameraX + Constants.viewWidth * 5, 
				Constants.cameraY - Constants.viewHeight * 5, Constants.cameraY + Constants.viewHeight * 5
		);
		
		radarFriendlyLocations.clear();
		radarEnemyLocations.clear();
		
		for (PhysicalObject nearbyObject : nearbyObjects) {
			if (nearbyObject.getFaction().equals(faction)) {
				radarFriendlyLocations.add(new Float[] {(float) nearbyObject.getPosition().getX(), (float) nearbyObject.getPosition().getY()});
			}
			else if (!nearbyObject.getFaction().equals(faction)) {
				radarEnemyLocations.add(new Float[] {(float) nearbyObject.getPosition().getX(), (float) nearbyObject.getPosition().getY()});
			}
		}
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	private class PlayerSceneNode extends SceneNode {
		InterfaceBox interfaceBox = null;
		
		public PlayerSceneNode() {
			super(null);
			interfaceBox = new InterfaceBox(new Vector2D(Constants.viewWidth/2, 200/2), 200, 200);
			interfaceBox.setActivated(true);
		}
		
		@Override
		public void update(GL3bc gl) {
			interfaceBox.updateView();
			
			for (SceneNode interfaceNode : interfaceBox.getView()) {
				interfaceNode.update(gl);
			}
			
			gl.glDisable(GL3bc.GL_LIGHTING);
			gl.glDisable(GL3bc.GL_TEXTURE_2D);
			gl.glDisable(GL3bc.GL_BLEND);
			gl.glDisable(GL3bc.GL_DEPTH_TEST);
			
			gl.glPushMatrix();
			
			gl.glTranslatef(Constants.viewWidth/2, 200/2, 0.0f);
			gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
			
			gl.glBegin(GL3bc.GL_QUADS);
				gl.glVertex3f(90, 90, 0);
				gl.glVertex3f(90, -90, 0);
				gl.glVertex3f(-90, -90, 0);
				gl.glVertex3f(-90, 90, 0);
			gl.glEnd();
			
			gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
			for (Float[] radarPosition : radarFriendlyLocations) {
				//TODO check this:
				float x = (radarPosition[0] - Constants.cameraX) / 5000 * 200;
				float y = (radarPosition[1] - Constants.cameraY) / 5000 * 200;
				
				gl.glPushMatrix();
				
				gl.glTranslatef(x, y * -1, 0.0f);
				
				gl.glBegin(GL3bc.GL_QUADS);
				gl.glVertex3f(2, 2, 0);
				gl.glVertex3f(2, -2, 0);
				gl.glVertex3f(-2, -2, 0);
				gl.glVertex3f(-2, 2, 0);
				gl.glEnd();
				
				gl.glPopMatrix();
			}
			
			gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
			for (Float[] radarPosition : radarEnemyLocations) {
				//TODO check this:
				float x = (radarPosition[0] - Constants.cameraX) / 5000 * 200;
				float y = (radarPosition[1] - Constants.cameraY) / 5000 * 200;
				
				gl.glPushMatrix();
				
				gl.glTranslatef(x, y * -1, 0.0f);
				
				gl.glBegin(GL3bc.GL_QUADS);
				gl.glVertex3f(2, 2, 0);
				gl.glVertex3f(2, -2, 0);
				gl.glVertex3f(-2, -2, 0);
				gl.glVertex3f(-2, 2, 0);
				gl.glEnd();
				
				gl.glPopMatrix();
			}
			
			gl.glPopMatrix();
			
			gl.glEnable(GL3bc.GL_LIGHTING);
			gl.glEnable(GL3bc.GL_TEXTURE_2D);
			gl.glEnable(GL3bc.GL_BLEND);
			gl.glEnable(GL3bc.GL_DEPTH_TEST);
		}
	};
}
