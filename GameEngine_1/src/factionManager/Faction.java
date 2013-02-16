package factionManager;

import java.util.ArrayList;

import objectManager.GameObject;

/**
 * Represents a faction: lists all controlled objects? enemies? allies? behaviour, etc.
 * 
 * @author noise
 *
 */
public class Faction {
	//TODO list enemy factions
	
	//TODO list friendly factions
	
	ArrayList<GameObject> factionObjects = new ArrayList<GameObject>();
	
	public void update() {
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		for (GameObject factionObject : factionObjects) {
			if (!factionObject.isAlive()) {
				toRemove.add(factionObject);
			}
		}
		
		for (GameObject factionObject : toRemove) {
			factionObjects.remove(factionObject);
		}
 	}
	
	public void addFactionObject(GameObject factionObject) {
		factionObjects.add(factionObject);
	}
	
	public boolean containsFactionObject(GameObject factionObject) {
		if (factionObjects.contains(factionObject)) {
			return true;
		}
		return false;
	}
}
