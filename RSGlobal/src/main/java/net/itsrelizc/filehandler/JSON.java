package net.itsrelizc.filehandler;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON {
	public static JSONObject pathLoadData(String path) {
		JSONParser parser = new JSONParser();
		Object object = null;
		try {
			object = parser.parse(new FileReader(path));
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		return (JSONObject) object;
	}
	
	public static JSONObject loadDataFromDataBase(String name) {
		return pathLoadData("D:\\ServerData\\" + name);
	}
	
	public static void pathSaveData(String path, JSONObject data) {
		FileWriter file;
		try {
			file = new FileWriter(path);
			file.write(data.toJSONString());
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveDataFromDataBase(String name, JSONObject data) {
		pathSaveData("D:\\ServerData\\" + name, data);
	}
}
