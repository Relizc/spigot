	package net.itsrelizc.copy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JSON {
	public static JsonObject pathLoadData(String path) {
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(path));
			return new JsonParser().parse(reader).getAsJsonObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static JsonObject loadDataFromDataBase(String name) {
		return pathLoadData("D:\\ServerData\\" + name);
	}
	
	public static void pathSaveData(String path, JsonElement data) {
		FileWriter file;
		try {
			file = new FileWriter(path);
			Gson gson = new Gson();
			gson.toJson(data, file);
	        file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveDataFromDataBase(String name, JsonElement data) {
		pathSaveData("D:\\ServerData\\" + name, data);
	}
}
