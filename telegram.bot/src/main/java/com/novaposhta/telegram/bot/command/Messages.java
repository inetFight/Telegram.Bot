package com.novaposhta.telegram.bot.command;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.novaposhta.telegram.bot.helper.UserLanguage;
import com.novaposhta.telegram.bot.helper.Utf8ResourceBundle;
public class Messages {

	public static String getStartMessage (){
		return "<b>Ласкаво просимо до бота Нова пошта.</b> \n"
				+ "За допомогою бота ви легко зможете скористатися найпопулярнішими сервісами компанії. \n"
				+ "Щоб розпочати, натисніть СТАРТ. \n\n"
				+ "Підтримка: support@novaposhta.ua";
		
	}
	
	public static String getLanguageMessage (){
		return "<b>Оберіть мову</b>";		
	}
	
	
	public static List<KeyboardRow> getLanguageMenu (){
	  List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		// Первая строчка клавиатуры
      KeyboardRow keyboardFirstRow = new KeyboardRow();
      // Добавляем кнопки в первую строчку клавиатуры
      keyboardFirstRow.add("Українська мова");
      keyboardFirstRow.add("Русский язык");
      keyboard.add(keyboardFirstRow);
		return keyboard;		
	}
	
	
	public static String getChoiceLanguageMessage (Message msg){
		ResourceBundle lang = Utf8ResourceBundle.getBundle("data_" + UserLanguage.getUserLanguage(msg));
		return lang.getString("ChoiceLanguageMessage");
	
	}
	
	public static String getErrorMessageChoiсeLanguage (Message msg){
		ResourceBundle lang = Utf8ResourceBundle.getBundle("data_" + UserLanguage.getUserLanguage(msg));
		return lang.getString("LanguageMessageError");
	
	}
	
	public static String getErrorMessage (){
		return "UnknownCommand";		
	}
	
	public static String getMainMenuMessage(Message msg){
		ResourceBundle lang = Utf8ResourceBundle.getBundle("data_" + UserLanguage.getUserLanguage(msg));
		return lang.getString("ChoiceItem");
	}
	
	public static List<KeyboardRow> getMailMenu (Message msg){
		  ResourceBundle lang = Utf8ResourceBundle.getBundle("data_" + UserLanguage.getUserLanguage(msg));
		  
		  List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		  
			// Первая строчка клавиатуры
	      KeyboardRow keyboardFirstRow = new KeyboardRow();
	      // Добавляем кнопки в первую строчку клавиатуры
	      keyboardFirstRow.add(lang.getString("FindWarehouse"));
	      keyboardFirstRow.add(lang.getString("Tracking"));
	      KeyboardRow keyboardSecondRow = new KeyboardRow();
	      // Добавляем кнопки во вторую строчку клавиатуры
	      keyboardSecondRow.add(lang.getString("CallCourier"));
	      keyboardSecondRow.add(lang.getString("Price"));
	      
	      keyboard.add(keyboardFirstRow);
	      keyboard.add(keyboardSecondRow);
	      
	      
			return keyboard;		
		}
	
	public static String getFindWarehouseMessage(Message msg){
		ResourceBundle lang = Utf8ResourceBundle.getBundle("data_" + UserLanguage.getUserLanguage(msg));
		return lang.getString("FindWarehouseLocation");
	}
	
	public static List<KeyboardRow> getFindWarehouseMenu(Message msg){
		ResourceBundle lang = Utf8ResourceBundle.getBundle("data_" + UserLanguage.getUserLanguage(msg));
		List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		KeyboardButton button = new KeyboardButton(lang.getString("FindWarehouseButton"));
		keyboardFirstRow.add(button);
		button.setRequestLocation(true);
		keyboard.add(keyboardFirstRow);
		return keyboard;
	}
	
	public static String getWarehouseMessageAnswer(Message msg, JSONObject obj){
		if(UserLanguage.getUserLanguage(msg).equals("ua")){
			return new String ((String) obj.get("Description"));
		}
		else if(UserLanguage.getUserLanguage(msg).equals("ru")){
			return new String ((String) obj.get("DescriptionRu"));
		}
		else {
			return new String ((String) obj.get("Description"));
		}
	}
	

}
