package game.subsystem;

import objectManager.EntityHashMap;
import objectManager.GameObject;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import sectionManager.Section;

public class TestTurret extends Weapon {
	private double targetOrientation;
	
	private PhysicalObject objectTarget;
	private Section sectionTarget;
	
	public TestTurret(GameObject source, Section systemSection, Vector2D systemPosition, double orientation, double fireIncrement, double lifeDecrement, double firingArc) {
		super(source, systemSection, SubSystemType.TURRET, systemPosition, orientation, fireIncrement, lifeDecrement, firingArc);
		
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
	public void updateView() {
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		if (!systemSection.isAlive()) {
			setAlive(false);
			return;
		}
		
		if (objectTarget == null) {
			return;
		}
		
		Vector2D absolutePosition = getAbsolutePosition();
		Vector2D dir = sectionTarget.getAbsolutePosition().subtract(absolutePosition);
		Vector2D zeroVec = new Vector2D(1.0, 0.0);
		
		targetOrientation = Math.acos(dir.dotProduct(zeroVec) / dir.getNorm() * zeroVec.getNorm());; 
		
		if (dir.getY() < 0) {
			targetOrientation = Math.PI * 2 - targetOrientation;
		}
		
		if (fireCount <= 0.0 && activated) {
			projectileValues.put("forceX", Math.cos(targetOrientation));
			projectileValues.put("forceY", Math.sin(targetOrientation));
			projectileValues.put("positionX", absolutePosition.getX());
			projectileValues.put("positionY", absolutePosition.getY());
			
			fire();
			fireCount = 1.0;
		}
		else {
			fireCount -= fireIncrement;
		}
	}
	
	public void setObjectTarget(PhysicalObject objectTarget) {
		this.objectTarget = objectTarget;
	}
	
	public void setSectionTarget(Section sectionTarget) {
		this.sectionTarget = sectionTarget;
	}
}
