package net.itsrelizc.global;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.json.simple.JSONObject;

import net.itsrelizc.players.DataManager;
import net.itsrelizc.players.Profile;

public class BanUtils {
	
	public static void checkLogin(Profile p) {
		JSONObject obj = DataManager.loadPureJsonFromDb("banned-players.json");
		if (obj.containsKey(p.realUUID.toString())) {
			JSONObject data = (JSONObject) obj.get(p.realUUID.toString());
			
			if (!data.containsKey(p.owner.getUniqueId().toString())) {
				data.put(p.owner.getUniqueId().toString(), p.realUUID.toString());
				JSON.saveDataFromDataBase("banned-players.json", data);
			}
			
			long secondsOffset = (((long) (data.get("expires")) * 1000) - System.currentTimeMillis()) / 1000;
			boolean dotime = (boolean) data.get("permanent");
			
			long days = 0L, hours = 0L, minutes = 0L, seconds = 0L;
			
			if (!dotime) {
				if (secondsOffset <= 0) {
					if (secondsOffset <= 0) {
						for (Object pair : obj.keySet()) {
							String a = (String) pair;
							if (a.equalsIgnoreCase(p.realUUID.toString()) || ((String) obj.get(a)).equalsIgnoreCase(p.realUUID.toString())) {
								data.remove(a);
							}
						}
						return;
					}
					JSON.saveDataFromDataBase("banned-players.json", data);
					return;
				}
				
				days = secondsOffset / 86400;
		    	hours = (secondsOffset - (days * 86400)) / 3600;
		    	minutes = (secondsOffset - (days * 86400) - (hours * 3600)) / 60;
		    	seconds = secondsOffset % 60;
			}
	    	
	    	String mutereason;
	    	
	    	if (dotime) {
	    		if ((boolean) data.get("final")) {
		    		mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been permanently suspended to access our server."
							+ "\n\n§cYou have been suspended due to the following reason:§r§b\n" + data.get("reason")
							+ "\n\n"
							+ "§7This ban decision is §cFINAL§7, which no appeals are accepted and no"
							+ "\n§7exceptions will be tolerated. This might be a result of obvious rule"
							+ "\n§7breaking or continuous spamming appeals."
							+ "\n\n§bAppeals are not accepted for this ban.";
		    	} else {
		    		 mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been permanently suspended to access our server."
							+ "\n\n§cYou have been suspended due to the following reason:\n§b" + data.get("reason")
							+ "\n\n"
							+ "§7To submit an appeal, you will be required to provide your current"
							+ "\n§7Ban ID (§e@" + ((String) data.get("id")).toUpperCase() + "§7) and"
							+ " a sufficient amount of evidence."
							+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
							+ "\n§7decision will be final and appeals will no longer be accepted."
							+ "\n\n§aAppeals avaliable at §2https://mc.itsrelizc.net/appeals";
		    	}
	    	} else {
	    		if ((boolean) data.get("final")) {
		    		mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been temporarily suspended to access our server for"
							+ "\n§b§l"  + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
							+ "\n\n§cYou have been suspended due to the following reason:§r§b\n" + data.get("reason")
							+ "\n\n"
							+ "§7This ban decision is §cFINAL§7, which no appeals are accepted and no"
							+ "\n§7exceptions will be tolerated. This might be a result of obvious rule"
							+ "\n§7breaking or continuous spamming appeals."
							+ "\n\n§bAppeals are not accepted for this ban.";
		    	} else {
		    		 mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been temporarily suspended to access our server for"
							+ "\n§b§l"  + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
							+ "\n\n§cYou have been suspended due to the following reason:\n§b" + data.get("reason")
							+ "\n\n"
							+ "§7To submit an appeal, you will be required to provide your current"
							+ "\n§7Ban ID (§e@" + ((String) data.get("id")).toUpperCase() + "§7) and"
							+ " a sufficient amount of evidence."
							+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
							+ "\n§7decision will be final and appeals will no longer be accepted."
							+ "\n\n§aAppeals avaliable at §2https://mc.itsrelizc.net/appeals§r§a or"
							+ "\n§avisit §2https://mc.itsrelizc.net/a/" + ((String) data.get("id")).toUpperCase() + "§r§a to directly visit"
							+ "\n§ayour appeal page.";
		    	}
	    	}
	    	
			
			p.owner.kickPlayer(mutereason);
		}
	}
	
	public static void checkLogin(PlayerLoginEvent event) {
		JSONObject obj = DataManager.loadPureJsonFromDb("banned-players.json");
		if (obj.containsKey(event.getPlayer().getUniqueId().toString())) {
			
			String actualRecord = event.getPlayer().getUniqueId().toString();
			
			if (obj.get(event.getPlayer().getUniqueId().toString()) instanceof String) {
				actualRecord = (String) obj.get(event.getPlayer().getUniqueId().toString());
			}
			
			JSONObject data = (JSONObject) obj.get(actualRecord);
			
			System.out.print(event.getPlayer().getUniqueId().toString());
			
			long secondsOffset = (((long) (data.get("expires")) * 1000) - System.currentTimeMillis()) / 1000;
			
			if (secondsOffset <= 0) {
				for (Object pair : obj.keySet()) {
					String a = (String) pair;
					if (a.equalsIgnoreCase(actualRecord) || ((String) obj.get(a)).equalsIgnoreCase(actualRecord)) {
						data.remove(a);
					}
				}
				JSON.saveDataFromDataBase("banned-players.json", data);
				return;
			}
			
			long days = secondsOffset / 86400;
	    	long hours = (secondsOffset - (days * 86400)) / 3600;
	    	long minutes = (secondsOffset - (days * 86400) - (hours * 3600)) / 60;
	    	long seconds = secondsOffset % 60;
	    	
	    	String mutereason;
	    	
	    	if ((boolean) data.get("final")) {
	    		mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
		    			+ "\n§chas been temporarily suspended to access our server for"
						+ "\n§b§l"  + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
						+ "\n\n§cYou have been suspended due to the following reason:§b\n" + data.get("reason")
						+ "\n\n"
						+ "§7This ban decision is §cFINAL§7, which no appeals are accepted and no"
						+ "\n§7exceptions will be tolerated. This might be a result of obvious rule"
						+ "\n§7breaking or continuous spamming appeals."
						+ "\n\n§bAppeals are not accepted for this ban.";
	    	} else {
	    		 mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
		    			+ "\n§chas been temporarily suspended to access our server for"
						+ "\n§b§l"  + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
						+ "\n\n§cYou have been suspended due to the following reason:§b\n" + data.get("reason")
						+ "\n\n"
						+ "§7To submit an appeal, you will be required to provide your current"
						+ "\n§7Ban ID (§e@" + ((String) data.get("id")).toUpperCase() + "§7) and"
						+ " a sufficient amount of evidence."
						+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
						+ "\n§7decision will be final and appeals will no longer be accepted."
						+ "\n\n§aAppeals avaliable at §2https://mc.itsrelizc.net/appeals§a or"
						+ "\n§avisit §2https://mc.itsrelizc.net/a/" + ((String) data.get("id")).toUpperCase() + "§a to directly visit"
						+ "\n§ayour appeal page.";
	    	}
	    	
			
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
