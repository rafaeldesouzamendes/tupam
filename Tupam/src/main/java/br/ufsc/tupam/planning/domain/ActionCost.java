package br.ufsc.tupam.planning.domain;

public class ActionCost {
	private String state;
	private String action;
	private float cost;

	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public String getAction() {
		return this.action;
	}
	public void setAction(final String action) {
		this.action = action;
	}
	public float getCost() {
		return this.cost;
	}
	public void setCost(final float cost) {
		this.cost = cost;
	}
}
