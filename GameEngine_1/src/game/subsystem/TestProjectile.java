package game.subsystem;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.opengl.GL3bc;

import objectManager.EntityHashMap;
import objectManager.GameObject;
import objectManager.ObjectType;
import physicsManager.PhysicalObject;
import sceneManager.SceneNode;
import sectionManager.Section;
import textureManager.TextureLoader;
import utilityManager.MathBox;
import aiManager.Agent;
import collisionManager.Collidable;

public class TestProjectile extends Agent {
	private double life = 1;
	private double lifeDecrement;

	private boolean exploding = false;
	private int explosionTimer = 100;

	private float projectileWidth;
	private float projectileHeight;
	
	public TestProjectile(GameObject source, Hashtable<String, Double> values, double lifeDecrement, float projectileWidth, float projectileHeight) {
		super(ObjectType.PROJECTILE, source, values, null, null, null);
		this.lifeDecrement = lifeDecrement;
		this.projectileWidth = projectileWidth;
		this.projectileHeight = projectileHeight;
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null && !exploding) {
			SceneNode root = new SceneNode(null) {
				@Override
				public void update(GL3bc gl) {
					if (!TextureLoader.getCurrentTextureName().equals("ProjectileTexture1")) {
						TextureLoader.loadTexture("ProjectileTexture1", "ProjectileTexture1.png");
						TextureLoader.setCurrentTexture(gl, "ProjectileTexture1");
					}

					gl.glEnable(GL3bc.GL_BLEND);
					gl.glEnable(GL3bc.GL_TEXTURE_2D);
					gl.glDisable(GL3bc.GL_LIGHTING);

					gl.glPushMatrix();

					// translate and rotate
					gl.glTranslatef((float) objectPosition.getX(), (float) objectPosition.getY(), 0);
					gl.glRotatef((float) (orientation * 180 / Math.PI), 0, 0, 1);

					gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					gl.glBegin(GL3bc.GL_QUADS);
					gl.glTexCoord3f(1.0f, 1.0f, 0.0f);
					gl.glVertex3f(projectileWidth, projectileHeight, 0.0f);

					gl.glTexCoord3f(1.0f, 0.0f, 0.0f);
					gl.glVertex3f(projectileWidth, -projectileHeight, 0.0f);

					gl.glTexCoord3f(0.0f, 0.0f, 0.0f);
					gl.glVertex3f(-projectileWidth, -projectileHeight, 0.0f);

					gl.glTexCoord3f(0.0f, 1.0f, 0.0f);
					gl.glVertex3f(-projectileWidth, projectileHeight, 0.0f);
					gl.glEnd();

					gl.glPopMatrix();

					gl.glDisable(GL3bc.GL_BLEND);
					gl.glDisable(GL3bc.GL_TEXTURE_2D);
					gl.glEnable(GL3bc.GL_LIGHTING);
				}
			};
			sceneNodes.put("root", root);
		}
		else if (exploding) {
			if (sceneNodes.get("explosionNode") == null) {
				SceneNode explosionNode = new SceneNode(null) {
					private int numParticles = 250;
					private float[][] particles;

					@Override
					public void update(GL3bc gl) {
						if (!TextureLoader.getCurrentTextureName().equals("ProjectileTexture1")) {
							TextureLoader.loadTexture("ProjectileTexture1", "ProjectileTexture1.png");
							TextureLoader.setCurrentTexture(gl, "ProjectileTexture1");
						}

						if (particles == null) {
							particles = new float[numParticles][5];

							// construct initial particles
							for (int i = 0; i < numParticles; i++) {
								// initial values
								particles[i] = new float[] { 
										(float) objectPosition.getX(), //x position
										(float) objectPosition.getY(), //y position
										MathBox.nextFloat() * 0.5f * MathBox.nextSign(), //x velocity
										MathBox.nextFloat() * 0.5f * MathBox.nextSign(), //y velocity 
										MathBox.nextFloat(), //alpha
										MathBox.nextFloat() * 10 + 90 //start time
								};
								
								// normalise velocity
								float magnitude = (float) Math.sqrt(particles[i][2] * particles[i][2] + particles[i][3] * particles[i][3]);
								particles[i][2] = particles[i][2] / magnitude;
								particles[i][3] = particles[i][3] / magnitude;
							}
						}
						else {
							// adjust positions of particles
							//TODO rewrite deacceleration to use defined fields
							for (int i = 0; i < numParticles; i++) {
								if (explosionTimer <= particles[i][5]) {
									particles[i][0] += particles[i][2];
									particles[i][1] += particles[i][3];
									particles[i][2] = Math.abs(particles[i][2] * 0.99f) >= particles[i][2] * 0.1f ? particles[i][2] * 0.99f : particles[i][2] * 0.1f;
									particles[i][3] = Math.abs(particles[i][3] * 0.99f) >= particles[i][3] * 0.1f ? particles[i][3] * 0.99f : particles[i][3] * 0.1f;
									particles[i][4] = particles[i][4] - 0.01f > 0.0f ? particles[i][4] - 0.01f : 0.0f; //TODO not sure if this is working properly, particles seem to fade at the same time
								}
							}
						}

						gl.glEnable(GL3bc.GL_BLEND);
						gl.glEnable(GL3bc.GL_TEXTURE_2D);
						gl.glDisable(GL3bc.GL_LIGHTING);

						for (int i = 0; i < numParticles; i++) {
							// render particles
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
	public void collide(Collidable collider) {
		life = -1;
	}

	@Override
	public double getRadius() {
		return 5;
	}

	@Override
	public void update(EntityHashMap entityHashMap) {
		life -= lifeDecrement;

		if (life <= 0) {
			exploding = true;
			explosionTimer--;

			if (explosionTimer <= 0) {
				setAlive(false);
			}
		}
	}

	@Override
	protected PhysicalObject createFragment(ArrayList<Section> sections, ArrayList<ArrayList<Integer>> adjacencyList) {
		return null;
	}

	@Override
	public ArrayList<double[]> getLines() {
		ArrayList<double[]> lines = new ArrayList<double[]>();

		double x1 = objectPosition.getX();
		double y1 = objectPosition.getY();

//		double x2 = oldObjectPosition.getX();
//		double y2 = oldObjectPosition.getY();
		
//		lines.add(new double[] {x1, y1, x2, y2});
		
		// top
		double[] l1 = new double[] { x1 - projectileWidth, y1 + projectileHeight, x1 + projectileWidth, y1 + projectileHeight, };

		// right
		double[] l2 = new double[] { x1 + projectileWidth, y1 + projectileHeight, x1 + projectileWidth, y1 - projectileHeight };

		// bottom
		double[] l3 = new double[] { x1 + projectileWidth, y1 - projectileHeight, x1 - projectileWidth, y1 - projectileHeight };

		// left
		double[] l4 = new double[] { x1 - projectileWidth, y1 - projectileHeight, x1 - projectileWidth, y1 + projectileHeight };

		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(l4);

		return lines;
		// ArrayList<double[]> positionLine = new ArrayList<double[]>();
		// positionLine.add(new double[] {oldPosition.getX(),
		// oldPosition.getY(), position.getX(), position.getY()});
		// return positionLine;
	}

	@Override
	public boolean canCollide() {
		if (life > 0) {
			return true;
		}
		else {
			return false;
		}
	}

}
