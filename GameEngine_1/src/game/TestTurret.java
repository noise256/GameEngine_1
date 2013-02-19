package game;

import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.EntityHashMap;
import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import utilityManager.MathBox;

import brickManager.SystemBrick;

public class TestTurret extends SubSystem {
	private double targetOrientation;
	
	private double fireCount;
	private double fireIncrement;
	private double lifeDecrement;
	private double maxRange;
	
	private PhysicalObject target;
	private ArrayList<TestProjectile> projectiles = new ArrayList<TestProjectile>();
	
	public TestTurret(GameObject source, SystemBrick systemBrick, Vector2D position, double orientation, double fireIncrement, double lifeDecrement) {
		super(source, systemBrick, SubSystemType.TURRET, position, orientation);
		this.source = source;
		this.fireIncrement = fireIncrement;
		this.lifeDecrement = lifeDecrement;

		this.maxRange = 5 * (1 / lifeDecrement);
	}

	@Override
	public void updateView() {
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		if (!systemBrick.isAlive()) {
			setAlive(false);
			return;
		}
		
		if (target == null) {
			return;
		}
		
		Vector2D dir = target.getPosition().subtract(position);
		Vector2D zeroVec = new Vector2D(1.0, 0.0);
		
		targetOrientation = Math.acos(dir.dotProduct(zeroVec) / dir.getNorm() * zeroVec.getNorm());; 
		
		if (dir.getY() < 0) {
			targetOrientation = Math.PI * 2 - targetOrientation;
		}
		
		if (fireCount <= 0.0 && activated) {
			fire();
			fireCount = 1.0;
		}
		else {
			fireCount -= fireIncrement;
		}
	}
	
	private void fire() {
		Hashtable<String, Double> projValues = new Hashtable<String, Double>();

		projValues.put("mass", 5.0);
		projValues.put("maxVelocity", 5.0);
		projValues.put("maxTurningVelocity", 0.01);
		projValues.put("maxForce", 0.5);
		projValues.put("maxTurningForce", 100.0);

//		projValues.put("forceX", Math.cos(orientation));
//		projValues.put("forceY", Math.sin(orientation));
		projValues.put("forceX", Math.cos(targetOrientation));
		projValues.put("forceY", Math.sin(targetOrientation));
		
		projValues.put("forceMagnitude", 1.0);

		projValues.put("turningForce", 0.0);
		projValues.put("velocityX", 0.0);
		projValues.put("velocityY", 0.0);
		projValues.put("turningVelocity", 0.0);

		projValues.put("damage", 1.0);

		projValues.put("positionX", position.getX() + MathBox.rotatePoint(systemBrick.getPosition(), orientation).getX());
		projValues.put("positionY", position.getY() + MathBox.rotatePoint(systemBrick.getPosition(), orientation).getY());
		projValues.put("orientation", orientation);

		TestProjectile newProjectile = new TestProjectile(source, projValues, lifeDecrement);
		newProjectile.addObserver(GameManager.getObjectManager());
		projectiles.add(newProjectile);
	}
	
	public void setTarget(PhysicalObject target) {
		this.target = target;
	}
	
	public double getMaxRange() {
		return maxRange;
	}
	
	public ArrayList<TestProjectile> getProjectiles() {
		return projectiles;
	}

	public void clearProjectiles() {
		projectiles.clear();
	}
	
	public boolean isValidTarget(PhysicalObject target) {
		if (!target.isAlive()) {
			return false;
		}
		else if (position.add(systemBrick.getPosition()).distance(target.getPosition()) > maxRange) {
			return false;
		}
		return true;
	}
}
