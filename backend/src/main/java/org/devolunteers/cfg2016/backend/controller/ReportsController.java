package org.devolunteers.cfg2016.backend.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reports")
public class ReportsController {
	
	ApplicationContext dataSourceContext = new ClassPathXmlApplicationContext("Beans.xml");
	SampleService sampleService = (SampleService) dataSourceContext.getBean("sampleService");
	DBService dbService = (DBService) dataSourceContext.getBean("dbService");
	
	@RequestMapping(
			value = "/submit", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean handleSub2(@RequestParam Map<String,String> allRequestParams) {
		
		dbService.storeReport(
				allRequestParams.get("latitude"),
				allRequestParams.get("longitude"),
				allRequestParams.get("disability"),
				allRequestParams.get("country"),
				allRequestParams.get("actionRequired"),
				allRequestParams.get("comments")
				);
		return true;
	}
}
