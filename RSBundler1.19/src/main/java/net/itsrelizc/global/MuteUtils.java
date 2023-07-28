package net.itsrelizc.global;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.simple.JSONObject;

public class MuteUtils {

	public static boolean chatEvent(AsyncPlayerChatEvent event) {
		JSONObject obj = JSON.loadDataFromDataBase("muted-players.json");
		
		if (obj.containsKey(event.getPlayer().getUniqueId().toString())) {
			
			JSONObject data = (JSONObject) obj.get(event.getPlayer().getUniqueId().toString());
			
			long secondsOffset = (((long) data.get("expires")) * 1000 - System.currentTimeMillis()) / 1000;
			
			if (secondsOffset <= 0) {
				obj.remove(event.getPlayer().getUniqueId().toString());
				JSON.saveDataFromDataBase("muted-players.json", obj);
				return false;
			}
			
			long days = secondsOffset / 86400;
	    	long hours = (secondsOffset - (days * 86400)) / 3600;
	    	long minutes = (secondsOffset - (days * 86400) - (hours * 3600)) / 60;
	    	long seconds = secondsOffset % 60;
			
			String mutereason = "§c§m" + StringUtils.repeat('-', 50)
					+ "\n§cYou have been muted."
					+ "\n "
					+ "\n§eRemaining Time: §b" + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
					+ "\n "
					+ "\n§eMute Reason: §b" + data.get("reason")
					+ "\n§eAssociated Mute ID: §b#" + ((String) data.get("id")).toUpperCase()
					+ "\n "
					+ "\n§aAppeals avaliable at §2§nhttps://itsrelizc.net/appeals§r"
					+ "\n§c§m" + StringUtils.repeat('-', 50);
			
			event.setCancelled(true);
			event.getPlayer().sendMessage(mutereason);
			
			return true;
		}
		
		return false;
	}
	
	public static void createMute(Player player, String reason, Long secondsOffset) {
		JSONObject hash = new JSONObject();
		
		JSONObject obj = JSON.loadDataFromDataBase("muted-players.json");
		
		hash.put("reason", reason);
		hash.put("expires", (System.currentTimeMillis() / 1000) + secondsOffset);
    	hash.put("id", generateRandomID());
    	
    	obj.put(player.getUniqueId().toString(), hash);
    	
    	JSON.saveDataFromDataBase("muted-players.json", obj);
    	
    	long days = secondsOffset / 86400;
    	long hours = (secondsOffset - (days * 86400)) / 3600;
    	long minutes = (secondsOffset - (days * 86400) - (hours * 86400)) / 60;
    	long seconds = secondsOffset % 60;
    	
    	String mutereason = "§c§m" + StringUtils.repeat('-', 50)
				+ "\n§cYou have been muted."
				+ "\n "
				+ "\n§eRemaining Time: §b" + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
				+ "\n "
				+ "\n§eMute Reason: §b" + hash.get("reason")
				+ "\n§eAssociated Mute ID: §b#" + ((String) hash.get("id")).toUpperCase()
				+ "\n "
				+ "\n§aAppeals avaliable at §2§nhttps://itsrelizc.net/appeals§r"
				+ "\n§c§m" + StringUtils.repeat('-', 50);
    	
    	player.sendMessage(mutereason);
    	
    	ChatUtils.broadcastSystemMessage("§c§lMUTE", "§b" + player.getDisplayName() + " §ehad been muted! Shame on him!");
    	ChatUtils.broadcastSystemMessage("§c§lMUTE", "§eUse §b/report §eto report more rule breakers!");
	}
	
	public static String generateRandomID() {
		String f = "";
		for (int i = 0; i < 8; i ++) {
			if (new Random().nextBoolean()) {
				char c = (char) (new Random().nextInt((57 - 48) + 1) + 48);
				f += Character.toString(c);
			} else {
				char c = (char) (new Random().nextInt((90 - 65) + 1) + 65);
				f += Character.toString(c);
			}
		}
		return f;
	}

}
