package com.novaposhta.telegram.bot.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.novaposhta.telegram.bot.api.Warehouse;
import com.novaposhta.telegram.bot.command.Messages;
import com.novaposhta.telegram.bot.helper.UserLanguage;
import com.novaposhta.telegram.bot.helper.Utf8ResourceBundle;
import com.novaposhta.telegram.bot.main.App;

public class Bot extends TelegramLongPollingBot{

	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "Test_np_bot";
	}

	public void onUpdateReceived(Update e) {

		Message msg = e.getMessage(); // Это нам понадобится
		String txt = msg.getText();
		if(msg.getLocation() != null){
			try {
				String address = Warehouse.findNearestWarehouse(msg.getLocation().getLatitude(), msg.getLocation().getLongitude());
				if(address != null){
					JSONObject obj = Warehouse.getInfoWarehouse(address);
					sendMsg(msg, Messages.getWarehouseMessageAnswer(msg, obj), null);
				}
				else {
					sendMsg(msg, "Нифига не найдено", null);
				}
				
			
				
			} catch (URISyntaxException | IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		if(txt != null){
			switch (txt) {
			case "/start":
				sendMsg(msg, Messages.getStartMessage(), null);
				// Проверяем, выбрал ли пользователь уже язык
				if (!UserLanguage.userHaslanguage(msg)) {
					sendMsg(msg, Messages.getLanguageMessage(), Messages.getLanguageMenu());
				}
				else {
					sendMsg(msg, Messages.getMainMenuMessage(msg), Messages.getMailMenu(msg));
				}
				break;
			case "Українська мова":

				if (!UserLanguage.userHaslanguage(msg)) {
					UserLanguage.usersLanguages.put(msg.getFrom().getId(), "ua");

					sendMsg(msg, Messages.getChoiceLanguageMessage(msg), null);

				} else {

					sendMsg(msg, Messages.getErrorMessageChoiсeLanguage(msg), null);
				}

				sendMsg(msg, Messages.getMainMenuMessage(msg), Messages.getMailMenu(msg));
				break;
			case "Русский язык":

				if (!UserLanguage.userHaslanguage(msg)) {
					UserLanguage.usersLanguages.put(msg.getFrom().getId(), "ru");
					sendMsg(msg, Messages.getChoiceLanguageMessage(msg), null);

				} else {

					sendMsg(msg, Messages.getErrorMessageChoiсeLanguage(msg), null);
				}
				sendMsg(msg, Messages.getMainMenuMessage(msg), Messages.getMailMenu(msg));
				break;
			case "Пошук найближчого відділення":
				
				sendMsg(msg, Messages.getFindWarehouseMessage(msg), Messages.getFindWarehouseMenu(msg));
				break;
			case "Поиск ближайшего отделения":
				
				sendMsg(msg, Messages.getFindWarehouseMessage(msg), Messages.getFindWarehouseMenu(msg));
				break;
			default:
				ResourceBundle lang = Utf8ResourceBundle.getBundle("data_" + UserLanguage.getUserLanguage(msg));
				sendMsg(msg, lang.getString(Messages.getErrorMessage()), null);
				break;
			}
		}
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "482390109:AAGXEZ5mzLfZoOpECmScs2w2EsGUXVsDrGE";
	}
	
	private void sendMsg(Message msg, String text, List<KeyboardRow> keyboard) {
		
		SendMessage sendMessage = new SendMessage();
		sendMessage.setParseMode(ParseMode.HTML);
		 // Создаем клавиуатуру
		if(keyboard != null){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        replyKeyboardMarkup.setKeyboard(keyboard);
		}
        sendMessage.setChatId(msg.getChatId().toString());
//        sendMessage.setReplyToMessageId(msg.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		
		
		
	}

}
