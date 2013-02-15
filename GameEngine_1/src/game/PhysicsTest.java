package game;

import gameManager.GameManager;

import interfaceManager.InterfaceBox;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Hashtable;

import modelManager.ModelLoader;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import utilityManager.MathBox;
import aiManager.AgentInputAttack;
import brickManager.Brick;
import brickManager.SystemBrick;
import brickManager.TriangleBrick.BrickOrientation;

import com.jogamp.common.nio.Buffers;

public class PhysicsTest extends GameManager {
	public static void main(String[] args) {
		new Thread(new PhysicsTest()).run();
	}

	public PhysicsTest() {
		super();

		loadBuffers();

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

		ArrayList<Brick> bricks1 = new ArrayList<Brick>();

		// row1
		bricks1.add(new ShipSquareBrick(0, new Vector2D(0.0, 0.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(1, new Vector2D(0.0, 10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(2, new Vector2D(0.0, -10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(3, new Vector2D(0.0, 20.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(4, new Vector2D(0.0, -20.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(5, new Vector2D(0.0, 30.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(6, new Vector2D(0.0, -30.0), 10.0f, 1));

		// row2
		bricks1.add(new ShipSquareBrick(7, new Vector2D(-10.0, 0.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(8, new Vector2D(-10.0, 10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(9, new Vector2D(-10.0, -10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(10, new Vector2D(-10.0, 20.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(11, new Vector2D(-10.0, -20.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(12, new Vector2D(-10.0, 30.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(13, new Vector2D(-10.0, -30.0), 10.0f, 1));

		// row3
		bricks1.add(new ShipSquareBrick(14, new Vector2D(-20.0, 0.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(15, new Vector2D(-20.0, 10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(16, new Vector2D(-20.0, -10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(17, new Vector2D(-20.0, 30.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(18, new Vector2D(-20.0, -30.0), 10.0f, 1));

		// row4
		bricks1.add(new ShipSquareBrick(19, new Vector2D(-30.0, 0.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(20, new Vector2D(-30.0, 10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(21, new Vector2D(-30.0, -10.0), 10.0f, 1));
		bricks1.add(new ShipTriangleBrick(22, new Vector2D(-30.0, 30.0), BrickOrientation.ONE, 10.0f, 1));
		bricks1.add(new ShipTriangleBrick(23, new Vector2D(-30.0, -30.0), BrickOrientation.ZERO, 10.0f, 1));

		// row5
		bricks1.add(new ShipTriangleBrick(24, new Vector2D(-40.0, 10.0), BrickOrientation.ONE, 10.0f, 1));
		bricks1.add(new ShipTriangleBrick(25, new Vector2D(-40.0, -10.0), BrickOrientation.ZERO, 10.0f, 1));

		// row6
		bricks1.add(new ShipSquareBrick(26, new Vector2D(10.0, 0.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(27, new Vector2D(10.0, 10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(28, new Vector2D(10.0, -10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(29, new Vector2D(10.0, 30.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(30, new Vector2D(10.0, -30.0), 10.0f, 1));

		// row7
		bricks1.add(new ShipSquareBrick(31, new Vector2D(20.0, 0.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(32, new Vector2D(20.0, 10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(33, new Vector2D(20.0, -10.0), 10.0f, 1));
		bricks1.add(new ShipTriangleBrick(34, new Vector2D(20.0, 30.0), BrickOrientation.TWO, 10.0f, 1));
		bricks1.add(new ShipTriangleBrick(35, new Vector2D(20.0, -30.0), BrickOrientation.THREE, 10.0f, 1));

		// row8
		bricks1.add(new ShipSquareBrick(36, new Vector2D(30.0, 0.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(37, new Vector2D(30.0, 10.0), 10.0f, 1));
		bricks1.add(new ShipSquareBrick(38, new Vector2D(30.0, -10.0), 10.0f, 1));

		// row9
		bricks1.add(new ShipTriangleBrick(39, new Vector2D(40.0, 10.0), BrickOrientation.TWO, 10.0f, 1));
		bricks1.add(new ShipTriangleBrick(40, new Vector2D(40.0, -10.0), BrickOrientation.THREE, 10.0f, 1));

		// weapon brick : row10
		SystemBrick systemBrick1 = new ShipSystemBrick(41, new Vector2D(40.0, 0.0), 10.0f, 1);
		bricks1.add(systemBrick1);

		ArrayList<ArrayList<Integer>> adjacencyList1 = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> adjacencyBrick0 = new ArrayList<Integer>();
		adjacencyBrick0.add(1);
		adjacencyBrick0.add(2);
		adjacencyBrick0.add(7);
		adjacencyBrick0.add(26);

		ArrayList<Integer> adjacencyBrick1 = new ArrayList<Integer>();
		adjacencyBrick1.add(0);
		adjacencyBrick1.add(3);
		adjacencyBrick1.add(8);
		adjacencyBrick1.add(27);

		ArrayList<Integer> adjacencyBrick2 = new ArrayList<Integer>();
		adjacencyBrick2.add(0);
		adjacencyBrick2.add(4);
		adjacencyBrick2.add(9);
		adjacencyBrick2.add(28);

		ArrayList<Integer> adjacencyBrick3 = new ArrayList<Integer>();
		adjacencyBrick3.add(1);
		adjacencyBrick3.add(5);
		adjacencyBrick3.add(10);

		ArrayList<Integer> adjacencyBrick4 = new ArrayList<Integer>();
		adjacencyBrick4.add(2);
		adjacencyBrick4.add(6);
		adjacencyBrick4.add(11);

		ArrayList<Integer> adjacencyBrick5 = new ArrayList<Integer>();
		adjacencyBrick5.add(3);
		adjacencyBrick5.add(12);
		adjacencyBrick5.add(29);

		ArrayList<Integer> adjacencyBrick6 = new ArrayList<Integer>();
		adjacencyBrick6.add(4);
		adjacencyBrick6.add(13);
		adjacencyBrick6.add(30);

		ArrayList<Integer> adjacencyBrick7 = new ArrayList<Integer>();
		adjacencyBrick7.add(0);
		adjacencyBrick7.add(8);
		adjacencyBrick7.add(9);
		adjacencyBrick7.add(14);

		ArrayList<Integer> adjacencyBrick8 = new ArrayList<Integer>();
		adjacencyBrick8.add(1);
		adjacencyBrick8.add(7);
		adjacencyBrick8.add(10);
		adjacencyBrick8.add(15);

		ArrayList<Integer> adjacencyBrick9 = new ArrayList<Integer>();
		adjacencyBrick9.add(2);
		adjacencyBrick9.add(7);
		adjacencyBrick9.add(11);
		adjacencyBrick9.add(16);

		ArrayList<Integer> adjacencyBrick10 = new ArrayList<Integer>();
		adjacencyBrick10.add(3);
		adjacencyBrick10.add(8);
		adjacencyBrick10.add(12);

		ArrayList<Integer> adjacencyBrick11 = new ArrayList<Integer>();
		adjacencyBrick11.add(4);
		adjacencyBrick11.add(9);
		adjacencyBrick11.add(13);

		ArrayList<Integer> adjacencyBrick12 = new ArrayList<Integer>();
		adjacencyBrick12.add(5);
		adjacencyBrick12.add(10);
		adjacencyBrick12.add(17);

		ArrayList<Integer> adjacencyBrick13 = new ArrayList<Integer>();
		adjacencyBrick13.add(6);
		adjacencyBrick13.add(11);
		adjacencyBrick13.add(18);

		ArrayList<Integer> adjacencyBrick14 = new ArrayList<Integer>();
		adjacencyBrick14.add(7);
		adjacencyBrick14.add(15);
		adjacencyBrick14.add(16);
		adjacencyBrick14.add(19);

		ArrayList<Integer> adjacencyBrick15 = new ArrayList<Integer>();
		adjacencyBrick15.add(8);
		adjacencyBrick15.add(14);
		adjacencyBrick15.add(20);

		ArrayList<Integer> adjacencyBrick16 = new ArrayList<Integer>();
		adjacencyBrick16.add(9);
		adjacencyBrick16.add(14);
		adjacencyBrick16.add(21);

		ArrayList<Integer> adjacencyBrick17 = new ArrayList<Integer>();
		adjacencyBrick17.add(12);
		adjacencyBrick17.add(22);

		ArrayList<Integer> adjacencyBrick18 = new ArrayList<Integer>();
		adjacencyBrick18.add(13);
		adjacencyBrick18.add(23);

		ArrayList<Integer> adjacencyBrick19 = new ArrayList<Integer>();
		adjacencyBrick19.add(14);
		adjacencyBrick19.add(20);
		adjacencyBrick19.add(21);

		ArrayList<Integer> adjacencyBrick20 = new ArrayList<Integer>();
		adjacencyBrick20.add(15);
		adjacencyBrick20.add(19);
		adjacencyBrick20.add(24);

		ArrayList<Integer> adjacencyBrick21 = new ArrayList<Integer>();
		adjacencyBrick21.add(16);
		adjacencyBrick21.add(19);
		adjacencyBrick21.add(25);

		ArrayList<Integer> adjacencyBrick22 = new ArrayList<Integer>();
		adjacencyBrick22.add(17);

		ArrayList<Integer> adjacencyBrick23 = new ArrayList<Integer>();
		adjacencyBrick23.add(18);

		ArrayList<Integer> adjacencyBrick24 = new ArrayList<Integer>();
		adjacencyBrick24.add(20);

		ArrayList<Integer> adjacencyBrick25 = new ArrayList<Integer>();
		adjacencyBrick25.add(21);

		ArrayList<Integer> adjacencyBrick26 = new ArrayList<Integer>();
		adjacencyBrick26.add(0);
		adjacencyBrick26.add(27);
		adjacencyBrick26.add(28);
		adjacencyBrick26.add(31);

		ArrayList<Integer> adjacencyBrick27 = new ArrayList<Integer>();
		adjacencyBrick27.add(1);
		adjacencyBrick27.add(26);
		adjacencyBrick27.add(32);

		ArrayList<Integer> adjacencyBrick28 = new ArrayList<Integer>();
		adjacencyBrick28.add(2);
		adjacencyBrick28.add(26);
		adjacencyBrick28.add(33);

		ArrayList<Integer> adjacencyBrick29 = new ArrayList<Integer>();
		adjacencyBrick29.add(5);
		adjacencyBrick29.add(34);

		ArrayList<Integer> adjacencyBrick30 = new ArrayList<Integer>();
		adjacencyBrick30.add(6);
		adjacencyBrick30.add(35);

		ArrayList<Integer> adjacencyBrick31 = new ArrayList<Integer>();
		adjacencyBrick31.add(26);
		adjacencyBrick31.add(32);
		adjacencyBrick31.add(33);
		adjacencyBrick31.add(36);

		ArrayList<Integer> adjacencyBrick32 = new ArrayList<Integer>();
		adjacencyBrick32.add(27);
		adjacencyBrick32.add(31);
		adjacencyBrick32.add(37);

		ArrayList<Integer> adjacencyBrick33 = new ArrayList<Integer>();
		adjacencyBrick33.add(28);
		adjacencyBrick33.add(31);
		adjacencyBrick33.add(38);

		ArrayList<Integer> adjacencyBrick34 = new ArrayList<Integer>();
		adjacencyBrick34.add(29);

		ArrayList<Integer> adjacencyBrick35 = new ArrayList<Integer>();
		adjacencyBrick35.add(30);

		ArrayList<Integer> adjacencyBrick36 = new ArrayList<Integer>();
		adjacencyBrick36.add(31);
		adjacencyBrick36.add(37);
		adjacencyBrick36.add(38);
		adjacencyBrick36.add(41);

		ArrayList<Integer> adjacencyBrick37 = new ArrayList<Integer>();
		adjacencyBrick37.add(32);
		adjacencyBrick37.add(36);
		adjacencyBrick37.add(39);

		ArrayList<Integer> adjacencyBrick38 = new ArrayList<Integer>();
		adjacencyBrick38.add(33);
		adjacencyBrick38.add(36);
		adjacencyBrick38.add(40);

		ArrayList<Integer> adjacencyBrick39 = new ArrayList<Integer>();
		adjacencyBrick39.add(37);
		adjacencyBrick39.add(41);

		ArrayList<Integer> adjacencyBrick40 = new ArrayList<Integer>();
		adjacencyBrick40.add(38);
		adjacencyBrick40.add(41);

		ArrayList<Integer> adjacencyBrick41 = new ArrayList<Integer>();
		adjacencyBrick41.add(36);
		adjacencyBrick41.add(39);
		adjacencyBrick41.add(40);

		adjacencyList1.add(adjacencyBrick0);
		adjacencyList1.add(adjacencyBrick1);
		adjacencyList1.add(adjacencyBrick2);
		adjacencyList1.add(adjacencyBrick3);
		adjacencyList1.add(adjacencyBrick4);
		adjacencyList1.add(adjacencyBrick5);
		adjacencyList1.add(adjacencyBrick6);
		adjacencyList1.add(adjacencyBrick7);
		adjacencyList1.add(adjacencyBrick8);
		adjacencyList1.add(adjacencyBrick9);
		adjacencyList1.add(adjacencyBrick10);
		adjacencyList1.add(adjacencyBrick11);
		adjacencyList1.add(adjacencyBrick12);
		adjacencyList1.add(adjacencyBrick13);
		adjacencyList1.add(adjacencyBrick14);
		adjacencyList1.add(adjacencyBrick15);
		adjacencyList1.add(adjacencyBrick16);
		adjacencyList1.add(adjacencyBrick17);
		adjacencyList1.add(adjacencyBrick18);
		adjacencyList1.add(adjacencyBrick19);
		adjacencyList1.add(adjacencyBrick20);
		adjacencyList1.add(adjacencyBrick21);
		adjacencyList1.add(adjacencyBrick22);
		adjacencyList1.add(adjacencyBrick23);
		adjacencyList1.add(adjacencyBrick24);
		adjacencyList1.add(adjacencyBrick25);
		adjacencyList1.add(adjacencyBrick26);
		adjacencyList1.add(adjacencyBrick27);
		adjacencyList1.add(adjacencyBrick28);
		adjacencyList1.add(adjacencyBrick29);
		adjacencyList1.add(adjacencyBrick30);
		adjacencyList1.add(adjacencyBrick31);
		adjacencyList1.add(adjacencyBrick32);
		adjacencyList1.add(adjacencyBrick33);
		adjacencyList1.add(adjacencyBrick34);
		adjacencyList1.add(adjacencyBrick35);
		adjacencyList1.add(adjacencyBrick36);
		adjacencyList1.add(adjacencyBrick37);
		adjacencyList1.add(adjacencyBrick38);
		adjacencyList1.add(adjacencyBrick39);
		adjacencyList1.add(adjacencyBrick40);
		adjacencyList1.add(adjacencyBrick41);
		/**
		 * End construct bricks for test ship 1.
		 */

		TestShip testShip1 = new TestShip(values, bricks1, adjacencyList1);

		TestWeapon testWeapon = new TestWeapon(testShip1, systemBrick1, testShip1.getPosition(), testShip1.getOrientation(), 0.1, 0.01);
		testShip1.addSubSystem(testWeapon);

		objectManager.addPhysicalObject(testShip1);
		testShip1.addObserver(objectManager);

		for (int i = 0; i < 5; i++) {
			ArrayList<Brick> bricks2 = new ArrayList<Brick>();

			// row1
			bricks2.add(new ShipSquareBrick(0, new Vector2D(0.0, 0.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(1, new Vector2D(0.0, 10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(2, new Vector2D(0.0, -10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(3, new Vector2D(0.0, 20.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(4, new Vector2D(0.0, -20.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(5, new Vector2D(0.0, 30.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(6, new Vector2D(0.0, -30.0), 10.0f, 1));

			// row2
			bricks2.add(new ShipSquareBrick(7, new Vector2D(-10.0, 0.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(8, new Vector2D(-10.0, 10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(9, new Vector2D(-10.0, -10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(10, new Vector2D(-10.0, 20.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(11, new Vector2D(-10.0, -20.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(12, new Vector2D(-10.0, 30.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(13, new Vector2D(-10.0, -30.0), 10.0f, 1));

			// row3
			bricks2.add(new ShipSquareBrick(14, new Vector2D(-20.0, 0.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(15, new Vector2D(-20.0, 10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(16, new Vector2D(-20.0, -10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(17, new Vector2D(-20.0, 30.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(18, new Vector2D(-20.0, -30.0), 10.0f, 1));

			// row4
			bricks2.add(new ShipSquareBrick(19, new Vector2D(-30.0, 0.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(20, new Vector2D(-30.0, 10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(21, new Vector2D(-30.0, -10.0), 10.0f, 1));
			bricks2.add(new ShipTriangleBrick(22, new Vector2D(-30.0, 30.0), BrickOrientation.ONE, 10.0f, 1));
			bricks2.add(new ShipTriangleBrick(23, new Vector2D(-30.0, -30.0), BrickOrientation.ZERO, 10.0f, 1));

			// row5
			bricks2.add(new ShipTriangleBrick(24, new Vector2D(-40.0, 10.0), BrickOrientation.ONE, 10.0f, 1));
			bricks2.add(new ShipTriangleBrick(25, new Vector2D(-40.0, -10.0), BrickOrientation.ZERO, 10.0f, 1));

			// row6
			bricks2.add(new ShipSquareBrick(26, new Vector2D(10.0, 0.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(27, new Vector2D(10.0, 10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(28, new Vector2D(10.0, -10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(29, new Vector2D(10.0, 30.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(30, new Vector2D(10.0, -30.0), 10.0f, 1));

			// row7
			bricks2.add(new ShipSquareBrick(31, new Vector2D(20.0, 0.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(32, new Vector2D(20.0, 10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(33, new Vector2D(20.0, -10.0), 10.0f, 1));
			bricks2.add(new ShipTriangleBrick(34, new Vector2D(20.0, 30.0), BrickOrientation.TWO, 10.0f, 1));
			bricks2.add(new ShipTriangleBrick(35, new Vector2D(20.0, -30.0), BrickOrientation.THREE, 10.0f, 1));

			// row8
			bricks2.add(new ShipSquareBrick(36, new Vector2D(30.0, 0.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(37, new Vector2D(30.0, 10.0), 10.0f, 1));
			bricks2.add(new ShipSquareBrick(38, new Vector2D(30.0, -10.0), 10.0f, 1));

			// row9
			bricks2.add(new ShipTriangleBrick(39, new Vector2D(40.0, 10.0), BrickOrientation.TWO, 10.0f, 1));
			bricks2.add(new ShipTriangleBrick(40, new Vector2D(40.0, -10.0), BrickOrientation.THREE, 10.0f, 1));

			// weapon brick : row10
			SystemBrick systemBrick2 = new ShipSystemBrick(41, new Vector2D(40.0, 0.0), 10.0f, 1);
			bricks2.add(systemBrick2);

			ArrayList<ArrayList<Integer>> adjacencyList2 = new ArrayList<ArrayList<Integer>>();

			ArrayList<Integer> adjacency2Brick0 = new ArrayList<Integer>();
			adjacency2Brick0.add(1);
			adjacency2Brick0.add(2);
			adjacency2Brick0.add(7);
			adjacency2Brick0.add(26);

			ArrayList<Integer> adjacency2Brick1 = new ArrayList<Integer>();
			adjacency2Brick1.add(0);
			adjacency2Brick1.add(3);
			adjacency2Brick1.add(8);
			adjacency2Brick1.add(27);

			ArrayList<Integer> adjacency2Brick2 = new ArrayList<Integer>();
			adjacency2Brick2.add(0);
			adjacency2Brick2.add(4);
			adjacency2Brick2.add(9);
			adjacency2Brick2.add(28);

			ArrayList<Integer> adjacency2Brick3 = new ArrayList<Integer>();
			adjacency2Brick3.add(1);
			adjacency2Brick3.add(5);
			adjacency2Brick3.add(10);

			ArrayList<Integer> adjacency2Brick4 = new ArrayList<Integer>();
			adjacency2Brick4.add(2);
			adjacency2Brick4.add(6);
			adjacency2Brick4.add(11);

			ArrayList<Integer> adjacency2Brick5 = new ArrayList<Integer>();
			adjacency2Brick5.add(3);
			adjacency2Brick5.add(12);
			adjacency2Brick5.add(29);

			ArrayList<Integer> adjacency2Brick6 = new ArrayList<Integer>();
			adjacency2Brick6.add(4);
			adjacency2Brick6.add(13);
			adjacency2Brick6.add(30);

			ArrayList<Integer> adjacency2Brick7 = new ArrayList<Integer>();
			adjacency2Brick7.add(0);
			adjacency2Brick7.add(8);
			adjacency2Brick7.add(9);
			adjacency2Brick7.add(14);

			ArrayList<Integer> adjacency2Brick8 = new ArrayList<Integer>();
			adjacency2Brick8.add(1);
			adjacency2Brick8.add(7);
			adjacency2Brick8.add(10);
			adjacency2Brick8.add(15);

			ArrayList<Integer> adjacency2Brick9 = new ArrayList<Integer>();
			adjacency2Brick9.add(2);
			adjacency2Brick9.add(7);
			adjacency2Brick9.add(11);
			adjacency2Brick9.add(16);

			ArrayList<Integer> adjacency2Brick10 = new ArrayList<Integer>();
			adjacency2Brick10.add(3);
			adjacency2Brick10.add(8);
			adjacency2Brick10.add(12);

			ArrayList<Integer> adjacency2Brick11 = new ArrayList<Integer>();
			adjacency2Brick11.add(4);
			adjacency2Brick11.add(9);
			adjacency2Brick11.add(13);

			ArrayList<Integer> adjacency2Brick12 = new ArrayList<Integer>();
			adjacency2Brick12.add(5);
			adjacency2Brick12.add(10);
			adjacency2Brick12.add(17);

			ArrayList<Integer> adjacency2Brick13 = new ArrayList<Integer>();
			adjacency2Brick13.add(6);
			adjacency2Brick13.add(11);
			adjacency2Brick13.add(18);

			ArrayList<Integer> adjacency2Brick14 = new ArrayList<Integer>();
			adjacency2Brick14.add(7);
			adjacency2Brick14.add(15);
			adjacency2Brick14.add(16);
			adjacency2Brick14.add(19);

			ArrayList<Integer> adjacency2Brick15 = new ArrayList<Integer>();
			adjacency2Brick15.add(8);
			adjacency2Brick15.add(14);
			adjacency2Brick15.add(20);

			ArrayList<Integer> adjacency2Brick16 = new ArrayList<Integer>();
			adjacency2Brick16.add(9);
			adjacency2Brick16.add(14);
			adjacency2Brick16.add(21);

			ArrayList<Integer> adjacency2Brick17 = new ArrayList<Integer>();
			adjacency2Brick17.add(12);
			adjacency2Brick17.add(22);

			ArrayList<Integer> adjacency2Brick18 = new ArrayList<Integer>();
			adjacency2Brick18.add(13);
			adjacency2Brick18.add(23);

			ArrayList<Integer> adjacency2Brick19 = new ArrayList<Integer>();
			adjacency2Brick19.add(14);
			adjacency2Brick19.add(20);
			adjacency2Brick19.add(21);

			ArrayList<Integer> adjacency2Brick20 = new ArrayList<Integer>();
			adjacency2Brick20.add(15);
			adjacency2Brick20.add(19);
			adjacency2Brick20.add(24);

			ArrayList<Integer> adjacency2Brick21 = new ArrayList<Integer>();
			adjacency2Brick21.add(16);
			adjacency2Brick21.add(19);
			adjacency2Brick21.add(25);

			ArrayList<Integer> adjacency2Brick22 = new ArrayList<Integer>();
			adjacency2Brick22.add(17);

			ArrayList<Integer> adjacency2Brick23 = new ArrayList<Integer>();
			adjacency2Brick23.add(18);

			ArrayList<Integer> adjacency2Brick24 = new ArrayList<Integer>();
			adjacency2Brick24.add(20);

			ArrayList<Integer> adjacency2Brick25 = new ArrayList<Integer>();
			adjacency2Brick25.add(21);

			ArrayList<Integer> adjacency2Brick26 = new ArrayList<Integer>();
			adjacency2Brick26.add(0);
			adjacency2Brick26.add(27);
			adjacency2Brick26.add(28);
			adjacency2Brick26.add(31);

			ArrayList<Integer> adjacency2Brick27 = new ArrayList<Integer>();
			adjacency2Brick27.add(1);
			adjacency2Brick27.add(26);
			adjacency2Brick27.add(32);

			ArrayList<Integer> adjacency2Brick28 = new ArrayList<Integer>();
			adjacency2Brick28.add(2);
			adjacency2Brick28.add(26);
			adjacency2Brick28.add(33);

			ArrayList<Integer> adjacency2Brick29 = new ArrayList<Integer>();
			adjacency2Brick29.add(5);
			adjacency2Brick29.add(34);

			ArrayList<Integer> adjacency2Brick30 = new ArrayList<Integer>();
			adjacency2Brick30.add(6);
			adjacency2Brick30.add(35);

			ArrayList<Integer> adjacency2Brick31 = new ArrayList<Integer>();
			adjacency2Brick31.add(26);
			adjacency2Brick31.add(32);
			adjacency2Brick31.add(33);
			adjacency2Brick31.add(36);

			ArrayList<Integer> adjacency2Brick32 = new ArrayList<Integer>();
			adjacency2Brick32.add(27);
			adjacency2Brick32.add(31);
			adjacency2Brick32.add(37);

			ArrayList<Integer> adjacency2Brick33 = new ArrayList<Integer>();
			adjacency2Brick33.add(28);
			adjacency2Brick33.add(31);
			adjacency2Brick33.add(38);

			ArrayList<Integer> adjacency2Brick34 = new ArrayList<Integer>();
			adjacency2Brick34.add(29);

			ArrayList<Integer> adjacency2Brick35 = new ArrayList<Integer>();
			adjacency2Brick35.add(30);

			ArrayList<Integer> adjacency2Brick36 = new ArrayList<Integer>();
			adjacency2Brick36.add(31);
			adjacency2Brick36.add(37);
			adjacency2Brick36.add(38);
			adjacency2Brick36.add(41);

			ArrayList<Integer> adjacency2Brick37 = new ArrayList<Integer>();
			adjacency2Brick37.add(32);
			adjacency2Brick37.add(36);
			adjacency2Brick37.add(39);

			ArrayList<Integer> adjacency2Brick38 = new ArrayList<Integer>();
			adjacency2Brick38.add(33);
			adjacency2Brick38.add(36);
			adjacency2Brick38.add(40);

			ArrayList<Integer> adjacency2Brick39 = new ArrayList<Integer>();
			adjacency2Brick39.add(37);
			adjacency2Brick39.add(41);

			ArrayList<Integer> adjacency2Brick40 = new ArrayList<Integer>();
			adjacency2Brick40.add(38);
			adjacency2Brick40.add(41);

			ArrayList<Integer> adjacency2Brick41 = new ArrayList<Integer>();
			adjacency2Brick41.add(36);
			adjacency2Brick41.add(39);
			adjacency2Brick41.add(40);

			adjacencyList2.add(adjacency2Brick0);
			adjacencyList2.add(adjacency2Brick1);
			adjacencyList2.add(adjacency2Brick2);
			adjacencyList2.add(adjacency2Brick3);
			adjacencyList2.add(adjacency2Brick4);
			adjacencyList2.add(adjacency2Brick5);
			adjacencyList2.add(adjacency2Brick6);
			adjacencyList2.add(adjacency2Brick7);
			adjacencyList2.add(adjacency2Brick8);
			adjacencyList2.add(adjacency2Brick9);
			adjacencyList2.add(adjacency2Brick10);
			adjacencyList2.add(adjacency2Brick11);
			adjacencyList2.add(adjacency2Brick12);
			adjacencyList2.add(adjacency2Brick13);
			adjacencyList2.add(adjacency2Brick14);
			adjacencyList2.add(adjacency2Brick15);
			adjacencyList2.add(adjacency2Brick16);
			adjacencyList2.add(adjacency2Brick17);
			adjacencyList2.add(adjacency2Brick18);
			adjacencyList2.add(adjacency2Brick19);
			adjacencyList2.add(adjacency2Brick20);
			adjacencyList2.add(adjacency2Brick21);
			adjacencyList2.add(adjacency2Brick22);
			adjacencyList2.add(adjacency2Brick23);
			adjacencyList2.add(adjacency2Brick24);
			adjacencyList2.add(adjacency2Brick25);
			adjacencyList2.add(adjacency2Brick26);
			adjacencyList2.add(adjacency2Brick27);
			adjacencyList2.add(adjacency2Brick28);
			adjacencyList2.add(adjacency2Brick29);
			adjacencyList2.add(adjacency2Brick30);
			adjacencyList2.add(adjacency2Brick31);
			adjacencyList2.add(adjacency2Brick32);
			adjacencyList2.add(adjacency2Brick33);
			adjacencyList2.add(adjacency2Brick34);
			adjacencyList2.add(adjacency2Brick35);
			adjacencyList2.add(adjacency2Brick36);
			adjacencyList2.add(adjacency2Brick37);
			adjacencyList2.add(adjacency2Brick38);
			adjacencyList2.add(adjacency2Brick39);
			adjacencyList2.add(adjacency2Brick40);
			adjacencyList2.add(adjacency2Brick41);

			values.put("positionX", Math.random() * 2000.0 * MathBox.nextSign() + 25000.0);
			values.put("positionY", Math.random() * 2000.0 * MathBox.nextSign() + 25000.0);
			values.put("orientation", 0.0);

			TestShip testShip2 = new TestShip(values, bricks2, adjacencyList2);
			testShip2.addSubSystem(new TestWeapon(testShip2, systemBrick2, testShip2.getPosition(), testShip2.getOrientation(), 0.05, 0.01));
			testShip2.addInput(new AgentInputAttack(testShip1));

			objectManager.addPhysicalObject(testShip2);
			testShip2.addObserver(objectManager);
		}
		
		//set up the UI
		InterfaceBox interfaceBox = new InterfaceBox(new Vector2D(25000, 25000), 1000, 250);
		objectManager.addInterfaceObject(interfaceBox);
	}

	private void loadBuffers() {
		float projWidth = 10.0f;
		float projHeight = 10.0f;

		FloatBuffer projVertexBuffer = Buffers.newDirectFloatBuffer(18);
		projVertexBuffer.put(projWidth / 2);
		projVertexBuffer.put(projHeight / 2);
		projVertexBuffer.put(0.0f);
		projVertexBuffer.put(projWidth / 2);
		projVertexBuffer.put(-projHeight / 2);
		projVertexBuffer.put(0.0f);
		projVertexBuffer.put(-projWidth / 2);
		projVertexBuffer.put(-projHeight / 2);
		projVertexBuffer.put(0.0f);
		projVertexBuffer.put(projWidth / 2);
		projVertexBuffer.put(projHeight / 2);
		projVertexBuffer.put(0.0f);
		projVertexBuffer.put(-projWidth / 2);
		projVertexBuffer.put(-projHeight / 2);
		projVertexBuffer.put(0.0f);
		projVertexBuffer.put(-projWidth / 2);
		projVertexBuffer.put(projHeight / 2);
		projVertexBuffer.put(0.0f);
		projVertexBuffer.rewind();
		ModelLoader.loadVertexBuffer("testProjectile", projVertexBuffer);
	}
}
