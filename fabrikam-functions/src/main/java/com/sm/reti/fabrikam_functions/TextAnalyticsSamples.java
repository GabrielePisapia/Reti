package com.sm.reti.fabrikam_functions;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.ai.textanalytics.models.*;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.TextAnalyticsClient;


public class TextAnalyticsSamples {
	
	

	
	static TextAnalyticsClient authenticateClient(String key, String endpoint) {
	    return new TextAnalyticsClientBuilder()
	        .credential(new AzureKeyCredential(key))
	        .endpoint(endpoint)
	        .buildClient();
	}
	
	static void sentimentAnalysisExample(TextAnalyticsClient client,String text)
	{
	    // The text that need be analyzed.
	   

	    DocumentSentiment documentSentiment = client.analyzeSentiment(text);
	    System.out.println("Il testo è: "+text);
	    System.out.printf(
	        "Recognized document sentiment: %s, positive score: %s, neutral score: %s, negative score: %s.%n",
	        documentSentiment.getSentiment(),
	        documentSentiment.getConfidenceScores().getPositive(),
	        documentSentiment.getConfidenceScores().getNeutral(),
	        documentSentiment.getConfidenceScores().getNegative());

	   /* for (SentenceSentiment sentenceSentiment : documentSentiment.getSentences()) {
	        System.out.printf(
	            "Recognized sentence sentiment: %s, positive score: %s, neutral score: %s, negative score: %s.%n",
	            sentenceSentiment.getSentiment(),
	            sentenceSentiment.getConfidenceScores().getPositive(),
	            sentenceSentiment.getConfidenceScores().getNeutral(),
	            sentenceSentiment.getConfidenceScores().getNegative());
	        }*/
	    }
	
	static void detectLanguageExample(TextAnalyticsClient client)
	{
	    // The text that need be analyzed.
	    String text = "Posto comodissimo nelle vicinanze di piazza Vanvitelli dove poter assaggiare un'ampia varietà di gustosissimi crostoni anche componibili a proprio piacimento! Da provare per una pausa veloce ma senza rinunciare alla bontà.";

	    DetectedLanguage detectedLanguage = client.detectLanguage(text);
	    System.out.printf("Detected primary language: %s, ISO 6391 name: %s, score: %.2f.%n",
	        detectedLanguage.getName(),
	        detectedLanguage.getIso6391Name(),
	        detectedLanguage.getConfidenceScore());
	}
	
	static void extractKeyPhrasesExample(TextAnalyticsClient client)
	{
	    // The text that need be analyzed.
	    String text = "La pizza è buona, ma il locale è sporco";

	    System.out.printf("Recognized phrases: %n");
	    for (String keyPhrase : client.extractKeyPhrases(text)) {
	        System.out.printf("%s%n", keyPhrase);
	    }
	}
}
	

