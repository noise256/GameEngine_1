package objectManager;

import java.util.ArrayList;

import eventManager.UpdateEvent;
import eventManager.UpdateEventType;

import sceneManager.SceneNode;

public class ObjectEvent extends UpdateEvent {
	private static final long serialVersionUID = 5778767032480873151L;
	
	private ArrayList<SceneNode> sceneNodes;
	
	public ObjectEvent(Object source, ArrayList<SceneNode> sceneNodes) {
		super(source, UpdateEventType.OBJECT);
		setSceneNodes(sceneNodes);
	}

	public ArrayList<SceneNode> getSceneNodes() {
		return sceneNodes;
	}

	public void setSceneNodes(ArrayList<SceneNode> sceneNodes) {
		this.sceneNodes = sceneNodes;
	}
}
