package game;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import brickManager.SystemBrick;

import objectManager.GameObject;
import objectManager.ObjectType;

public abstract class SubSystem extends GameObject {
	public enum SubSystemType {
		weapon;
	}
	
	protected SubSystemType subSystemType;
	protected SystemBrick systemBrick;
	
	protected boolean activated;
	
	protected Vector2D position;
	protected double orientation;
	
	public SubSystem(GameObject source, SystemBrick systemBrick, SubSystemType subSystemType, Vector2D position, double orientation) {
		super(ObjectType.SUBSYSTEM, source);
		this.systemBrick = systemBrick;
		this.subSystemType = subSystemType;
	}
	
	public SystemBrick getSystemBrick() {
		return systemBrick;
	}
	
	public void setSystemBrick(SystemBrick systemBrick) {
		this.systemBrick = systemBrick;
	}
	
	public SubSystemType getSubSystemType() {
		return subSystemType;
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

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
