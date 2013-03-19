package gameManager;

import javax.media.opengl.GL3bc;

import graphicsManager.GWindow;
import graphicsManager.SceneRenderer;
import inputManager.InputManager;
import inputManager.InputUpdateEvent;
import objectManager.ObjectManager;

import com.jogamp.newt.opengl.GLWindow;

import eventManager.Observer;

public abstract class GameManager implements Runnable, Observer<InputUpdateEvent> {
	protected static GLWindow glWindow;

	protected static SceneRenderer renderer = new SceneRenderer();
	protected static ObjectManager objectManager = new ObjectManager();
	protected static InputManager inputManager = new InputManager();

	protected static GL3bc gl;
	
	public GameManager() {
		GWindow window = new GWindow();
		glWindow = window.getWindow();
		glWindow.addGLEventListener(renderer);
		
		// start game and add observers
		objectManager.addObserver(renderer);

		renderer.addObserver(this);

		// start gwindow and renderer
		glWindow.addKeyListener(renderer);
		glWindow.addMouseListener(renderer);
		
		glWindow.display();
	}

	int fps;
	int lastFpsTime;

	// TODO didn't write this, needs reworking
	public void run() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

		// keep looping round til the game ends
		while (true) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			// update our FPS counter if a second has passed since
			// we last recorded
			if (lastFpsTime >= 1000000000) {
				System.out.println("(FPS: " + fps + ")");
				lastFpsTime = 0;
				fps = 0;
			}

			// update the game logic
			tick();

			// render
			glWindow.display();

			// sleep the current thread for the appropriate amount of time
			try {
				Thread.sleep(Math.max(0, (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void tick() {
		objectManager.tick();
	}

	public static ObjectManager getObjectManager() {
		return objectManager;
	}

	@Override
	public void update(InputUpdateEvent eventObject) {
		inputManager.update(eventObject, objectManager);
	}
}
