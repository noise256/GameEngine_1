package game;

import factionManager.Faction;
import factionManager.Player;
import game.section.ShipSection;
import game.ship.TestShip;
import game.subsystem.TestEngine;
import game.subsystem.TestTurret;
import gameManager.GameManager;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sectionManager.Section;
import utilityManager.MathBox;
import aiManager.AgentInputAttack;

public class PhysicsTest extends GameManager {
	public static void main(String[] args) {
		new Thread(new PhysicsTest()).run();
	}

	public PhysicsTest() {
		super();
		
		Faction goodGuys = new Faction();
		Faction badGuys = new Faction();

		Hashtable<String, Double> values = new Hashtable<String, Double>();
		values.put("mass", 50.0);
		values.put("positionX", 25000.0);
		values.put("positionY", 25000.0);
		values.put("orientation", 0.0);
		values.put("forceX", 0.0);
		values.put("forceY", 0.0);
		values.put("forceMagnitude", 0.0);
		values.put("turningForce", 0.0);
		values.put("velocityX", 0.0);
		values.put("velocityY", 0.0);
		values.put("turningVelocity", 0.0);
		values.put("maxVelocity", 1.0);
		values.put("maxTurningVelocity", 0.1);
		values.put("maxForce", 1.0);
		values.put("maxTurningForce", 1.0);

		ArrayList<Section> ship1Sections = new ArrayList<Section>();
		
		//front hull
		ShipSection ship1Section0 = new ShipSection(null, 0, new Vector2D(194, 38.5), "front_hull_left", "front_hull_left.png");
		ShipSection ship1Section1 = new ShipSection(null, 1, new Vector2D(194, -38.5), "front_hull_right", "front_hull_right.png");
		
		//tail hull
		ShipSection ship1Section2 = new ShipSection(null, 2, new Vector2D(-7.5, 37), "tail_hull_left", "tail_hull_left.png");
		ShipSection ship1Section3 = new ShipSection(null, 3, new Vector2D(-7.5, -37), "tail_hull_right", "tail_hull_right.png");
		
		//front guns
		ShipSection ship1Section10 = new ShipSection(null, 10, new Vector2D(173.5, 43.5), "front_gun_left", "front_gun_left.png");
		ShipSection ship1Section11 = new ShipSection(null, 11, new Vector2D(173.5, -43.5), "front_gun_right", "front_gun_right.png");
		
		//front armour
		ShipSection ship1Section4 = new ShipSection(null, 4, new Vector2D(186.5, 62.5), "front_armour_left", "front_armour_left.png");
		ShipSection ship1Section5 = new ShipSection(null, 5, new Vector2D(186.5, -62.5), "front_armour_right", "front_armour_right.png");
		
		//tail armour
		ShipSection ship1Section6 = new ShipSection(null, 6, new Vector2D(-93.5, 75), "tail_armour_left", "tail_armour_left.png");
		ShipSection ship1Section7 = new ShipSection(null, 7, new Vector2D(-93.5, -75), "tail_armour_right", "tail_armour_right.png");
		
		//tail engines
		ShipSection ship1Section8 = new ShipSection(null, 8, new Vector2D(-172, 53), "tail_engine_left", "tail_engine_left.png");
		ShipSection ship1Section9 = new ShipSection(null, 9, new Vector2D(-172, -53), "tail_engine_right", "tail_engine_right.png");
		
		ship1Sections.add(ship1Section0);
		ship1Sections.add(ship1Section1);
		
		ship1Sections.add(ship1Section2);
		ship1Sections.add(ship1Section3);
		
		ship1Sections.add(ship1Section10);
		ship1Sections.add(ship1Section11);
		
		ship1Sections.add(ship1Section4);
		ship1Sections.add(ship1Section5);
		
		ship1Sections.add(ship1Section6);
		ship1Sections.add(ship1Section7);
		
		ship1Sections.add(ship1Section8);
		ship1Sections.add(ship1Section9);

		
		ArrayList<ArrayList<Integer>> ship1AdjacencyList = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> ship1Section0Adjacency = new ArrayList<Integer>();
		ship1Section0Adjacency .add(1);
		ship1Section0Adjacency .add(4);
		
		ArrayList<Integer> ship1Section1Adjacency = new ArrayList<Integer>();
		ship1Section1Adjacency .add(0);
		ship1Section1Adjacency .add(5);
		
		ArrayList<Integer> ship1Section2Adjacency = new ArrayList<Integer>();
		ship1Section2Adjacency .add(3);
		ship1Section2Adjacency .add(4);
		ship1Section2Adjacency .add(6);
		
		ArrayList<Integer> ship1Section3Adjacency = new ArrayList<Integer>();
		ship1Section3Adjacency .add(2);
		ship1Section3Adjacency .add(5);
		ship1Section3Adjacency .add(7);
		
		ArrayList<Integer> ship1Section4Adjacency = new ArrayList<Integer>();
		ship1Section4Adjacency .add(0);
		ship1Section4Adjacency .add(2);
		ship1Section4Adjacency .add(5);
		ship1Section4Adjacency .add(10);
		
		ArrayList<Integer> ship1Section5Adjacency = new ArrayList<Integer>();
		ship1Section5Adjacency .add(1);
		ship1Section5Adjacency .add(3);
		ship1Section5Adjacency .add(4);
		ship1Section5Adjacency .add(11);
		
		ArrayList<Integer> ship1Section6Adjacency = new ArrayList<Integer>();
		ship1Section6Adjacency .add(2);
		ship1Section6Adjacency .add(7);
		ship1Section6Adjacency .add(8);
		
		ArrayList<Integer> ship1Section7Adjacency = new ArrayList<Integer>();
		ship1Section7Adjacency .add(3);
		ship1Section7Adjacency .add(6);
		ship1Section7Adjacency .add(9);
		
		ArrayList<Integer> ship1Section8Adjacency = new ArrayList<Integer>();
		ship1Section8Adjacency .add(6);
		
		ArrayList<Integer> ship1Section9Adjacency = new ArrayList<Integer>();
		ship1Section9Adjacency .add(7);
		
		ArrayList<Integer> ship1Section10Adjacency = new ArrayList<Integer>();
		ship1Section10Adjacency .add(4);
		
		ArrayList<Integer> ship1Section11Adjacency = new ArrayList<Integer>();
		ship1Section11Adjacency .add(5);
		
		ship1AdjacencyList.add(ship1Section0Adjacency);
		ship1AdjacencyList.add(ship1Section1Adjacency);
		ship1AdjacencyList.add(ship1Section2Adjacency);
		ship1AdjacencyList.add(ship1Section3Adjacency);
		ship1AdjacencyList.add(ship1Section4Adjacency);
		ship1AdjacencyList.add(ship1Section5Adjacency);
		ship1AdjacencyList.add(ship1Section6Adjacency);
		ship1AdjacencyList.add(ship1Section7Adjacency);
		ship1AdjacencyList.add(ship1Section8Adjacency);
		ship1AdjacencyList.add(ship1Section9Adjacency);
		ship1AdjacencyList.add(ship1Section10Adjacency);
		ship1AdjacencyList.add(ship1Section11Adjacency);
		
		TestShip testShip1 = new TestShip(values, ship1Sections, ship1AdjacencyList, goodGuys);
		
		TestTurret ship1TestTurret1 = new TestTurret(testShip1, ship1Section10, new Vector2D(0.0, 0.0), testShip1.getOrientation(), 0.1, 0.001, Math.PI*2);
		testShip1.addSubSystem(ship1TestTurret1);
		
		TestTurret ship1TestTurret2 = new TestTurret(testShip1, ship1Section11, new Vector2D(0.0, 0.0), testShip1.getOrientation(), 0.1, 0.001, Math.PI*2);
		testShip1.addSubSystem(ship1TestTurret2);
		
		TestEngine ship1TestEngine0 = new TestEngine(testShip1, ship1Section8, new Vector2D(0.0, 0.0), testShip1.getOrientation(), 1);
		testShip1.addSubSystem(ship1TestEngine0);
		
		TestEngine ship1TestEngine1 = new TestEngine(testShip1, ship1Section9, new Vector2D(0.0, 0.0), testShip1.getOrientation(), 1);
		testShip1.addSubSystem(ship1TestEngine1);
		
		for (Section section : testShip1.getSections()) {
			section.setParent(testShip1);
		}
		
		objectManager.addPhysicalObject(testShip1);
		testShip1.addObserver(objectManager);

		goodGuys.addFactionObject(testShip1);
		testShip1.setFaction(goodGuys);
		
		/**
		 * Create Enemy Ships.
		 */
		for (int i = 0; i < 1; i++) {
			values.put("positionX", Math.random() * 2000.0 * MathBox.nextSign() + 25000.0);
			values.put("positionY", Math.random() * 2000.0 * MathBox.nextSign() + 25000.0);
			values.put("orientation", 0.0);
			
			ArrayList<Section> ship2Sections = new ArrayList<Section>();
			
			//front hull
			ShipSection ship2Section0 = new ShipSection(null, 0, new Vector2D(194, 38.5), "front_hull_left", "front_hull_left.png");
			ShipSection ship2Section1 = new ShipSection(null, 1, new Vector2D(194, -38.5), "front_hull_right", "front_hull_right.png");
			
			//tail hull
			ShipSection ship2Section2 = new ShipSection(null, 2, new Vector2D(-7.5, 37), "tail_hull_left", "tail_hull_left.png");
			ShipSection ship2Section3 = new ShipSection(null, 3, new Vector2D(-7.5, -37), "tail_hull_right", "tail_hull_right.png");
			
			//front guns
			ShipSection ship2Section10 = new ShipSection(null, 10, new Vector2D(173.5, 43.5), "front_gun_left", "front_gun_left.png");
			ShipSection ship2Section11 = new ShipSection(null, 11, new Vector2D(173.5, -43.5), "front_gun_right", "front_gun_right.png");
			
			//front armour
			ShipSection ship2Section4 = new ShipSection(null, 4, new Vector2D(186.5, 62.5), "front_armour_left", "front_armour_left.png");
			ShipSection ship2Section5 = new ShipSection(null, 5, new Vector2D(186.5, -62.5), "front_armour_right", "front_armour_right.png");
			
			//tail armour
			ShipSection ship2Section6 = new ShipSection(null, 6, new Vector2D(-93.5, 75), "tail_armour_left", "tail_armour_left.png");
			ShipSection ship2Section7 = new ShipSection(null, 7, new Vector2D(-93.5, -75), "tail_armour_right", "tail_armour_right.png");
			
			//tail engines
			ShipSection ship2Section8 = new ShipSection(null, 8, new Vector2D(-172, 53), "tail_engine_left", "tail_engine_left.png");
			ShipSection ship2Section9 = new ShipSection(null, 9, new Vector2D(-172, -53), "tail_engine_right", "tail_engine_right.png");
			
			ship2Sections.add(ship2Section0);
			ship2Sections.add(ship2Section1);
			
			ship2Sections.add(ship2Section2);
			ship2Sections.add(ship2Section3);
			
			ship2Sections.add(ship2Section10);
			ship2Sections.add(ship2Section11);
			
			ship2Sections.add(ship2Section4);
			ship2Sections.add(ship2Section5);
			
			ship2Sections.add(ship2Section6);
			ship2Sections.add(ship2Section7);
			
			ship2Sections.add(ship2Section8);
			ship2Sections.add(ship2Section9);

			
			ArrayList<ArrayList<Integer>> ship2AdjacencyList = new ArrayList<ArrayList<Integer>>();

			ArrayList<Integer> ship2Section0Adjacency = new ArrayList<Integer>();
			ship2Section0Adjacency .add(1);
			ship2Section0Adjacency .add(4);
			
			ArrayList<Integer> ship2Section1Adjacency = new ArrayList<Integer>();
			ship2Section1Adjacency .add(0);
			ship2Section1Adjacency .add(5);
			
			ArrayList<Integer> ship2Section2Adjacency = new ArrayList<Integer>();
			ship2Section2Adjacency .add(3);
			ship2Section2Adjacency .add(4);
			ship2Section2Adjacency .add(6);
			
			ArrayList<Integer> ship2Section3Adjacency = new ArrayList<Integer>();
			ship2Section3Adjacency .add(2);
			ship2Section3Adjacency .add(5);
			ship2Section3Adjacency .add(7);
			
			ArrayList<Integer> ship2Section4Adjacency = new ArrayList<Integer>();
			ship2Section4Adjacency .add(0);
			ship2Section4Adjacency .add(2);
			ship2Section4Adjacency .add(5);
			ship2Section4Adjacency .add(10);
			
			ArrayList<Integer> ship2Section5Adjacency = new ArrayList<Integer>();
			ship2Section5Adjacency .add(1);
			ship2Section5Adjacency .add(3);
			ship2Section5Adjacency .add(4);
			ship2Section5Adjacency .add(11);
			
			ArrayList<Integer> ship2Section6Adjacency = new ArrayList<Integer>();
			ship2Section6Adjacency .add(2);
			ship2Section6Adjacency .add(7);
			ship2Section6Adjacency .add(8);
			
			ArrayList<Integer> ship2Section7Adjacency = new ArrayList<Integer>();
			ship2Section7Adjacency .add(3);
			ship2Section7Adjacency .add(6);
			ship2Section7Adjacency .add(9);
			
			ArrayList<Integer> ship2Section8Adjacency = new ArrayList<Integer>();
			ship2Section8Adjacency .add(6);
			
			ArrayList<Integer> ship2Section9Adjacency = new ArrayList<Integer>();
			ship2Section9Adjacency .add(7);
			
			ArrayList<Integer> ship2Section10Adjacency = new ArrayList<Integer>();
			ship2Section10Adjacency .add(4);
			
			ArrayList<Integer> ship2Section11Adjacency = new ArrayList<Integer>();
			ship2Section11Adjacency .add(5);
			
			ship2AdjacencyList.add(ship2Section0Adjacency);
			ship2AdjacencyList.add(ship2Section1Adjacency);
			ship2AdjacencyList.add(ship2Section2Adjacency);
			ship2AdjacencyList.add(ship2Section3Adjacency);
			ship2AdjacencyList.add(ship2Section4Adjacency);
			ship2AdjacencyList.add(ship2Section5Adjacency);
			ship2AdjacencyList.add(ship2Section6Adjacency);
			ship2AdjacencyList.add(ship2Section7Adjacency);
			ship2AdjacencyList.add(ship2Section8Adjacency);
			ship2AdjacencyList.add(ship2Section9Adjacency);
			ship2AdjacencyList.add(ship2Section10Adjacency);
			ship2AdjacencyList.add(ship2Section11Adjacency);
			
			TestShip testShip2 = new TestShip(values, ship2Sections, ship2AdjacencyList, goodGuys);
			
			TestTurret ship2TestTurret1 = new TestTurret(testShip2, ship2Section10, new Vector2D(0.0, 0.0), testShip2.getOrientation(), 0.1, 0.01, Math.PI*2);
			testShip2.addSubSystem(ship2TestTurret1);
			
			TestTurret ship2TestTurret2 = new TestTurret(testShip2, ship2Section11, new Vector2D(0.0, 0.0), testShip2.getOrientation(), 0.1, 0.01, Math.PI*2);
			testShip2.addSubSystem(ship2TestTurret2);
			
			TestEngine ship2TestEngine0 = new TestEngine(testShip2, ship2Section8, new Vector2D(0.0, 0.0), testShip2.getOrientation(), 1);
			testShip2.addSubSystem(ship2TestEngine0);
			
			TestEngine ship2TestEngine1 = new TestEngine(testShip2, ship2Section9, new Vector2D(0.0, 0.0), testShip2.getOrientation(), 1);
			testShip2.addSubSystem(ship2TestEngine1);
			
			for (Section section : testShip2.getSections()) {
				section.setParent(testShip2);
			}
			
			objectManager.addPhysicalObject(testShip2);
			testShip2.addObserver(objectManager);

			testShip2.addInput(new AgentInputAttack(testShip1, testShip1.getClosestSection(testShip2.getObjectPosition())));
			
			badGuys.addFactionObject(testShip2);
			testShip2.setFaction(badGuys);
		}
		
		//set up the UI
		objectManager.setPlayer(new Player(ObjectType.PLAYER, null, goodGuys));
//		InterfaceBox interfaceBox = new InterfaceBox(new Vector2D(Constants.viewWidth/2, 150/2), Constants.viewWidth, 150);
//		objectManager.addInterfaceObject(interfaceBox);
	}
}
