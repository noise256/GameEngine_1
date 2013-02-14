package interfaceManager;

import objectManager.GameObject;
import objectManager.ObjectType;

public abstract class InterfaceObject extends GameObject {
	public InterfaceObject(ObjectType objectType, GameObject source) {
		super(ObjectType.INTERFACE_OBJECT, source);
	}
}
