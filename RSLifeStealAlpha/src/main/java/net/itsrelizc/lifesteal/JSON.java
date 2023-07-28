package net.itsrelizc.lifesteal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.itsrelizc.lifesteal.JSON;

public class JSON {
	public static JSONObject pathLoadData(String path) {
		JSONParser parser = new JSONParser();
		Object object = null;
		try {
			object = parser.parse(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (JSONObject) object;
	}
	
	public static JSONObject loadDataFromDataBase(String name) {
		return pathLoadData(Bukkit.getPluginManager().getPlugin("RSLifeStealAlpha").getDataFolder().getAbsolutePath() + "\\" + name);
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
		pathSaveData(Bukkit.getPluginManager().getPlugin("RSLifeStealAlpha").getDataFolder().getAbsolutePath() + "\\" + name, data);
	}
	
	public static boolean checkAccountExsists(Player player) {
		try {

			JSONObject players = JSON.loadDataFromDataBase("player.json");
			return players.containsKey(player.getUniqueId().toString());
			
			
		} catch (Exception e) {
			Bukkit.getLogger().warning("Cannot find true UUID of player " + player.getDisplayName());
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void checkAccountExsistsThenCreate(Player player) {
		if (!checkAccountExsists(player)) {
			try {
				JSONObject loaded = JSON.loadDataFromDataBase("player.json");
				
				// Data Sertalization
				JSONObject pack = new JSONObject();
				pack.put("name", player.getDisplayName());
				pack.put("name_lower", player.getName());
				pack.put("rank", 0);
				pack.put("cosmetic_rank", 0);
				pack.put("lang", 0);
				
				loaded.put(player.getUniqueId().toString(), pack);
				
				
				
				JSONObject stats = JSON.loadDataFromDataBase("stats.json");
				
				pack = new JSONObject();
				pack.put("deathswap_kills", 0);
				pack.put("deathswap_deaths", 0);
				pack.put("deathswap_wins", 0);
				pack.put("deathswap_winstreak", 0);
				pack.put("deathswap_best_winstreak", 0);
				pack.put("deathsawp_losestreak", 0);
				pack.put("deathsawp_best_losestreak", 0);
				
				stats.put(player.getUniqueId().toString(), pack);
				
				JSON.saveDataFromDataBase("player.json", loaded);
				JSON.saveDataFromDataBase("stats.json", stats);
				
				
			} catch (Exception e) {
				Bukkit.getLogger().warning("Cannot find true UUID of player " + player.getDisplayName());
				e.printStackTrace();
			}
			
			
		}
	}
}
