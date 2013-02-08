package game;
import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.GameObject;
import objectManager.SubSystem;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import brickManager.SystemBrick;

public class TestWeapon extends SubSystem {
	private Vector2D position;
	private Vector2D velocity;
	private double orientation;
	
	private double fireCount;
	private double fireIncrement;

	private double lifeDecrement;
	
	private ArrayList<TestProjectile> projectiles = new ArrayList<TestProjectile>();
	
	public TestWeapon(GameObject source, SystemBrick systemBrick, Vector2D position, double orientation, double fireIncrement, double lifeDecrement) {
		super(source, systemBrick);
		this.source = source;
		this.position = position;
		this.orientation = orientation;
		this.fireIncrement = fireIncrement;
		this.lifeDecrement = lifeDecrement;
	}

	private void fire() {
		Hashtable<String, Double> projValues = new Hashtable<String, Double>();
		
		projValues.put("mass", 5.0);
		projValues.put("maxVelocity", 5.0);
		projValues.put("maxTurningVelocity", 0.01);
		projValues.put("maxForce", 0.5);
		projValues.put("maxTurningForce", 100.0);
		
		projValues.put("forceX", Math.cos(orientation));
		projValues.put("forceY", Math.sin(orientation));
		projValues.put("forceMagnitude", 1.0);
		
		projValues.put("turningForce", 0.0);
		projValues.put("velocityX", velocity.getX());
		projValues.put("velocityY", velocity.getY());
		projValues.put("turningVelocity", 0.0);
		
		projValues.put("damage", 1.0);
		
		projValues.put("positionX", position.getX());
		projValues.put("positionY", position.getY());
		projValues.put("orientation", orientation);
		
		TestProjectile newProjectile = new TestProjectile(source, projValues, lifeDecrement);
		newProjectile.addObserver(GameManager.getObjectManager()); //TODO don't really like this static call to get the objectmanager
		projectiles.add(newProjectile);
		
	}

	public void update() {
		if (fireCount <= 0.0) {
			fire();
			fireCount = 1.0;
		}
		else {
			fireCount -= fireIncrement;
		}
	}
	
	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<TestProjectile> getProjectiles() {
		return projectiles;
	}

	public void clearProjectiles() {
		projectiles.clear();
	}
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}
}
