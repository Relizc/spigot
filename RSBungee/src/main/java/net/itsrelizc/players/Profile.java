package net.itsrelizc.players;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.saltyfishstudios.bungee.DataManager;

public class Profile {
	
	public ProxiedPlayer owner;
	
	public String name;
	public String displayName;
	public List<String> nameHistory;
	
	public long permission;
	public boolean useAdvancedNameDisplay;
	public String advancedNameDisplay;
	
	public UUID realUUID;
	
	public long level;
	
	private static List<Profile> profiles = new ArrayList<Profile>();
	
	public Profile(ProxiedPlayer player, JSONObject profiledata) {
		this.owner = player;
		
		this.displayName = player.getDisplayName();
		this.name = player.getName();
		
		this.nameHistory = (List<String>) profiledata.get("general-namehist");
		
		this.permission = (long) profiledata.get("general-rank");
		this.level = (long) profiledata.get("general-lv");
		
		this.owner.setPermission("main.admin", Rank.findByPermission(this.permission).useop);
		
		addProfile(this);
	}
	
	public static String coloredName(ProxiedPlayer player) {
		Profile p = findByOwner(player);
		return Rank.findByPermission(p.permission).displayName.substring(0, 2) + player.getDisplayName();
	}
	
	public static Profile findByOwner(ProxiedPlayer player) {
		for (Profile p : profiles) {
			if (p.owner.getName().equalsIgnoreCase(player.getName())) {
				return p;
			}
		}
		
		return null;
	}
	
	public static Profile findByOwner(String player) {
		for (Profile p : profiles) {
			if (p.owner.getName().equalsIgnoreCase(player)) {
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
	
	public static boolean checkAccountExsistsThenCreate(ProxiedPlayer player) {
		
		JSONObject j = DataManager.loadPureJsonFromDb("players.json");
		
		if (!j.keySet().contains(player.getUniqueId().toString())) {
			
			
			
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
			
			player.sendMessage(
				"§e§m----------------------------------------------------\n" +
			  "§r§b§l             Welcome To Relizc Network!\n"+
			  "§r§7§o            Thanks for playing our server!\n"+
			  "§r\n"+
			    "§r§e   Dont know where               Want to socialize  \n" +
			    "§r§e     to start?                         more?        \n" +
			    "§r§b   ✔§7 Start by               §b✔§7 Take a look    \n" +
			    "§r§7      looking at                    at our clan     \n" +
			    "§r§7      the menu!                      browser!       \n"
			);
		}
		
		return true;
		
	}
	

	public static Profile createProfile(ProxiedPlayer player) {
		
		JSONObject d = null;
		
		checkAccountExsistsThenCreate(player);
		
		try {
			 d = (JSONObject) DataManager.loadPureJsonFromDb("players.json").get(player.getUniqueId().toString());
		} catch (Exception e) {
			
		}
		
		Profile a = new Profile(player, d);
		
		
		return a;
	}
	
}
