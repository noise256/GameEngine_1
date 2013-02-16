package objectManager;

import graphicsManager.Constants;
import inputManager.ExtendedMouseEvent;
import interfaceManager.InterfaceObject;

import java.util.ArrayList;

import objectManager.ObjectChangeEvent.ObjectChangeType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import physicsManager.PhysicsEngine;
import sceneManager.SceneNode;
import aiManager.Agent;
import aiManager.AgentInput;
import aiManager.AgentInputAttack;
import aiManager.AgentInputMove;
import collisionManager.CollisionManager;

import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

import eventManager.Observable;
import eventManager.Observer;
import eventManager.UpdateEventType;

//TODO move handleInput out of this library and into implementation project
public class ObjectManager implements Observable<ObjectEvent>, Observer<ObjectChangeEvent> {
	/**
	 * A collection of observers of this ObjectManager.
	 */
	private ArrayList<Observer<ObjectEvent>> observers = new ArrayList<Observer<ObjectEvent>>();

	private ArrayList<InterfaceObject> interfaceObjects = new ArrayList<InterfaceObject>();
	
	private ArrayList<InterfaceObject> interfaceObjectsToAdd = new ArrayList<InterfaceObject>();
	
	/**
	 * Collection of physical objects.
	 */
	private ArrayList<PhysicalObject> physicalObjects = new ArrayList<PhysicalObject>();

	/**
	 * Collection of game objects to add during the current tick.
	 */
	private ArrayList<PhysicalObject> objectsToAdd = new ArrayList<PhysicalObject>();

	/**
	 * Collection of game objects to remove during the current tick.
	 */
	private ArrayList<PhysicalObject> objectsToRemove = new ArrayList<PhysicalObject>();

	/**
	 * Collection of selected game objects.
	 */
	private ArrayList<Agent> selectedAgents = new ArrayList<Agent>();

	/**
	 * Map of the locations of all game objects.
	 */
	private EntityHashMap entityHashMap = new EntityHashMap(Constants.entityMapWidth, Constants.entityMapHeight);

	/**
	 * Increments a time step for each object in the game.
	 */
	public void tick() {
		/**
		 * Iterate through current objects.
		 */
		for (PhysicalObject physicalObject : physicalObjects) {
			physicalObject.update(entityHashMap);

			PhysicsEngine.update(physicalObject);

			entityHashMap.moveEntity(physicalObject);

			CollisionManager.checkCollisions(physicalObject, entityHashMap);

			if (!physicalObject.isAlive()) {
				objectsToRemove.add(physicalObject);
			}
		}

		/**
		 * Add new objects.
		 */
		for (PhysicalObject physicalObject : objectsToAdd) {
			physicalObjects.add(physicalObject);
			entityHashMap.addEntity(physicalObject, physicalObject.getPosition().getX(), physicalObject.getPosition().getY());
		}
		objectsToAdd.clear();

		/**
		 * Add new interface objects.
		 */
		for (InterfaceObject interfaceObject : interfaceObjectsToAdd) {
			interfaceObjects.add(interfaceObject);
			interfaceObject.setActivated(true);
		}
		interfaceObjectsToAdd.clear();
		
		/**
		 * Remove new objects.
		 */
		for (PhysicalObject physicalObject : objectsToRemove) {
			physicalObjects.remove(physicalObject);
			try {
				entityHashMap.removeEntity(physicalObject, physicalObject.getPosition().getX(), physicalObject.getPosition().getY());
			}
			catch (EntityHashMapException e) {
				e.printStackTrace();
			}
		}
		objectsToRemove.clear();

		updateObservers(getObjectDisplayUpdateEvent());
		updateObservers(getInterfaceDisplayUpdateEvent());
	}

	public ObjectEvent getObjectDisplayUpdateEvent() {
		ArrayList<SceneNode> objectNodes = new ArrayList<SceneNode>();

		for (GameObject gameObject : physicalObjects) {
			gameObject.updateView();
			objectNodes.addAll(gameObject.getView());
		}
		
		return new ObjectEvent(this, UpdateEventType.OBJECT_DISPLAY, objectNodes);
	}

	public ObjectEvent getInterfaceDisplayUpdateEvent() {
		ArrayList<SceneNode> interfaceNodes = new ArrayList<SceneNode>();
		
		for (InterfaceObject interfaceObject : interfaceObjects) {
			interfaceObject.updateView();
			interfaceNodes.addAll(interfaceObject.getView());
		}
		
		return new ObjectEvent(this, UpdateEventType.INTERFACE_DISPLAY, interfaceNodes);
	}
	
	/**
	 * Add a GameObject to be managed.
	 * 
	 * @param gameObject
	 *            The GameObject to added.
	 */
	public void addPhysicalObject(PhysicalObject physicalObject) {
		objectsToAdd.add(physicalObject);
	}

	public void addInterfaceObject(InterfaceObject interfaceObject) {
		interfaceObjectsToAdd.add(interfaceObject);
	}
	
	/**
	 * Returns the map of object locations.
	 * 
	 * @return The map of object locations.
	 */
	public EntityHashMap getEntityHashMap() {
		return entityHashMap;
	}

	@Override
	public void addObserver(Observer<ObjectEvent> observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer<ObjectEvent> observer) {
		observers.remove(observer);
	}

	@Override
	public void updateObservers(ObjectEvent eventObject) {
		for (Observer<ObjectEvent> observer : observers) {
			observer.update(eventObject);
		}
	}

	public void handleInput(InputEvent inputEvent) {
		if (inputEvent instanceof ExtendedMouseEvent) {
			handleInput((ExtendedMouseEvent) inputEvent);
		}
		else if (inputEvent instanceof KeyEvent) {
			handleInput((KeyEvent) inputEvent);
		}
	}

	private void handleInput(ExtendedMouseEvent mouseEvent) {
		if (mouseEvent.getEventType() == MouseEvent.EVENT_MOUSE_RELEASED) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
				if (!mouseEvent.isShiftDown()) {
					for (Agent agent : selectedAgents) {
						agent.setSelected(false);
					}
					selectedAgents.clear();
				}

				// check for selection
				PhysicalObject closest = getClosestPhysicalObject(mouseEvent.getPosition());

				if (closest != null) {
					if (closest.getObjectType() == ObjectType.AGENT) {
						((Agent) closest).setSelected(true);
						selectedAgents.add((Agent) closest);
					}
				}
			}
			else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
				AgentInput agentInput = null;
				PhysicalObject closest = getClosestPhysicalObject(mouseEvent.getPosition());

				if (closest != null && !selectedAgents.contains(closest)) {
					agentInput = new AgentInputAttack(closest);
				}
				else {
					agentInput = new AgentInputMove(new Vector2D(mouseEvent.getPosition().getX(), mouseEvent.getPosition().getY()));
				}

				for (Agent agent : selectedAgents) {
					agent.addInput(agentInput);
				}
			}
		}
	}

	private void handleInput(KeyEvent keyEvent) {

	}

	private PhysicalObject getClosestPhysicalObject(Vector2D position) {
		// ArrayList<Agent> agentsInRange =
		// entityHashMap.getEntities(ObjectType.AGENT, position.getX()-25,
		// position.getX()+25, position.getY()-25, position.getY()+25);
		//
		// Agent closest = null;
		// if (!agentsInRange.isEmpty()) {
		// closest = agentsInRange.get(0);
		// for (Agent agent : agentsInRange) {
		// if (agent.getPosition().distance(position) <
		// closest.getPosition().distance(position)) {
		// closest = agent;
		// }
		// }
		// }

		// return closest;

		return CollisionManager.checkPointCollisions(position, entityHashMap);
	}

	@Override
	public void update(ObjectChangeEvent event) {
		if (event.getChangeType() == ObjectChangeType.CREATION) {
			objectsToAdd.addAll(event.getChangedObjects());
		}
		else if (event.getChangeType() == ObjectChangeType.REMOVAL) {
			objectsToRemove.addAll(event.getChangedObjects());
		}
	}
}
