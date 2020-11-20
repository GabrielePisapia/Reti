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
	
	static String sentimentAnalysisExample(TextAnalyticsClient client, String text)
	{
	    // The text that need be analyzed.
	   
	    DocumentSentiment documentSentiment = client.analyzeSentiment(text);
	    System.out.println("Il testo è: " + text);
	    return "Recognized document sentiment: " + documentSentiment.getSentiment() + 
	    		", positive score: " +  documentSentiment.getConfidenceScores().getPositive() +
	    		", neutral score: " + documentSentiment.getConfidenceScores().getNeutral() + 
	    		", negative score: " + documentSentiment.getConfidenceScores().getNegative() + ".";

	   /* for (SentenceSentiment sentenceSentiment : documentSentiment.getSentences()) {
	        System.out.printf(
	            "Recognized sentence sentiment: %s, positive score: %s, neutral score: %s, negative score: %s.%n",
	            sentenceSentiment.getSentiment(),
	            sentenceSentiment.getConfidenceScores().getPositive(),
	            sentenceSentiment.getConfidenceScores().getNeutral(),
	            sentenceSentiment.getConfidenceScores().getNegative());
	        }*/
	    }
	
	static void sentimentAnalysisWithOpinionMiningExample(TextAnalyticsClient client)
	{
	    // The Document that needs be analyzed.
	    String document = "Partiamo dal sapore: ho provato il pistacchio e un altro gusto con la ricotta, ma non mi ha colpita in positivo nessuno dei due... Nel primo caso, il classico retrogusto leggermente salato che contraddistingue questo gusto, era completamente assente, quasi come se ne avessi preso uno alla panna! In generale, anche l'altro è risultato essere troppo delicato, facilmente confondibile. \\nPrezzi poco chiari: una coppetta piccola (due gusti) costa 2 euro e 50, mentre un cono 3 euro; non sapendo questa distinzione, ho chiesto comunque il cono scegliendo due gusti, scoprendo che in realtà  per la stessa cifra se ne potevano prendere tre... Considerando la porzione davvero piccola (altra nota dolente), avrei senz'altro avuto piacere di aggiungere un altro gusto per lo stesso prezzo! Insomma, per me c'è di meglio.";

	    System.out.printf("Document = %s%n", document);

	    AnalyzeSentimentOptions options = new AnalyzeSentimentOptions().setIncludeOpinionMining(true);

	    final DocumentSentiment documentSentiment = client.analyzeSentiment(document, "en", options);

	    SentimentConfidenceScores scores = documentSentiment.getConfidenceScores();
	    System.out.printf(
	            "\tRecognized document sentiment: %s, positive score: %f, neutral score: %f, negative score: %f.%n",
	            documentSentiment.getSentiment(), scores.getPositive(), scores.getNeutral(), scores.getNegative());

	    documentSentiment.getSentences().forEach(sentenceSentiment -> {
	        SentimentConfidenceScores sentenceScores = sentenceSentiment.getConfidenceScores();
	        System.out.printf("\t\tSentence sentiment: %s, positive score: %f, neutral score: %f, negative score: %f.%n",
	                sentenceSentiment.getSentiment(), sentenceScores.getPositive(), sentenceScores.getNeutral(), sentenceScores.getNegative());
	        sentenceSentiment.getMinedOpinions().forEach(minedOpinions -> {
	            AspectSentiment aspectSentiment = minedOpinions.getAspect();
	            System.out.printf("\t\t\tAspect sentiment: %s, aspect text: %s%n", aspectSentiment.getSentiment(),
	                    aspectSentiment.getText());
	            for (OpinionSentiment opinionSentiment : minedOpinions.getOpinions()) {
	                System.out.printf("\t\t\t\t'%s' opinion sentiment because of \"%s\". Is the opinion negated: %s.%n",
	                        opinionSentiment.getSentiment(), opinionSentiment.getText(), opinionSentiment.isNegated());
	            }
	        });
	    });
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
	
	static void extractKeyPhrasesExample(TextAnalyticsClient client, String text)
	{
	    // The text that need be analyzed.

	    System.out.printf("Recognized phrases: %n");
	    for (String keyPhrase : client.extractKeyPhrases(text)) {
	        System.out.printf("%s%n", keyPhrase);
	    }
	}
}
	

