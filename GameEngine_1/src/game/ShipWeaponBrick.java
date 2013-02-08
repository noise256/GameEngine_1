package game;

import java.util.ArrayList;

import objectManager.SubSystem;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import brickManager.SystemBrick;

public class ShipWeaponBrick extends SystemBrick {

	public ShipWeaponBrick(SubSystem subSystem, int index, int health, Vector2D position, float edgeLength, int numSegments) {
		super(subSystem, index, health, position, edgeLength, numSegments);
	}

	@Override
	public ArrayList<Float> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<double[]> getLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Float> getTextureCoords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		
	}
}
