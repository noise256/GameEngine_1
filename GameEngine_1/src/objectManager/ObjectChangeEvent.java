package objectManager;

import java.util.ArrayList;

import physicsManager.PhysicalObject;

import eventManager.UpdateEvent;
import eventManager.UpdateEventType;

public class ObjectChangeEvent extends UpdateEvent {
	public enum ObjectChangeType {
		CREATION, REMOVAL;
	}
	
	private static final long serialVersionUID = -2119655314984156683L;

	private ObjectChangeType changeType;
	
	private ArrayList<PhysicalObject> changedObjects = new ArrayList<PhysicalObject>();
	
	public ObjectChangeEvent(Object source, ObjectChangeType changeType, ArrayList<PhysicalObject> changedObjects) {
		super(source, UpdateEventType.OBJECT_CHANGE);
		this.changeType = changeType;
		this.changedObjects = changedObjects;
	}

	public ObjectChangeType getChangeType() {
		return changeType;
	}
	
	public ArrayList<PhysicalObject> getChangedObjects() {
		return changedObjects;
	}
}
