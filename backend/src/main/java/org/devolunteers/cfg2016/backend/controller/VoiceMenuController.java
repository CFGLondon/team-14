package org.devolunteers.cfg2016.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devolunteers.cfg2016.backend.domain.Call;
import org.devolunteers.cfg2016.backend.services.DBService;
import org.devolunteers.cfg2016.backend.services.SampleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/voice-menu")
public class VoiceMenuController {
	
	ApplicationContext dataSourceContext = new ClassPathXmlApplicationContext("Beans.xml");
	SampleService sampleService = (SampleService) dataSourceContext.getBean("sampleService");
	DBService dbService = (DBService) dataSourceContext.getBean("dbService");
	
	@RequestMapping(
			value = "/main-menu", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String mainMenu(HttpServletRequest request, HttpServletResponse response) {
		
		return sampleService.mainMenu();
	}
	
	@RequestMapping(
			value = "/handle-main-menu", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String handleMainMenu(HttpServletRequest request, HttpServletResponse response) {
		logCallFromRequest(request);
		
		String digits = request.getParameter("Digits");
		return sampleService.handleMainMenu(digits);
	}
	
	@RequestMapping(
			value = "/handle-sub-1", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String handleSub1(HttpServletRequest request, HttpServletResponse response) {
		String digits = request.getParameter("Digits");
		String mainMenuChoice = request.getParameter("mainMenuChoice");
		return sampleService.handleSub1(digits, mainMenuChoice);
	}
	
	@RequestMapping(
			value = "/handle-sub-2", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String handleSub2(HttpServletRequest request, HttpServletResponse response) {
		String digits = request.getParameter("Digits");
		String mainMenuChoice = request.getParameter("mainMenuChoice");
		String sub1MenuChoice = request.getParameter("sub1MenuChoice");
		return sampleService.handleSub2(digits, mainMenuChoice, sub1MenuChoice);
	}
	
	private void logCallFromRequest(HttpServletRequest request) {

		String from = request.getParameter("From");
		String fromCity = request.getParameter("FromCity");
		String fromState = request.getParameter("FromState");
		String fromCountry = request.getParameter("FromCountry");
		
		String to = request.getParameter("To");
		
		dbService.logCall(new Call(from, to, fromCity, fromState, fromCountry));
	}
	
}
