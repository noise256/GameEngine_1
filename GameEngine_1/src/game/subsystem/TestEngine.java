package game.subsystem;

import javax.media.opengl.GL3bc;

import objectManager.EntityHashMap;
import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;
import sectionManager.Section;

public class TestEngine extends Engine {
	
	public TestEngine(GameObject source, Section systemSection, Vector2D sectionPosition, double orientation, double maxForce) {
		super(source, systemSection, sectionPosition, orientation, maxForce);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			sceneNodes.put("root", new TestEngineSceneNode());
		}
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		//add new particle at absolute position of this engine
		Vector2D absolutePosition = getAbsolutePosition();
		
		positionBuffer.add(new Float[] {(float) absolutePosition.getX(), (float) absolutePosition.getY()});
//		float[] rotatedSectionPosition = MathBox.rotatePoint(
//			new float[] {
//				(float) systemSection.getSectionPosition().getX() - 100,  
//				(float) systemSection.getSectionPosition().getY()
//			}, 
//			(float) orientation
//		);
//		positionBuffer.add(new Float[] {(float) position.getX() + rotatedBrickPosition[0], (float) position.getY() + rotatedBrickPosition[1]});
	}
	
	private class TestEngineSceneNode extends SceneNode {
		public TestEngineSceneNode() {
			super(null);
		}

		@Override
		public void update(GL3bc gl) {
			gl.glPushMatrix();
//			gl.glLoadIdentity();
			
			gl.glEnable(GL3bc.GL_BLEND);
			gl.glDisable(GL3bc.GL_LIGHTING);
			gl.glDisable(GL3bc.GL_TEXTURE_2D);

//			gl.glTranslatef(- (float) position.getX(), - (float) position.getY(), 0);
//			gl.glTranslatef(0, 0, 0);
			
			Object[] positionBufferAsArray = positionBuffer.toArray();
			
			gl.glBegin(GL3bc.GL_POINTS);
			for (int i = 0; i < positionBufferAsArray.length; i++) {
				float alpha = (1 - (float) (positionBufferAsArray.length - i) / (float) positionBufferAsArray.length) / 5;
				
				Float[] enginePosition = (Float[]) positionBufferAsArray[i];
				
				gl.glColor4f(1.0f, 1.0f, 1.0f, alpha);
				gl.glVertex3f(enginePosition[0] + 2, enginePosition[1] + 2, 0);
				
//				gl.glBegin(GL3bc.GL_QUADS);
//					gl.glVertex3f(enginePosition[0] + 2, enginePosition[1] + 2, 0);
//					gl.glVertex3f(enginePosition[0] + 2, enginePosition[1] - 2, 0);
//					gl.glVertex3f(enginePosition[0] - 2, enginePosition[1] - 2, 0);
//					gl.glVertex3f(enginePosition[0] - 2, enginePosition[1] + 2, 0);
//				gl.glEnd();
			}
			gl.glEnd();
			

			gl.glDisable(GL3bc.GL_BLEND);
			gl.glEnable(GL3bc.GL_LIGHTING);

			gl.glPopMatrix();
		}
		
	}
}
