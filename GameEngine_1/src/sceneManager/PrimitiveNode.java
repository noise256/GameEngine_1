package sceneManager;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class PrimitiveNode extends SceneNode {
	protected Vector2D position;
	protected double orientation;

	protected float[] colour;

	public PrimitiveNode(SceneNodeType type, Vector2D position, double orientation, float[] colour) {
		super(type);
		this.position = position;
		this.orientation = orientation;
		this.colour = colour;
	}

	public void setColour(float[] colour) {
		this.colour = colour;
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

	public Vector2D getPosition() {
		return position;
	}

	public float[] getColour() {
		return colour;
	}
}
