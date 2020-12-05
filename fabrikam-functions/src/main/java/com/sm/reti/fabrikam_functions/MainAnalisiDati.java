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
		int [] score = new int [3];
		for(int i = 0; i < fileArray.length; i++) {
			score = analize(fileArray[i].getName(),ristorantiNord);
			totalCountPositiveNord += score[0];
			totalCountNeutralNord += score[1];
			totalCountNegativeNord += score[2];
		}
		
		System.out.println("Score pos: "+ totalCountPositiveNord+" Score neut: "+ totalCountNeutralNord+" Score neg "+ totalCountNegativeNord);
	}
	
	public static int[] analize(String s,ArrayList<RistoBean> ristorantiNord) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("output\\" + s));
		JsonObject risto = (JsonObject) obj;
		JsonArray j = (JsonArray) risto.get("RISTORANTI");
		
		
		int counterPositiveScoreNord = 0;
		int counterNeutralScoreNord = 0;
		int counterNegativeScoreNord = 0;
		
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
							if (sentRev.equals("positive")) {
								counterPositiveScoreNord++;
							}else {
								if(sentRev.equals("neutral")) {
									counterNeutralScoreNord++;
								}else {
									counterNegativeScoreNord++;
								}
							}
							
						}
				}
			}


		}

		int [] score= new int[3];
		score[0] = counterPositiveScoreNord;
		score[1] = counterNeutralScoreNord;
		score[2] = counterNegativeScoreNord;
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
