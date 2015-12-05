package org.devolunteers.cfg2016.backend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.devolunteers.cfg2016.backend.domain.SomeObject;
import org.devolunteers.cfg2016.backend.twilio.SMSSendingService;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.verbs.Dial;
import com.twilio.sdk.verbs.Gather;
import com.twilio.sdk.verbs.Pause;
import com.twilio.sdk.verbs.Redirect;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

public class SampleService {
	
	private NamedParameterJdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
	}

	public void storeSomeObject(SomeObject someObject) {
		
	}

	public List<SomeObject> getSomeObjects() {

		List<SomeObject> results = new ArrayList<SomeObject>();

		return results;
	}
	
	static String BASE = "http://devolunteers.org/api/voice-menu";
	static String HANDLE_MAIN_MENU = BASE + "/handle-main-menu";
	static String HANDLE_SUB_1 = BASE + "/handle-sub-1";
	
	public String mainMenu() { 
		TwiMLResponse response = new TwiMLResponse();

        try {
        	// no destination
    		Gather gather = new Gather();
    		gather.setAction(HANDLE_MAIN_MENU);
    		gather.setNumDigits(1);
    		gather.setMethod("GET");
    		Say say = new Say("For albinism, press 1. For wheel-chair press 2. For Blindness choose 3.");
    		gather.append(say);
    		response.append(gather);

        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        return(response.toXML());
	}
	
	public String handleMainMenu(String digit) {
		TwiMLResponse response = new TwiMLResponse();
		
		int index = -1;
        try {index = Integer.parseInt(digit);}
        catch (NumberFormatException nfe)
        	{ nfe.printStackTrace();}

        try {
        	switch(index) {
	        	case 1:
	    			// submenu 1
	    			Gather gather = new Gather();
	        		gather.setAction(HANDLE_SUB_1);
	        		gather.setNumDigits(1);
	        		gather.setMethod("GET");
	        		Say say = new Say("You are in submenu 1, choose shit..");
	        		gather.append(say);
	        		response.append(gather);
	    			break;
	    		case 2:
	    			break;
	    		case 3:
	    			break;
	    		default:
	    			// unknown choice
        	}
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        return(response.toXML());
	}
	
	public String handleSub1(String digit) {
		return null;
	}
}
