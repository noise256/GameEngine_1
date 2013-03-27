package utilityManager;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class MathBox {
	private static Random rand = new Random();

	public static Vector2D angleToVector(double angle) {
		return new Vector2D(Math.cos(angle), Math.sin(angle));
	}
	
	public static int nextSign() {
		return (rand.nextBoolean() ? 1 : -1);
	}

	public static float nextFloat() {
		return rand.nextFloat();
	}

	public static float[] normalise(float[] in) {
		float m = getVectorMagnitude(in);
		return new float[] { in[0] / m, in[1] / m, in[2] / m };
	}

	public static boolean compareDouble(double a, double b, double e) {
		if (Math.abs(a - b) < e) {
			return true;
		}
		return false;
	}

	public static double getVectorMagnitude(double[] v) {
		double m = 0;
		for (int i = 0; i < v.length; i++) {
			m += v[i] * v[i];
		}
		return Math.sqrt(m);
	}

	public static float getVectorMagnitude(float[] v) {
		float m = 0;
		for (int i = 0; i < v.length; i++) {
			m += v[i] * v[i];
		}
		return (float) Math.sqrt(m);
	}

	public static double[] getUnitVector(double[] v) {
		double[] n = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			if (v[i] == 0) {
				n[i] = 0;
			}
			else {
				n[i] = v[i] / getVectorMagnitude(v);
			}
		}
		return n;
	}

	public static double getDistanceBetweenPoints(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public static double getDistanceBetweenPoints(double[] v1, double[] v2) {
		return Math.sqrt(Math.pow(v2[0] - v1[0], 2) + Math.pow(v2[1] - v1[1], 2));
	}

	public static double[] getVectorBetweenPoints(double[] v1, double[] v2) {
		return new double[] { v2[0] - v1[0], v2[1] - v1[1] };
	}

	// public static <G extends WorldObject> ArrayList<G> getNeighbours(G
	// entity,
	// double min, double max, ArrayList<G> entities, boolean self) {
	// ArrayList<G> neighbours = new ArrayList<G>();
	// for (int i = 0; i < entities.size(); i++) {
	// if (!self && entities.get(i).equals(entity)) {
	// continue;
	// }
	// double d =
	// entity.getGeometry().getPosition().distance(entities.get(i).getGeometry().getPosition());
	// if (d >= min && d <= max) {
	// neighbours.add(entities.get(i));
	// }
	// }
	// return neighbours;
	// }

	public static double getAngleBetweenVectors(Vector2D v1, Vector2D v2) {
		double pDP = -v1.getY() * v2.getX() + v1.getX() * v2.getY();
		double dP = v1.getX() * v2.getX() + v1.getY() * v2.getY();
		return Math.atan2(pDP, dP);
	}

	public static double getTriangleArea(double[][] vertices) {
		double sum = 0;

		sum += vertices[0][0] * vertices[1][1];
		sum += vertices[1][0] * vertices[2][1];
		sum += vertices[2][0] * vertices[0][1];

		sum -= vertices[0][0] * vertices[2][1];
		sum -= vertices[1][0] * vertices[0][1];
		sum -= vertices[2][0] * vertices[1][1];

		return Math.abs(sum) / 2;
	}

	public static double[] rotatePointX(double[] o, double[] p, double a) {
		double[] v = new double[] { p[0] - o[0], p[1] - o[1] };

		return new double[] { v[0], v[1] * Math.cos(a) };
	}

	public static double[] rotatePointY(double[] o, double[] p, double a) {
		double[] v = new double[] { p[0] - o[0], p[1] - o[1] };

		return new double[] { v[0] * Math.cos(a), v[1] };
	}

	public static double[] rotatePointZ(double[] o, double[] p, double a) {
		double[] v = new double[] { p[0] - o[0], p[1] - o[1] };

		return new double[] { Math.cos(a) * v[0] - Math.sin(a) * v[1] + o[0], Math.sin(a) * v[0] + Math.cos(a) * v[1] + o[1] };
	}

	public static double[] multiplyVector(double[] vector, double scalar) {
		double[] newVec = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			newVec[i] = vector[i] * scalar;
		}
		return newVec;
	}

	public static float[] multiplyVector(float[] vector, float scalar) {
		float[] newVec = new float[vector.length];
		for (int i = 0; i < vector.length; i++) {
			newVec[i] = vector[i] * scalar;
		}
		return newVec;
	}

	public static double[] divideVector(double[] vector, double scalar) {
		double[] newVec = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			newVec[i] = vector[i] / scalar;
		}
		return newVec;
	}

	public static double[] addVectors(double[] v1, double[] v2) {
		return new double[] { v1[0] + v2[0], v1[1] + v2[1] };
	}

	public static double[] addVectors(double[][] vectors) {
		double[] sum = new double[vectors[0].length];
		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[0].length; j++) {
				sum[j] += vectors[i][j];
			}
		}
		return sum;
	}

	public static double[] addVectors(ArrayList<double[]> vectors) {
		double[] sum = new double[vectors.get(0).length];
		for (int i = 0; i < vectors.size(); i++) {
			for (int j = 0; j < vectors.get(0).length; j++) {
				sum[j] += vectors.get(i)[j];
			}
		}
		return sum;
	}

	public static double[] subtractVectors(double[] v1, double[] v2) {
		return new double[] { v1[0] - v2[0], v1[1] - v2[1] };
	}

	public static double sign(double d) {
		if (Double.isInfinite(d)) {
			return Double.POSITIVE_INFINITY;
		}
		else if (Double.isNaN(d)) {
			return Double.NaN;
		}
		else if (d < 0) {
			return -1;
		}
		else if (d > 0) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public static double[] getCrossProduct(double[] v0, double[] v1) {
		return new double[] { v0[1] * v1[2] - v1[1] * v0[2], v0[2] * v1[0] - v1[2] * v0[0], v0[0] * v1[1] - v1[0] * v0[1] };
	}

	public static double[][] multiply(double a[][], double b[][]) {

		int aRows = a.length, aColumns = a[0].length, bRows = b.length, bColumns = b[0].length;

		if (aColumns != bRows) {
			throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
		}

		double[][] resultant = new double[aRows][bColumns];

		for (int i = 0; i < aRows; i++) { // aRow
			for (int j = 0; j < bColumns; j++) { // bColumn
				for (int k = 0; k < aColumns; k++) { // aColumn
					resultant[i][j] += a[i][k] * b[k][j];
				}
			}
		}

		return resultant;
	}

	public static double[] flatten(double[][] matrix) {
		double[] flat = new double[matrix.length * matrix[0].length];

		int c = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				flat[c] = matrix[i][j];
				c++;
			}
		}

		return flat;
	}

	public static double[][] unflattenMatrix4(double[] matrix) {
		double[][] unflat = new double[4][4];

		int c = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				unflat[i][j] = matrix[c];
				c++;
			}
		}

		return unflat;
	}

	// TODO rewrite these to use only two calls to math.cos and math.sin or look
	// up table???
	public static Vector2D rotatePoint(Vector2D point, double angle) {
		return new Vector2D(point.getX() * Math.cos(angle) - point.getY() * Math.sin(angle), point.getX() * Math.sin(angle) + point.getY() * Math.cos(angle));
	}

	public static float[] rotatePoint(float[] point, float angle) {
		float cosx = (float) Math.cos(angle);
		float sinx = (float) Math.sin(angle);
		return new float[] { point[0] * cosx - point[1] * sinx, point[0] * sinx + point[1] * cosx };
	}

	public static double[] rotatePoint(double[] point, double angle) {
		return new double[] { point[0] * Math.cos(angle) - point[1] * Math.sin(angle), point[0] * Math.sin(angle) + point[1] * Math.cos(angle) };
	}

	public static double[] rotatePoint(double[] point, double[] pivot, double angle) {
		double[] r = new double[2];

		r[0] = (point[0] - pivot[0]) * Math.cos(angle) - (point[1] - pivot[1]) * Math.sin(angle);
		r[1] = (point[0] - pivot[0]) * Math.sin(angle) + (point[1] - pivot[1]) * Math.cos(angle);

		r[0] = r[0] + pivot[0];
		r[1] = r[1] + pivot[1];

		return r;
	}
	// public static Vector2D rotateAroundPivot(Vector2D pivot, Vector2D point,
	// double angle) {
	// Vector2D translated = point.subtract(pivot);
	// return new Vector2D(
	// translated.getX() * Math.cos(angle) + translated.getY() *
	// Math.sin(angle),
	// -translated.getX() * Math.sin(angle) + translated.getY() *
	// Math.cos(angle)
	// ).add(pivot);
	// }
}
