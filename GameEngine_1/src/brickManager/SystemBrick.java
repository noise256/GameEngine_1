package brickManager;


import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class SystemBrick extends Brick {
	public SystemBrick(int index, int health, Vector2D position, float edgeLength, int numSegments) {
		super(BrickType.SYSTEM, index, health, position, edgeLength, numSegments);
	}
}
