package br.ufsc.tupam.planning.service;

import java.util.List;

import br.ufsc.tupam.planning.domain.ActionCost;
import br.ufsc.tupam.planning.domain.ActionProb;
import br.ufsc.tupam.planning.domain.ActionReward;

public interface IPlanningService {

	void addStates(List<String> states);

	void addActions(List<String> actions);

	void addProbs(List<ActionProb> probs);

	List<String> getPlan();

	void addCosts(List<ActionCost> costs);

	void addRewards(List<ActionReward> rewards);
}
