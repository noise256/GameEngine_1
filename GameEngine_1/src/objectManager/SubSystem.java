package objectManager;

import brickManager.SystemBrick;

public abstract class SubSystem extends GameObject {
	private SystemBrick systemBrick;
	
	public SubSystem(GameObject source, SystemBrick systemBrick) {
		super(ObjectType.SUBSYSTEM, source);
	}
	
	public SystemBrick getSystemBrick() {
		return systemBrick;
	}
	
	public void setSystemBrick(SystemBrick systemBrick) {
		this.systemBrick = systemBrick;
	}
}
