package net.itsrelizc.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.itsrelizc.filehandler.JSON;
import net.itsrelizc.lang.Lang;
import net.itsrelizc.lang.Lang.LangType;
import net.itsrelizc.lang.Lang.LangValue;
import net.itsrelizc.lang.Lang.Language;
import net.itsrelizc.player.Rank.Cosmetic;
import net.itsrelizc.player.Rank.Measurable;

public class PlayerProfile {
	
	public static boolean checkAccountExsists(Player player) {
		try {
			URL url = new URL("http://127.0.0.1:65534/getUUID?target=" + player.getName());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			System.out.println("Player Profile get UUID Response Code - " + responseCode);
			
			BufferedReader br = null;
			if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
			    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
			    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			UUID trueuuid = UUID.fromString(br.readLine());
			
			JSONObject players = JSON.loadDataFromDataBase("player.json");
			return players.containsKey(trueuuid.toString());
			
			
		} catch (Exception e) {
			Bukkit.getLogger().warning("Cannot find true UUID of player " + player.getDisplayName());
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void checkAccountExsistsThenCreate(Player player) {
		if (!checkAccountExsists(player)) {
			try {
				URL url = new URL("http://127.0.0.1:65534/getUUID?target=" + player.getName());
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				
				int responseCode = con.getResponseCode();
				System.out.println("Player Profile get UUID Response Code - " + responseCode);
				
				BufferedReader br = null;
				if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
				    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				} else {
				    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				}
				UUID trueuuid = UUID.fromString(br.readLine());
				
				
				JSONObject loaded = JSON.loadDataFromDataBase("player.json");
				
				// Data Sertalization
				JSONObject pack = new JSONObject();
				pack.put("name", player.getDisplayName());
				pack.put("name_lower", player.getName());
				pack.put("rank", 0);
				pack.put("cosmetic_rank", 0);
				pack.put("lang", 0);
				
				loaded.put(trueuuid.toString(), pack);
				
				
				
				JSONObject stats = JSON.loadDataFromDataBase("stats.json");
				
				pack = new JSONObject();
				pack.put("deathswap_kills", 0);
				pack.put("deathswap_deaths", 0);
				pack.put("deathswap_wins", 0);
				pack.put("deathswap_winstreak", 0);
				pack.put("deathswap_best_winstreak", 0);
				pack.put("deathsawp_losestreak", 0);
				pack.put("deathsawp_best_losestreak", 0);
				
				stats.put(trueuuid.toString(), pack);
				
				JSON.saveDataFromDataBase("player.json", loaded);
				JSON.saveDataFromDataBase("stats.json", stats);
				
				
			} catch (Exception e) {
				Bukkit.getLogger().warning("Cannot find true UUID of player " + player.getDisplayName());
				e.printStackTrace();
			}
			
			
		}
	}

	private Player player;
	private String name;
	private String lowerName;
	private Long rank;
	private Long language;
	private Long cosmeticRank;
	private UUID uuid;
	
	public PlayerProfile(Player player) {
		try {
			URL url = new URL("http://127.0.0.1:65534/getUUID?target=" + player.getName());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			System.out.println("Player Profile get UUID Response Code - " + responseCode);
			
			BufferedReader br = null;
			if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
			    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
			    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			this.uuid = UUID.fromString(br.readLine());
			
		} catch (Exception e) {
			Bukkit.getLogger().warning("Cannot find true UUID of player " + player.getDisplayName());
			e.printStackTrace();
		}
				
		JSONObject loaded = JSON.loadDataFromDataBase("player.json");
		JSONObject hash = (JSONObject) loaded.get(this.uuid.toString());
		
		this.player = player;
		this.name = this.player.getDisplayName();
		this.lowerName = this.player.getName();
		this.rank = (Long) hash.get("rank");
		this.cosmeticRank = (Long) hash.get("cosmetic_rank");
		this.language = (Long) hash.get("lang");
		
	}
	
	public UUID getCurrentUUID() {
		return this.player.getUniqueId();
	}
	
	public UUID getRealUUID() {
		return this.uuid;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getName() {
		return lowerName;
	}
	
	public String getFullName() {
		return name;
	}
	
	public Long getRankValue() {
		return this.rank;
	}
	
	public Long getCosmeticRankValue() {
		return this.cosmeticRank;
	}
	
	public Measurable getRankType() {
		for (Measurable rank : Rank.Measurable.values()) {
			System.out.print(rank);
			System.out.print(rank.getPermission());
			System.out.print(this.rank);
			if (rank.getPermission().equals(this.rank)) {
				System.out.print("found");
				return rank;
			}
		}
		return null;
	}
	
	public LangType getLangType() {
		for (LangType lang : Lang.LangType.values()) {
			if (lang.getValue() == this.language) {
				return lang;
			}
		}
		return null;
	}
	
	public String getLangResult(Language language) {
		LangType type = this.getLangType();
		System.out.print(type);
		for (LangValue value : LangValue.values()) {
			System.out.print(1);
			System.out.print(value);
			if (value.name().equalsIgnoreCase(language.name())) {
				if (type.name().equalsIgnoreCase("EN_US")) {
					return value.getEnUsValue();
				} else if (type.name().equalsIgnoreCase("ZH_CN")) {
					return value.getZhCnValue();
				}
			}
		}
		return null;
	}
	
	public String b(Language language) {
		return this.getLangResult(language);
	}
}
