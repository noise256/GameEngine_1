package inputManager;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import com.jogamp.newt.event.MouseEvent;

public class ExtendedMouseEvent extends MouseEvent {
	private static final long serialVersionUID = -968263200754192643L;
	private Vector2D position; // TODO change name to worldPosition

	public ExtendedMouseEvent(MouseEvent mouseEvent, Vector2D position) {
		super(mouseEvent.getEventType(), mouseEvent.getSource(), mouseEvent.getWhen(), mouseEvent.getModifiers(), mouseEvent.getX(), mouseEvent.getY(),
				mouseEvent.getClickCount(), mouseEvent.getButton(), mouseEvent.getWheelRotation());
		this.position = position;
	}

	public Vector2D getPosition() {
		return position;
	}
}
