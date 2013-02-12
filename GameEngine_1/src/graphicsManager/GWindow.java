package graphicsManager;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

import com.jogamp.newt.opengl.GLWindow;

/**
 * Generates an implementation of a NEWT (a variation of AWT) window for
 * providing a basis for JOGL rendering. GWindow uses the singleton pattern to
 * ensure there is only one application window.
 * 
 * @author Douglas H Cuthill
 */
public class GWindow {

	/**
	 * The GLWindow that GWindow generates.
	 */
	private GLWindow window;

	/**
	 * Private constructor that generates a GLWindow.
	 * 
	 * @param title
	 *            The title to be displayed in the top bar.
	 */
	public GWindow() {
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		window = GLWindow.create(caps);

		// window.setFullscreen(true);
		window.setSize(Constants.viewWidth, Constants.viewHeight);
		window.setVisible(true);
		window.setTitle("");

		window.addWindowListener(new GWindowListener());
	}

	/**
	 * Returns the GLWindow generated by GWindow.
	 * 
	 * @return The GLWindow of the GWindow.
	 */
	public GLWindow getWindow() {
		return window;
	}
}
