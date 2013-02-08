package brickManager;

import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import utilityManager.MathBox;

public abstract class SquareBrick extends Brick {
	public SquareBrick(int index, int health, Vector2D position, float edgeLength, int numSegments) {
		super(BrickType.SQUARE, index, health, position, edgeLength, numSegments);
	}

	@Override
	public ArrayList<Float> getVertices() {
		if (vertices == null) {
			vertices = new ArrayList<Float>();
			
			float e = edgeLength/2;
			float s = edgeLength/numSegments;
			
			for (int i = 0; i < numSegments; i++) {
				for (int j = 0; j < numSegments; j++) {
					vertices.add(-e + s * i);
					vertices.add(-e + s * j);
					vertices.add(0.0f);
					
					vertices.add(-e + s * i);
					vertices.add(-e + s * j + s);
					vertices.add(0.0f);
					
					vertices.add(-e + s * i + s);
					vertices.add(-e + s * j);
					vertices.add(0.0f);
					
					vertices.add(-e + s * i);
					vertices.add(-e + s * j + s);
					vertices.add(0.0f);
					
					vertices.add(-e + s * i + s);
					vertices.add(-e + s * j + s);
					vertices.add(0.0f);
					
					vertices.add(-e + s * i + s);
					vertices.add(-e + s * j);
					vertices.add(0.0f);
				}
			}
		}
		
		return vertices;
	}
	
	public ArrayList<Float> getNormals(float radius) {
		if (normals == null) {
			normals = new ArrayList<Float>();
			
			float bx = (float) position.getX();
			float by = (float) position.getY();
			
			float e = edgeLength/2;
			float s = edgeLength/numSegments;
			
			float norm[];
			for (int i = 0; i < numSegments; i++) {
				for (int j = 0; j < numSegments; j++) {
					norm = new float[] {
							bx - e + s * i,
							by - e + s * j,
							1.0f
						};
					norm = MathBox.normalise(norm);
					normals.add(norm[0]);
					normals.add(norm[1]);
					normals.add(norm[2]);
						
					norm = new float[] {
							bx - e + s * i,
							by - e + s * j + s,
							1.0f
						};
					norm = MathBox.normalise(norm);
					normals.add(norm[0]);
					normals.add(norm[1]);
					normals.add(norm[2]);
						
					norm = new float[] {
						bx - e + s * i + s,
						by - e + s * j,
						1.0f
					};
					norm = MathBox.normalise(norm);
					normals.add(norm[0]);
					normals.add(norm[1]);
					normals.add(norm[2]);
					
					norm = new float[] {
							bx - e + s * i,
							by - e + s * j + s,
							1.0f
						};
					norm = MathBox.normalise(norm);
					normals.add(norm[0]);
					normals.add(norm[1]);
					normals.add(norm[2]);
					
					norm = new float[] {
							bx - e + s * i + s,
							by - e + s * j + s,
							1.0f
						};
					norm = MathBox.normalise(norm);
					normals.add(norm[0]);
					normals.add(norm[1]);
					normals.add(norm[2]);
					
					norm = new float[] {
						bx - e + s * i + s,
						by - e + s * j,
						1.0f
					};
					norm = MathBox.normalise(norm);
					normals.add(norm[0]);
					normals.add(norm[1]);
					normals.add(norm[2]);
				}
			}
		}
		return normals;
	}
	
	@Override
	public ArrayList<Float> getTextureCoords() {
		if (textureCoords == null) {
			textureCoords = new ArrayList<Float>();	
			
			float s = 1.0f/numSegments;
			
			for (int i = 0; i < numSegments; i++) {
				for (int j = 0; j < numSegments; j++) {
					textureCoords.add(s * i);
					textureCoords.add(1.0f - s * j);
					textureCoords.add(0.0f);
					
					textureCoords.add(s * i);
					textureCoords.add(1.0f - s * j - s);
					textureCoords.add(0.0f);
					
					textureCoords.add(s * i + s);
					textureCoords.add(1.0f - s * j);
					textureCoords.add(0.0f);
					
					textureCoords.add(s * i);
					textureCoords.add(1.0f - s * j - s);
					textureCoords.add(0.0f);
					
					textureCoords.add(s * i + s);
					textureCoords.add(1.0f - s * j - s);
					textureCoords.add(0.0f);
					
					textureCoords.add(s * i + s);
					textureCoords.add(1.0f - s * j);
					textureCoords.add(0.0f);
				}
			}
		}
		
		return textureCoords;
	}
	
	@Override
	public ArrayList<double[]> getLines() {
		ArrayList<double[]> lines = new ArrayList<double[]>();
		
		double x = position.getX();
		double y = position.getY();
		
		//top
		double[] l1 = new double[] {
			x - edgeLength/2, y + edgeLength/2,
			x + edgeLength/2, y + edgeLength/2,
		};
		
		//right
		double[] l2 = new double[] {
			x + edgeLength/2, y + edgeLength/2,
			x + edgeLength/2, y - edgeLength/2
		};
		
		//bottom
		double[] l3 = new double[] {
			x + edgeLength/2, y - edgeLength/2,
			x - edgeLength/2, y - edgeLength/2
		};
		
		//left
		double[] l4 = new double[] {
			x - edgeLength/2, y - edgeLength/2,
			x - edgeLength/2, y + edgeLength/2
		};
		
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(l4);
		
		return lines;
	}
}
