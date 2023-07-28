package net.itsrelizc.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.simple.JSONObject;

import net.itsrelizc.lifesteal.JSON;

public class PlayerManager {
	public static Map<Player, Rank> ranks = new HashMap<Player, Rank>();
	
	public static void checkAccountExists(Player player) {
		JSONObject data = JSON.loadDataFromDataBase("players.json");
		if (!data.containsKey(player.getUniqueId().toString())) {
			JSONObject hash = new JSONObject();
			
			hash.put("cosmetic_rank", 0);
			hash.put("rank", 0);
			hash.put("lang", 0);
			hash.put("name", player.getDisplayName());
			
			data.put(player.getUniqueId().toString(), hash);
			
			JSON.saveDataFromDataBase("players.json", data);
		}
	}
	
	
	
	public static void loginCheck(Player player) {
		checkAccountExists(player);
		JSONObject data = (JSONObject) JSON.loadDataFromDataBase("players.json").get(player.getUniqueId().toString());
		Rank rank = Rank.findByPermission(((Long) data.get("rank")).intValue());
		
		if (rank.useop) {
			player.setOp(true);
		} else {
			player.setOp(false);
		}
		
		ranks.put(player, rank);
	}

	public static void chatEvent(AsyncPlayerChatEvent event) {
		event.setFormat(ranks.get(event.getPlayer()).displayName + " " + event.getPlayer().getDisplayName() + "§7: §r" + event.getMessage().replaceAll("%", "%%"));
	}
}
