package game.subsystem;

import javax.media.opengl.GL3bc;

import objectManager.EntityHashMap;
import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;
import utilityManager.MathBox;
import brickManager.SystemBrick;

public class TestEngine extends Engine {
	
	public TestEngine(GameObject source, SystemBrick systemBrick, Vector2D position, double orientation, double maxForce) {
		super(source, systemBrick, position, orientation, maxForce);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			sceneNodes.put("root", new TestEngineSceneNode());
		}
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		float[] rotatedBrickPosition = MathBox.rotatePoint(new float[] {(float) systemBrick.getPosition().getX(),  (float) systemBrick.getPosition().getY()}, (float) orientation);
		positionBuffer.add(new Float[] {(float) position.getX() + rotatedBrickPosition[0], (float) position.getY() + rotatedBrickPosition[1]});
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
			for (int i = 0; i < positionBufferAsArray.length; i++) {
				Float[] enginePosition = (Float[]) positionBufferAsArray[i];
				float alpha = 1 - (float) (positionBufferAsArray.length - i) / (float) positionBufferAsArray.length;
				
				gl.glColor4f(1.0f, 1.0f, 1.0f, alpha);
				
				gl.glBegin(GL3bc.GL_QUADS);
					gl.glVertex3f(enginePosition[0] + 2, enginePosition[1] + 2, 0);
					gl.glVertex3f(enginePosition[0] + 2, enginePosition[1] - 2, 0);
					gl.glVertex3f(enginePosition[0] - 2, enginePosition[1] - 2, 0);
					gl.glVertex3f(enginePosition[0] - 2, enginePosition[1] + 2, 0);
				gl.glEnd();
			}
			

			gl.glDisable(GL3bc.GL_BLEND);
			gl.glEnable(GL3bc.GL_LIGHTING);

			gl.glPopMatrix();
		}
		
	}
}
