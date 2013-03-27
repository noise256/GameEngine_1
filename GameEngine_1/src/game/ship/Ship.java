package game.ship;

import factionManager.Faction;
import game.subsystem.SubSystem;
import game.subsystem.SubSystem.SubSystemType;
import game.subsystem.TestTurret;
import game.subsystem.TestWeapon;
import game.subsystem.Weapon;
import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.EntityHashMap;
import objectManager.ObjectChangeEvent;
import objectManager.ObjectChangeEvent.ObjectChangeType;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import sectionManager.Section;
import textureManager.TextureLoader;
import utilityManager.MathBox;
import aiManager.Agent;
import aiManager.AgentInput.AgentInputType;
import aiManager.AgentInputAttack;
import aiManager.AgentInputMove;

public abstract class Ship extends Agent {
	protected ArrayList<SubSystem> subSystems = new ArrayList<SubSystem>();

	public Ship(Hashtable<String, Double> values, ArrayList<Section> sections, ArrayList<ArrayList<Integer>> adjacencyList, Faction faction) {
		super(ObjectType.AGENT, null, values, sections, adjacencyList, faction);
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		ArrayList<PhysicalObject> newGameObjects = new ArrayList<PhysicalObject>();

		// check alive state
		if (getSectionFromIndex(0) == null) {
			setAlive(false);
		}

		// update brick states, remove dead sections, generate fragments
		updateSections();

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

				PhysicalObject objectTarget = currentAttackOrder.getObjectTarget();
				Section sectionTarget = currentAttackOrder.getSectionTarget();
				
				if (objectTarget != null) {
					if (objectTarget.isAlive() && !objectTarget.getFaction().equals(faction)) {
						attack(((AgentInputAttack) currentOrder).getObjectTarget(), sectionTarget, 1 / 0.005 * 5); //TODO get range from where?
					}
					else {
						currentOrder = null;
					}
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
			if (!system.getSystemSection().isAlive()) {
				subSystemsToRemove.add(system);
				continue;
			}

			system.setSystemOrientation(orientation);

			if (currentOrder != null) {
				if (system.getSubSystemType() == SubSystemType.GUN) {
					if (currentOrder.getAgentInputType() == AgentInputType.ATTACK && ((Weapon) system).isValidTarget(((AgentInputAttack) currentOrder).getObjectTarget())) {
						system.setActivated(true);
					}
					else {
						system.setActivated(false);
					}
				}
				else if (system.getSubSystemType() == SubSystemType.TURRET) {
					if (currentOrder.getAgentInputType() == AgentInputType.ATTACK && ((Weapon) system).isValidTarget(((AgentInputAttack) currentOrder).getObjectTarget())) {
						system.setActivated(true);
						((TestTurret) system).setObjectTarget(((AgentInputAttack) currentOrder).getObjectTarget());
						((TestTurret) system).setSectionTarget(((AgentInputAttack) currentOrder).getSectionTarget());
					}
					else {
						system.setActivated(false);
					}
				}
			}
			else {
				system.setActivated(false);
			}

			system.update(entityHashMap);

			if (system.getSubSystemType() == SubSystemType.GUN ) {
				newGameObjects.addAll(((TestWeapon) system).getProjectiles());
				((TestWeapon) system).clearProjectiles();
			}
			else if (system.getSubSystemType() == SubSystemType.TURRET) {
				newGameObjects.addAll(((TestTurret) system).getProjectiles());
				((TestTurret) system).clearProjectiles();
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
		if (sections.isEmpty()) {
			return 0.0;
		}

		Section max = sections.get(0);
		for (Section section : sections) {
			if (!section.isExploding() && section.isAlive() && section.getSectionPosition().getNorm() > max.getSectionPosition().getNorm()) {
				max = section;
			}
		}

		return Math.max(Math.abs(max.getSectionPosition().getX()), Math.abs(max.getSectionPosition().getY())) + 
			   Math.max(TextureLoader.getTexture(max.getSectionName()).getWidth(), TextureLoader.getTexture(max.getSectionName()).getHeight())/2;
	}

	@Override
	protected PhysicalObject createFragment(ArrayList<Section> fragmentSections, ArrayList<ArrayList<Integer>> adjacencyList) {
		// get average position of fragment sections
		Vector2D averagePosition = new Vector2D(0.0, 0.0);
		for (Section section : fragmentSections) {
			averagePosition = averagePosition.add(section.getSectionPosition());
		}
		averagePosition = averagePosition.scalarMultiply(1.0 / fragmentSections.size());

		// adjust position of sections to be relative to new average position
		for (Section section : fragmentSections) {
			section.setSectionPosition(section.getSectionPosition().subtract(averagePosition));
		}

		// update brick indices
		for (int i = 0; i < fragmentSections.size(); i++) { //TODO BUG HERE!!!!!!!!!!!!!!!!!!!!!!
			// change adjacency list
			for (int j = 0; j < adjacencyList.size(); j++) {
				if (adjacencyList.get(j).contains(new Integer(fragmentSections.get(i).getIndex()))) {
					adjacencyList.get(j).remove(new Integer(fragmentSections.get(i).getIndex()));
					adjacencyList.get(j).add(new Integer(i));
				}
			}
			// change brick index
			fragmentSections.get(i).setIndex(i);
		}

		// set averagePosition of sections to an absolute position
		averagePosition = MathBox.rotatePoint(averagePosition, orientation).add(objectPosition);

		// generate new values
		Hashtable<String, Double> values = new Hashtable<String, Double>();
		values.put("mass", 5000.0);
		values.put("positionX", averagePosition.getX());
		values.put("positionY", averagePosition.getY());
		values.put("orientation", orientation);
		values.put("forceX", Math.random() * MathBox.nextSign());
		values.put("forceY", Math.random() * MathBox.nextSign());
		values.put("forceMagnitude", Math.random() * 20.0);
		values.put("turningForce", Math.random() * 0.05 * MathBox.nextSign());
		values.put("velocityX", 0.0);
		values.put("velocityY", 0.0);
		values.put("turningVelocity", 0.0);
		values.put("maxVelocity", 0.5);
		values.put("maxTurningVelocity", 0.1);
		values.put("maxForce", 1.0);
		values.put("maxTurningForce", 1.0);

		TestShipFragment fragment = new TestShipFragment(this, values, fragmentSections, adjacencyList, faction);
		fragment.addObserver(GameManager.getObjectManager());
		return fragment;
	}
}
