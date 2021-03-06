package physicsManager;

import java.util.Hashtable;

import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import collisionManager.Collidable;
import factionManager.Faction;

public abstract class PhysicalObject extends GameObject implements Collidable {
	protected Hashtable<String, Double> forces = new Hashtable<String, Double>();

	protected Vector2D objectPosition;
	protected Vector2D oldObjectPosition;

	protected double orientation;

	protected double mass;

	protected double maxVelocity;
	protected Vector2D velocityVec;

	protected Vector2D accelerationVec;

	protected Vector2D force;
	protected double forceMagnitude;
	protected double maxForce;

	protected double turningVelocity;
	protected double maxTurningVelocity;

	protected double turningAcceleration;

	protected double turningForce;
	protected double maxTurningForce;

	public PhysicalObject(ObjectType objectType, GameObject source, Hashtable<String, Double> values, Faction faction) {
		super(objectType, source, faction);

		objectPosition = new Vector2D(values.get("positionX"), values.get("positionY"));
		oldObjectPosition = new Vector2D(values.get("positionX"), values.get("positionY"));

		orientation = values.get("orientation");

		mass = values.get("mass");

		maxVelocity = values.get("maxVelocity");
		velocityVec = new Vector2D(values.get("velocityX"), values.get("velocityY"));

		accelerationVec = new Vector2D(0.0, 0.0);

		force = new Vector2D(values.get("forceX"), values.get("forceY"));
		forceMagnitude = values.get("forceMagnitude");
		maxForce = values.get("maxForce");

		turningVelocity = values.get("turningVelocity");
		maxTurningVelocity = values.get("maxTurningVelocity");

		turningAcceleration = 0.0;

		turningForce = values.get("turningForce");
		maxTurningForce = values.get("maxTurningForce");
	}

	// TODO add new variables
	// @Override
	// public String toString() {
	// String string = "";
	// string = string.concat("Physical Object ID: [" + super.toString() + "]");
	// string = string.concat(" Position = [" + position.getX() + ", " +
	// position.getY()) + "]";
	// string = string.concat(" Velocity = [" + velocityVec.getX() + ", " +
	// velocityVec.getY() + "]");
	// string = string.concat(" Acceleration = [" + accelerationVec.getX() +
	// ", " + accelerationVec.getY()) + "]";
	// string = string.concat("\nOrientation = [" + orientation + "]");
	// string = string.concat(" Turning Velocity = [" + turningVelocity + "]");
	// string = string.concat("Turning Acceleration = [" + turningAcceleration +
	// "]");
	// return string;
	// }

	public void removeForce(String name) {
		forces.remove(name);
	}

	public void setForce(String name, double value) {
		forces.put(name, value);
	}

	public Vector2D getObjectPosition() {
		return objectPosition;
	}

	public void setObjectPosition(Vector2D objectPosition) {
		setOldObjectPosition(this.objectPosition);
		this.objectPosition = objectPosition;
	}

	public Vector2D getOldObjectPosition() {
		return oldObjectPosition;
	}

	private void setOldObjectPosition(Vector2D oldObjectPosition) {
		this.oldObjectPosition = oldObjectPosition;
	}

	public Vector2D getVelocityVec() {
		return velocityVec;
	}

	public void setVelocityVec(Vector2D velocityVec) {
		this.velocityVec = velocityVec;
	}

	public Vector2D getAccelerationVec() {
		return accelerationVec;
	}

	public void setAccelerationVec(Vector2D accelerationVec) {
		this.accelerationVec = accelerationVec;
	}

	public Vector2D getForce() {
		return force;
	}

	public void setForce(Vector2D force) {
		this.force = force;
	}

	public double getForceMagnitude() {
		return forceMagnitude;
	}

	public void setForceMagnitude(double forceMagnitude) {
		this.forceMagnitude = forceMagnitude;
	}

	public double getMaxForce() {
		return maxForce;
	}

	public void setMaxForce(double maxForce) {
		this.maxForce = maxForce;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public double getTurningVelocity() {
		return turningVelocity;
	}

	public void setTurningVelocity(double turningVelocity) {
		this.turningVelocity = turningVelocity;
	}

	public double getMaxTurningVelocity() {
		return maxTurningVelocity;
	}

	public void setMaxTurningVelocity(double maxTurningVelocity) {
		this.maxTurningVelocity = maxTurningVelocity;
	}

	public double getTurningAcceleration() {
		return turningAcceleration;
	}

	public void setTurningAcceleration(double turningAcceleration) {
		this.turningAcceleration = turningAcceleration;
	}

	public double getTurningForce() {
		return turningForce;
	}

	public void setTurningForce(double turningForce) {
		this.turningForce = turningForce;
	}

	public double getMaxTurningForce() {
		return maxTurningForce;
	}

	public void setMaxTurningForce(double maxTurningForce) {
		this.maxTurningForce = maxTurningForce;
	}
	
	@Override
	public Vector2D getCollidablePosition() {
		return objectPosition;
	}
}
