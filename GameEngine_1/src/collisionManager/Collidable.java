package collisionManager;

import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public interface Collidable {
	public void collide(Collidable collider);

	public double getRadius();

	public ArrayList<double[]> getLines();

	public Vector2D getCollidablePosition();

	public boolean canCollide();
}
