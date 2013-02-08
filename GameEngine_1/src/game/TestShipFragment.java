package game;

import gameManager.GameManager;
import graphicsManager.Constants;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.opengl.GL3bc;

import objectManager.GameObject;
import objectManager.ObjectType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import physicsManager.PhysicalObject;
import sceneManager.SceneNode;
import utilityManager.MathBox;
import brickManager.Brick;
import brickManager.Brick.BrickType;
import brickManager.BrickObject;
import collisionManager.Collidable;


public class TestShipFragment extends BrickObject {

	public TestShipFragment(GameObject source, Hashtable<String, Double> values, ArrayList<Brick> bricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		super(ObjectType.FRAGMENT, source, values, bricks, adjacencyList);
	}

	@Override
	public void collide(Collidable collider) {
		Brick brick = getClosestBrick(collider.getPosition());
		
		brick.setHealth(brick.getHealth() - 1);
		
		if (bricks.get(0).getHealth() <= 0) {
			setAlive(false);
		}
	}

	@Override
	public double getRadius() {
		if (bricks.isEmpty()) {
			return 0.0;
		}
		
		Brick max = bricks.get(0);
		for (Brick brick : bricks) {
			if (brick.getPosition().getNorm() > max.getPosition().getNorm()) {
				max = brick;
			}
		}
		
		if (max.getBrickType() == BrickType.SQUARE) {
			return max.getPosition().getNorm() + + Math.sqrt(max.getEdgeLength()*2 + max.getEdgeLength()*2);
		}
		else if (max.getBrickType() == BrickType.TRIANGLE) {
			return max.getPosition().getNorm();
		}
		else {
			return -1;
		}
	}

	@Override
	public void updateView() {
		if (sceneNodes.get("root") == null) {
			SceneNode root = new SceneNode(null) {
				@Override
				public void update(GL3bc gl) {
					gl.glPushMatrix();

					gl.glEnable(GL3bc.GL_BLEND);
					gl.glEnable(GL3bc.GL_LIGHTING);
					
					// translate and rotate
					gl.glTranslatef((float) position.getX(), (float) position.getY(), 0);
					gl.glRotatef((float) (orientation * 180 / Math.PI), 0, 0, 1);
					

			        
					for (Brick brick : bricks) {
						ArrayList<Float> vertices = brick.getVertices();
				        ArrayList<Float> normals = brick.getNormals((float) getRadius());
				        ArrayList<Float> textureCoords = brick.getTextureCoords();
				        
						gl.glPushMatrix();
						
						gl.glEnable(GL3bc.GL_BLEND);
						gl.glEnable(GL3bc.GL_LIGHTING);
						gl.glEnable(GL3bc.GL_TEXTURE_2D);
						
						gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
						gl.glScalef(1.0f, 1.0f, 1.0f);
						
						gl.glTranslatef((float) brick.getPosition().getX(), (float) brick.getPosition().getY(), 0.0f);
						
						gl.glBegin(GL3bc.GL_TRIANGLES);
						for (int j = 0; j < vertices.size(); j+=9) {
							gl.glNormal3f(normals.get(j), normals.get(j+1), normals.get(j+2));
							gl.glTexCoord3f(textureCoords.get(j), textureCoords.get(j+1), textureCoords.get(j+2));
							gl.glVertex3f(vertices.get(j), vertices.get(j+1), vertices.get(j+2) + 5);
							
							gl.glNormal3f(normals.get(j+3), normals.get(j+4), normals.get(j+5));
							gl.glTexCoord3f(textureCoords.get(j+3), textureCoords.get(j+4), textureCoords.get(j+5));
							gl.glVertex3f(vertices.get(j+3), vertices.get(j+4), vertices.get(j+5) + 5);
							
							gl.glNormal3f(normals.get(j+6), normals.get(j+7), normals.get(j+8));
							gl.glTexCoord3f(textureCoords.get(6), textureCoords.get(j+7), textureCoords.get(j+8));
							gl.glVertex3f(vertices.get(j+6), vertices.get(j+7), vertices.get(j+8) + 5);
						}
						gl.glEnd();

						gl.glDisable(GL3bc.GL_BLEND);
						gl.glDisable(GL3bc.GL_LIGHTING);
						gl.glDisable(GL3bc.GL_TEXTURE_2D);
						
						gl.glPopMatrix();
						
//						gl.glPushMatrix();
//						gl.glTranslatef((float) brick.getPosition().getX(), (float) brick.getPosition().getY(), 0.0f);
//						
//						ArrayList<Float> vertices = brick.getVertices();
//				        ArrayList<Float> normals = brick.getNormals((float) getRadius());
//				        
//						gl.glPushMatrix();
//						gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//						gl.glScalef(1.0f, 1.0f, 1.0f);
//						gl.glBegin(GL3bc.GL_TRIANGLES);
//						for (int i = 0; i < vertices.size(); i+=3) {
//							gl.glNormal3f(normals.get(i), normals.get(i+1), normals.get(i+2));
//							gl.glVertex3f(vertices.get(i), vertices.get(i+1), vertices.get(i+2) + 1);
//						}
//						gl.glEnd();
//						gl.glPopMatrix();
						
						if (Constants.displayNormals) {
							gl.glPushMatrix();
							gl.glDisable(GL3bc.GL_LIGHTING);
							gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
							gl.glLineWidth(0.5f);
							gl.glBegin(GL3bc.GL_LINES);
							for (int i = 0; i < vertices.size(); i+=3) {
								gl.glVertex3f(vertices.get(i), vertices.get(i+1), vertices.get(i+2) + 2);
								gl.glVertex3f(normals.get(i)*1.5f, normals.get(i+1)*1.5f, normals.get(i+2) + 2);
							}
							gl.glEnd();
							gl.glEnable(GL3bc.GL_LIGHTING);
							gl.glPopMatrix();
						}
						
						gl.glPopMatrix();
					}
					
					gl.glDisable(GL3bc.GL_BLEND);
					gl.glDisable(GL3bc.GL_LIGHTING);
					gl.glPopMatrix();
				}
			};
			sceneNodes.put("root", root);
		}
	}

	@Override
	public void update() {
		updateBricks();
	}

	@Override
	protected PhysicalObject createFragment(ArrayList<Brick> fragmentBricks, ArrayList<ArrayList<Integer>> adjacencyList) {
		//get average position of fragment bricks
		Vector2D averagePosition = new Vector2D(0.0, 0.0);
		for (Brick brick : fragmentBricks) {
			averagePosition = averagePosition.add(brick.getPosition());
		}
		averagePosition = averagePosition.scalarMultiply(1.0/fragmentBricks.size());
		
		//adjust position of bricks to be relative to new average position
		for (Brick brick : fragmentBricks) {
			brick.setPosition(brick.getPosition().subtract(averagePosition));
		}
		
		//update brick indices
		for (int i = 0; i < fragmentBricks.size(); i++) {
			//change adjacency list
			for (int j = 0; j < adjacencyList.size(); j++) {
				if (adjacencyList.get(j).contains(new Integer(fragmentBricks.get(i).getIndex()))) {
					adjacencyList.get(j).remove(new Integer(fragmentBricks.get(i).getIndex()));
					adjacencyList.get(j).add(new Integer(i));
				}
			}
			//change brick index
			fragmentBricks.get(i).setIndex(i);
		}
		
		//set averagePosition of bricks to an absolute position
		averagePosition = MathBox.rotatePoint(averagePosition, orientation).add(position);
				
		Hashtable<String, Double> values = new Hashtable<String, Double>();
		values.put("mass", 5000.0);
		values.put("positionX", averagePosition.getX());
		values.put("positionY", averagePosition.getY());
		values.put("orientation", orientation);
		values.put("forceX", Math.random() * MathBox.nextSign());
		values.put("forceY", Math.random() * MathBox.nextSign());
		values.put("forceMagnitude", Math.random() * 100.0);
		values.put("turningForce", Math.random() * 0.1 * MathBox.nextSign());
		values.put("velocityX", 0.0);
		values.put("velocityY", 0.0);
		values.put("turningVelocity", 0.0);
		values.put("maxVelocity", 0.5);
		values.put("maxTurningVelocity", 0.1);
		values.put("maxForce", 1.0);
		values.put("maxTurningForce", 1.0);
		
		TestShipFragment fragment = new TestShipFragment(this, values, fragmentBricks, adjacencyList);
		fragment.addObserver(GameManager.getObjectManager());
		return fragment;
	}

	@Override
	public boolean canCollide() {
		return true;
	}
}
