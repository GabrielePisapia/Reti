package com.sm.reti.fabrikam_functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.azure.ai.textanalytics.TextAnalyticsClient;

public class WriterFile {
	
	private static TextAnalyticsClient client;
	
	public static void inizialize(String key, String endpoint) {
		client = TextAnalyticsSamples.authenticateClient(key, endpoint);		
		client = TextAnalyticsSamples.authenticateClient(key, endpoint);
	}
	
	public static void writeFile(ArrayList<RistoBean> array, String category) throws IOException {
		//FileWriter fw = new FileWriter("output\\" + category + ".txt");
		JSONArray ristos = new JSONArray();
		for(RistoBean r : array) {
			
			JSONObject obj = new JSONObject();
			
			obj.put("NOME",r.getNomeRistorante());
			obj.put("CITTA'" , r.getCity());
			obj.put("RANKING" ,r.getRate());
			
			ArrayList<ReviewsBean> b = r.getRecensioni();
			JSONArray recensioni = new JSONArray();
			
			for(ReviewsBean t : b) {
				String textOfReview = t.getTitolo()+" "+t.getCorpo();
				recensioni.put(textOfReview);
				JSONArray sentimentInfo = new JSONArray();
				sentimentInfo = TextAnalyticsSamples.sentimentAnalysisWithOpinionMining(client,textOfReview);
				recensioni.put(sentimentInfo);
				//recensioni = TextAnalyticsSamples.sentimentAnalysisWithOpinionMining(client, t.getTitolo() + " "+ t.getCorpo(), recensioni);
			}
			obj.put("RECENSIONI", recensioni);
			ristos.put(obj);
		}
		
		FileWriter fw = new FileWriter("output//"+category+".json");
		
		for (int i =0;i<ristos.length();i++) {
			fw.write(ristos.getJSONObject(i).toString());
		}
		fw.flush();
		//fw.close();
		
	}
	
	
	/*String s = Translator.translate("", "en", t.getTitolo() + ": " + t.getCorpo());
	fw.write("TESTO: " + s + "\n");
	String j = TextAnalyticsSamples.sentimentAnalysisWithOpinionMining(client, s);
	fw.write("ANALISI: " + s);*/
}
