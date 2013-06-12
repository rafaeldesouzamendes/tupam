package br.ufsc.tupam.planning.service.impl;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.tupam.planning.domain.ActionCost;
import br.ufsc.tupam.planning.domain.ActionProb;
import br.ufsc.tupam.planning.domain.ActionReward;
import br.ufsc.tupam.planning.service.IPlanningService;

public class PlanningServiceImpl implements IPlanningService {

	private final List<String> states = new ArrayList<String>();
	private final List<String> actions = new ArrayList<String>();
	private final List<ActionProb> probs = new ArrayList<ActionProb>();
	private final List<ActionCost> costs = new ArrayList<ActionCost>();
	private final List<ActionReward> rewards = new ArrayList<ActionReward>();

	@Override
	public List<String> getPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStates(final List<String> states) {
		this.states.addAll(states);
	}

	@Override
	public void addActions(final List<String> actions) {
		this.actions.addAll(actions);
	}

	@Override
	public void addProbs(final List<ActionProb> probs) {
		this.probs.addAll(probs);
	}

	@Override
	public void addCosts(final List<ActionCost> costs) {
		this.costs.addAll(costs);
	}

	@Override
	public void addRewards(final List<ActionReward> rewards) {
		this.rewards.addAll(rewards);
	}
}
