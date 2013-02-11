package aiManager;

import inputManager.Selectable;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;

import brickManager.Brick;
import brickManager.BrickObject;

public abstract class Agent extends BrickObject implements Selectable {
	protected AgentInput currentOrder;
	
	protected ArrayList<AgentInput> inputQueue = new ArrayList<AgentInput>();
	protected boolean selected;
	
	public Agent(ObjectType objectType, GameObject source, Hashtable<String, Double> values, ArrayList<Brick> bricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		super(objectType, source, values, bricks, adjacencyList);
	}

	protected void attack(PhysicalObject target) {
		Vector2D dest = target.getPosition().subtract(position);
		
		//attempt to stay at appropriate range
		double weaponRange = 300.0; //TODO need to caculate this based on actual weapon characteristics
		
		if (dest.getNorm() < weaponRange) {
			//attempt to 'lead' target
			Vector2D targetTravelVector = null;
			try {
				double projectileVelocity = 2.5; //TODO need to calculate this based on actualy weapon characteristics
				targetTravelVector = target.getVelocityVec().normalize().scalarMultiply(target.getVelocityVec().getNorm() * dest.getNorm() / projectileVelocity);
			}
			catch (MathArithmeticException e) {
				targetTravelVector = new Vector2D(0.0, 0.0);
			}
			
			dest = dest.add(targetTravelVector);
		}
		
		//turn agent
		turnTo(dest);
		
		dest = dest.scalarMultiply(1 - weaponRange/dest.getNorm());
		
		double posAcc = maxForce/mass;
		double reqAcc = dest.getNorm() - velocityVec.getNorm() * mass;
		
		setForce(dest.subtract(velocityVec.scalarMultiply(mass)));
		if (Math.abs(reqAcc) > posAcc) {
			setForceMagnitude(maxForce);
		}
		else {
			setForceMagnitude(reqAcc);
		}
	}
	
	protected void follow(PhysicalObject object) {
		moveTo(object.getPosition());
	}
	
	protected void moveTo(Vector2D destination) {
		//curent relative destination vec
		Vector2D dest = getDestinationVec(destination).normalize();
		
		//current velocity vec
		Vector2D vel = null;
		try {
			vel = velocityVec.normalize();
		}
		catch (MathArithmeticException e) {
			vel = new Vector2D(0.0, 0.0);
		}
		
		//move vector - cancels current drifts
		Vector2D move = dest.add(dest.subtract(vel));
		
		//check to see if in range of destination
		if (getDestinationVec(destination).getNorm() > 50.0) {	
			turnTo(move);
			setForce(move);
			setForceMagnitude(maxForce);
		}
		else {
			turnTo(move);
			setForce(vel.negate());
			setForceMagnitude(maxForce);
		}
		
	}
	
	private Vector2D getDestinationVec(Vector2D destination) {
		return destination.subtract(position);
	}
	
//	public void stop() {
//		if (turnTo(velocityVec.negate())) {
//			double possibleAcceleration = maxForce/mass;
//			double requiredAcceleration = Math.sqrt(Math.pow(velocityVec.getX(), 2) + Math.pow(velocityVec.getY(), 2));
//			
//			if (possibleAcceleration <= requiredAcceleration) {
//				setForce(maxForce);
//			}
//			else {
//				setForce(mass * requiredAcceleration);
//			}
//		}
//	}
	
	public void turnTo(Vector2D vec) {
		Vector2D zeroVec = new Vector2D(1.0, 0.0);
		double angle = Math.acos(vec.dotProduct(zeroVec) / vec.getNorm() * zeroVec.getNorm());
		
		if (vec.getY() < 0) {
			angle = Math.PI * 2 - angle;
		}
		
		turnTo(angle);
	}
	
	//TODO if destination is above it can only turn one way and if it is bellow it can only turn another way
	//TODO what if object can't be turned to, i.e. maxTurningVelocity < relative radial velocity of location?
	private void turnTo(double angle) {
		double turningAngle = angle - orientation;
		
		if (turningAngle < -Math.PI) {
			turningAngle += Math.PI*2;
		}
		else if (turningAngle > Math.PI) {
			turningAngle -= Math.PI*2;
		}
		
		double posAcceleration = maxTurningForce/mass;
		double reqAcceleration = turningAngle - turningVelocity * mass;
		
		if (Math.abs(reqAcceleration) > posAcceleration) {
			setTurningForce(posAcceleration * (Math.abs(reqAcceleration)/reqAcceleration));
		}
		else {
			setTurningForce(reqAcceleration);
		}
	}
	
	public boolean stopTurning() {
		//set turning velocity and turning acceleration to 0
		//so v = u + a.. i.e. v = u + a | v is as close to 0 as possible
		double possibleAcceleration = maxTurningForce/mass;
		double requiredAcceleration = -turningVelocity;
		if (turningVelocity > 0.0) {
			if (possibleAcceleration < Math.abs(requiredAcceleration)) {
				setTurningForce(-maxTurningForce);
			}
			else {
				setTurningForce(mass * requiredAcceleration);
			}
			return false;
		}
		else if (turningVelocity < 0.0) {
			if (possibleAcceleration < Math.abs(requiredAcceleration)) {
				setTurningForce(maxTurningForce);
			}
			else {
				setTurningForce(mass * requiredAcceleration);
			}
			return false;
		}
		setTurningForce(0.0);
		return true;
	}
	
	//TODO should this be if abs(orientation - angle) < some value return true?
	public boolean compareOrientation(double angle, double error) {
		if (Math.abs(orientation) - Math.abs(angle) <= error) {
			return true;
		}
		return false;
	}
	
	public boolean compareOrientation(Vector2D vector, double error) {
		return compareOrientation(Math.atan2(vector.getY(), vector.getX()), error);
	}
	
	public void addInput(AgentInput input) {
		inputQueue.add(input);
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}
}
