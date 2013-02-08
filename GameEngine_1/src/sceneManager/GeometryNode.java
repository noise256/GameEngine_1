package sceneManager;
import java.nio.FloatBuffer;

import javax.media.opengl.GL3bc;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class GeometryNode extends SceneNode {
	protected Vector2D position;
	protected double orientation;
	
	protected FloatBuffer vertexBuffer;

	public GeometryNode(Vector2D position, double orientation, FloatBuffer vertexBuffer) {
		super(SceneNodeType.GEOMETRY_NODE);
		this.position = position;
		this.orientation = orientation;
		this.vertexBuffer = vertexBuffer;
	}
	
	public void display(GL3bc gl) {
		gl.glPushMatrix();
		
		//set material properties
		gl.glMaterialfv(GL3bc.GL_FRONT_AND_BACK, GL3bc.GL_SPECULAR, new float[] {1.0f, 1.0f, 1.0f}, 0);
		gl.glMaterialfv(GL3bc.GL_FRONT_AND_BACK, GL3bc.GL_SHININESS, new float[] {128}, 0);
		
		//translate and rotate
		gl.glTranslatef((float) position.getX(), (float) position.getY(), 0);
		gl.glRotatef((float) (orientation * 180/Math.PI), 0, 0, 1);
		
		//set colour
		gl.glColor3f(0.25f, 0.25f, 0.25f);
		
		gl.glBegin(GL3bc.GL_TRIANGLES);
		for (int i = 0; i < vertexBuffer.capacity()/3; i++) {
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glVertex3f(vertexBuffer.get(), vertexBuffer.get(), vertexBuffer.get());
		}
		gl.glEnd();
		
		vertexBuffer.rewind();
		gl.glPopMatrix();
	}

	@Override
	public void update(GL3bc gl) {
		display(gl);
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
	public void setVertexBuffer(FloatBuffer vertexBuffer) {
		this.vertexBuffer = vertexBuffer;
	}
	
	@Override
	public String toString() {
		return "Geometry Node: " + position;
	}
}
