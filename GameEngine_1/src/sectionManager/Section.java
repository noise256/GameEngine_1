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
	
	protected double textureWidth;
	protected double textureHeight;
	
	protected ArrayList<Double> textureVertices;
	protected ArrayList<Vector2D> textureLines;
	
	protected boolean alive = true;
	protected boolean exploding = false;
	protected int explosionTimer = 100;
	
	public Section(SectionObject parent, int index, int health, String sectionName, String texturePath, Vector2D sectionPosition, double textureWidth, double textureHeight, ArrayList<Double> textureVertices) {
		this.parent = parent;
		this.index = index;
		this.health = health;
		this.sectionName = sectionName;
		this.texturePath = texturePath;
		this.sectionPosition = sectionPosition;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.textureVertices = textureVertices;
		
		//Generate texture lines
		textureLines = new ArrayList<Vector2D>();
		int numVerts = textureVertices.size();
		for (int i = 0; i < numVerts; i+=2) {
			textureLines.add(new Vector2D(textureVertices.get(i % numVerts) - textureWidth/2, (textureHeight - textureVertices.get((i+1) % numVerts)) - textureHeight/2));
			textureLines.add(new Vector2D(textureVertices.get((i+2) % numVerts) - textureWidth/2, (textureHeight - textureVertices.get((i+3) % numVerts)) - textureHeight/2));
		}
	}

	@Override
	public ArrayList<SceneNode> getView() {
		ArrayList<SceneNode> nodes = new ArrayList<SceneNode>(sceneNodes.values());
		return nodes;
	}

	public Vector2D getAbsolutePosition() {
		return MathBox.rotatePoint(sectionPosition, parent.getOrientation()).add(parent.getObjectPosition());
	}
	
	public ArrayList<Vector2D> getTextureLines() {
		return textureLines;
	}
	
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

	public double getTextureWidth() {
		return textureWidth;
	}

	public void setTextureWidth(double textureWidth) {
		this.textureWidth = textureWidth;
	}

	public double getTextureHeight() {
		return textureHeight;
	}

	public void setTextureHeight(double textureHeight) {
		this.textureHeight = textureHeight;
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