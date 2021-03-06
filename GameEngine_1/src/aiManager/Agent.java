package aiManager;

import factionManager.Faction;
import inputManager.Selectable;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import sectionManager.Section;
import sectionManager.SectionObject;

public abstract class Agent extends SectionObject implements Selectable {
	protected AgentInput currentOrder;

	protected ArrayList<AgentInput> inputQueue = new ArrayList<AgentInput>();
	protected boolean selected;

	public Agent(ObjectType objectType, GameObject source, Hashtable<String, Double> values, ArrayList<Section> sections, ArrayList<ArrayList<Integer>> adjacencyList, Faction faction) {
		super(objectType, source, values, sections, adjacencyList, faction);
	}

	protected void attack(PhysicalObject objectTarget, Section sectionTarget, double weaponRange) {
		Vector2D dest = objectTarget.getObjectPosition().add(sectionTarget.getSectionPosition()).subtract(objectPosition);

		//attempt to stay at max range
		if (dest.getNorm() < weaponRange) {
			// attempt to 'lead' target
			Vector2D targetTravelVector = null;
			try {
				double projectileVelocity = 5; // TODO need to calculate this
													// based on actual weapon
													// characteristics
				targetTravelVector = objectTarget.getVelocityVec().normalize()
						.scalarMultiply(objectTarget.getVelocityVec().getNorm() * dest.getNorm() / projectileVelocity);
			}
			catch (MathArithmeticException e) {
				targetTravelVector = new Vector2D(0.0, 0.0);
			}

			dest = dest.add(targetTravelVector);
		}

		// turn agent
		turnTo(dest);
		
		if (dest.getNorm() > weaponRange) {
			//if outside of range: move towards target
			dest = dest.scalarMultiply(1 - weaponRange / dest.getNorm());
		}
		else {
			//if inside of range: stay still
			dest = new Vector2D(0.0, 0.0);
		}

		double posAcc = maxForce / mass;
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
		moveTo(object.getObjectPosition());
	}

	protected void moveTo(Vector2D destination) {
		// curent relative destination vec
		Vector2D dest = null;
		try {
			dest = destination.subtract(objectPosition).normalize();
		}
		catch (MathArithmeticException e) {
			dest = new Vector2D(0.0, 0.0);
		}

		// current velocity vec
		Vector2D vel = null;
		try {
			vel = velocityVec.normalize();
		}
		catch (MathArithmeticException e) {
			vel = new Vector2D(0.0, 0.0);
		}

		// move vector - cancels current drifts
		Vector2D move = dest.add(dest.subtract(vel));

		// check to see if in range of destination
		if (destination.subtract(objectPosition).getNorm() > 50.0) {
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

//	private Vector2D getDestinationVec(Vector2D destination) {
//		return destination.subtract(objectPosition);
//	}

	public void stop() {
		Vector2D dest = new Vector2D(0.0, 0.0);

		double posAcc = maxForce / mass;
		double reqAcc = dest.getNorm() - velocityVec.getNorm();

		setForce(dest.subtract(velocityVec));
		if (Math.abs(reqAcc) > posAcc) {
			setForceMagnitude(maxForce);
		}
		else {
			setForceMagnitude(reqAcc);
		}
	}

	public void turnTo(Vector2D vec) {
		Vector2D zeroVec = new Vector2D(1.0, 0.0);
		double angle = Math.acos(vec.dotProduct(zeroVec) / vec.getNorm() * zeroVec.getNorm());

		if (vec.getY() < 0) {
			angle = Math.PI * 2 - angle;
		}

		turnTo(angle);
	}

	// TODO what if object can't be turned to, i.e. maxTurningVelocity <
	// relative radial velocity of location?
	private void turnTo(double angle) {
		double turningAngle = angle - orientation;

		if (turningAngle < -Math.PI) {
			turningAngle += Math.PI * 2;
		}
		else if (turningAngle > Math.PI) {
			turningAngle -= Math.PI * 2;
		}

		double posAcceleration = maxTurningForce / mass;
		double reqAcceleration = turningAngle - turningVelocity * mass;

		if (Math.abs(reqAcceleration) > posAcceleration) {
			setTurningForce(posAcceleration * (Math.abs(reqAcceleration) / reqAcceleration));
		}
		else {
			setTurningForce(reqAcceleration);
		}
	}

	public boolean stopTurning() {
		// set turning velocity and turning acceleration to 0
		// so v = u + a.. i.e. v = u + a | v is as close to 0 as possible
		double possibleAcceleration = maxTurningForce / mass;
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

	// TODO should this be if abs(orientation - angle) < some value return true?
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
