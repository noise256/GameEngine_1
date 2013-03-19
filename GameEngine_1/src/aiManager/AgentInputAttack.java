package aiManager;

import physicsManager.PhysicalObject;
import sectionManager.Section;

public class AgentInputAttack extends AgentInput {
	private PhysicalObject objectTarget;
	private Section sectionTarget;

	public AgentInputAttack(PhysicalObject objectTarget, Section sectionTarget) {
		super(AgentInputType.ATTACK);
		this.objectTarget = objectTarget;
		this.sectionTarget = sectionTarget;
	}

	public PhysicalObject getObjectTarget() {
		return objectTarget;
	}
	
	public Section getSectionTarget() {
		return sectionTarget;
	}
}
