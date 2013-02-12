package brickManager;

import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class TriangleBrick extends Brick {
	public enum BrickOrientation {
		ZERO, ONE, TWO, THREE;
	}

	protected BrickOrientation brickOrientation;

	public TriangleBrick(int index, int health, Vector2D position, BrickOrientation brickOrientation, float edgeLength, int numSegments) {
		super(BrickType.TRIANGLE, index, health, position, edgeLength, numSegments);

		this.brickOrientation = brickOrientation;
	}

	@Override
	public ArrayList<Float> getVertices() {
		if (vertices == null) {
			vertices = new ArrayList<Float>();

			switch (brickOrientation) {
			case ZERO:
				vertices.add(edgeLength / 2);
				vertices.add(edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(edgeLength / 2);
				vertices.add(-edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(-edgeLength / 2);
				vertices.add(edgeLength / 2);
				vertices.add(0.0f);
				break;
			case ONE:
				vertices.add(edgeLength / 2);
				vertices.add(edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(edgeLength / 2);
				vertices.add(-edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(-edgeLength / 2);
				vertices.add(-edgeLength / 2);
				vertices.add(0.0f);
				break;
			case TWO:
				vertices.add(edgeLength / 2);
				vertices.add(-edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(-edgeLength / 2);
				vertices.add(-edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(-edgeLength / 2);
				vertices.add(edgeLength / 2);
				vertices.add(0.0f);
				break;
			case THREE:
				vertices.add(edgeLength / 2);
				vertices.add(edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(-edgeLength / 2);
				vertices.add(-edgeLength / 2);
				vertices.add(0.0f);

				vertices.add(-edgeLength / 2);
				vertices.add(edgeLength / 2);
				vertices.add(0.0f);
				break;
			}
		}

		return vertices;
	}

	@Override
	public ArrayList<Float> getTextureCoords() {
		if (textureCoords == null) {
			textureCoords = new ArrayList<Float>();

			switch (brickOrientation) {
			case ZERO:
				textureCoords.add(1.0f);
				textureCoords.add(1.0f);
				textureCoords.add(0.0f);

				textureCoords.add(1.0f);
				textureCoords.add(0.0f);
				textureCoords.add(0.0f);

				textureCoords.add(0.0f);
				textureCoords.add(1.0f);
				textureCoords.add(0.0f);
				break;
			case ONE:
				textureCoords.add(1.0f);
				textureCoords.add(1.0f);
				textureCoords.add(0.0f);

				textureCoords.add(1.0f);
				textureCoords.add(0.0f);
				textureCoords.add(0.0f);

				textureCoords.add(0.0f);
				textureCoords.add(0.0f);
				textureCoords.add(0.0f);
				break;
			case TWO:
				textureCoords.add(1.0f);
				textureCoords.add(0.0f);
				textureCoords.add(0.0f);

				textureCoords.add(0.0f);
				textureCoords.add(0.0f);
				textureCoords.add(0.0f);

				textureCoords.add(0.0f);
				textureCoords.add(1.0f);
				textureCoords.add(0.0f);
				break;
			case THREE:
				textureCoords.add(1.0f);
				textureCoords.add(1.0f);
				textureCoords.add(0.0f);

				textureCoords.add(0.0f);
				textureCoords.add(0.0f);
				textureCoords.add(0.0f);

				textureCoords.add(0.0f);
				textureCoords.add(1.0f);
				textureCoords.add(0.0f);
				break;
			}
		}
		return textureCoords;
	}

	@Override
	public ArrayList<Float> getNormals(float radius) {
		if (normals == null) {
			normals = new ArrayList<Float>();

			float bx = (float) position.getX();
			float by = (float) position.getY();

			switch (brickOrientation) {
			case ZERO:
				normals.add(bx + edgeLength / 2);
				normals.add(by + edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + edgeLength / 2);
				normals.add(by + -edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + -edgeLength / 2);
				normals.add(by + edgeLength / 2);
				normals.add(1.0f);
				break;
			case ONE:
				normals.add(bx + edgeLength / 2);
				normals.add(by + edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + edgeLength / 2);
				normals.add(by + -edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + -edgeLength / 2);
				normals.add(by + -edgeLength / 2);
				normals.add(1.0f);
				break;
			case TWO:
				normals.add(bx + edgeLength / 2);
				normals.add(by + -edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + -edgeLength / 2);
				normals.add(by + -edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + -edgeLength / 2);
				normals.add(by + edgeLength / 2);
				normals.add(1.0f);
				break;
			case THREE:
				normals.add(bx + edgeLength / 2);
				normals.add(by + edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + -edgeLength / 2);
				normals.add(by + -edgeLength / 2);
				normals.add(1.0f);

				normals.add(bx + -edgeLength / 2);
				normals.add(by + edgeLength / 2);
				normals.add(1.0f);
				break;
			}
		}
		return normals;
	}

	@Override
	public ArrayList<double[]> getLines() {
		ArrayList<double[]> lines = new ArrayList<double[]>();

		double x = position.getX();
		double y = position.getY();

		// hypo
		double[] l1 = new double[] { x - edgeLength / 2, y + edgeLength / 2, x + edgeLength / 2, y - edgeLength / 2, };

		// bottom
		double[] l2 = new double[] { x + edgeLength / 2, y - edgeLength / 2, x - edgeLength / 2, y - edgeLength / 2 };

		// left
		double[] l3 = new double[] { x - edgeLength / 2, y - edgeLength / 2, x - edgeLength / 2, y + edgeLength / 2 };

		lines.add(l1);
		lines.add(l2);
		lines.add(l3);

		return lines;
	}

	public BrickOrientation getBrickOrientation() {
		return brickOrientation;
	}
}
