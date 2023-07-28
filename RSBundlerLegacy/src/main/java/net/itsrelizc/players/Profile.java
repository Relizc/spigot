package net.itsrelizc.players;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Profile {
	
	public Player owner;
	
	public String name;
	public String displayName;
	public List<String> nameHistory;
	
	public long permission;
	public boolean useAdvancedNameDisplay;
	public String advancedNameDisplay;
	
	public long level;
	
	private static List<Profile> profiles = new ArrayList<Profile>();
	
	public Profile(Player player, JSONObject profiledata) {
		this.owner = player;
		
		this.displayName = player.getDisplayName();
		this.name = player.getName();
		this.nameHistory = (List<String>) profiledata.get("general-namehist");
		
		this.permission = (long) profiledata.get("general-rank");
		this.level = (long) profiledata.get("general-rank");
		
		addProfile(this);
	}
	
	public static Profile findByOwner(Player player) {
		for (Profile p : profiles) {
			if (p.owner.getName().equalsIgnoreCase(player.getName())) {
				return p;
			}
		}
		
		return null;
	}
	
	public static void addProfile(Profile profile) {
		profiles.add(profile);
	}
	
	public static void removeProfile(Profile profile) {
		profiles.remove(profile);
	}
	
	public static boolean checkAccountExists(Player player) {
		
		JSONObject j = DataManager.loadPureJsonFromDb("players.json");
		
		return j.containsKey(player.getUniqueId().toString());
	
	}
	
	public static boolean checkAccountExsistsThenCreate(Player player) {
		
		if (!checkAccountExists(player)) {
			JSONObject j = DataManager.loadPureJsonFromDb("players.json");
			
			JSONObject d_general = new JSONObject();
			d_general.put("general-lv", 0);
			d_general.put("general-rank", 1);
			
			JSONArray names = new JSONArray();
			names.add(player.getDisplayName());
			d_general.put("general-namehist", names);
			
			j.put(player.getUniqueId().toString(), d_general);
			
			try {
				DataManager.savePureJsonToDb("players.json", j);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
		}
		
		return true;
		
	}
	
	public static Profile createProfile(Player player) {
		
		checkAccountExsistsThenCreate(player);
		
		JSONObject d = (JSONObject) DataManager.loadPureJsonFromDb("players.json").get(player.getUniqueId().toString());
		
		return new Profile(player, d);
	}
	
}
