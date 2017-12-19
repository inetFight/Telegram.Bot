package com.novaposhta.telegram.bot.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Warehouse {

	public static String findNearestWarehouse(double Latitude, double Longitude) throws URISyntaxException, ClientProtocolException, IOException, ParseException{
		String latitude = Double.toString(Latitude);
		String longitude= Double.toString(Longitude);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder("https://api.novaposhta.ua/v2.0/json/");
		URI uri = builder.build();
		HttpPost request = new HttpPost(uri);
		StringEntity reqEntity = new StringEntity("{\"modelName\": \"Address\","
												+ "\"calledMethod\": \"findNearestWarehouse\","
												+ "\"methodProperties\": {"
												+ "\"SearchStringArray\": ["
												+ "\"" + latitude + "," + longitude + "\""
												+ "]}}");
		request.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(request);
		
		String otvet = EntityUtils.toString(response.getEntity(), "UTF-8");
//		System.out.println(EntityUtils.toString(reqEntity, "UTF-8"));
//		System.out.println(otvet);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(otvet);
		
		JSONArray data = (JSONArray) jsonObject.get("data");
		JSONObject innerObj = (JSONObject) data.get(0);
		JSONArray array = (JSONArray) innerObj.get("0");
		
		JSONObject Obj = null;
		if(!array.isEmpty()){
			Obj = (JSONObject) array.get(1);
			return (String) Obj.get("AddressRef");
		}
		
		return null;
		
	}
	
	public static JSONObject getInfoWarehouse(String AddressRef) throws URISyntaxException, ClientProtocolException, IOException, ParseException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder("https://api.novaposhta.ua/v2.0/json/");
		URI uri = builder.build();
		HttpPost request = new HttpPost(uri);
		StringEntity reqEntity = new StringEntity("{\"modelName\": \"Address\","
												+ "\"calledMethod\": \"getWarehouses\","
												+ "\"methodProperties\": {"
												+ "\"Ref\":\"" + AddressRef + "\""
												+ "}}");
		request.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(request);
		
		String otvet = EntityUtils.toString(response.getEntity(), "UTF-8");
//		System.out.println(EntityUtils.toString(reqEntity, "UTF-8"));
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(otvet);
//		System.out.println( AddressRef + " -" + otvet);
		JSONArray data = (JSONArray) jsonObject.get("data");
		JSONObject innerObj = null;
		if(!data.isEmpty()){
		innerObj = (JSONObject) data.get(0);
		
		}
		return innerObj;
	}
}
