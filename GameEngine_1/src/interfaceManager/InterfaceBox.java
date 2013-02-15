package interfaceManager;

import javax.media.opengl.GL3bc;

import modelManager.TextureLoader;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;

public class InterfaceBox extends InterfaceObject {
	
	public InterfaceBox(Vector2D position, float width, float height) {
		super(position, width, height);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			SceneNode root = new SceneNode(null) {
				@Override
				public void update(GL3bc gl) {
					if (!activated) {
						return;
					}
					
					if (!TextureLoader.getCurrentTextureName().equals("InterfaceBox1")) {
						TextureLoader.loadTexture(gl, "InterfaceBox1", "InterfaceBox1.png");
						TextureLoader.setCurrentTexture(gl, "InterfaceBox1");
					}
					
					gl.glDisable(GL3bc.GL_LIGHTING);
					gl.glDisable(GL3bc.GL_BLEND);
					gl.glEnable(GL3bc.GL_TEXTURE_2D);
					gl.glEnable(GL3bc.GL_DEPTH_TEST);
					
					gl.glPushMatrix();
					
					gl.glTranslatef(width/2.0f, height/2.0f, 0.0f);
					gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					gl.glBegin(GL3bc.GL_QUADS);
						gl.glTexCoord2f(1.0f, 1.0f);
						gl.glVertex3f(width/2, height/2, 1);
						
						gl.glTexCoord2f(1.0f, 0.0f);
						gl.glVertex3f(width/2, -height/2, 1);
						
						gl.glTexCoord2f(0.0f, 0.0f);
						gl.glVertex3f(-width/2, -height/2, 1);
						
						gl.glTexCoord2f(0.0f, 1.0f);
						gl.glVertex3f(-width/2, height/2, 1);
					gl.glEnd();
					
					gl.glPopMatrix();
					
					gl.glEnable(GL3bc.GL_LIGHTING);
					gl.glEnable(GL3bc.GL_BLEND);
					gl.glDisable(GL3bc.GL_TEXTURE_2D);
					gl.glDisable(GL3bc.GL_DEPTH_TEST);
				}
			};
			sceneNodes.put("root", root);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
