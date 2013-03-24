package sectionManager;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import sceneManager.EntityView;
import sceneManager.SceneNode;
import utilityManager.MathBox;

public abstract class Section implements EntityView {
	protected Hashtable<String, SceneNode> sceneNodes = new Hashtable<String, SceneNode>();
	
	protected SectionObject parent;
	
	protected int index;
	protected int health;
	
	protected String sectionName;
	protected String texturePath;
	
	protected Vector2D sectionPosition;
	
	protected int textureWidth;
	protected int textureHeight;
	protected ArrayList<Float> textureVertices;
	
	protected boolean alive = true;
	protected boolean exploding = false;
	protected int explosionTimer = 100;
	
	public Section(SectionObject parent, int index, int health, String sectionName, String texturePath, Vector2D sectionPosition, int textureWidth, int textureHeight, ArrayList<Float> textureVertices) {
		this.parent = parent;
		this.index = index;
		this.health = health;
		this.sectionName = sectionName;
		this.texturePath = texturePath;
		this.sectionPosition = sectionPosition;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.textureVertices = textureVertices;
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

	public SectionObject getParent() {
		return parent;
	}

	public void setParent(SectionObject parent) {
		this.parent = parent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}

	public Vector2D getSectionPosition() {
		return sectionPosition;
	}

	public void setSectionPosition(Vector2D sectionPosition) {
		this.sectionPosition = sectionPosition;
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public void setTextureWidth(int textureWidth) {
		this.textureWidth = textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public void setTextureHeight(int textureHeight) {
		this.textureHeight = textureHeight;
	}

	public ArrayList<Float> getTextureVertices() {
		return textureVertices;
	}

	public void setTextureVertices(ArrayList<Float> textureVertices) {
		this.textureVertices = textureVertices;
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
}