package org.devolunteers.cfg2016.backend.twilio;
import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.factory.CallFactory;
 
public class CallMakingService {
	TwilioRestClient client;
	
	// alice can make calls in many languages.. see https://www.twilio.com/docs/api/twiml/say for list
	private static final String CALL_RESPONSE_FORMAT = "<Response><Pause length=\"2\"/><Say voice=\"alice\" language=\"en-GB\">%s</Say></Response>";
	
	// initialise the calling service
	public CallMakingService() {
		this.client = new TwilioRestClient(TwilioConstants.ACCOUNT_SID, TwilioConstants.AUTH_TOKEN);
	}
  // make call to number "to", say text "sayText"
    public String makeCall(String to, String sayText) throws TwilioRestException {
        Account mainAccount = client.getAccount();
        CallFactory callFactory = mainAccount.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("To", to); // Replace with your phone number
        callParams.put("From", TwilioConstants.FROM_NUMBER); // Replace with a Twilio number
        
        
        String responseURL = String.format(CALL_RESPONSE_FORMAT, sayText);
        responseURL = URLEncoder.encode(responseURL);
        callParams.put("Url", "http://twimlets.com/echo?Twiml=" + responseURL);
        // Make the call
        Call call = callFactory.create(callParams);

        return call.getSid();
    }
}