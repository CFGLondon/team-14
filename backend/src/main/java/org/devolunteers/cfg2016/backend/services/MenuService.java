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
	// setting up the data for portuguese language
	String[] menu1Portuguese = { "albinismo", "deficiência visual", "autismo",
			"doença crônica", "deficiência intelectual", "dificuldade de aprendizagem",
			"perda de memória", "deficiência física", "doença mental",
			"fala e linguagem desordem" };
	
	String[] menu2Portuguese = { "assédio", "rejeição escola", "área inacessível" };
	
	String[] menu3Portuguese = { "sim", "não" };
	

	// setting up the data for english language
	String[] menu1English = { "albinism", "visual impairment", "autism",
			"chronic illness", "intelectual disability", "learning disability",
			"memory loss", "physical disability", "mental illness",
			"speech and language disorder" };
	String[] menu2English = { "harassment", "school rejection", "inaccessible area" };
	String[] menu3English = { "yes", "no" };

	// urls to link the menus 
	static String BASE = "http://devolunteers.org/api/voice-menu";
	static String HANDLE_MAIN_MENU = BASE + "/handle-main-menu";
	static String HANDLE_SUB_1 = BASE + "/handle-sub-1";
	static String HANDLE_SUB_2 = BASE + "/handle-sub-2";

	// function to initialise the main menu with given language
	public String mainMenu(Language lang) {
		if (lang == Language.PORTUGUESE) return mainMenuPortuguese();
		else return mainMenu();
	}
	
	// function to initialise the main menu in English
	public String mainMenu () {
		TwiMLResponse response = new TwiMLResponse();

		try {
			// create GATHER element
			Gather gather = new Gather();
			gather.setAction(HANDLE_MAIN_MENU+"?language=english");
			gather.setNumDigits(1);
			gather.setMethod("GET");
			String s = "";
			// generate text to tell the caller
			for (int i = 0; i < menu1English.length; i++) {
				s += "If you have " + menu1English[i] + " press " + (i + 1) + ". ";					
			}
			
			// append the gather elements into the response file
			gather.append(new Say(s));
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}
	
	// function to initialise main menu in portuguese
	public String mainMenuPortuguese () {
		TwiMLResponse response = new TwiMLResponse();

		try {
			// create GATHER element with appropriate arguments
			Gather gather = new Gather();
			gather.setAction(HANDLE_MAIN_MENU+"?language=portugese");
			gather.setNumDigits(1);
			gather.setMethod("GET");
			String s = "";
			// generate text to read
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
			// set language to Portuguese
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
	// function to handle the response from main menu in English
	public String handleMainMenu(String digit){
		
		TwiMLResponse response = new TwiMLResponse();

		int index = -1;
		// get the number typed in previous menu
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		index--;
		try {
			// reiterate what has been entered
			if (index < menu1English.length)
				response.append(new Say("You chose "
						+ menu1English[index]));
			// if incorrect option has been submitted, return to the previous menu
			else {
				response.append(new Say("No such option, please try again. "));
				Gather gather = new Gather();
				gather.setAction(HANDLE_MAIN_MENU+"?language=english");
				gather.setNumDigits(1);
				gather.setMethod("GET");
				String s = "";
				for (int i = 0; i < menu1English.length; i++) {
					s += "If you have " + menu1English[i] + " press " + (i + 1) + ". ";
				}
				gather.append(new Say(s));

				return (response.toXML());

			}

			

		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// create another menu structure (sub-menu)
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_1+"?language=english" + "&amp;mainMenuChoice=" + (index+1));
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
	
	// function to handle the response from main menu in Portuguese
public String handleMainMenuPortuguese (String digit){
		
		TwiMLResponse response = new TwiMLResponse();
		// get the previous choice
		int index = -1;
		try {
			index = Integer.parseInt(digit);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		index--;
		// reiterate what has been selected
		try {
			if (index < menu1Portuguese.length){
				Say say = new Say("");
				String s= "Você escolhe "
						+ menu1Portuguese[index];
				try {

					// strip of all the special characters
					say = new Say(URLEncoder.encode(s, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				say.setLanguage("pt-BR");
				say.setVoice("alice");
				response.append(say);
			}
			// if wrong choice was selected return to previous menu
			else {
				Say say = new Say("");
				String s= "Sem tal opção, por favor, tente novamente. ";
				// strip of all the special characters
				try {
					say = new Say(URLEncoder.encode(s, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				say.setLanguage("pt-BR");
				say.setVoice("alice");
				response.append(say);
				Gather gather = new Gather();
				gather.setAction(HANDLE_MAIN_MENU+"?language=portugese");
				gather.setNumDigits(1);
				gather.setMethod("GET");
				s = "";
				for (int i = 0; i < menu1Portuguese.length; i++) {
					s += "Se você tem " + menu1Portuguese[i] + " pressione " + (i + 1) + ". ";
				}
				Say say2 = new Say("");
				try {
					// strip of all the special characters
					say2 = new Say(URLEncoder.encode(s, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// configure language settings
				say2.setLanguage("pt-BR");
				say2.setVoice("alice");
				gather.append(say2);
				response.append(gather);

				return (response.toXML());

			}


		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			// progress to the next sub-menu
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_1+"?language=portugese" + "&amp;mainMenuChoice=" + (index+1));
			gather.setNumDigits(1);
			gather.setMethod("GET");
			String s = "";
			for (int i = 0; i < menu2Portuguese.length; i++) {
				s += "Para relatar " + menu2Portuguese[i] + " pressione " + (i + 1) + ". ";
			}
			Say say = new Say("");
			try {
				// strip of all the special characters
				say = new Say(URLEncoder.encode(s, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// configure language settings
			say.setLanguage("pt-BR");
			say.setVoice("alice");
			gather.append(say);
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}
	
	//function to call sub-menu with language argument
	public String handleSub1(String digit, String mainMenuChoice, Language lang) {
		if (lang == Language.PORTUGUESE) return handleSub1Portuguese (digit, mainMenuChoice);
		else return handleSub1 (digit, mainMenuChoice);
	}
	
	// function to handle response from sub-menu in English
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
				gather.setAction(HANDLE_SUB_2+"?language=english" + "&amp;mainMenuChoice="
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
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_2+"?language=english" + "&amp;mainMenuChoice=" + mainMenuChoice
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
	// handle response from sub-menu in Portuguese
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
			if (index < menu2Portuguese.length){
				Say say = new Say("");
				try {
					String s = "Você escolhe "+ menu1Portuguese[Integer.parseInt(mainMenuChoice)-1]+" então "
							+ menu2Portuguese[index];
					// strip of all the special characters
					say = new Say(URLEncoder.encode(s, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// configure language settings
				say.setLanguage("pt-BR");
				say.setVoice("alice");
				response.append(say);
			}
				
			else {
				String s1 = "Sem tal opção , por favor, tente novamente.";
				Say say1 = new Say("");
				try {
					say1 = new Say(URLEncoder.encode(s1, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				response.append(say1);
				Gather gather = new Gather();
				gather.setAction(HANDLE_SUB_2+"?language=portugese" + "&amp;mainMenuChoice="
						+ mainMenuChoice + "&amp;sub1MenuChoice=" + (index+1));
				gather.setNumDigits(1);
				gather.setMethod("GET");
				String s = "";
				for (int i = 0; i < menu2Portuguese.length; i++) {
					s += "Para relatar " + menu2Portuguese[i] + " pressione " + (i + 1) + ". ";
				}
				Say say = new Say("");
				try {
					// strip of all the special characters
					say = new Say(URLEncoder.encode(s, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// configure language settings
				say.setLanguage("pt-BR");
				say.setVoice("alice");
				gather.append(say);
				response.append(gather);
				return (response.toXML());

			}

			
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		try {
			Gather gather = new Gather();
			gather.setAction(HANDLE_SUB_2+"?language=portugese" + "&amp;mainMenuChoice=" + mainMenuChoice
					+ "&amp;sub1MenuChoice=" + (index+1));
			gather.setNumDigits(1);
			gather.setMethod("GET");
			Say say = new Say("");
					
			String s = "A questão requer ação imediata? Se sim, pressione 1. Se não, prima 2.";
			try {
				// strip of all the special characters
				say = new Say(URLEncoder.encode(s, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// configure language settings
			say.setLanguage("pt-BR");
			say.setVoice("alice");
			gather.append(say);
			response.append(gather);

		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());
	}
	 // function to call second sub-menu with language parameter
	public String handleSub2(String digit, String mainMenuChoice,
			String sub1MenuChoice, Language lang) {
		if (lang == Language.PORTUGUESE) return handleSub2Portuguese(digit, mainMenuChoice, sub1MenuChoice);
		else return handleSub2(digit, mainMenuChoice, sub1MenuChoice);
	}
	// function to initial second sub-menu in English
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
			// only two options - yes/no
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

	// function to initial second sub-menu in Portuguese
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
			// only two options - yes/no
			switch (index) {
			case 1:
				Say say1 = new Say("");
				String s =	String.format(
								"Você informou que há %s que exige uma acção imediata... Obrigado, vamos agir sobre ela.",
								menu2Portuguese[Integer.parseInt(sub1MenuChoice) - 1]);
				try {
					say1 = new Say(URLEncoder.encode(s, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// configure language settings
				say1.setLanguage("pt-BR");
				say1.setVoice("alice");
				response.append(say1);
				break;
			case 2:
				Say say2 = new Say("");
				// strip of special characters
				String s2 =	String.format(
						"Você informou que há %s.. Obrigado, seu relatório foi armazenado.",
						menu2Portuguese[Integer.parseInt(sub1MenuChoice) - 1]);
				try {
					say2 = new Say(URLEncoder.encode(s2, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// configure language settings
				say2.setLanguage("pt-BR");
				say2.setVoice("alice");
				response.append(say2);
				break;
			}
		} catch (TwiMLException e) {
			e.printStackTrace();
		}

		return (response.toXML());

	}
}
