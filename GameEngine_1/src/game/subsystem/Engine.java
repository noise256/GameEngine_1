package game.subsystem;

import objectManager.GameObject;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sectionManager.Section;

public abstract class Engine extends SubSystem {
	private double maxForce;
	private double force;
	
	protected CircularFifoBuffer positionBuffer = new CircularFifoBuffer(128);
	
	public Engine(GameObject source, Section systemSection, Vector2D systemPosition, double orientation, double maxForce) {
		super(source, systemSection, SubSystemType.ENGINE, systemPosition, orientation);
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
