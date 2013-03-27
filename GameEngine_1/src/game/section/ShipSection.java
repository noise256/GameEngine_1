package game.section;

import java.util.ArrayList;

import javax.media.opengl.GL3bc;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;
import sectionManager.Section;
import sectionManager.SectionObject;
import textureManager.TextureLoader;

public class ShipSection extends Section {
	public boolean takingDamage;
	
	public ShipSection(SectionObject parent, int index, int health, String sectionName, String texturePath, Vector2D sectionPosition, double textureWidth, double textureHeight, ArrayList<Double> textureVertices) {
		super(parent, index, health, sectionName, texturePath, sectionPosition, textureWidth, textureHeight, textureVertices);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null && !exploding) {
			SceneNode root = new SceneNode(null) {
				@Override
				public void update(GL3bc gl) {
					if (!TextureLoader.getCurrentTextureName().equals(getTexturePath())) {
						TextureLoader.loadTexture(getTexturePath(), getTexturePath());
						TextureLoader.setCurrentTexture(gl, getTexturePath());
					}
					
					gl.glPushMatrix();

					gl.glEnable(GL3bc.GL_BLEND);
					gl.glEnable(GL3bc.GL_LIGHTING);
					gl.glEnable(GL3bc.GL_TEXTURE_2D);

					gl.glTranslatef((float) sectionPosition.getX(), (float) sectionPosition.getY(), 0.0f);
					
					float width = TextureLoader.getCurrentTexture().getWidth();
					float height = TextureLoader.getCurrentTexture().getHeight();
					
					gl.glBegin(GL3bc.GL_QUADS);
//						gl.glNormal3f(1.0f, 1.0f, 1.0f);
						gl.glTexCoord3f(1.0f, 1.0f, 1.0f);
						gl.glVertex3f(width/2, height/2, 0.0f);
						
//						gl.glNormal3f(1.0f, 0.0f, 1.0f);
						gl.glTexCoord3f(1.0f, 0.0f, 1.0f);
						gl.glVertex3f(width/2, -height/2, 0.0f);
						
//						gl.glNormal3f(0.0f, 0.0f, 1.0f);
						gl.glTexCoord3f(0.0f, 0.0f, 1.0f);
						gl.glVertex3f(-width/2, -height/2, 0.0f);
						
//						gl.glNormal3f(0.0f, 1.0f, 1.0f);
						gl.glTexCoord3f(0.0f, 1.0f, 1.0f);
						gl.glVertex3f(-width/2, height/2, 0.0f);
					gl.glEnd();

					gl.glDisable(GL3bc.GL_BLEND);
					gl.glDisable(GL3bc.GL_LIGHTING);
					gl.glDisable(GL3bc.GL_TEXTURE_2D);

					gl.glPopMatrix();
				}
			};

			sceneNodes.put("root", root);
		}
	}
}
