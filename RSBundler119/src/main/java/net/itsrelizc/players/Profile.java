package net.itsrelizc.players;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;

public class Profile {
	
	public Player owner;
	
	public String name;
	public String displayName;
	public List<String> nameHistory;
	
	public long permission;
	public boolean useAdvancedNameDisplay;
	public String advancedNameDisplay;
	
	public UUID realUUID;
	
	public long level;
	
	public static List<Player> awaitCreation = new ArrayList<Player>();
	public static List<Player> awaitSetUUID = new ArrayList<Player>();
	
	private static List<Profile> profiles = new ArrayList<Profile>();
	
	public Profile(Player player, JSONObject profiledata) {
		this.owner = player;
		
		this.displayName = player.getDisplayName();
		this.name = player.getName();
		
//		this.nameHistory = (List<String>) profiledata.get("general-namehist");
//		
//		this.permission = (long) profiledata.get("general-rank");
//		this.level = (long) profiledata.get("general-rank");
//		
//		this.realUUID = null;
		
		this.nameHistory = null;
		
		this.permission = -1L;
		this.level = -1L;
		
		this.realUUID = null;
		
		addProfile(this);
	}
	
	public static String coloredName(Player player) {
		Profile p = findByOwner(player);
		return Rank.findByPermission(p.permission).displayName.substring(0, 2) + player.getDisplayName();
	}
	
	public static Profile findByOwner(Player player) {
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
	
	public static boolean checkAccountExists(Player player) {
		
		JSONObject j = DataManager.loadPureJsonFromDb("players.json");
		
		
		
		return j.containsKey(player.getUniqueId().toString());
	
	}
	
	public static boolean checkAccountExsistsThenCreate(Player player) {
		
		Communication com = new Communication(CommunicationType.BUNGEE_PLAYER_INFO);
		com.writeByte((byte) 0x01);
		com.writeString(player.getName());
		try {
			com.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public void reloadProfile() {
		JSONObject profiledata = (JSONObject) DataManager.loadPureJsonFromDb("players.json").get(this.realUUID.toString());
		
		this.nameHistory = (List<String>) profiledata.get("general-namehist");
		
		this.permission = (long) profiledata.get("general-rank");
		this.level = (long) profiledata.get("general-rank");
		
		Rank r = Rank.findByPermission(this.permission);
		
		if (Grouping.showPrefix) {
			this.owner.setPlayerListName(r.displayName + " " + this.owner.getName());
		} else {
			this.owner.setPlayerListName(r.displayName.substring(0, 2) + this.owner.getName());
		}
		
		this.owner.setOp(r.useop);
	}
	
	public static Profile createProfile(Player player) {
		
		JSONObject d = null;
		
		try {
			 d = (JSONObject) DataManager.loadPureJsonFromDb("players.json").get(player.getUniqueId().toString());
		} catch (Exception e) {
			
		}
		
		Profile a = new Profile(player, d);
		
		
		checkAccountExsistsThenCreate(player);
		
		
		
		return a;
	}
	
}
