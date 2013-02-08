package aiManager;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class AgentInputMove extends AgentInput {
	private Vector2D destination;
	
	public AgentInputMove(Vector2D destination) {
		super(AgentInputType.MOVE);
		this.destination = destination;
	}
	
	public Vector2D getDestination() {
		return destination;
	}
}
