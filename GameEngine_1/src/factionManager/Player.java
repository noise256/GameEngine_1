package factionManager;

import game.ui.InterfaceBox;
import graphicsManager.Constants;

import javax.media.opengl.GL3bc;

import objectManager.EntityHashMap;
import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;

/**
 * The player needs to represented. For example many visual variables might depend on the players fleet, e.g. radar strength etc.
 * 
 * @author noise
 *
 */
public class Player extends GameObject {
	private Faction faction;
	
	public Player(ObjectType objectType, GameObject source) {
		super(objectType, source);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			sceneNodes.put("root", new PlayerSceneNode());
		}
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
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
			
			gl.glPushMatrix();
			
			gl.glTranslatef(Constants.viewWidth/2, 200/2, 0.0f);
			gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
			
			gl.glBegin(GL3bc.GL_QUADS);
				gl.glVertex3f(90, 90, 0);
				gl.glVertex3f(90, -90, 0);
				gl.glVertex3f(-90, -90, 0);
				gl.glVertex3f(-90, 90, 0);
			gl.glEnd();
			
			gl.glPopMatrix();
			
			gl.glEnable(GL3bc.GL_LIGHTING);
			gl.glEnable(GL3bc.GL_TEXTURE_2D);
			gl.glEnable(GL3bc.GL_BLEND);
		}
	};
}
