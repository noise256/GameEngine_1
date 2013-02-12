package game;

import graphicsManager.Constants;

import java.util.ArrayList;

import javax.media.opengl.GL3bc;

import modelManager.TextureLoader;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.SceneNode;
import utilityManager.MathBox;
import brickManager.SystemBrick;

public class ShipSystemBrick extends SystemBrick {
	public ShipSystemBrick(int index, Vector2D position, float edgeLength, int numSegments) {
		super(index, 10, position, edgeLength, numSegments);
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null && !exploding) {
			SceneNode root = new SceneNode(null) {
				@Override
				public void update(GL3bc gl) {
					if (!TextureLoader.getCurrentTextureName().equals("BrickTexture1")) {
						TextureLoader.loadTexture(gl, "BrickTexture1", "BrickTexture1.png");
						TextureLoader.setCurrentTexture(gl, "BrickTexture1");
					}
					
					ArrayList<Float> vertices = getVertices();
					ArrayList<Float> normals = getNormals((float) parent.getRadius());
					ArrayList<Float> textureCoords = getTextureCoords();

					gl.glPushMatrix();

					gl.glEnable(GL3bc.GL_BLEND);
					gl.glEnable(GL3bc.GL_LIGHTING);
					gl.glEnable(GL3bc.GL_TEXTURE_2D);

					gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
					gl.glScalef(1.0f, 1.0f, 1.0f);

					gl.glTranslatef((float) position.getX(), (float) position.getY(), 0.0f);

					gl.glBegin(GL3bc.GL_TRIANGLES);
					for (int j = 0; j < vertices.size(); j += 9) {
						gl.glNormal3f(normals.get(j), normals.get(j + 1), normals.get(j + 2));
						gl.glTexCoord3f(textureCoords.get(j), textureCoords.get(j + 1), textureCoords.get(j + 2));
						gl.glVertex3f(vertices.get(j), vertices.get(j + 1), vertices.get(j + 2) + 5);

						gl.glNormal3f(normals.get(j + 3), normals.get(j + 4), normals.get(j + 5));
						gl.glTexCoord3f(textureCoords.get(j + 3), textureCoords.get(j + 4), textureCoords.get(j + 5));
						gl.glVertex3f(vertices.get(j + 3), vertices.get(j + 4), vertices.get(j + 5) + 5);

						gl.glNormal3f(normals.get(j + 6), normals.get(j + 7), normals.get(j + 8));
						gl.glTexCoord3f(textureCoords.get(6), textureCoords.get(j + 7), textureCoords.get(j + 8));
						gl.glVertex3f(vertices.get(j + 6), vertices.get(j + 7), vertices.get(j + 8) + 5);
					}
					gl.glEnd();

					gl.glDisable(GL3bc.GL_BLEND);
					gl.glDisable(GL3bc.GL_LIGHTING);
					gl.glDisable(GL3bc.GL_TEXTURE_2D);

					gl.glPopMatrix();

					if (Constants.displayNormals) {
						gl.glPushMatrix();
						gl.glDisable(GL3bc.GL_LIGHTING);
						gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
						gl.glLineWidth(0.5f);
						gl.glBegin(GL3bc.GL_LINES);
						for (int j = 0; j < vertices.size(); j += 3) {
							gl.glVertex3f(vertices.get(j), vertices.get(j + 1),
									vertices.get(j + 2) + 2);
							gl.glVertex3f(normals.get(j) * 1.5f,
									normals.get(j + 1) * 1.5f,
									normals.get(j + 2) + 10);
						}
						gl.glEnd();
						gl.glEnable(GL3bc.GL_LIGHTING);
						gl.glPopMatrix();
					}
				}
			};

			sceneNodes.put("root", root);
		}
		else if (exploding) {
			if (sceneNodes.get("explosionNode") == null) {
				SceneNode explosionNode = new SceneNode(null) {
					private int numParticles = 100;
					private float[][] particles;
					
					@Override
					public void update(GL3bc gl) {
						if (!TextureLoader.getCurrentTextureName().equals("ProjectileTexture2")) {
							TextureLoader.loadTexture(gl, "ProjectileTexture2", "ProjectileTexture2.png");
							TextureLoader.setCurrentTexture(gl, "ProjectileTexture2");
						}
						
						if (particles == null) {
							particles = new float[numParticles][5];
							
							//construct initial particles
							for (int i = 0; i < numParticles; i++) {
								particles[i] = new float[] {
									(float) position.getX(), (float) position.getY(),
									MathBox.nextFloat() * 0.5f * MathBox.nextSign(), MathBox.nextFloat() * 0.5f * MathBox.nextSign(),
									MathBox.nextFloat()
								};
								//normalise velocity
								float magnitude = (float) Math.sqrt(particles[i][2]*particles[i][2] + particles[i][3] * particles[i][3]);
								particles[i][2] = particles[i][2] / magnitude;
								particles[i][3] = particles[i][3] / magnitude;
							}
						}
						else {
							//adjust positions of particles
							for (int i = 0; i < numParticles; i++) {
								particles[i][0] += particles[i][2];
								particles[i][1] += particles[i][3];
							}
						}
						
						gl.glEnable(GL3bc.GL_BLEND);
						gl.glEnable(GL3bc.GL_TEXTURE_2D);
						gl.glDisable(GL3bc.GL_LIGHTING);
						
						for (int i = 0; i < numParticles; i++) {
							//render particles
							gl.glPushMatrix();
							
							// translate and rotate
							gl.glTranslatef(particles[i][0], particles[i][1], 0);
							
							gl.glColor4f(1.0f, 1.0f, 1.0f, particles[i][4]);
							gl.glBegin(GL3bc.GL_QUADS);
								gl.glTexCoord3f(1.0f, 1.0f, 0.0f);
								gl.glVertex3f(2.5f, 2.5f, 0.0f);
								
								gl.glTexCoord3f(1.0f, 0.0f, 0.0f);
								gl.glVertex3f(2.5f, -2.5f, 0.0f);
								
								gl.glTexCoord3f(0.0f, 0.0f, 0.0f);
								gl.glVertex3f(-2.5f, -2.5f, 0.0f);
								
								gl.glTexCoord3f(0.0f, 1.0f, 0.0f);
								gl.glVertex3f(-2.5f, 2.5f, 0.0f);
							gl.glEnd();
							
							gl.glPopMatrix();
						}
						
						gl.glDisable(GL3bc.GL_BLEND);
						gl.glDisable(GL3bc.GL_TEXTURE_2D);
						gl.glEnable(GL3bc.GL_LIGHTING);
					}
				};
				sceneNodes.put("explosionNode", explosionNode);
				sceneNodes.remove("root");
			}
		}
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