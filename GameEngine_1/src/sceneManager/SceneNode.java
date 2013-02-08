package sceneManager;
import javax.media.opengl.GL3bc;

public abstract class SceneNode {
	protected SceneNodeType type;
	
	public SceneNode(SceneNodeType type) {
		this.type = type;
	}
	
	public abstract void update(GL3bc gl);
}
