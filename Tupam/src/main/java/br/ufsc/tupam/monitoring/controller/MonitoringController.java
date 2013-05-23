package br.ufsc.tupam.monitoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {

	@RequestMapping("/list")
	public String listMonitoring(){
		return "monitoring/index";
	}
}
