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
	
	public static String endpoint = "https://progettoreti.cognitiveservices.azure.com/";
	public static String key = "39f38062827f4b568dc1f4ca8ad528e6";
	
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, IOException {
	    
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
				"Gelaterie: " + r_dolci.size() + "\n"+
				"Ristoranti: " + r_ristoranti.size()
				);
		
		WriterFile.inizialize(key, endpoint);
		
		//Scrittura dei file con i risultati di analisi
		/*WriterFile.writeFile(r_orientali, "orientali");
		WriterFile.writeFile(r_pizzerie, "pizzerie");
		WriterFile.writeFile(r_ristoranti, "ristoranti");       // commentati per test
		WriterFile.writeFile(r_bar, "bar");
		WriterFile.writeFile(r_fast_food, "fast_food");
		WriterFile.writeFile(r_dolci, "dolci_gelaterie");
		WriterFile.writeFile(r_enoteche, "enoteche");
		WriterFile.writeFile(r_paninoteche, "paninoteche");*/
		WriterFile.writeFile(r_stranieri, "stranieri");
		
	   /* TextAnalyticsClient client = TextAnalyticsSamples.authenticateClient(KEY, ENDPOINT);*/
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
		fast_food.addAll(fast_food_);
		ristorante.addAll(ristorante_);
		
		dolci.addAll(dolci_);
		n_pizza.addAll(pizzaN);
		n_enoteca.addAll(enotecaN);
		n_paninoteca.addAll(paninotecaN);
		n_bar.addAll(barN);
		n_ristorante.addAll(ristoranteN);
	}
	
	static void findCategory(RistoBean r) {
		ArrayList<String> cucina = r.getCucina();
		
		if(cucina.isEmpty()) {
			checkName(r);
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
				checkName(r);
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
			
			if(fast_food.contains(s) && !r_fast_food.contains(r)) {
				r_fast_food.add(r);
				flag = true;
			}
			
			if(straniera.contains(s) && !r_stranieri.contains(r)) {
				r_stranieri.add(r);
				flag = true;
			}			
		}
		
		if(!flag) {	
			
			if(!checkName(r)) {
				for(String t : cucina) {
					String s = t.toLowerCase();
					if(ristorante.contains(s) && !r_ristoranti.contains(r)) {
						r_ristoranti.add(r);
						flag = true;
					}	
				}	
			}
			
			flag = false;
		} else {
			flag = false;
		}

	}
	
	static boolean checkName(RistoBean r) {
		boolean flag = false;
		String nameOfResturant = r.getNomeRistorante();
		String[] splittedNameOfResturant = nameOfResturant.split("\\s+");
		for (int i =0; i<splittedNameOfResturant.length;i++) {
			if (dolci.contains(splittedNameOfResturant[i].toLowerCase()) && !r_dolci.contains(r)) {
				r_dolci.add(r);
				flag = true;
			}
			
			if(n_ristorante.contains(splittedNameOfResturant[i].toLowerCase()) && !r_ristoranti.contains(r)) {
				r_ristoranti.add(r);
				flag = true;
			}
			
			if (n_pizza.contains(splittedNameOfResturant[i].toLowerCase()) && !r_pizzerie.contains(r)) {
				r_pizzerie.add(r);
				flag = true;
			}
			
			if (n_enoteca.contains(splittedNameOfResturant[i].toLowerCase()) && !r_enoteche.contains(r)) {
				r_enoteche.add(r);
				flag = true;
			}
			
			if (n_paninoteca.contains(splittedNameOfResturant[i].toLowerCase()) && !r_paninoteche.contains(r)) {
				r_paninoteche.add(r);
				flag = true;
			}
			
			if (n_bar.contains(splittedNameOfResturant[i].toLowerCase()) && !r_bar.contains(r)) {
				r_bar.add(r);
				flag = true;
			}
		}
		return flag;
	}
	
	/*Legge il JSON e riempi beans*/
	static ArrayList<RistoBean> readJson(String name) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("file_list\\" + name));
		System.out.println(name);
		JsonArray jsonArray = (JsonArray) obj;
		ArrayList<RistoBean> arrayRisto = new ArrayList<RistoBean>();
		int i = 0;
		for (JsonElement element : jsonArray) {
			if(i < 10) {
				
					RistoBean risto = new RistoBean();
					risto.setNomeRistorante(getNomeRisto(element));
					risto.setRate(getRatingRisto(element));
					risto.setCucina(getCucina(element));
					ArrayList<String> cucina = risto.getCucina();
					for(String c : cucina) {
						System.out.println(c);
					}
					System.out.println("%%%%%%%%%%%%%%%%%%%%%");
					risto.setRecensioni(getReviews(element));
					risto.setCity(name.substring(0,name.length()-5));
					if (risto.getRecensioni().size()>0) {
						arrayRisto.add(risto);
						i++;
					}
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
		if(jsonObject.get("rating") == null) {
			return 0;
		}
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
				j++;
			}
		}
		return reviews;
	}
	
	/** SEZIONE CUCINE **/
	private static List<String> orientale_ =  Arrays.asList("asian", "japanese", "korean", "chinese", "sushi", "fujian");
	private static String pizzeria = "pizza";
	private static List<String> paninoteca_ =  Arrays.asList("hamburger", "pub", "brew pub"); 
	private static List<String> enoteca_ =  Arrays.asList("wine bar", "wine");
	private static List<String> bar_ =  Arrays.asList("bar", "cafe");
	private static List<String> fast_food_ = Arrays.asList("fast food", "street food");
	private static List<String> straniera_ =  Arrays.asList("greek", "egyptian", "moroccan", "tunisian", "turkish", "brazilian", "arabic", "ethiopian", "african", "mexican", "latin", "central american", "south american", "polish", "french");
	private static List<String> ristorante_ = Arrays.asList("italian", "mediterranean", "neapolitan", "european");
	
	private static ArrayList<String> orientale = new ArrayList<String>();
	private static ArrayList<String> paninoteca = new ArrayList<String>();
	private static ArrayList<String> enoteca = new ArrayList<String>();
	private static ArrayList<String> bar = new ArrayList<String>();
	private static ArrayList<String> straniera = new ArrayList<String>();
	private static ArrayList<String> fast_food = new ArrayList<String>();
	private static ArrayList<String> ristorante = new ArrayList<String>();
	
	/**SEZIONE NOMI **/
	private static List<String> ristoranteN =  Arrays.asList("restaurant", "ristorante", "osteria", "trattoria", "cucina");
	private static List<String> dolci_ = Arrays.asList("gelateria", "pasticceria", "gelato", "gelati", "dolce", "dolci", "cremeria", "cioccolato");
	private static List<String> pizzaN = Arrays.asList("pizzeria", "pizza", "pizze");
	private static List<String> paninotecaN = Arrays.asList("panino", "panini", "burgers", "burger", "pub", "paninoteca", "piadineria", "piadine","piadina");
	private static List<String> enotecaN = Arrays.asList("vino", "vini", "vineria", "enoteca");
	private static List<String> barN = Arrays.asList("bar", "cafe", "cafe\'", "caffe'", "caffè", "caffe", "caffetteria", "baretto", "barz8");
	
	private static ArrayList<String> dolci = new ArrayList<String>();	
	private static ArrayList<String> n_pizza = new ArrayList<String>();
	private static ArrayList<String> n_enoteca = new ArrayList<String>();
	private static ArrayList<String> n_paninoteca = new ArrayList<String>();
	private static ArrayList<String> n_bar = new ArrayList<String>();
	private static ArrayList<String> n_ristorante = new ArrayList<String>();
		
	/** RISTORANTI CATEGORIZZATI **/
	public static ArrayList<RistoBean> r_orientali = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_pizzerie = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_paninoteche = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_enoteche = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_bar = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_fast_food = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_stranieri = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_dolci = new ArrayList<RistoBean>();
	public static ArrayList<RistoBean> r_ristoranti = new ArrayList<RistoBean>();
}
