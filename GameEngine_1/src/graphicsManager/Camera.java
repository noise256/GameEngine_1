package graphicsManager;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Camera {
	private float angle;

	/**
	 * Vector containing the position of the camera.
	 */
	private Vector3D cam = new Vector3D(0, 0, 0);

	/**
	 * Vector containing the position of the view target of the camera.
	 */
	private Vector3D view = new Vector3D(0, 0, 0);

	/**
	 * Vector that contains any new axis of rotation from keypressed or mouse
	 * movement.
	 */
	private Vector3D newAxis = new Vector3D(0, 0, 0);

	/**
	 * Double that contains the any new rotation along newAxis.
	 */
	private double rotation = 0;

	/**
	 * The current rotation quaternion for the camera.
	 */
	private Rotation total = Rotation.IDENTITY;

	private Rotation local = Rotation.IDENTITY;

	private Vector2D mouseStart = new Vector2D(0, 0);

	private Vector2D mouseEnd = new Vector2D(0, 0);

	private boolean mouseRotation;

	/**
	 * Class constructor.
	 * 
	 * @param cam
	 *            The position of the camera.
	 * @param view
	 *            The target of the camera.
	 * @param width
	 *            The width of the world space.
	 * @param height
	 *            The height of the world space.
	 */
	public Camera(Vector3D cam, Vector3D view, float angle) {
		this.cam = cam;
		this.view = view;
		this.angle = angle;
	}

	/**
	 * Rotates the camera by combining the current rotation (total) with any new
	 * axis/angle representation of a new rotation (newAxis, rotation).
	 */
	public void rotateCamera() {
		if (rotation != 0) {
			if (mouseRotation) {
				Vector3D m1 = getPointOnSphere(mouseStart);
				Vector3D m2 = getPointOnSphere(mouseEnd);

				newAxis = m1.crossProduct(m2);
				rotation = Vector3D.angle(m1, m2) * 20;

				mouseStart = mouseEnd;
			}

			// Construct quaternion from the new rotation:
			local = new Rotation(Math.cos(rotation / 2), Math.sin(rotation / 2) * newAxis.getX(), Math.sin(rotation / 2) * newAxis.getY(),
					Math.sin(rotation / 2) * newAxis.getZ(), true);

			// Generate new camera rotation quaternion from current rotation
			// quaternion and new rotation quaternion:
			// total = total.applyTo(local);
			total = local.applyTo(total);

			// rotation is complete so set the next rotation to 0
			rotation = 0;
		}
	}

	public void translate(Vector3D axis, double distance) {
		// get forward vector
		Vector3D forward = new Vector3D(0, 0, 1);
		total.applyTo(forward).normalize();

		// get right vector
		Vector3D right = new Vector3D(1, 0, 0);
		total.applyTo(right).normalize();

		// get up vector
		Vector3D up = new Vector3D(0, 1, 0);
		total.applyTo(up).normalize();

		// translate camera position
		if (axis.getX() == 1) {
			cam = new Vector3D(cam.getX() + right.getX() * distance, cam.getY() + right.getY() * distance, cam.getZ() + right.getZ() * distance);
			view = new Vector3D(view.getX() + right.getX() * distance, view.getY() + right.getY() * distance, view.getZ() + right.getZ() * distance);
		}
		else if (axis.getY() == 1) {
			cam = new Vector3D(cam.getX() + up.getX() * distance, cam.getY() + up.getY() * distance, cam.getZ() + up.getZ() * distance);
			view = new Vector3D(view.getX() + up.getX() * distance, view.getY() + up.getY() * distance, view.getZ() + up.getZ() * distance);
		}
		else if (axis.getZ() == 1) {
			cam = new Vector3D(cam.getX() + forward.getX() * distance, cam.getY() + forward.getY() * distance, cam.getZ() + forward.getZ() * distance);
			view = new Vector3D(view.getX() + forward.getX() * distance, view.getY() + forward.getY() * distance, view.getZ() + forward.getZ() * distance);
		}
	}

	public double[] getRotationMatrix() {
		double q0 = total.getQ0();
		double q1 = total.getQ1();
		double q2 = total.getQ2();
		double q3 = total.getQ3();

		return new double[] { 1 - 2 * q2 * q2 - 2 * q3 * q3, 2 * q1 * q2 - 2 * q0 * q3, 2 * q1 * q3 + 2 * q0 * q2, 0, 2 * q1 * q2 + 2 * q0 * q3,
				1 - 2 * q1 * q1 - 2 * q3 * q3, 2 * q2 * q3 - 2 * q0 * q1, 0, 2 * q1 * q3 - 2 * q0 * q2, 2 * q2 * q3 + 2 * q0 * q1,
				1 - 2 * q1 * q1 - 2 * q2 * q2, 0, 0, 0, 0, 1 };
	}

	/**
	 * Set the axis of a new rotation.
	 * 
	 * @param newAxis
	 *            The rotation axis.
	 */
	public void setAxis(Vector3D newAxis) {
		this.newAxis = newAxis;
	}

	/**
	 * Set the rotation of a new rotation.
	 * 
	 * @param rotation
	 *            The rotation in radians.
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	/**
	 * Return the position of the camera.
	 * 
	 * @return The position of the camera.
	 */
	public Vector3D getCam() {
		return cam;
	}

	/**
	 * Return the position of the target of the camera.
	 * 
	 * @return The position of the target of the camera.
	 */
	public Vector3D getView() {
		return view;
	}

	/**
	 * Find the point on the unit sphere of the 3D viewport corresponding to the
	 * point on the 2D viewport defined by pX and pY.
	 * 
	 * @param pX
	 *            The X coordinate on the 2D viewport.
	 * @param pY
	 *            The Y coordinate on the 2D viewport.
	 * 
	 * @return The 3D point on the unit sphere.
	 */
	private Vector3D getPointOnSphere(Vector2D point) {
		// Scale bounds to [0,0] - [2,2]
		double x = point.getX() / (Constants.viewWidth / 2);
		double y = point.getY() / (Constants.viewHeight / 2);

		// Translate 0, 0 to the centre
		x = x - 1;

		// Flip so +Y is up instead of down
		y = 1 - y;

		// Solve for z
		double z = 1 - x * x - y * y;
		z = z < 0 ? 0 : Math.sqrt(z);

		return new Vector3D(x, y, z).normalize();
	}

	public void setMouseStart(int x, int y) {
		mouseStart = new Vector2D(x, y);
	}

	public void setMouseEnd(int x, int y) {
		mouseEnd = new Vector2D(x, y);
	}

	public void setMouseRotation(boolean mouseRotation) {
		this.mouseRotation = mouseRotation;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

}
