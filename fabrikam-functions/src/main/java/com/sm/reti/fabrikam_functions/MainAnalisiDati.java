package com.sm.reti.fabrikam_functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class MainAnalisiDati {
	
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		
		
		//Prendo i nomi di tutti i file per recuperare i ristoranti
		File file = new File("output");
		File[] fileArray = file.listFiles();
		for(int i = 0; i < fileArray.length; i++) {
				analize(fileArray[i].getName());
		}
	}
	
	public static void analize(String s) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader("output\\" + s));
		JsonObject risto = (JsonObject) obj;
		JsonArray j = (JsonArray) risto.get("RISTORANTI");
		JsonObject x = (JsonObject) j.get(0);
		System.out.println(((JsonArray) x.get("RECENSIONI")).size());
	}

}
