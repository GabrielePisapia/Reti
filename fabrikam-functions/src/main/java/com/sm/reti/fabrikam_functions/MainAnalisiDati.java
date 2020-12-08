package com.sm.reti.fabrikam_functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class MainAnalisiDati {
	
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		
		
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
		
		System.out.println("Score NORD:  pos: "+ totalCountPositiveNord+"  neut: "+ totalCountNeutralNord+"  neg: "+ totalCountNegativeNord+ " mixed: "+totalCountMixedNord+"\n");
		System.out.println("Score CENTRO: pos: "+totalCountPositiveCenter+" neut: "+totalCountNeutralCenter+" neg: "+totalCountNegativeCenter+" mixed: "+totalCountMixedCenter+"\n");
		System.out.println("Score SUD: pos: "+totalCountPositiveSud+" neut: "+totalCountNeutralSud+" neg: "+totalCountNegativeSud+" mixed: "+totalCountMixedSud+"\n");
		
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

}
