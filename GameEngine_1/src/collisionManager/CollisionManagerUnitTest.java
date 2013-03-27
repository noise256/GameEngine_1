package collisionManager;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.Test;

public class CollisionManagerUnitTest {

//	@Test
	public void testCompareCircleBoundsCollidableCollidable() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCompareCircleBoundsDoubleDoubleDouble() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCompareLineBounds() {
		fail("Not yet implemented");
	}

	@Test
	public void testLineSegmentIntersection() {
		boolean intersects;
		
		double x1;
		double y1;
		double x2;
		double y2;
		double x3;
		double y3;
		double x4;
		double y4;

		//

		System.out.println("Line Segment Test 1: Intersecting Line Segments.");
		
		x1 = 0;
		y1 = 0;
		x2 = 1;
		y2 = 1;
		x3 = 1;
		y3 = 0;
		x4 = 0;
		y4 = 1;

		intersects = CollisionManager.lineSegmentIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

		System.out.println("Result = " + intersects + " Expected = true");
		Assert.assertTrue(intersects);
		
		//
		
		System.out.println("Line Segment Test 2: Parallel Line Segments.");
		
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 1;
		x3 = 1;
		y3 = 0;
		x4 = 1;
		y4 = 1;
		
		intersects = CollisionManager.lineSegmentIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

		System.out.println("Result = " + intersects + " Expected = false");
		Assert.assertFalse(intersects);
		
		//
		
		System.out.println("Line Segment Test 2: Non-Intersection Perpendicular Line Segments.");
		
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 1;
		x3 = 1;
		y3 = 0;
		x4 = 1;
		y4 = 1;
		
		intersects = CollisionManager.lineSegmentIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

		System.out.println("Result = " + intersects + " Expected = false");
		Assert.assertFalse(intersects);
	}
	
//	@Test
	public void testLineIntersection() {
		Vector2D intersects;
		double x1;
		double y1;
		double x2;
		double y2;
		double x3;
		double y3;
		double x4;
		double y4;

		//

		x1 = 0;
		y1 = 0;
		x2 = 1;
		y2 = 1;
		x3 = 1;
		y3 = 0;
		x4 = 0;
		y4 = 1;

		intersects = CollisionManager.lineIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

		System.out.println("Intersects = " + intersects);
		Assert.assertNotNull(intersects);

		//

		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 1;
		x3 = 1;
		y3 = 0;
		x4 = 1;
		y4 = 1;

		intersects = CollisionManager.lineIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

		System.out.println("Intersects = " + intersects);
		Assert.assertNull(intersects);
		
		//

		x1 = 0;
		y1 = 0;
		x2 = 0.1;
		y2 = 0.1;
		x3 = 1;
		y3 = 0;
		x4 = 0;
		y4 = 1;

		intersects = CollisionManager.lineIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

		System.out.println("Intersects = " + intersects);
		Assert.assertNull(intersects);
	}

}
