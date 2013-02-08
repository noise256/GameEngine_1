package aiManager;

import physicsManager.PhysicalObject;

public class AgentInputAttack extends AgentInput {
	private PhysicalObject target;
	
	public AgentInputAttack(PhysicalObject target) {
		super(AgentInputType.ATTACK);
		this.target = target;
	}

	public PhysicalObject getTarget() {
		return target;
	}
}
