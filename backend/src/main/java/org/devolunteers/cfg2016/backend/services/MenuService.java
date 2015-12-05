package org.devolunteers.cfg2016.backend.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.twilio.sdk.verbs.Gather;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

public class MenuService {

	private NamedParameterJdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
	}

	String[] menu1Portuguese = { "albinismo", "deficiência visual", "autismo",
			"doença crônica", "deficiência intelectual", "dificuldade de aprendizagem",
			"perda de memória", "deficiência física", "doença mental",
			"fala e linguagem desordem" };
	
	String[] menu2Portuguese = { "assédio", "rejeição escola", "área inacessível" };
	
	String[] menu3Portuguese = { "sim", "não" };
	
	String[] menu1English = { "albinism", "visual impairment", "autism",
			"chronic illness", "intelectual disability", "learning disability",
			"memory loss", "physical disability", "mental illness",
			"speech and language disorder" };
	String[] menu2English = { "harassment", "school rejection", "inaccessible area" };
	String[] menu3English = { "yes", "no" };

	static String BASE = "http://devolunteers.org/api/voice-menu";
	static String HANDLE_MAIN_MENU = BASE + "/handle-main-menu";
	static String HANDLE_SUB_1 = BASE + "/handle-sub-1";
	static String HANDLE_SUB_2 = BASE + "/handle-sub-2";

	public String mainMenu(Language lang) {
		if (lang == Language.PORTUGUESE) return mainMenuPortuguese();
		else return mainMenu();
	}
	
	public String mainMenu () {
		TwiMLResponse response = new TwiMLResponse();

		try {
			// no destinationno
			Gather gather = new Gather();
			gather.setAction(HANDLE_MAIN_MENU);
			gather.setNumDigits(1);
			gather.setMethod("GET");
			String s = "";
			
			for (int i = 0; i < menu1English.length; i++) {
				s += "If you have " + menu1English[i] + " press " + (i + 1) + ". ";					
			}
	
			gather.append(new Say(s));
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}
	
	public String mainMenuPortuguese () {
		TwiMLResponse response = new TwiMLResponse();

		try {
			// no destinationno
			Gather gather = new Gather();
			gather.setAction(HANDLE_MAIN_MENU);
			gather.setNumDigits(1);
			gather.setMethod("GET");
			String s = "";
			for (int i = 0; i < menu1Portuguese.length; i++) {
				s += "Se você tem " + menu1Portuguese[i] + " pressione " + (i + 1) + ". ";					
			}
			Say say = new Say("");
			try {
				say = new Say(URLEncoder.encode(s, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			say.setLanguage("pt-BR");
			say.setVoice("alice");
			gather.append(say);
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}

	public String handleMainMenu(String digit, Language lang) {
		if (lang == Language.PORTUGUESE){
			return handleMainMenuPortuguese(digit);
		} else {
			return handleMainMenu(digit);
		}
	}
	
	public String handleMainMenu(String digit){
		
		TwiMLResponse response = new TwiMLResponse();

		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		index--;
		try {
			if (index < menu1English.length)
				response.append(new Say("You chose "
						+ menu1English[index]));
			else {
				response.append(new Say("No such option, please try again. "));
				Gather gather = new Gather();
				gather.setAction(HANDLE_MAIN_MENU);
				gather.setNumDigits(1);
				gather.setMethod("GET");
				String s = "";
				for (int i = 0; i < menu1English.length; i++) {
					s += "If you have " + menu1English[i] + " press " + (i + 1) + ". ";
				}
				gather.append(new Say(s));

				return (response.toXML());

			}

			/*
			 * switch (index) { case 1: // push to database that the user is
			 * albian response.append(new Say("In the main menu you've chosen "
			 * + index)); break; case 2: // store in db that the user is blind
			 * response.append(new Say("In the main menu you've chosen " +
			 * index)); break; case 3: response.append(new
			 * Say("In the main menu you've chosen " + index)); break; case 4:
			 * response.append(new Say("In the main menu you've chosen " +
			 * index)); break; case 5: response.append(new
			 * Say("In the main menu you've chosen " + index)); break; default:
			 * Say say6 = new Say("No such option, please try again. ");
			 * response.append(say6); Gather gather = new Gather();
			 * gather.setAction(HANDLE_MAIN_MENU); gather.setNumDigits(1);
			 * gather.setMethod("GET"); Say say = new Say(
			 * "For albinism, press 1. For blindness choose 2. For wheel-chair press 3."
			 * + "For disability 4 press 4. For disability 5 press 5.");
			 * response.append(gather); gather.append(say);
			 * 
			 * return (response.toXML());
			 * 
			 * }
			 */

		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// no destination
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_1 + "?mainMenuChoice=" + (index+1));
			gather.setNumDigits(1);
			gather.setMethod("GET");
			String s = "";
			for (int i = 0; i < menu2English.length; i++) {
				s += "To report " + menu2English[i] + " press " + (i + 1) + ". ";
			}
			gather.append(new Say(s));
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}
	
public String handleMainMenuPortuguese (String digit){
		
		TwiMLResponse response = new TwiMLResponse();

		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		index--;
		try {
			if (index < menu1Portuguese.length)
				response.append(new Say("Você escolhe "
						+ menu1Portuguese[index]));
			else {
				response.append(new Say("Sem tal opção, por favor, tente novamente. "));
				Gather gather = new Gather();
				gather.setAction(HANDLE_MAIN_MENU);
				gather.setNumDigits(1);
				gather.setMethod("GET");
				String s = "";
				for (int i = 0; i < menu1Portuguese.length; i++) {
					s += "Se você tem " + menu1Portuguese[i] + " pressione " + (i + 1) + ". ";
				}
				gather.append(new Say(s));

				return (response.toXML());

			}

			/*
			 * switch (index) { case 1: // push to database that the user is
			 * albian response.append(new Say("In the main menu you've chosen "
			 * + index)); break; case 2: // store in db that the user is blind
			 * response.append(new Say("In the main menu you've chosen " +
			 * index)); break; case 3: response.append(new
			 * Say("In the main menu you've chosen " + index)); break; case 4:
			 * response.append(new Say("In the main menu you've chosen " +
			 * index)); break; case 5: response.append(new
			 * Say("In the main menu you've chosen " + index)); break; default:
			 * Say say6 = new Say("No such option, please try again. ");
			 * response.append(say6); Gather gather = new Gather();
			 * gather.setAction(HANDLE_MAIN_MENU); gather.setNumDigits(1);
			 * gather.setMethod("GET"); Say say = new Say(
			 * "For albinism, press 1. For blindness choose 2. For wheel-chair press 3."
			 * + "For disability 4 press 4. For disability 5 press 5.");
			 * response.append(gather); gather.append(say);
			 * 
			 * return (response.toXML());
			 * 
			 * }
			 */

		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// no destination
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_1 + "?mainMenuChoice=" + (index+1));
			gather.setNumDigits(1);
			gather.setMethod("GET");
			String s = "";
			for (int i = 0; i < menu2Portuguese.length; i++) {
				s += "Para relatar " + menu2Portuguese[i] + " pressione " + (i + 1) + ". ";
			}
			gather.append(new Say(s));
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}
	

	public String handleSub1(String digit, String mainMenuChoice, Language lang) {
		if (lang == Language.PORTUGUESE) return handleSub1Portuguese (digit, mainMenuChoice);
		else return handleSub1 (digit, mainMenuChoice);
	}
	
	public String handleSub1(String digit, String mainMenuChoice) {
		TwiMLResponse response = new TwiMLResponse();

		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		index--;
		try {
			if (index < menu2English.length)
				response.append(new Say("You chose "+ menu1English[Integer.parseInt(mainMenuChoice)-1]+" then "
						+ menu2English[index]));
			else {
				response.append(new Say("No such option, please try again. "));
				Gather gather = new Gather();
				gather.setAction(HANDLE_SUB_2 + "?mainMenuChoice="
						+ mainMenuChoice + "&amp;sub1MenuChoice=" + (index+1));
				gather.setNumDigits(1);
				gather.setMethod("GET");
				String s = "";
				for (int i = 0; i < menu2English.length; i++) {
					s += "To report " + menu2English[i] + " press " + (i + 1) + ". ";
				}
				gather.append(new Say(s));
				response.append(gather);
				return (response.toXML());

			}

			
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// no destination
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_2 + "?mainMenuChoice=" + mainMenuChoice
					+ "&amp;sub1MenuChoice=" + (index+1));
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

	public String handleSub1Portuguese (String digit, String mainMenuChoice) {
		TwiMLResponse response = new TwiMLResponse();

		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		index--;
		try {
			if (index < menu2Portuguese.length)
				response.append(new Say("Você escolhe "+ menu1Portuguese[Integer.parseInt(mainMenuChoice)-1]+" then "
						+ menu2Portuguese[index]));
			else {
				response.append(new Say("No such option, please try again. "));
				Gather gather = new Gather();
				gather.setAction(HANDLE_SUB_2 + "?mainMenuChoice="
						+ mainMenuChoice + "&amp;sub1MenuChoice=" + (index+1));
				gather.setNumDigits(1);
				gather.setMethod("GET");
				String s = "";
				for (int i = 0; i < menu2Portuguese.length; i++) {
					s += "Para relatar " + menu2Portuguese[i] + " pressione " + (i + 1) + ". ";
				}
				gather.append(new Say(s));
				response.append(gather);
				return (response.toXML());

			}

			
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// no destination
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_2 + "?mainMenuChoice=" + mainMenuChoice
					+ "&amp;sub1MenuChoice=" + (index+1));
			gather.setNumDigits(1);
			gather.setMethod("GET");
			Say say = new Say(
					"A questão requer ação imediata? Se sim, pressione 1. Se não, prima 2.");
			gather.append(say);
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}
	
	public String handleSub2(String digit, String mainMenuChoice,
			String sub1MenuChoice, Language lang) {
		if (lang == Language.PORTUGUESE) return handleSub2Portuguese(digit, mainMenuChoice, sub1MenuChoice);
		else return handleSub2(digit, mainMenuChoice, sub1MenuChoice);
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
								"You reported there is %s that requires immediate action.. Thank you, we will act on it.",
								menu2English[Integer.parseInt(sub1MenuChoice) - 1]));
				response.append(say1);
				break;
			case 2:
				Say say2 = new Say(
						String.format(
								"You reported there is %s.. Thank you, your report has been stored.",
								menu2English[Integer.parseInt(sub1MenuChoice) - 1]));
				response.append(say2);
				break;
			}
		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());

	}
	public String handleSub2Portuguese(String digit, String mainMenuChoice,
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
								"Você informou que há %s que exige uma acção imediata... Obrigado, vamos agir sobre ela.",
								menu2Portuguese[Integer.parseInt(sub1MenuChoice) - 1]));
				response.append(say1);
				break;
			case 2:
				Say say2 = new Say(
						String.format(
								"Você informou que há %s.. Obrigado, seu relatório foi armazenado.",
								menu2Portuguese[Integer.parseInt(sub1MenuChoice) - 1]));
				response.append(say2);
				break;
			}
		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());

	}
}
