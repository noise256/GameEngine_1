package factionManager;

import java.util.ArrayList;

public class FactionManager {
	private ArrayList<Faction> factions = new ArrayList<Faction>();
	
	public void tick() {
		for (Faction faction : factions) {
			faction.update();
		}
	}
}
