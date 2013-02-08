package collisionManager;

import java.util.ArrayList;

import objectManager.EntityHashMap;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;

public class CollisionManager {
	public static void checkCollisions(PhysicalObject collidable,
			EntityHashMap entityHashMap) {
		ArrayList<PhysicalObject> nearbyObjects = entityHashMap
				.getNearbyEntities(collidable.getPosition());

		for (PhysicalObject object : nearbyObjects) {
			if (object.canCollide() && collidable.canCollide()) {
				if (!collidable.equals(object) && !isParent(collidable, object) && !isSameParent(collidable, object)) {
					if (compareCircleBounds(collidable, object)) {
						if (compareLineBounds(collidable, object)) {
							collidable.collide(object);
							object.collide(collidable);
						}
					}
				}
			}
		}
	}

	public static PhysicalObject checkPointCollisions(Vector2D point,
			EntityHashMap entityHashMap) {
		ArrayList<PhysicalObject> nearbyObjects = entityHashMap
				.getNearbyEntities(point);

		for (PhysicalObject object : nearbyObjects) {
			if (compareCircleBounds(1, object.getRadius(),
					point.distance(object.getPosition()))) {
				return object;
			}
		}
		return null;
	}

	public static boolean compareCircleBounds(Collidable first,
			Collidable second) {
		double dist = first.getPosition().distance(second.getPosition());
		return compareCircleBounds(first.getRadius(), second.getRadius(), dist);
	}

	public static boolean compareCircleBounds(double rad1, double rad2,
			double dist) {
		if (rad1 + rad2 > dist) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean compareLineBounds(Collidable first, Collidable second) {
		for (int i = 0; i < first.getLines().size(); i++) {
			double[] l1 = first.getLines().get(i);
			for (int j = 0; j < second.getLines().size(); j++) {
				double[] l2 = second.getLines().get(j);
				if (lineIntersection(l1[0], l1[1], l1[2], l1[3], l2[0], l2[1], l2[2], l2[3]) != null) {
					return true;
				}
			}
		}
		return false;
	}

	private static Vector2D lineIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d == 0) {
			return null;
		}

		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

		return new Vector2D(xi, yi);
	}
	
	private static boolean isParent(PhysicalObject first, PhysicalObject second) {
		if (first.getSource() == null && second.getSource() == null) {
			return false;
		} else if (first.getSource() != null && second.getSource() == null) {
			if (first.getSource().equals(second)) {
				return true;
			} else {
				return false;
			}
		} else if (first.getSource() == null && second.getSource() != null) {
			if (second.getSource().equals(first)) {
				return true;
			} else {
				return false;
			}
		} else if (first.getSource() != null && second.getSource() != null) {
			if (first.getSource().equals(second)) {
				return true;
			} else if (second.getSource().equals(first)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static boolean isSameParent(PhysicalObject first,
			PhysicalObject second) {
		if (first.getSource() == null || second.getSource() == null) {
			return false;
		} else if (first.getSource().equals(second.getSource())) {
			return true;
		} else {
			return false;
		}
	}
}
