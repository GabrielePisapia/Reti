package com.sm.reti.fabrikam_functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.azure.ai.textanalytics.TextAnalyticsClient;

public class MainAzure {
	private static String ENDPOINT = "https://progettoreti.cognitiveservices.azure.com/";
	private static String KEY = "39f38062827f4b568dc1f4ca8ad528e6";
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
	    //You will create these methods later in the quickstart.
		String text= "Pizza was good";
		readJson();
	    /*TextAnalyticsClient client = TextAnalyticsSamples.authenticateClient(KEY, ENDPOINT);

	    //detectLanguageExample(client);
	    TextAnalyticsSamples.sentimentAnalysisExample(client,text); 
	    //extractKeyPhrasesExample(client);**/
	}
	
	/*Legge il JSON e riempi beans*/
	static void readJson () throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("C:\\Users\\Gabriele\\Downloads\\Telegram Desktop\\milano.json"));
		JsonArray jsonArray = (JsonArray) obj;
		for (JsonElement element : jsonArray) {
		JsonObject jsonObject = element.getAsJsonObject();

		String nomeristo = jsonObject.get("name").getAsString();
		
		JsonArray cucina = jsonObject.get("cuisine").getAsJsonArray();
		for (JsonElement cuc:cucina) {
			
			String tipiCucina = cuc.getAsString();
			System.out.println(tipiCucina);
			
		}
		System.out.println("----------------------------------------------------------------------------------"+"\n");
		JsonArray reviews = jsonObject.get("reviews").getAsJsonArray();

		for (JsonElement e: reviews) {
			JsonObject oggetto =e.getAsJsonObject();
			String titolo = oggetto.get("title").getAsString();
			/*String text = oggetto.get("text").getAsString();*/
			}
		}
	}
}
