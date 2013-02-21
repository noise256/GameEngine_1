package game.subsystem;

import objectManager.GameObject;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import brickManager.SystemBrick;

public abstract class Engine extends SubSystem {
	private double maxForce;
	private double force;
	
	protected CircularFifoBuffer positionBuffer = new CircularFifoBuffer(128);
	
	public Engine(GameObject source, SystemBrick systemBrick, Vector2D position, double orientation, double maxForce) {
		super(source, systemBrick, SubSystemType.ENGINE, position, orientation);
		this.maxForce = maxForce;
	}
	
	public double getEngineForce() {
		return force;
	}
	
	public void setForce(double force) {
		this.force = force > maxForce ? maxForce : force;
		this.force = force < 0 ? 0 : force;
	}
}
