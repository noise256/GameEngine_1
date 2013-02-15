package interfaceManager;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import objectManager.GameObject;
import objectManager.ObjectType;

public abstract class InterfaceObject extends GameObject {
	protected boolean activated;
	
	protected Vector2D position;
	
	protected float width;
	protected float height;
	
	public InterfaceObject(Vector2D position, float width, float height) {
		super(ObjectType.INTERFACE_OBJECT, null);
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public boolean isActivated() {
		return activated;
	}
}
