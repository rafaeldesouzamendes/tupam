package br.ufsc.tupam.monitoring.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.ufsc.tupam.monitoring.service.ISNMPService;
import br.ufsc.tupam.planning.service.IPlanningService;

@RunWith(SpringJUnit4ClassRunner.class)
public class SNMPServiceImplTestCase {

	@Autowired
	private ISNMPService service;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
