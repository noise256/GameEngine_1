package game.subsystem;

import objectManager.EntityHashMap;
import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sectionManager.Section;

public class TestWeapon extends Weapon {
	public TestWeapon(GameObject source, Section systemSection, Vector2D systemPosition, double systemOrientation, double fireIncrement, double lifeDecrement, double firingArc, float projectileWidth, float projectileHeight) {
		super(source, systemSection, SubSystemType.GUN, systemPosition, systemOrientation, fireIncrement, lifeDecrement, firingArc, projectileWidth, projectileHeight);

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
		projectileValues.put("orientation", systemOrientation);
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		if (!systemSection.isAlive()) {
			alive = false;
			return;
		}

		if (fireCount <= 0.0 && activated) {
			Vector2D absolutePosition = getAbsolutePosition();
			
			projectileValues.put("forceX", Math.cos(systemOrientation));
			projectileValues.put("forceY", Math.sin(systemOrientation));
			projectileValues.put("positionX", absolutePosition.getX());
			projectileValues.put("positionY", absolutePosition.getY());
			
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
