package objectManager;

import java.util.ArrayList;
import java.util.Hashtable;

import sceneManager.EntityView;
import sceneManager.SceneNode;
import eventManager.Observable;
import eventManager.Observer;
import factionManager.Faction;

public abstract class GameObject implements EntityView, Observable<ObjectChangeEvent> {
	protected ArrayList<Observer<ObjectChangeEvent>> observers = new ArrayList<Observer<ObjectChangeEvent>>();
	
	protected GameObject source;
	
	protected ObjectType objectType;
	
	protected Faction faction;
	
	protected boolean alive = true;
	
	protected Hashtable<String, SceneNode> sceneNodes = new Hashtable<String, SceneNode>();

	public GameObject(ObjectType objectType, GameObject source) {
		this.objectType = objectType;
		this.source = source;
	}

	public abstract void update();

	@Override
	public void addObserver(Observer<ObjectChangeEvent> observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer<ObjectChangeEvent> observer) {
		observers.remove(observer);
	}

	@Override
	public void updateObservers(ObjectChangeEvent eventObject) {
		for (Observer<ObjectChangeEvent> observer : observers) {
			observer.update(eventObject);
		}
	}

	@Override
	public ArrayList<SceneNode> getView() {
		return new ArrayList<SceneNode>(sceneNodes.values());
	}

	public ObjectType getObjectType() {
		return objectType;
	}
	
	public GameObject getSource() {
		return source;
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	public Faction getFaction() {
		return faction;
	}
}
