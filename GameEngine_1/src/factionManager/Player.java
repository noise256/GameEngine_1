package factionManager;

import objectManager.GameObject;
import objectManager.ObjectType;

/**
 * The player needs to represented. For example many visual variables might depend on the players fleet, e.g. radar strength etc.
 * 
 * @author noise
 *
 */
public class Player extends GameObject {
	private Faction faction;
	
	public Player(ObjectType objectType, GameObject source) {
		super(objectType, source);
	}

	@Override
	public void updateView() {
	}

	@Override
	public void update() {
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	public Faction getFaction() {
		return faction;
	}
}
