package br.ufsc.tupam.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsc.tupam.monitoring.service.ISNMPService;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {

	@Autowired
	private ISNMPService snmpService;

	@RequestMapping("/list")
	public String listMonitoring(){
		return "monitoring/index";
	}

	public ISNMPService getSnmpService() {
		return this.snmpService;
	}

	public void setSnmpService(final ISNMPService snmpService) {
		this.snmpService = snmpService;
	}
}
