package net.itsrelizc.copy;

import com.google.gson.JsonObject;

import net.itsrelizc.copy.JSON;
import net.itsrelizc.copy.Lang;
import net.itsrelizc.copy.Lang.LangType;
import net.itsrelizc.copy.Lang.LangValue;
import net.itsrelizc.copy.Lang.Language;
import net.itsrelizc.copy.Rank.Cosmetic;
import net.itsrelizc.copy.Rank.Measurable;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerProfile {

	private ProxiedPlayer player;
	private String name;
	private String lowerName;
	private Long rank;
	private Long language;
	private Long cosmeticRank;
	
	public static boolean checkAccountExsists(ProxiedPlayer player) {
		JsonObject loaded = JSON.loadDataFromDataBase("player.json");
		return loaded.keySet().contains(player.getUniqueId().toString());
	}
	
	public static void checkAccountExsistsThenCreate(ProxiedPlayer player) {
		if (!checkAccountExsists(player)) {
			JsonObject loaded = JSON.loadDataFromDataBase("player.json");
			
			// Data Sertalization
			JsonObject pack = new JsonObject();
			pack.addProperty("name", player.getDisplayName());
			pack.addProperty("name", player.getDisplayName());
			pack.addProperty("name_lower", player.getName());
			pack.addProperty("rank", 0);
			pack.addProperty("cosmetic_rank", 0);
			pack.addProperty("lang", 0);
			
			loaded.add(player.getUniqueId().toString(), pack);
			
			
			
			JsonObject stats = JSON.loadDataFromDataBase("stats.json");
			
			pack = new JsonObject();
			pack.addProperty("deathswap_kills", 0);
			pack.addProperty("deathswap_deaths", 0);
			pack.addProperty("deathswap_wins", 0);
			pack.addProperty("deathswap_winstreak", 0);
			pack.addProperty("deathswap_best_winstreak", 0);
			pack.addProperty("deathsawp_losestreak", 0);
			pack.addProperty("deathsawp_best_losestreak", 0);
			
			stats.add(player.getUniqueId().toString(), pack);
			
			JSON.saveDataFromDataBase("player.json", loaded);
			JSON.saveDataFromDataBase("stats.json", stats);
		}
	}
	
	public PlayerProfile(ProxiedPlayer player) {
		JsonObject loaded = JSON.loadDataFromDataBase("player.json");
		JsonObject hash = (JsonObject) loaded.get(player.getUniqueId().toString());
		
		this.player = player;
		this.name = this.player.getDisplayName();
		this.lowerName = this.player.getName();
		this.rank = (Long) hash.get("rank").getAsLong();
		this.cosmeticRank = (Long) hash.get("cosmetic_rank").getAsLong();
		this.language = (Long) hash.get("lang").getAsLong();
	}
	
	public ProxiedPlayer getPlayer() {
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
			if (rank.getPermission() == this.rank) {
				System.out.print(1);
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
