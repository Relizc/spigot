package net.saltyfishstudios.bungee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataManager {
	
	public static JSONObject loadPureJsonFromDb(String dbname) {
		File current = new File(System.getProperty("user.dir"));
		File database = new File(current.getParentFile().toString() + "\\database");
		
		for (File f : database.listFiles()) {
			if (f.getName().equalsIgnoreCase(dbname)) {
				JSONParser parser = new JSONParser();
				
				Object obj = null;
				try {
					obj = parser.parse(new FileReader(f));
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            JSONObject jsonObject =  (JSONObject) obj;
	            
	            return jsonObject;
			}
		}
		
		return null;
	}
	
	public static void savePureJsonToDb(String dbname, JSONObject result) throws FileNotFoundException {
		File current = new File(System.getProperty("user.dir"));
		File database = new File(current.getParentFile().toString() + "\\database");
		
		for (File f : database.listFiles()) {
			if (f.getName().equalsIgnoreCase(dbname)) {
				try {
					FileWriter fw = new FileWriter(f);
					fw.write(result.toJSONString());
					fw.flush();
					fw.close();
					
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		throw new FileNotFoundException();
	}
	
}
