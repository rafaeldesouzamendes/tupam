package br.ufsc.tupam.planning.domain;

public class ActionProb {
	private String sourceState;
	private String action;
	private String targetState;
	private float prob;

	public String getSourceState() {
		return this.sourceState;
	}
	public void setSourceState(final String sourceState) {
		this.sourceState = sourceState;
	}
	public String getAction() {
		return this.action;
	}
	public void setAction(final String action) {
		this.action = action;
	}
	public String getTargetState() {
		return this.targetState;
	}
	public void setTargetState(final String targetState) {
		this.targetState = targetState;
	}
	public float getProb() {
		return this.prob;
	}
	public void setProb(final float prob) {
		this.prob = prob;
	}
}
