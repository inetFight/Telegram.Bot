package com.novaposhta.telegram.bot.main;

import java.util.HashMap;
import java.util.Map;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.novaposhta.telegram.bot.core.Bot;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main( String[] args ){
    	
    	System.out.println("Запущено");
    	ApiContextInitializer.init(); // Инициализируем апи
    	TelegramBotsApi botapi = new TelegramBotsApi();
    	try {
    		botapi.registerBot(new Bot());
    	} catch (TelegramApiException e) {
    		e.printStackTrace();
    	}
        
        
    }

	
	
}
