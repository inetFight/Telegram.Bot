package com.novaposhta.telegram.bot.helper;
import java.util.HashMap;
import java.util.Map;

import org.telegram.telegrambots.api.objects.Message;

import com.novaposhta.telegram.bot.main.App;

public class UserLanguage {
	
	public static Map<Integer, String> usersLanguages = new HashMap<>();
	
	
	public static String getUserLanguage (Message msg){
		if(usersLanguages.get(msg.getFrom().getId()) == null)
		return "";
		else if(usersLanguages.get(msg.getFrom().getId()).equals("ua")){
			return "ua";
		}
		else {
			return "ru";
		}
		
	}
	
	public static boolean userHaslanguage (Message msg){
		if(usersLanguages.get(msg.getFrom().getId()) == null){
			return false;
		}
		else {
			return true;
		}
		
	}
	

}
