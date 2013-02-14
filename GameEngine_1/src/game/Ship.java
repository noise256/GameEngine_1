package game;

import game.SubSystem.SubSystemType;
import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import objectManager.ObjectChangeEvent;
import objectManager.ObjectType;
import objectManager.ObjectChangeEvent.ObjectChangeType;
import physicsManager.PhysicalObject;
import utilityManager.MathBox;

import brickManager.Brick;
import brickManager.Brick.BrickType;

import aiManager.Agent;
import aiManager.AgentInputAttack;
import aiManager.AgentInputMove;
import aiManager.AgentInput.AgentInputType;

public abstract class Ship extends Agent {
	private ArrayList<SubSystem> subSystems = new ArrayList<SubSystem>();

	public Ship(Hashtable<String, Double> values, ArrayList<Brick> bricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		super(ObjectType.AGENT, null, values, bricks, adjacencyList);
	}

	@Override
	public void update() {
		ArrayList<PhysicalObject> newGameObjects = new ArrayList<PhysicalObject>();

		// check alive state
		if (getBrickFromIndex(0) == null) {
			setAlive(false);
		}

		// update brick states, remove dead bricks, generate fragments
		updateBricks();

		// update behaviour
		if (!inputQueue.isEmpty()) {
			currentOrder = inputQueue.remove(0);
		}
		if (currentOrder != null) {
			if (currentOrder.getAgentInputType() == AgentInputType.MOVE) {
				moveTo(((AgentInputMove) currentOrder).getDestination());
			}
			else if (currentOrder.getAgentInputType() == AgentInputType.ATTACK) {
				AgentInputAttack currentAttackOrder = (AgentInputAttack) currentOrder;

				PhysicalObject target = currentAttackOrder.getTarget();

				if (target.isAlive()) {
					attack(((AgentInputAttack) currentOrder).getTarget());
				}
				else {
					currentOrder = null;
				}
			}
		}
		else {
			stop();
			stopTurning();
		}

		// update subsystem states
		ArrayList<SubSystem> subSystemsToRemove = new ArrayList<SubSystem>();
		for (SubSystem system : subSystems) {
			if (!system.getSystemBrick().isAlive()) {
				subSystemsToRemove.add(system);
				continue;
			}

			system.setPosition(position);
			system.setOrientation(orientation);

			if (system.getSubSystemType() == SubSystemType.weapon && currentOrder != null) {
				if (currentOrder.getAgentInputType() == AgentInputType.ATTACK
						&& ((TestWeapon) system).isValidTarget(((AgentInputAttack) currentOrder).getTarget())) {
					system.setActivated(true);
				}
				else {
					system.setActivated(false);
				}
			}
			else {
				system.setActivated(false);
			}

			system.update();

			if (system.getSubSystemType() == SubSystemType.weapon) {
				newGameObjects.addAll(((TestWeapon) system).getProjectiles());
				((TestWeapon) system).clearProjectiles();
			}
		}

		if (!newGameObjects.isEmpty()) {
			updateObservers(new ObjectChangeEvent(this, ObjectChangeType.CREATION, newGameObjects));
			newGameObjects.clear();
		}

		if (!subSystemsToRemove.isEmpty()) {
			subSystems.removeAll(subSystemsToRemove);
		}
	}

	@Override
	public boolean canCollide() {
		if (alive) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSubSystem(SubSystem subSystem) {
		subSystems.add(subSystem);
	}

	@Override
	public double getRadius() {
		if (bricks.isEmpty()) {
			return 0.0;
		}

		Brick max = bricks.get(0);
		for (Brick brick : bricks) {
			if (!brick.isExploding() && brick.isAlive() && brick.getPosition().getNorm() > max.getPosition().getNorm()) {
				max = brick;
			}
		}

		if (max.getBrickType() == BrickType.SQUARE) {
			return max.getPosition().getNorm() + Math.sqrt(max.getEdgeLength() * 2 + max.getEdgeLength() * 2);
		}
		else if (max.getBrickType() == BrickType.TRIANGLE) {
			return max.getPosition().getNorm();
		}
		else {
			return -1;
		}
	}

	@Override
	protected PhysicalObject createFragment(ArrayList<Brick> fragmentBricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		// get average position of fragment bricks
		Vector2D averagePosition = new Vector2D(0.0, 0.0);
		for (Brick brick : fragmentBricks) {
			averagePosition = averagePosition.add(brick.getPosition());
		}
		averagePosition = averagePosition.scalarMultiply(1.0 / fragmentBricks.size());

		// adjust position of bricks to be relative to new average position
		for (Brick brick : fragmentBricks) {
			brick.setPosition(brick.getPosition().subtract(averagePosition));
		}

		// update brick indices
		for (int i = 0; i < fragmentBricks.size(); i++) {
			// change adjacency list
			for (int j = 0; j < adjacencyList.size(); j++) {
				if (adjacencyList.get(j).contains(new Integer(fragmentBricks.get(i).getIndex()))) {
					adjacencyList.get(j).remove(new Integer(fragmentBricks.get(i).getIndex()));
					adjacencyList.get(j).add(new Integer(i));
				}
			}
			// change brick index
			fragmentBricks.get(i).setIndex(i);
		}

		// set averagePosition of bricks to an absolute position
		averagePosition = MathBox.rotatePoint(averagePosition, orientation).add(position);

		// generate new values
		Hashtable<String, Double> values = new Hashtable<String, Double>();
		values.put("mass", 5000.0);
		values.put("positionX", averagePosition.getX());
		values.put("positionY", averagePosition.getY());
		values.put("orientation", orientation);
		values.put("forceX", Math.random() * MathBox.nextSign());
		values.put("forceY", Math.random() * MathBox.nextSign());
		values.put("forceMagnitude", Math.random() * 100.0);
		values.put("turningForce", Math.random() * 0.1 * MathBox.nextSign());
		values.put("velocityX", 0.0);
		values.put("velocityY", 0.0);
		values.put("turningVelocity", 0.0);
		values.put("maxVelocity", 0.5);
		values.put("maxTurningVelocity", 0.1);
		values.put("maxForce", 1.0);
		values.put("maxTurningForce", 1.0);

		TestShipFragment fragment = new TestShipFragment(this, values, fragmentBricks, adjacencyList);
		fragment.addObserver(GameManager.getObjectManager());
		return fragment;
	}
}
