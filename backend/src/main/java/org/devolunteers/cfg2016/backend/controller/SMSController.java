package org.devolunteers.cfg2016.backend.controller;


import java.io.IOException;
import java.util.Map;

import org.devolunteers.cfg2016.backend.services.DBService;
import org.devolunteers.cfg2016.backend.twilio.SMSSendingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.sdk.TwilioRestException;

@RestController
@RequestMapping(value = "/sms")
public class SMSController {
	
	ApplicationContext dataSourceContext = new ClassPathXmlApplicationContext("Beans.xml");
	DBService dbService = (DBService) dataSourceContext.getBean("dbService");
	
	@RequestMapping(value = "/receivesms", method = RequestMethod.POST)
	public @ResponseBody String receivesms(@RequestParam Map<String, String> params) throws TwilioRestException, IOException {
		
		System.err.printf("Encountered new message!! '%s' Adding it to DB%n", params.get("MessageSid"));
		if (!dbService.hasSMSFrom(params.get("From"))) {
			String welcomeSMS = "ADD is here to help! \n"
							  + "It will help the community if you can tell us about yourself. Can you please tell us what condition or disability do you have?\n";
							  
			SMSSendingService sms = new SMSSendingService();
			sms.sendMessage(params.get("From"), welcomeSMS);
			dbService.initPerson(params.get("From"));
		} else {
			
			int currentStage = dbService.getAndIncrementPersonStage(params.get("From"));
			if (currentStage == 0) {
				
				dbService.getAndIncrementPersonStage(params.get("From"));
				String thankYouSMS = "Thank you for sharing that with us, your voice is apreciated. Feel free to text or call us at any point about any issues you encounter. Text the word issue to get going.";
						  
				SMSSendingService sms = new SMSSendingService();
				sms.sendMessage(params.get("From"), thankYouSMS);
			} else {
				
				String issueSMS = "We have recieved your feedback, your help to spread awareness is valued!";
				SMSSendingService sms = new SMSSendingService();
				sms.sendMessage(params.get("From"), issueSMS);
			}
		}
		return "";
	}
	@RequestMapping(value = "/receivesms-foreign", method = RequestMethod.POST)
	public @ResponseBody String receivesmsForeign(@RequestParam Map<String, String> params) throws TwilioRestException, IOException {
		
		if (!dbService.hasSMSFrom(params.get("From"))) {
			String welcomeSMS = "ADD está aqui para ajudar! \n"
							  + "Ele vai ajudar a comunidade se você pode nos dizer sobre si mesmo. Você pode por favor diga-nos o que condição ou deficiência que você tem?";
							  
			SMSSendingService sms = new SMSSendingService();
			sms.sendMessageForeign(params.get("From"), welcomeSMS);
			dbService.initPerson(params.get("From"));
		} else {
			
			int currentStage = dbService.getAndIncrementPersonStage(params.get("From"));
			if (currentStage == 0) {
				
				dbService.getAndIncrementPersonStage(params.get("From"));
				String thankYouSMS = "Obrigado por compartilhar isso com a gente , a sua voz é apreciado . Sinta-se livre para texto ou ligue para nós em qualquer ponto sobre quaisquer problemas que encontrar . Texto a palavra questão de ir.";
						  
				SMSSendingService sms = new SMSSendingService();
				sms.sendMessageForeign(params.get("From"), thankYouSMS);
			} else {
				
				String issueSMS = "Temos recebido seu feedback, de sua ajuda para difundir o conhecimento é valorizado !";
				SMSSendingService sms = new SMSSendingService();
				sms.sendMessageForeign(params.get("From"), issueSMS);
			}
		}
		return "";
	}
}

