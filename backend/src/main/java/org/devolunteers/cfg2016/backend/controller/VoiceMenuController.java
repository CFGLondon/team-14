package org.devolunteers.cfg2016.backend.controller;
// views to link the menus together and extract data from the call

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devolunteers.cfg2016.backend.domain.Call;
import org.devolunteers.cfg2016.backend.services.DBService;
import org.devolunteers.cfg2016.backend.services.Language;
import org.devolunteers.cfg2016.backend.services.MenuService;
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
	MenuService menuService = (MenuService) dataSourceContext.getBean("menuService");
	DBService dbService = (DBService) dataSourceContext.getBean("dbService");
	
	// when "[URL]/main-menu" is selected call main menu method
	@RequestMapping(
			value = "/main-menu", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String mainMenu(HttpServletRequest request, HttpServletResponse response) {
		
		return menuService.mainMenu();
	}
	

	// when "[URL]/main-menu-foreign" is selected call main menu in Portuguese
	@RequestMapping(
			value = "/main-menu-foreign", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String mainMenuForeign(HttpServletRequest request, HttpServletResponse response) {
		
		return menuService.mainMenu(Language.PORTUGUESE);
	}
	
	// when "[URL]/handle-main-menu" is selected call corresponding handling method 
	// in language extracted from the url
	@RequestMapping(
			value = "/handle-main-menu", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String handleMainMenu(HttpServletRequest request, HttpServletResponse response) {
		logCallFromRequest(request);
		
		String digits = request.getParameter("Digits");
		String language = request.getParameter("language");
		if (language.equals("portugese"))
			return menuService.handleMainMenuPortuguese(digits);
		else
			return menuService.handleMainMenu(digits);
	}

	// when "[URL]/handle-sub-1" is selected call corresponding handling method 
	// in language extracted from the url
	@RequestMapping(
			value = "/handle-sub-1", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String handleSub1(HttpServletRequest request, HttpServletResponse response) {
		String digits = request.getParameter("Digits");
		String mainMenuChoice = request.getParameter("mainMenuChoice");
		String language = request.getParameter("language");
		if (language.equals("portugese"))
			return menuService.handleSub1Portuguese(digits, mainMenuChoice);
		else
			return menuService.handleSub1(digits, mainMenuChoice);
	}

	// when "[URL]/handle-sub-2" is selected call corresponding handling method 
	// in language extracted from the url
	@RequestMapping(
			value = "/handle-sub-2", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public String handleSub2(HttpServletRequest request, HttpServletResponse response) {
		String digits = request.getParameter("Digits");
		String mainMenuChoice = request.getParameter("mainMenuChoice");
		String sub1MenuChoice = request.getParameter("sub1MenuChoice");
		String language = request.getParameter("language");
		if (language.equals("portugese"))
			return menuService.handleSub2Portuguese(digits, mainMenuChoice, sub1MenuChoice);
		else
			return menuService.handleSub2(digits, mainMenuChoice, sub1MenuChoice);
	}
	
	// function to log the data from caller
	private void logCallFromRequest(HttpServletRequest request) {

		String from = request.getParameter("From");
		String fromCity = request.getParameter("FromCity");
		String fromState = request.getParameter("FromState");
		String fromCountry = request.getParameter("FromCountry");
		
		String to = request.getParameter("To");
		String toCity = request.getParameter("ToCity");
		String toState = request.getParameter("ToState");
		String toCountry = request.getParameter("ToCountry");
		
		// push it to database
		dbService.logCall(new Call(from, to, fromCity, fromState, fromCountry));
	}
	
}
