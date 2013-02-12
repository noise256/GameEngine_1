package sceneManager;

import javax.media.opengl.GL3bc;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class CircleNode extends PrimitiveNode {
	private float radius;

	public CircleNode(Vector2D position, float radius, float[] colour) {
		super(SceneNodeType.CIRCLE_NODE, position, 0.0, colour);
		this.radius = radius;
	}

	public void display(GL3bc gl) {
		gl.glPushMatrix();
		gl.glTranslatef((float) position.getX(), (float) position.getY(), 0.0f);
		gl.glBegin(GL3bc.GL_LINE_LOOP);
		for (int i = 0; i < 360; i++) {
			float degInRad = (float) (i * Math.PI / 180);
			gl.glColor3f(colour[0], colour[1], colour[2]);
			gl.glVertex3f((float) (Math.cos(degInRad) * radius), (float) (Math.sin(degInRad) * radius), 0);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}

	@Override
	public void update(GL3bc gl) {
		display(gl);
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}
