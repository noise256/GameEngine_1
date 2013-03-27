package game.subsystem;

import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import sectionManager.Section;
import utilityManager.MathBox;

public abstract class Weapon extends SubSystem {
	protected double fireCount;
	protected double fireIncrement;
	protected double lifeDecrement;
	
	protected float projectileWidth;
	protected float projectileHeight;
	
	protected boolean burst = true;
	protected int burstNum = 10;
	protected double burstInterval = 0.01;
	protected double burstCount;
	
	protected Hashtable<String, Double> projectileValues = new Hashtable<String, Double>();
	
	private double maxRange;
	
	private double firingArc;
	
	private ArrayList<TestProjectile> projectiles = new ArrayList<TestProjectile>();
	
	public Weapon(GameObject source, Section systemSection, SubSystemType subSystemType, Vector2D systemPosition, double orientation, double fireIncrement, double lifeDecrement, double firingArc, float projectileWidth, float projectileHeight) {
		super(source, systemSection, subSystemType, systemPosition, orientation);
		this.fireIncrement = fireIncrement;
		this.lifeDecrement = lifeDecrement;
		this.maxRange = 5 * (1 / lifeDecrement);
		this.firingArc = firingArc;
		this.projectileWidth = projectileWidth;
		this.projectileHeight = projectileHeight;
	}
	
	protected void fire(int num) {
		for (int i = 0; i < num; i++) {
			TestProjectile newProjectile = new TestProjectile(source, projectileValues, lifeDecrement, projectileWidth, projectileHeight);
			newProjectile.addObserver(GameManager.getObjectManager());
			projectiles.add(newProjectile);
		}
	}
	
	public boolean isValidTarget(PhysicalObject target) {
		if (!target.isAlive()) {
			return false;
		}
		else if (!isInRange(target)) {
			return false;
		}
		else if (!isInFiringArc(target)) {
			return false;
		}
		return true;
	}
	
	private boolean isInRange(PhysicalObject target) {
		return getAbsolutePosition().distance(target.getObjectPosition()) > maxRange ? false : true;
	}
	
	private boolean isInFiringArc(PhysicalObject target) {
		double angleToTarget = MathBox.getAngleBetweenVectors(target.getObjectPosition().subtract(getAbsolutePosition()), MathBox.angleToVector(systemOrientation));

		return Math.abs(angleToTarget) <= firingArc ? true : false;
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
