package game.subsystem;

import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import brickManager.SystemBrick;

public abstract class Weapon extends SubSystem {
	protected double fireCount;
	protected double fireIncrement;
	protected double lifeDecrement;

	protected Hashtable<String, Double> projectileValues = new Hashtable<String, Double>();
	
	private double maxRange;
	
	private ArrayList<TestProjectile> projectiles = new ArrayList<TestProjectile>();
	
	public Weapon(GameObject source, SystemBrick systemBrick, SubSystemType subSystemType, Vector2D position, double orientation, double fireIncrement, double lifeDecrement) {
		super(source, systemBrick, subSystemType, position, orientation);
		this.fireIncrement = fireIncrement;
		this.lifeDecrement = lifeDecrement;
		this.maxRange = 5 * (1 / lifeDecrement);
	}
	
	protected void fire() {
		TestProjectile newProjectile = new TestProjectile(source, projectileValues, lifeDecrement);
		newProjectile.addObserver(GameManager.getObjectManager());
		projectiles.add(newProjectile);
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
	
	public ArrayList<TestProjectile> getProjectiles() {
		return projectiles;
	}

	public void clearProjectiles() {
		projectiles.clear();
	}

	public double getMaxRange() {
		return maxRange;
	}
}