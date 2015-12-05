package org.devolunteers.cfg2016.backend.services;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.devolunteers.cfg2016.backend.domain.SomeObject;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.twilio.sdk.verbs.Gather;
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

	String[] menu1 = { "albinism", "visual impairment", "wheel-chair",
			"disab4", "disab5" };
	String[] menu2 = { "harassment", "no medicine", "issue3", "issue4",
			"issue5" };
	String[] menu3 = { "yes", "no" };

	static String BASE = "http://devolunteers.org/api/voice-menu";
	static String HANDLE_MAIN_MENU = BASE + "/handle-main-menu";
	static String HANDLE_SUB_1 = BASE + "/handle-sub-1";
	static String HANDLE_SUB_2 = BASE + "/handle-sub-2";

	public String mainMenu() {
		TwiMLResponse response = new TwiMLResponse();

		try {
			// no destination
			Gather gather = new Gather();
			gather.setAction(HANDLE_MAIN_MENU);
			gather.setNumDigits(1);
			gather.setMethod("GET");
			Say say = new Say(
					"For albinism, press 1. For blindness choose 2. For wheel-chair press 3."
							+ "For disability 4 press 4. For disability 5 press 5.");
			gather.append(say);
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}

	public String handleMainMenu(String digit) {
		TwiMLResponse response = new TwiMLResponse();

		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

		try {
			switch (index) {
			case 1:
				// push to database that the user is albian
				response.append(new Say("In the main menu you've chosen "
						+ index));
				break;
			case 2:
				// store in db that the user is blind
				response.append(new Say("In the main menu you've chosen "
						+ index));
				break;
			case 3:
				response.append(new Say("In the main menu you've chosen "
						+ index));
				break;
			case 4:
				response.append(new Say("In the main menu you've chosen "
						+ index));
				break;
			case 5:
				response.append(new Say("In the main menu you've chosen "
						+ index));
				break;
			default:
				Say say6 = new Say("No such option, please try again. ");
				response.append(say6);
				Gather gather = new Gather();
				gather.setAction(HANDLE_MAIN_MENU);
				gather.setNumDigits(1);
				gather.setMethod("GET");
				Say say = new Say(
						"For albinism, press 1. For blindness choose 2. For wheel-chair press 3."
								+ "For disability 4 press 4. For disability 5 press 5.");
				response.append(gather);
				gather.append(say);

				return (response.toXML());

			}
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// no destination
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_1 + "?mainMenuChoice=" + index);
			gather.setNumDigits(1);
			gather.setMethod("GET");
			Say say = new Say(
					"To report harassment press 1. To report that you ran out of medicine press 2. To report issue 3 press 3."
							+ " To report issue 4 press 4.To report issue 5 press 5.");
			response.append(gather);
			gather.append(say);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}

	public String handleSub1(String digit, String mainMenuChoice) {

		TwiMLResponse response = new TwiMLResponse();

		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

		try {
			switch (index) {
			case 1:
				response.append(new Say(String.format(
						"You've chosen %s and then %d", mainMenuChoice, index)));
				break;
			case 2:
				response.append(new Say(String.format(
						"You've chosen %s and then %d", mainMenuChoice, index)));
				break;
			case 3:
				response.append(new Say(String.format(
						"You've chosen %s and then %d", mainMenuChoice, index)));
				break;
			case 4:
				response.append(new Say(String.format(
						"You've chosen %s and then %d", mainMenuChoice, index)));
				break;
			case 5:
				response.append(new Say(String.format(
						"You've chosen %s and then %d", mainMenuChoice, index)));
				break;
			default:
				Say say6 = new Say("No such option, please try again. ");
				response.append(say6);
				Gather gather = new Gather();
				gather.setAction(HANDLE_SUB_1);
				gather.setNumDigits(1);
				gather.setMethod("GET");
				Say say = new Say(
						"To report harassment press 1. To report that you ran out of medicine press 2. To report issue 3 press 3."
								+ " To report issue 4 press 4.To report issue 5 press 5.");
				gather.append(say);
				response.append(gather);

			}
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// no destination
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_2 + "?mainMenuChoice=" + mainMenuChoice
					+ "&amp;sub1MenuChoice=" + index);
			gather.setNumDigits(1);
			gather.setMethod("GET");
			Say say = new Say(
					"Does the issue require immediate action? If yes, press 1. If not, press 2.");
			gather.append(say);
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}

	public String handleSub2(String digit, String mainMenuChoice,
			String sub1MenuChoice) {
		TwiMLResponse response = new TwiMLResponse();
		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

		try {
			switch (index) {
			case 1:
				Say say1 = new Say(
						String.format(
								"Chose %s then %s then %d.. Thank you, immediate action will be taken.",
								mainMenuChoice, sub1MenuChoice, index));
				response.append(say1);
				break;
			case 2:
				Say say2 = new Say(
						String.format(
								"Chose %s then %s then %d.. Thank you, your issue has been submitted.",
								mainMenuChoice, sub1MenuChoice, index));
				response.append(say2);
				break;
			}
		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());

	}
}
