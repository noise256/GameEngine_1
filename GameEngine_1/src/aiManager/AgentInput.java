package aiManager;


public abstract class AgentInput {
	public enum AgentInputType {
		MOVE, ATTACK;
	}

	private AgentInputType agentInputType;
	
	public AgentInput(AgentInputType agentInputType) {
		this.agentInputType = agentInputType;
	}

	public AgentInputType getAgentInputType() {
		return agentInputType;
	}
}
