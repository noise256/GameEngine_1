package physicsManager;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PhysicsEngine {
	public static void update(PhysicalObject object) {
		updateOrientation(object);
		updatePosition(object);
	}
	
	private static void updateOrientation(PhysicalObject object) {
		//set acceleration using f = ma
		object.setTurningAcceleration(object.getTurningForce()/object.getMass());
		
		//set velocity using v = u + at
		object.setTurningVelocity(object.getTurningVelocity() + object.getTurningAcceleration());
		
		//clamp velocity using v = min(velocity, maxVelocity)
		if (Math.abs(object.getTurningVelocity()) > Math.abs(object.getMaxTurningVelocity())) {
			if (object.getTurningVelocity() < 0) {
				object.setTurningVelocity(object.getMaxTurningVelocity() * -1);
			}
			else {
				object.setTurningVelocity(object.getMaxTurningVelocity());
			}
		}
		
		//set new orientation using oi+1 = oi + v
		object.setOrientation(object.getOrientation() + object.getTurningVelocity());
		
		//constrain to region 0 <= x <= Math.PI*2
		if (object.getOrientation() > Math.PI*2) {
			object.setOrientation(object.getOrientation() - Math.PI*2);
		}
		else if (object.getOrientation() < 0) {
			object.setOrientation(Math.PI*2 - Math.abs(object.getOrientation()));
		}
	}
	
	private static void updatePosition(PhysicalObject object) {
		Vector2D force;
		try {
			force = object.getForce().normalize();
		}
		catch (MathArithmeticException e) {
			force = new Vector2D(0.0, 0.0);
		}
		
		//set acceleration using f = ma
		object.setAccelerationVec(force.scalarMultiply(object.getForceMagnitude()/object.getMass()));
		
		try {
			//set velocity using v = u + at
			object.setVelocityVec(object.getVelocityVec().add(object.getAccelerationVec()));
			
			//clamp velocity using v = norm(v) * min(maxVelocity, velocity)
			double clamp = Math.min(object.getVelocityVec().getNorm(), object.getMaxVelocity());
			object.setVelocityVec(object.getVelocityVec().normalize().scalarMultiply(clamp));
		}
		catch (MathArithmeticException e) {
			object.setVelocityVec(new Vector2D(0, 0));
		}
		
		//set new position using pi+1 = pi + v
		object.setPosition(object.getPosition().add(object.getVelocityVec()));
	}
}
