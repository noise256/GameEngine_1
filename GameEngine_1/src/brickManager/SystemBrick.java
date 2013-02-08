package brickManager;

import java.util.ArrayList;

import objectManager.SubSystem;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class SystemBrick extends Brick {
	private SubSystem subSystem;
	
	public SystemBrick(SubSystem subSystem, int index, int health, Vector2D position, float edgeLength, int numSegments) {
		super(BrickType.SYSTEM, index, health, position, edgeLength, numSegments);
		this.subSystem = subSystem;
	}
	
	public SubSystem getSubSystem() {
		return subSystem;
	}
	
	@Override
	public ArrayList<Float> getNormals(float radius) {
		return null;
	}
}
