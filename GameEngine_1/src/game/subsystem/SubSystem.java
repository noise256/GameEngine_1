package game.subsystem;

import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sectionManager.Section;
import utilityManager.MathBox;

public abstract class SubSystem extends GameObject {
	public enum SubSystemType {
		GUN, TURRET, ENGINE;
	}

	protected SubSystemType subSystemType;
	protected Section systemSection;

	protected boolean activated;

	protected Vector2D systemPosition;
	protected double systemOrientation;

	public SubSystem(GameObject source, Section systemSection, SubSystemType subSystemType, Vector2D systemPosition, double systemOrientation) {
		super(ObjectType.SUBSYSTEM, source, null);
		this.systemSection = systemSection;
		this.subSystemType = subSystemType;
		this.systemPosition = systemPosition;
		this.systemOrientation = systemOrientation;
	}

	public Vector2D getAbsolutePosition() {
		Vector2D absolutePosition = MathBox.rotatePoint(systemPosition.add(systemSection.getSectionPosition()), systemSection.getParent().getOrientation()).add(systemSection.getParent().getObjectPosition());
		return absolutePosition;
	}
	
	public Section getSystemSection() {
		return systemSection;
	}

	public void setSystemSection(Section systemSection) {
		this.systemSection = systemSection;
	}

	public SubSystemType getSubSystemType() {
		return subSystemType;
	}

	public Vector2D getSystemPosition() {
		return systemPosition;
	}

	public void setSystemPosition(Vector2D systemPosition) {
		this.systemPosition = systemPosition;
	}

	public double getSystemOrientation() {
		return systemOrientation;
	}

	public void setSystemOrientation(double systemOrientation) {
		this.systemOrientation = systemOrientation;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
