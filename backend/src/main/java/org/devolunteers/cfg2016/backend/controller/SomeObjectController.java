package org.devolunteers.cfg2016.backend.controller;

import java.util.List;

import org.devolunteers.cfg2016.backend.domain.SomeObject;
import org.devolunteers.cfg2016.backend.services.SampleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/someObject")
public class SomeObjectController {
	
	ApplicationContext dataSourceContext = new ClassPathXmlApplicationContext("Beans.xml");
	SampleService reportsService = (SampleService) dataSourceContext.getBean("reportsService");
	
	@RequestMapping(
			value = "/allObjects", 
			method = RequestMethod.GET)
	public List<SomeObject> allCrimes() {
		return reportsService.getSomeObjects();
	}
	
	@RequestMapping(
			value = "/storeObject",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void storeReport(@RequestBody SomeObject someObject) {
		reportsService.storeSomeObject(someObject);
	}
}
