package inputManager;

import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;

import objectManager.ObjectManager;

//TODO Determining input context for when interface is implemented.
//TOOD Handle global commands in a more generalised way, rather than explictly defining key and action in code here.
public class InputManager {
	/**
	 * Used to update the InputReceivers with a new key/mouse events.
	 * 
	 * @param inputEvent
	 *            The JOGL NEWT InputEvent.
	 */
	public void update(InputUpdateEvent inputUpdateEvent, ObjectManager objectManager) {
		InputEvent inputEvent = inputUpdateEvent.getInputEvent();
		if (inputEvent instanceof KeyEvent) {
			if (((KeyEvent) inputEvent).getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
		objectManager.handleInput(inputEvent);
	}
}
