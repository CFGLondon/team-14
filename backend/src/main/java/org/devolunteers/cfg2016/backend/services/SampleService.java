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
	

	
	static String BASEURL="http://devolunteers.org/api/someObject/menu1";
	
	public String getTwiML(String digits) {
		int index;
        try
        	{index = Integer.parseInt(digits);}
        catch (NumberFormatException nfe)
        	{ index=-1;}
        

		// @start snippet
		/* Define Menu */
		String[] defaultMenu = {"albinism", "wheel-chair", "blidness"};

        Map<String,String[]> menuDefinition = new HashMap<String,String[]>();
        menuDefinition.put("default", defaultMenu);

		TwiMLResponse response = new TwiMLResponse();

        try {
			if (index==1){
		        Say say = new Say("You chose albinism.. It's a condition where you ain't got no pigment.");
				
				response.append(say);
			} else if (index==2){
				Say say = new Say("You chose wheel chair");
				
				SMSSendingService sss;
				try {
					sss = new SMSSendingService();
					sss.sendMessage("+447574155899", "wheel-chair");
				} catch (TwilioRestException e) {
					e.printStackTrace();
				}
				
				response.append(say);
			} else if (index==3){
				Say say = new Say("You chose blindness");
				
				SMSSendingService sss;
				try {
					sss = new SMSSendingService();
					sss.sendMessage("+447574155899", "blidness");
				} catch (TwilioRestException e) {
					e.printStackTrace();
				}
				
				response.append(say);
			} else { 
				// no destination
				Gather gather = new Gather();
				gather.setAction(BASEURL);
				gather.setNumDigits(1);
				gather.setMethod("GET");
				Say say = new Say("For albinism, press 1. For wheel-chair press 2. For Blindness choose 3.");
				gather.append(say);
				response.append(gather);
			}

        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        return(response.toXML());
      }
}
