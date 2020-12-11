package com.sm.reti.fabrikam_functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class MainAnalisiDati {
	
	public static ArrayList<String> food = new ArrayList<String>();
	public static ArrayList<String> location = new ArrayList<String>();
	public static ArrayList<String> service = new ArrayList<String>();
	public static ArrayList<String> price = new ArrayList<String>();
	
	public static Score scoreFoodNord = new Score();
	public static Score scoreLocationNord = new Score();
	public static Score scoreServiceNord = new Score();
	public static Score scorePriceNord = new Score();
	
	public static Score scoreFoodCenter = new Score();
	public static Score scoreLocationCenter = new Score();
	public static Score scoreServiceCenter = new Score();
	public static Score scorePriceCenter = new Score();
	
	public static Score scoreFoodSud = new Score();
	public static Score scoreLocationSud = new Score();
	public static Score scoreServiceSud = new Score();
	public static Score scorePriceSud = new Score();
	
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, IOException {
		
		
		//Prendo i nomi di tutti i file per recuperare i ristoranti
		File file = new File("output");
		File[] fileArray = file.listFiles();
		ArrayList<RistoBean> ristorantiNord = new ArrayList<>();
		ArrayList<RistoBean> ristorantiCentro = new ArrayList<>();
		ArrayList<RistoBean> ristorantiSud = new ArrayList<>();
		
		int totalCountPositiveNord = 0;
		int totalCountNegativeNord = 0;
		int totalCountNeutralNord = 0;
		int totalCountMixedNord = 0;
		
		int totalCountPositiveCenter = 0;
		int totalCountNegativeCenter = 0;
		int totalCountNeutralCenter = 0;
		int totalCountMixedCenter = 0;
		
		int totalCountPositiveSud = 0;
		int totalCountNegativeSud = 0;
		int totalCountNeutralSud = 0;
		int totalCountMixedSud = 0;
		
		
		int [] scoresNord = new int [4];
		int [] scoresCenter = new int [4];
		int [] scoresSud = new int [4];
		
		inizialize();
		
		for(int i = 0; i < fileArray.length; i++) {
			scoresNord = analyzeNord(fileArray[i].getName(),ristorantiNord);
			totalCountPositiveNord += scoresNord[0];
			totalCountNeutralNord += scoresNord[1];
			totalCountNegativeNord += scoresNord[2];
			totalCountMixedNord += scoresNord[3];
			scoresCenter = analyzeCenter(fileArray[i].getName(),ristorantiCentro);
			totalCountPositiveCenter += scoresCenter[0];
			totalCountNeutralCenter += scoresCenter[1];
			totalCountNegativeCenter += scoresCenter[2];
			totalCountMixedCenter += scoresCenter[3];
			scoresSud = analyzeSud(fileArray[i].getName(),ristorantiSud);
			totalCountPositiveSud += scoresSud[0];
			totalCountNeutralSud+= scoresSud[1];
			totalCountNegativeSud+= scoresSud[2];
			totalCountMixedSud+= scoresSud[3];
		}
		
		System.out.println("Score NORD:  pos: "+ totalCountPositiveNord+"  neut: "+ totalCountNeutralNord+"  neg: "+ totalCountNegativeNord+ " mixed: "+totalCountMixedNord);
		System.out.println("Food score positive: " + scoreFoodNord.getCountPositive() + " Food score negative: " + scoreFoodNord.getCountNegative() + " Food score neutral: " + scoreFoodNord.getCountNeutral() + " Food score mixed: " + scoreFoodNord.getCountMixed());
		System.out.println("FOOD TOTAL: " + scoreFoodNord.getTotalCount() + " AVG POSITIVE: " + scoreFoodNord.getAvg()[0] + " AVG NEGATIVE: " + scoreFoodNord.getAvg()[1] + " AVG MIXED: " + scoreFoodNord.getAvg()[3]);
		System.out.println("Location score positive: " + scoreLocationNord.getCountPositive() + " Location score negative: " + scoreLocationNord.getCountNegative() + " Location score neutral: " + scoreLocationNord.getCountNeutral() + " Location score mixed: " + scoreLocationNord.getCountMixed());
		System.out.println("LOCATION TOTAL: " + scoreLocationNord.getTotalCount() + " AVG POSITIVE: " + scoreLocationNord.getAvg()[0] + " AVG NEGATIVE: " + scoreLocationNord.getAvg()[1] + " AVG MIXED: " + scoreLocationNord.getAvg()[3]);
		System.out.println("Service score positive: " + scoreServiceNord.getCountPositive() + " Service score negative: " + scoreServiceNord.getCountNegative() + " Service score neutral: " + scoreServiceNord.getCountNeutral() + " Service score mixed: " + scoreServiceNord.getCountMixed());
		System.out.println("SERVICE TOTAL: " + scoreServiceNord.getTotalCount() + " AVG POSITIVE: " + scoreServiceNord.getAvg()[0] + " AVG NEGATIVE: " + scoreServiceNord.getAvg()[1] + " AVG MIXED: " + scoreServiceNord.getAvg()[3]);
		System.out.println("Price score positive: " + scorePriceNord.getCountPositive() + " Price score negative: " + scorePriceNord.getCountNegative() + " Price score neutral: " + scorePriceNord.getCountNeutral() + " Price score mixed: " + scorePriceNord.getCountMixed());
		System.out.println("PRICE TOTAL: " + scorePriceNord.getTotalCount() + " AVG POSITIVE: " + scorePriceNord.getAvg()[0] + " AVG NEGATIVE: " + scorePriceNord.getAvg()[1] + " AVG MIXED: " + scorePriceNord.getAvg()[3] + "\n" +"\n");
		
		System.out.println("Score CENTRO: pos: "+totalCountPositiveCenter+" neut: "+totalCountNeutralCenter+" neg: "+totalCountNegativeCenter+" mixed: "+totalCountMixedCenter);
		System.out.println("Food score positive: " + scoreFoodCenter.getCountPositive() + " Food score negative: " + scoreFoodCenter.getCountNegative() + " Food score neutral: " + scoreFoodNord.getCountNeutral() + " Food score mixed: " + scoreFoodNord.getCountMixed());
		System.out.println("FOOD TOTAL: " + scoreFoodCenter.getTotalCount() + " AVG POSITIVE: " + scoreFoodCenter.getAvg()[0] + " AVG NEGATIVE: " + scoreFoodCenter.getAvg()[1] + " AVG MIXED: " + scoreFoodCenter.getAvg()[3]);
		System.out.println("Location score positive: " + scoreLocationCenter.getCountPositive() + " Location score negative: " + scoreLocationCenter.getCountNegative() + " Location score neutral: " + scoreLocationCenter.getCountNeutral() + " Location score mixed: " + scoreLocationCenter.getCountMixed());
		System.out.println("LOCATION TOTAL: " + scoreLocationCenter.getTotalCount() + " AVG POSITIVE: " + scoreLocationCenter.getAvg()[0] + " AVG NEGATIVE: " + scoreLocationCenter.getAvg()[1] + " AVG MIXED: " + scoreLocationCenter.getAvg()[3]);
		System.out.println("Service score positive: " + scoreServiceCenter.getCountPositive() + " Service score negative: " + scoreServiceCenter.getCountNegative() + " Service score neutral: " + scoreServiceCenter.getCountNeutral() + " Service score mixed: " + scoreServiceCenter.getCountMixed());
		System.out.println("SERVICE TOTAL: " + scoreServiceCenter.getTotalCount() + " AVG POSITIVE: " + scoreServiceCenter.getAvg()[0] + " AVG NEGATIVE: " + scoreServiceCenter.getAvg()[1] + " AVG MIXED: " + scoreServiceCenter.getAvg()[3]);
		System.out.println("Price score positive: " + scorePriceCenter.getCountPositive() + " Price score negative: " + scorePriceCenter.getCountNegative() + " Price score neutral: " + scorePriceCenter.getCountNeutral() + " Price score mixed: " + scorePriceCenter.getCountMixed());
		System.out.println("PRICE TOTAL: " + scorePriceCenter.getTotalCount() + " AVG POSITIVE: " + scorePriceCenter.getAvg()[0] + " AVG NEGATIVE: " + scorePriceCenter.getAvg()[1] + " AVG MIXED: " + scorePriceCenter.getAvg()[3] + "\n" +"\n");
		
		System.out.println("Score SUD: pos: "+totalCountPositiveSud+" neut: "+totalCountNeutralSud+" neg: "+totalCountNegativeSud+" mixed: "+totalCountMixedSud);
		System.out.println("Food score positive: " + scoreFoodSud.getCountPositive() + " Food score negative: " + scoreFoodSud.getCountNegative() + " Food score neutral: " + scoreFoodNord.getCountNeutral() + " Food score mixed: " + scoreFoodNord.getCountMixed());
		System.out.println("FOOD TOTAL: " + scoreFoodSud.getTotalCount() + " AVG POSITIVE: " + scoreFoodSud.getAvg()[0] + " AVG NEGATIVE: " + scoreFoodSud.getAvg()[1] + " AVG MIXED: " + scoreFoodSud.getAvg()[3]);
		System.out.println("Location score positive: " + scoreLocationSud.getCountPositive() + " Location score negative: " + scoreLocationSud.getCountNegative() + " Location score neutral: " + scoreLocationSud.getCountNeutral() + " Location score mixed: " + scoreLocationSud.getCountMixed());
		System.out.println("LOCATION TOTAL: " + scoreLocationSud.getTotalCount() + " AVG POSITIVE: " + scoreLocationSud.getAvg()[0] + " AVG NEGATIVE: " + scoreLocationSud.getAvg()[1] + " AVG MIXED: " + scoreLocationSud.getAvg()[3]);
		System.out.println("Service score positive: " + scoreServiceSud.getCountPositive() + " Service score negative: " + scoreServiceSud.getCountNegative() + " Service score neutral: " + scoreServiceSud.getCountNeutral() + " Service score mixed: " + scoreServiceSud.getCountMixed());
		System.out.println("SERVICE TOTAL: " + scoreServiceSud.getTotalCount() + " AVG POSITIVE: " + scoreServiceSud.getAvg()[0] + " AVG NEGATIVE: " + scoreServiceSud.getAvg()[1] + " AVG MIXED: " + scoreServiceSud.getAvg()[3]);
		System.out.println("Price score positive: " + scorePriceSud.getCountPositive() + " Price score negative: " + scorePriceSud.getCountNegative() + " Price score neutral: " + scorePriceSud.getCountNeutral() + " Price score mixed: " + scorePriceSud.getCountMixed());
		System.out.println("PRICE TOTAL: " + scorePriceSud.getTotalCount() + " AVG POSITIVE: " + scorePriceSud.getAvg()[0] + " AVG NEGATIVE: " + scorePriceSud.getAvg()[1] + " AVG MIXED: " + scorePriceSud.getAvg()[3] + "\n" +"\n");
	}
	
	public static void inizialize() throws IOException {
		File file = new File("file_temporanei");
		File[] fileArray = file.listFiles();
		for(int i = 1; i < fileArray.length; i++) {
				System.out.println(fileArray[i].getName());
				String name = fileArray[i].getName();
				
				FileReader reader = new FileReader(fileArray[i]);
				BufferedReader bf = new BufferedReader(reader);
				String line;
				switch(name) {
					case "food.txt": 
						while((line = bf.readLine()) != null) {
							food.add(line.toLowerCase());
						}
					break;
					case "location.txt":
						while((line = bf.readLine()) != null) {
							location.add(line.toLowerCase());
						}
					break;
					case "service.txt":
						while((line = bf.readLine()) != null) {
							service.add(line.toLowerCase());
						}
					break;
					case "price.txt":
						while((line = bf.readLine()) != null) {
							price.add(line.toLowerCase());
						}
					break;
				}
		}
	}
	
	public static int[] analyzeNord(String s,ArrayList<RistoBean> ristorantiNord) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("output\\" + s));
		JsonObject risto = (JsonObject) obj;
		JsonArray j = (JsonArray) risto.get("RISTORANTI");
		
		
		int counterPositiveScoreNord = 0;
		int counterNeutralScoreNord = 0;
		int counterNegativeScoreNord = 0;
		int counterMixedScoreNord = 0;
		
		for(int i =0;i<j.size();i++) {
			JsonObject x = (JsonObject) j.get(i);
			String city = x.get("CITTA'").toString().replace("\"","");
			String nameOfRest = x.get("NOME").toString().replace("\"","");
			JsonArray recensioni = (JsonArray) x.get("RECENSIONI");
			if(city.equals("bologna") || city.equals("genova") || city.equals("milano") || city.equals("torino") || city.equals("trieste") || city.equals("trento") || city.equals("venezia")) {
				if(!isAlreadyInArray(ristorantiNord,nameOfRest)) {
					RistoBean beanResturant = new RistoBean();
					beanResturant.setNomeRistorante(nameOfRest);
					beanResturant.setCity(city);
					ristorantiNord.add(beanResturant);
						for (int k = 0;k<recensioni.size();k++) {
							JsonObject oggettoRecensione = (JsonObject) recensioni.get(k);
							JsonObject temp = (JsonObject) oggettoRecensione.get("SENTIMENT RECENSIONE");
							String sentRev =  temp.get("SENTIMENT").toString().replace("\"","");
							//System.out.println(oggettoRecensione.get("RECENSIONE").getAsString());
							getAspects(temp, scoreFoodNord, scoreLocationNord, scoreServiceNord, scorePriceNord);
							switch(sentRev) {
								case "positive":
									counterPositiveScoreNord++;
									break;
								case "neutral":
									counterNeutralScoreNord++;
									break;
								case "negative":
									counterNegativeScoreNord++;
									break;
								case "mixed":
									counterMixedScoreNord++;
									break;
							}
					
							
						}
				}
			}


		}

		int [] score= new int[4];
		score[0] = counterPositiveScoreNord;
		score[1] = counterNeutralScoreNord;
		score[2] = counterNegativeScoreNord;
		score[3] = counterMixedScoreNord;
		return score;
		
		//System.out.println(((JsonArray) x.get("RECENSIONI")).size());
	}
	
	
	public static int[] analyzeCenter(String s,ArrayList<RistoBean> ristorantiCentro) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("output\\" + s));
		JsonObject risto = (JsonObject) obj;
		JsonArray j = (JsonArray) risto.get("RISTORANTI");
		
		
		int counterPositiveScoreCenter = 0;
		int counterNeutralScoreCenter = 0;
		int counterNegativeScoreCenter = 0;
		int counterMixedScoreCenter = 0;
		
		for(int i =0;i<j.size();i++) {
			JsonObject x = (JsonObject) j.get(i);
			String city = x.get("CITTA'").toString().replace("\"","");
			String nameOfRest = x.get("NOME").toString().replace("\"","");
			JsonArray recensioni = (JsonArray) x.get("RECENSIONI");
			if(city.equals("firenze") || city.equals("perugia") || city.equals("ancona") || city.equals("roma")) {
				if(!isAlreadyInArray(ristorantiCentro,nameOfRest)) {
					RistoBean beanResturant = new RistoBean();
					beanResturant.setNomeRistorante(nameOfRest);
					beanResturant.setCity(city);
					ristorantiCentro.add(beanResturant);
						for (int k = 0;k<recensioni.size();k++) {
							JsonObject oggettoRecensione = (JsonObject) recensioni.get(k);
							JsonObject temp = (JsonObject) oggettoRecensione.get("SENTIMENT RECENSIONE");
							String sentRev =  temp.get("SENTIMENT").toString().replace("\"","");
							//System.out.println(oggettoRecensione.get("RECENSIONE").getAsString());
							getAspects(temp, scoreFoodCenter, scoreLocationCenter, scoreServiceCenter, scorePriceCenter);
							switch(sentRev) {
							case "positive":
								counterPositiveScoreCenter++;
								break;
							case "neutral":
								counterNeutralScoreCenter++;
								break;
							case "negative":
								counterNegativeScoreCenter++;
								break;
							case "mixed":
								counterMixedScoreCenter++;
								break;
						}
							
						}
				}
			}


		}

		int [] score= new int[4];
		score[0] = counterPositiveScoreCenter;
		score[1] = counterNeutralScoreCenter;
		score[2] = counterNegativeScoreCenter;
		score[3] = counterMixedScoreCenter;
		return score;
		
		//System.out.println(((JsonArray) x.get("RECENSIONI")).size());
	}
	

	public static int[] analyzeSud(String s,ArrayList<RistoBean> ristorantiSud) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("output\\" + s));
		JsonObject risto = (JsonObject) obj;
		JsonArray j = (JsonArray) risto.get("RISTORANTI");
		
		
		int counterPositiveScoreSud = 0;
		int counterNeutralScoreSud = 0;
		int counterNegativeScoreSud = 0;
		int counterMixedScoreSud = 0;
		
		for(int i =0;i<j.size();i++) {
			JsonObject x = (JsonObject) j.get(i);
			String city = x.get("CITTA'").toString().replace("\"","");
			String nameOfRest = x.get("NOME").toString().replace("\"","");
			JsonArray recensioni = (JsonArray) x.get("RECENSIONI");
			if(city.equals("laquila") || city.equals("napoli") || city.equals("potenza") || city.equals("catanzaro") || city.equals("cagliari") || city.equals("palermo")) {
				if(!isAlreadyInArray(ristorantiSud,nameOfRest)) {
					RistoBean beanResturant = new RistoBean();
					beanResturant.setNomeRistorante(nameOfRest);
					beanResturant.setCity(city);
					ristorantiSud.add(beanResturant);
						for (int k = 0;k<recensioni.size();k++) {
							JsonObject oggettoRecensione = (JsonObject) recensioni.get(k);
							JsonObject temp = (JsonObject) oggettoRecensione.get("SENTIMENT RECENSIONE");
							String sentRev =  temp.get("SENTIMENT").toString().replace("\"","");
							//System.out.println(oggettoRecensione.get("RECENSIONE").getAsString());
							getAspects(temp, scoreFoodSud, scoreLocationSud, scoreServiceSud, scorePriceSud);
							switch(sentRev) {
								case "positive":
									counterPositiveScoreSud++;
									break;
								case "neutral":
									counterNeutralScoreSud++;
									break;
								case "negative":
									counterNegativeScoreSud++;
									break;
								case "mixed":
									counterMixedScoreSud++;
									break;
							}
					
							
						}
				}
			}


		}

		int [] score= new int[4];
		score[0] = counterPositiveScoreSud;
		score[1] = counterNeutralScoreSud;
		score[2] = counterNegativeScoreSud;
		score[3] = counterMixedScoreSud;
		return score;
		
		//System.out.println(((JsonArray) x.get("RECENSIONI")).size());
	}
	
	
	
	public static boolean isAlreadyInArray(ArrayList<RistoBean> array,String nameOfRest) {
		boolean flag = false;
		for (int i =0;i<array.size();i++) {
			if(array.get(i).getNomeRistorante() == nameOfRest) {
				flag = true;
				return flag;
			}
		}
		return flag;
		
	}
	
	public static void getAspects(JsonObject r, Score foods, Score locations, Score services, Score prices) {
		JsonArray sentences = (JsonArray) r.get("SENTENCES").getAsJsonArray();
		for(int i = 0; i < sentences.size(); i++) {
			JsonObject sentence = (JsonObject) sentences.get(i);
			JsonArray aspects = (JsonArray) sentence.get("ASPECTS");
			for(int j = 0; j < aspects.size(); j++) {
				JsonObject aspect = (JsonObject) aspects.get(j);
				String stringAspect = aspect.get("ASPECT").getAsString();
				if(food.contains(stringAspect.toLowerCase())) {
					String sentimentAspect = aspect.get("SENTIMENT ASPECT").getAsString();
					switch(sentimentAspect) {
						case "positive": 
							foods.addPositive();
							break;
						case "negative": 
							foods.addNegative();
							break;
						case "neutral":
							foods.addNeutral();
							break;
						case "mixed": 
							foods.addMixed();
							break;
					}
				} else if(location.contains(stringAspect.toLowerCase())) {
					String sentimentAspect = aspect.get("SENTIMENT ASPECT").getAsString();
					switch(sentimentAspect) {
						case "positive": 
							locations.addPositive();
							break;
						case "negative": 
							locations.addNegative();
							break;
						case "neutral":
							locations.addNeutral();
							break;
						case "mixed": 
							locations.addMixed();
							break;
					}
				} else if(service.contains(stringAspect.toLowerCase())) {
					String sentimentAspect = aspect.get("SENTIMENT ASPECT").getAsString();
					switch(sentimentAspect) {
						case "positive": 
							services.addPositive();
							break;
						case "negative": 
							services.addNegative();
							break;
						case "neutral":
							services.addNeutral();
							break;
						case "mixed": 
							services.addMixed();
							break;
					}
				} else if(price.contains(stringAspect.toLowerCase())) {
					String sentimentAspect = aspect.get("SENTIMENT ASPECT").getAsString();
					switch(sentimentAspect) {
						case "positive": 
							prices.addPositive();
							break;
						case "negative": 
							prices.addNegative();
							break;
						case "neutral":
							prices.addNeutral();
							break;
						case "mixed": 
							prices.addMixed();
							break;
					}
				}
				//System.out.println(" -- " + stringAspect);
				//allAspects.add(stringAspect);
			}
		}
	}

}
