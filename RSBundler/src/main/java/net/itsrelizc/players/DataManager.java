package net.itsrelizc.players;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataManager {
	
	public static File openFileFromDb(String dbname) {
		File current = new File(System.getProperty("user.dir"));
		File database = new File(current.getParentFile().getParentFile().toString() + "\\database\\" + dbname);
		
		return database;
	}
	
	public static JSONObject loadPureJsonFromDb(String dbname) {
		File current = new File(System.getProperty("user.dir"));
		File database = new File(current.getParentFile().getParentFile().toString() + "\\database\\" + dbname);
		
		if (database.exists()) {
			JSONParser p = new JSONParser();
			
			JSONObject result = null;
			
			try {
				result = (JSONObject) p.parse(new FileReader(database));
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return result;
		}
		
		
		return null;
	}
	
	public static void savePureJsonToDb(String dbname, JSONObject result) throws FileNotFoundException {
		File current = new File(System.getProperty("user.dir"));
		File database = new File(current.getParentFile().getParentFile().toString() + "\\database");
		File dataBaseMore = new File(current.getParentFile().getParentFile().toString()+"\\database\\"+dbname);

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
		try {
			FileWriter fw = new FileWriter(dataBaseMore);
			fw.write(result.toJSONString());
			fw.flush();
			fw.close();
			return;

		} catch (IOException e) {
			throw new FileNotFoundException();
		}


	}
	
}
