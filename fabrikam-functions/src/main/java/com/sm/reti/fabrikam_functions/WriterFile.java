package com.sm.reti.fabrikam_functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.azure.ai.textanalytics.TextAnalyticsClient;

public class WriterFile {
	
	private static TextAnalyticsClient client;
	
	public static void inizialize(String key, String endpoint) {
		client = TextAnalyticsSamples.authenticateClient(key, endpoint);		
		client = TextAnalyticsSamples.authenticateClient(key, endpoint);
	}
	
	public static void writeFile(ArrayList<RistoBean> array, String category) throws IOException {
		FileWriter fw = new FileWriter("output\\" + category + ".txt");
		for(RistoBean r : array) {
			fw.write("NOME: " + r.getNomeRistorante());
			fw.write("CITTA': " + r.getCity());
			fw.write("RANKING: " + r.getRate());
			
			fw.write("\n\n RECENSIONI");
			ArrayList<ReviewsBean> b = r.getRecensioni();
			for(ReviewsBean t : b) {
				String s = Translator.translate("", "en", t.getTitolo() + ": " + t.getCorpo());
				fw.write("TESTO: " + s + "\n");
				String j = TextAnalyticsSamples.sentimentAnalysisWithOpinionMining(client, s);
				fw.write("ANALISI: " + s);
			}
		}
	}
}
