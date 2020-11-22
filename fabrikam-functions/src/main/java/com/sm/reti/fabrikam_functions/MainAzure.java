package com.sm.reti.fabrikam_functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	private static List<String> orientale_ =  Arrays.asList("asian", "japanese", "korean", "chinese", "sushi", "fujian");
	private static String pizzeria = "pizza";
	private static List<String> paninoteca_ =  Arrays.asList("hamburger", "pub", "brew pub"); 
	private static List<String> enoteca_ =  Arrays.asList("wine bar", "wine");
	private static List<String> bar_ =  Arrays.asList("bar", "cafe");
	private static String fast_food = "fast food";
	private static List<String> straniera_ =  Arrays.asList("greek", "egyptian", "moroccan", "tunisian", "turkish", "brazilian", "arabic", "ethiopian", "african", "mexican", "latin", "central american", "south american", "polish", "french");
	private static List<String> dolci_ = Arrays.asList("gelateria", "pasticceria", "gelato", "gelati", "dolce", "dolci", "cremeria");
	private static String ristorante = "ristorante";
	
	private static ArrayList<String> orientale = new ArrayList<String>();
	private static ArrayList<String> paninoteca = new ArrayList<String>();
	private static ArrayList<String> enoteca = new ArrayList<String>();
	private static ArrayList<String> bar = new ArrayList<String>();
	private static ArrayList<String> straniera = new ArrayList<String>();
	private static ArrayList<String> dolci = new ArrayList<String>();
	
	public static ArrayList<RistoBean> r_orientali = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_pizzerie = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_paninoteche = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_enoteche = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_bar = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_fast_food = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_stranieri = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_dolci = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_ristoranti = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_temp = new ArrayList<RistoBean>();
	
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, IOException {
	    //You will create these methods later in the quickstart.
		inizialize();
		ArrayList<RistoBean> ristoranti = new ArrayList<RistoBean>();
		
		//Prendo i nomi di tutti i file per recuperare i ristoranti
		File file = new File("file_list");
		File[] fileArray = file.listFiles();
		for(int i = 0; i < fileArray.length; i++) {
			ristoranti.addAll(readJson(fileArray[i].getName()));		
		}
		System.out.println(ristoranti.size());
		
		for(RistoBean r : ristoranti) {
			findCategory(r);
		}
		
		System.out.println( 
				"Orientali: " + r_orientali.size() + "\n" +
				"Pizzerie: " + r_pizzerie.size() + "\n" +
				"Paninoteche: " + r_paninoteche.size() + "\n" +
				"Enoteche: " + r_enoteche.size() + "\n" +
				"Bar: " + r_bar.size() + "\n" +
				"Fast food: " + r_fast_food.size() + "\n" +
				"Stranieri: " + r_stranieri.size() + "\n" +
				"Temp: " + r_temp.size()+"\n"+
				"Gelaterie: "+r_dolci.size()
				);
		
	    //TextAnalyticsClient client = TextAnalyticsSamples.authenticateClient(KEY, ENDPOINT);		
	   /* TextAnalyticsClient client = TextAnalyticsSamples.authenticateClient(KEY, ENDPOINT);
	    TextAnalyticsSamples.sentimentAnalysisWithOpinionMiningExample(client);*/
	    //detectLanguageExample(client);
	    //TextAnalyticsSamples.sentimentAnalysisExample(client, text); 
	    //extractKeyPhrasesExample(client);
	}
	
	static void inizialize() {
		orientale.addAll(orientale_);
		paninoteca.addAll(paninoteca_);
		enoteca.addAll(enoteca_);
		bar.addAll(bar_);
		straniera.addAll(straniera_);
		dolci.addAll(dolci_);
	}
	
	static void findCategory(RistoBean r) {
		ArrayList<String> cucina = r.getCucina();
		
		if(cucina.isEmpty()) {
			/*Split del nome del ristorante*/
			String nameOfResturant = r.getNomeRistorante();
			String[] splittedNameOfResturant = nameOfResturant.split("\\s+");
			
			for (int i =0; i<splittedNameOfResturant.length;i++) {
				if (dolci.contains(splittedNameOfResturant[i].toLowerCase()) && !r_dolci.contains(r)) {
					r_dolci.add(r);
					System.out.println("Ho aggiunto una gelateria %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				}
			}
			return;
		}
		
		boolean flag = false;
		
		for(String t : cucina) {
			String s = t.toLowerCase();
			
			if(orientale.contains(s) && !r_orientali.contains(r)) {
				r_orientali.add(r);
				flag = true;
			}
			
			if(s.equalsIgnoreCase(pizzeria) && !r_pizzerie.contains(r)) {
				r_pizzerie.add(r);
				flag = true;
			}
			
			if(paninoteca.contains(s) && !r_paninoteche.contains(r)) {
				r_paninoteche.add(r);
				flag = true;
			}
			
			if(enoteca.contains(s) && !r_enoteche.contains(r)) {
				r_enoteche.add(r);
				flag = true;
			}
			
			if(bar.contains(s) && !r_bar.contains(r)) {
				r_bar.add(r);
				flag = true;
			}
			
			if(s.equalsIgnoreCase(fast_food) && !r_fast_food.contains(r)) {
				r_fast_food.add(r);
				flag = true;
			}
			
			if(straniera.contains(s) && !r_stranieri.contains(r)) {
				r_stranieri.add(r);
				flag = true;
			}			
		}
		
		if(!flag) {
			System.out.println("Categoria non trovata");
			r_temp.add(r);
		} else {
			flag = false;
		}

	}
	
	/*Legge il JSON e riempi beans*/
	static ArrayList<RistoBean> readJson(String name) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("file_list\\" + name));
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
