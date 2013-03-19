package sectionManager;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.EntityView;
import sceneManager.SceneNode;
import utilityManager.MathBox;

public abstract class Section implements EntityView {
	private String textureName;
	private String texturePath;
	
	protected Hashtable<String, SceneNode> sceneNodes = new Hashtable<String, SceneNode>();

	protected SectionObject parent;
	
	protected int index;
	protected int health;
	
	protected Vector2D sectionPosition;
	
	protected boolean alive = true;
	protected boolean exploding = false;
	protected int explosionTimer = 100;
	
	protected ArrayList<Float> vertices;
	protected ArrayList<Float> normals;
	protected ArrayList<Float> textureCoords;
	
	public Section(SectionObject parent, int index, int health, Vector2D sectionPosition, String textureName, String texturePath) {
		this.parent = parent;
		this.index = index;
		this.health = health;
		this.sectionPosition = sectionPosition;
		this.textureName = textureName;
		this.texturePath = texturePath;
	}

	@Override
	public ArrayList<SceneNode> getView() {
		ArrayList<SceneNode> nodes = new ArrayList<SceneNode>(sceneNodes.values());
		return nodes;
	}

	public Vector2D getAbsolutePosition() {
		return MathBox.rotatePoint(sectionPosition, parent.getOrientation()).add(parent.getObjectPosition());
	}
	
	public abstract ArrayList<Float> getVertices();

	public abstract ArrayList<Float> getNormals(float radius);

	public abstract ArrayList<Float> getTextureCoords();

	public abstract ArrayList<double[]> getLines();
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Vector2D getSectionPosition() {
		return sectionPosition;
	}

	public void setPosition(Vector2D sectionPosition) {
		this.sectionPosition = sectionPosition;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isExploding() {
		return exploding;
	}

	public void setExploding(boolean exploding) {
		this.exploding = exploding;
	}

	public int getExplosionTimer() {
		return explosionTimer;
	}

	public void setExplosionTimer(int explosionTimer) {
		this.explosionTimer = explosionTimer;
	}
	
	public String getTextureName() {
		return textureName;
	}
	
	public String getTexturePath() {
		return texturePath;
	}
	
	public SectionObject getParent() {
		return parent;
	}

	public void setParent(SectionObject parent) {
		this.parent = parent;
	}
}