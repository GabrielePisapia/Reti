package com.sm.reti.fabrikam_functions;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.ai.textanalytics.models.*;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;

import java.io.IOException;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import java.util.ArrayList;

import org.json.JSONArray;

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
	
	/*static ArrayList<String> getDocumentSentiment(TextAnalyticsClient client, ReviewsBean r, WriterFile w) throws IOException { 
		
		String doc = Translator.translate("", "en", r.getTitolo() + " " + r.getCorpo());
	    AnalyzeSentimentOptions options = new AnalyzeSentimentOptions().setIncludeOpinionMining(true);

	    final DocumentSentiment documentSentiment = client.analyzeSentiment(doc, "en", options);
	    SentimentConfidenceScores scores = documentSentiment.getConfidenceScores();
	    
	    ArrayList<String> res = new ArrayList<String>();
	    res.add(documentSentiment.getSentiment().toString());
	    res.add(String.valueOf(scores.getPositive()));
	    res.add(String.valueOf(scores.getNeutral()));
	    res.add(String.valueOf(scores.getNegative()));
	    return res;
	}*/
	
	static JSONArray sentimentAnalysisWithOpinionMining(TextAnalyticsClient client, String textOfReview ) throws IOException
	{
		System.out.printf("Document = %s%n", textOfReview);
		String doc = Translator.translate("", "en", textOfReview);
	    System.out.printf("Document = %s%n", doc);
	    
	    JSONArray sentInfo = new JSONArray();         /*L'array da restituire ed inserire in writerfile*/

	    AnalyzeSentimentOptions options = new AnalyzeSentimentOptions().setIncludeOpinionMining(true);
	    if(client== null) {
	    	System.out.println("stampa null");
	    }
	    final DocumentSentiment documentSentiment = client.analyzeSentiment(doc, "en", options);

	    SentimentConfidenceScores scores = documentSentiment.getConfidenceScores();
	    System.out.printf(
	            "\tRecognized document sentiment: %s, positive score: %f, neutral score: %f, negative score: %f.%n",
	            documentSentiment.getSentiment(), scores.getPositive(), scores.getNeutral(), scores.getNegative());
	    
	    String sentimento = documentSentiment.getSentiment().toString();
	    double positiveSc = scores.getPositive();
	    double neutralSc = scores.getNeutral();
	    double negativeSc = scores.getNegative();
	    
	    /*In questa parte inserisco sentiment e punteggio
	     * NB. SICCOME NON SI POSSONO DARE NOMI AI CAMPI DI UN JSON ARRAY IL FORMATO DEVE ESSERE SEMPRE QUESTO:
	     * 1) SENTIMENTO
	     * 2) punteggi in questo ordine (positivo,neutrale,negativo)
	     * */
	    
	    sentInfo.put(sentimento);
	    sentInfo.put(positiveSc);
	    sentInfo.put(neutralSc);
	    sentInfo.put(negativeSc);
	    

	    documentSentiment.getSentences().forEach(sentenceSentiment -> {
	        SentimentConfidenceScores sentenceScores = sentenceSentiment.getConfidenceScores();
	        System.out.printf("\t\tSentence sentiment: %s, positive score: %f, neutral score: %f, negative score: %f.%n",
	        		sentenceSentiment.getSentiment(), sentenceScores.getPositive(), sentenceScores.getNeutral(), sentenceScores.getNegative());
	     
	     String sentenceInfo = sentenceSentiment.getSentiment().toString();
	     double sentencePos = sentenceScores.getPositive();
	     double sentenceNeut = sentenceScores.getNeutral();
	     double sentenceNeg = sentenceScores.getNegative();
	     
	     
	       /*Qua prendiamo le info sul sentiment delle sentenze
	        * NB.Non mi interessa lo score, ma se lo si vuole aggiungere basta inserirlo con put
	        * */
	     sentInfo.put(sentenceInfo);
	     
	        sentenceSentiment.getMinedOpinions().forEach(minedOpinions -> {
	            AspectSentiment aspectSentiment = minedOpinions.getAspect();
	            System.out.printf("\t\t\tAspect sentiment: %s, aspect text: %s%n", aspectSentiment.getSentiment(),
	                    aspectSentiment.getText());
	            
	            /*Raccolta ed inserimento degli aspetti, formato sentimento- testo*/
	            
	            String sentAspect = aspectSentiment.getSentiment().toString();
	            String textAspect = aspectSentiment.getText();
	            sentInfo.put(sentAspect);
	            sentInfo.put(textAspect);
	            for (OpinionSentiment opinionSentiment : minedOpinions.getOpinions()) {
	                System.out.printf("\t\t\t\t'%s' opinion sentiment because of \"%s\". Is the opinion negated: %s.%n",
	                        opinionSentiment.getSentiment(), opinionSentiment.getText(), opinionSentiment.isNegated());
	                
	                /*raccolta opinioni ed inserimento formato sentiment-testo*/
	                String minedSentiment = opinionSentiment.getSentiment().toString();
	                String textMined = opinionSentiment.getText();
	                sentInfo.put(minedSentiment);
	                sentInfo.put(textMined);
	            }
	        });
	   });
	     return sentInfo;
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
	

