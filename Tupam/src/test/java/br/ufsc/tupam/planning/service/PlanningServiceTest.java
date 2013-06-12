package br.ufsc.tupam.planning.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.ufsc.tupam.planning.domain.ActionCost;
import br.ufsc.tupam.planning.domain.ActionProb;
import br.ufsc.tupam.planning.domain.ActionReward;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlanningServiceTest {

	@Autowired
	private IPlanningService service;

	@Test
	public void test() {
		final List<String> states = this.loadStates();
		final List<String> actions = this.loadActions();
		final List<ActionProb> probs = this.loadProbs();
		final List<ActionCost> costs = this.loadCost();
		final List<ActionReward> rewards = this.loadRewards();

		this.service.addStates(states);
		this.service.addActions(actions);
		this.service.addProbs(probs);
		this.service.addCosts(costs);
		this.service.addRewards(rewards);

		final List<String> plan = this.service.getPlan();

		for(final String action : plan)
			System.out.println(action + ",");
	}

	private List<ActionReward> loadRewards() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<ActionCost> loadCost() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<ActionProb> loadProbs() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<String> loadActions() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<String> loadStates() {
		// TODO Auto-generated method stub
		return null;
	}

}
