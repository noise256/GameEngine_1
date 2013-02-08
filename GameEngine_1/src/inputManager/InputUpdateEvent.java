package inputManager;

import com.jogamp.newt.event.InputEvent;

import eventManager.UpdateEvent;
import eventManager.UpdateEventType;

public class InputUpdateEvent extends UpdateEvent {
	private static final long serialVersionUID = -5664227470359945979L;
	
	private InputEvent inputEvent;
	
	public InputUpdateEvent(Object source, InputEvent inputEvent) {
		super(source, UpdateEventType.INPUT);
		this.inputEvent = inputEvent;
	}
	
	public InputEvent getInputEvent() {
		return inputEvent;
	}
}
