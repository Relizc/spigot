package net.itsrelizc.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.json.simple.JSONObject;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.JSON;
import net.itsrelizc.lifesteal.Music;

public class BanUtils {
	
	public static void checkLogin(PlayerLoginEvent event) {
		JSONObject obj = JSON.loadDataFromDataBase("banned-players.json");
		if (obj.containsKey(event.getPlayer().getUniqueId().toString())) {
			JSONObject data = (JSONObject) obj.get(event.getPlayer().getUniqueId().toString());
			
			long secondsOffset = (((long) (data.get("expires")) * 1000) - System.currentTimeMillis()) / 1000;
			
			if (secondsOffset <= 0) {
				data.remove(event.getPlayer().getUniqueId().toString());
				JSON.saveDataFromDataBase("banned-players.json", data);
				return;
			}
			
			long days = secondsOffset / 86400;
	    	long hours = (secondsOffset - (days * 86400)) / 3600;
	    	long minutes = (secondsOffset - (days * 86400) - (hours * 3600)) / 60;
	    	long seconds = secondsOffset % 60;
	    	
			String mutereason = "§cYou have been banned §r§cfrom joining §esmp.itsrelizc.net§r§c.\n"
					+ "§eRemaining Time: §b§l"  + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
					+ "\n\n§eBanned Reason: §b" + data.get("reason")
					+ "\n§eAssociated Ban ID: §b#" + ((String) data.get("id")).toUpperCase()
					+ "\n\n"
					+ "§7To appeal for your ban, please visit the link below. You are required"
					+ "\n§7to provide your Ban ID and sufficient evidence."
					+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
					+ "\n§7decision will be final and appeals will no longer be accepted."
					+ "\n\n§aAppeals avaliable at §2§nhttps://relizc.github.io/appeals§r §aor via §9Microsoft Teams";
			event.disallow(Result.KICK_BANNED, mutereason);
		}
	}

	public static void createBan(Player player, String reason, Long secondsOffset) {
		JSONObject hash = new JSONObject();
		
		JSONObject obj = JSON.loadDataFromDataBase("banned-players.json");
		
		hash.put("reason", reason);
		hash.put("expires", (System.currentTimeMillis() / 1000) + secondsOffset);
    	hash.put("id", MuteUtils.generateRandomID());
    	
    	obj.put(player.getUniqueId().toString(), hash);
    	
    	JSON.saveDataFromDataBase("banned-players.json", obj);
    	
    	long days = secondsOffset / 86400;
    	long hours = (secondsOffset - (days * 86400)) / 3600;
    	long minutes = (secondsOffset - (days * 86400) - (hours * 86400)) / 60;
    	long seconds = secondsOffset % 60;
    	
    	String mutereason = "§cYou have been banned §r§cfrom joining §esmp.itsrelizc.net§r§c.\n"
				+ "§eRemaining Time: §b§l" + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
				+ "\n\n§eBanned Reason: §b" + hash.get("reason")
				+ "\n§eAssociated Ban ID: §b#" + ((String) hash.get("id")).toUpperCase()
				+ "\n\n"
				+ "§7To appeal for your ban, please visit the link below. You are required"
				+ "\n§7to provide your Ban ID and sufficient evidence."
				+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
				+ "\n§7decision will be final and appeals will no longer be accepted."
				+ "\n\n§aAppeals avaliable at §2§nhttps://sfn.gg/appeals §aor via §9Microsoft Teams";
    	
    	player.kickPlayer(mutereason);
    	
    	ChatUtils.broadcastSystemMessage("§c§lBAN", "§b" + player.getDisplayName() + " §ehad been banned! Shame on him!");
    	ChatUtils.broadcastSystemMessage("§c§lBAN", "§eUse §b/report §eto report more rule breakers!");
	}
	
	public static void createBan(Player player, String reason) {
		createBan(player, reason, (long) (1 * 24 * 60 * 60));
	}

}
