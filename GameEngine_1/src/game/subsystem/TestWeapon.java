package game.subsystem;

import objectManager.EntityHashMap;
import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import utilityManager.MathBox;
import brickManager.SystemBrick;

public class TestWeapon extends Weapon {
	public TestWeapon(GameObject source, SystemBrick systemBrick, Vector2D position, double orientation, double fireIncrement, double lifeDecrement) {
		super(source, systemBrick, SubSystemType.GUN, position, orientation, fireIncrement, lifeDecrement);

		projectileValues.put("mass", 5.0);
		projectileValues.put("maxVelocity", 5.0);
		projectileValues.put("maxTurningVelocity", 0.01);
		projectileValues.put("maxForce", 0.5);
		projectileValues.put("maxTurningForce", 100.0);
		projectileValues.put("forceMagnitude", 1.0);
		projectileValues.put("turningForce", 0.0);
		projectileValues.put("velocityX", 0.0);
		projectileValues.put("velocityY", 0.0);
		projectileValues.put("turningVelocity", 0.0);
		projectileValues.put("damage", 1.0);
		projectileValues.put("orientation", orientation);
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		if (!systemBrick.isAlive()) {
			alive = false;
			return;
		}

		if (fireCount <= 0.0 && activated) {
			projectileValues.put("forceX", Math.cos(orientation));
			projectileValues.put("forceY", Math.sin(orientation));
			projectileValues.put("positionX", position.getX() + MathBox.rotatePoint(systemBrick.getPosition(), orientation).getX());
			projectileValues.put("positionY", position.getY() + MathBox.rotatePoint(systemBrick.getPosition(), orientation).getY());
			
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
}
