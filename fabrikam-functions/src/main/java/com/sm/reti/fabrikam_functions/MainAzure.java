package com.sm.reti.fabrikam_functions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, IOException {
	    //You will create these methods later in the quickstart.
		ArrayList<RistoBean> ristoranti = new ArrayList<RistoBean>();
		//ristoranti = readJson();
		//System.out.println(ristoranti.size());
		

	    //TextAnalyticsClient client = TextAnalyticsSamples.authenticateClient(KEY, ENDPOINT);
		Process p = Runtime.getRuntime().exec("python "+"file_list\\ProgettoReti\\LanguageDetector.py "+" Πήραμε δύο παραδοσιακά πιάτα, το οσομπούκο μιλανέζε και τη κοτολέτα. Οι μερίδες τεράστιες και το φαγητό πολύ νόστιμο. Η εξυπηρέτηση πολύ καλή. Στην αρχή μας έφεραν ένα ορεκτικό παραδοσιακό να δοκιμάσουμε και στο τέλος του γεύματος ένα πιατάκια με διάφορα μπισκότα.");
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String temp ="";
		while((temp = stdInput.readLine()) != null) {
			        System.out.println(temp);
			  }
		
		
	   /* TextAnalyticsClient client = TextAnalyticsSamples.authenticateClient(KEY, ENDPOINT);
	    TextAnalyticsSamples.sentimentAnalysisWithOpinionMiningExample(client);*/
	    //detectLanguageExample(client);
	    //TextAnalyticsSamples.sentimentAnalysisExample(client, text); 
	    //extractKeyPhrasesExample(client);
	}
	
	/*Legge il JSON e riempi beans*/
	static ArrayList<RistoBean> readJson() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("file_list\\milano.json"));
		JsonArray jsonArray = (JsonArray) obj;
		ArrayList<RistoBean> arrayRisto = new ArrayList<RistoBean>();
		int i = 0;
		for (JsonElement element : jsonArray) {
			if(i < 10) {
				RistoBean risto = new RistoBean();
				risto.setNomeRistorante(getNomeRisto(element));
				risto.setRate(getRatingRisto(element));
				risto.setCucina(getCucina(element));
				risto.setRecensioni(getReviews(element));
				risto.setCity("Milano");
				arrayRisto.add(risto);
				i++;
			}
		}
		return arrayRisto;
	}
	
	static String getNomeRisto(JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();
		return jsonObject.get("name").getAsString();
	}
	
	static float getRatingRisto(JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();
		return jsonObject.get("rating").getAsFloat();
	}
	
	static ArrayList<String> getCucina(JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();
		JsonArray cucList = jsonObject.get("cuisine").getAsJsonArray();	
		ArrayList<String> cucina = new ArrayList<String>();
		for (JsonElement cuc:cucList) {
			cucina.add(cuc.getAsString());		
		}
		return cucina;
	}
	
	static ArrayList<ReviewsBean> getReviews(JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();
		JsonArray revList = jsonObject.get("reviews").getAsJsonArray();	
		ArrayList<ReviewsBean> reviews = new ArrayList<ReviewsBean>();
		int j = 0;
		for (JsonElement rev : revList) {
			if(j < 15) {
				ReviewsBean rb = new ReviewsBean();
				JsonObject review = rev.getAsJsonObject();
				rb.setTitolo(review.get("title").getAsString());
				rb.setCorpo(review.get("text").getAsString());
				rb.setRateReview(review.get("rating").getAsFloat());
				if(!review.get("userLocation").isJsonNull()) {
					rb.setUserLocation(review.get("userLocation").getAsString());
				} else {
					rb.setUserLocation("Non localizzato");
				}
				reviews.add(rb);
			}
		}
		return reviews;
	}
}
