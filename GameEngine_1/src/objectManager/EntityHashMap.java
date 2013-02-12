package objectManager;

import graphicsManager.Constants;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;

public class EntityHashMap {
	private int mapWidth;
	private int mapHeight;
	private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, ArrayList<PhysicalObject>>> objectMap;

	public EntityHashMap(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		objectMap = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, ArrayList<PhysicalObject>>>();

		for (int i = 0; i < mapWidth; i++) {
			objectMap.put(i, new ConcurrentHashMap<Integer, ArrayList<PhysicalObject>>());
			for (int j = 0; j < mapHeight; j++) {
				objectMap.get(i).put(j, new ArrayList<PhysicalObject>());
			}
		}
	}

	public void addEntity(PhysicalObject physicalObject, double i, double j) {
		int[] tC = translateCoordinates(i, j);
		objectMap.get(tC[0]).get(tC[1]).add(physicalObject);
	}

	public void removeEntity(PhysicalObject physicalObject, double i, double j) throws EntityHashMapException {
		int[] tC = translateCoordinates(i, j);
		if (objectMap.get(tC[0]).get(tC[1]).remove(physicalObject)) {
		}
		else {
			throw new EntityHashMapException("Could not remove physicalObject: " + physicalObject + " @: [" + i + ", " + j
					+ "] from map: physicalObject does not exist @: " + tC[0] + " " + tC[1]);
		}
	}

	public void moveEntity(PhysicalObject physicalObject) {
		try {
			removeEntity(physicalObject, physicalObject.getOldPosition().getX(), physicalObject.getOldPosition().getY());
		}
		catch (EntityHashMapException e) {
			e.printStackTrace();
		}

		addEntity(physicalObject, physicalObject.getPosition().getX(), physicalObject.getPosition().getY());
	}

	@SuppressWarnings("unchecked")
	public <T extends PhysicalObject> ArrayList<T> getEntities(ObjectType objectType, double minX, double maxX, double minY, double maxY) {// ,
																																			// ObjectType
																																			// type)
																																			// {
		ArrayList<T> entities = new ArrayList<T>();

		int[] tC1 = translateCoordinates(minX, minY);
		int[] tC2 = translateCoordinates(maxX, maxY);

		for (int i = tC1[0]; i <= tC2[0]; i++) {
			for (int j = tC1[1]; j <= tC2[1]; j++) {
				for (PhysicalObject match : objectMap.get(i).get(j)) {
					if (match.getObjectType() == objectType) {
						entities.add((T) match);
					}
				}
			}
		}
		return entities;
	}

	public ArrayList<PhysicalObject> getNearbyEntities(Vector2D position) {
		ArrayList<PhysicalObject> entities = new ArrayList<PhysicalObject>();

		int[] tc = translateCoordinates(position.getX(), position.getY());

		int iMin = Math.max(tc[0] - 1, 0);
		int iMax = Math.min(tc[0] + 1, objectMap.size());
		int jMin = Math.max(tc[1] - 1, 0);
		int jMax = Math.min(tc[1] + 1, objectMap.get(0).size());

		for (int i = iMin; i <= iMax; i++) {
			for (int j = jMin; j <= jMax; j++) {
				entities.addAll(objectMap.get(i).get(j));
			}
		}
		return entities;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int[] translateCoordinates(double x, double y) {
		return new int[] { Math.min(mapWidth - 1, (int) (x / Constants.modelWidth * mapWidth)),
				Math.min(mapHeight - 1, (int) (y / Constants.modelHeight * mapHeight)) };
	}
}

class EntityHashMapException extends Exception {
	private static final long serialVersionUID = 7571417999608133444L;

	public EntityHashMapException(String message) {
		super(message);
	}
}