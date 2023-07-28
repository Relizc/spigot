package net.saltyfishstudios.bungee;

import java.io.FileNotFoundException;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import net.md_5.bungee.api.event.LoginEvent;

public class BanUtils {
	
	public static boolean checkLogin(LoginEvent event) {
		JSONObject obj = DataManager.loadPureJsonFromDb("banned-players.json");
		if (obj.containsKey(event.getConnection().getUniqueId().toString())) {
			
			String a = event.getConnection().getUniqueId().toString();
			
			JSONObject data = (JSONObject) obj.get(a);
			
			long secondsOffset = (((long) (data.get("expires")) * 1000) - System.currentTimeMillis()) / 1000;
			boolean dotime = (boolean) data.get("permanent");
			
			long days = 0L, hours = 0L, minutes = 0L, seconds = 0L;
			
			if (!dotime) {
				if (secondsOffset <= 0) {
					data.remove(a);
					try {
						DataManager.savePureJsonToDb("banned-players.json", data);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}
				
				days = secondsOffset / 86400;
		    	hours = (secondsOffset - (days * 86400)) / 3600;
		    	minutes = (secondsOffset - (days * 86400) - (hours * 3600)) / 60;
		    	seconds = secondsOffset % 60;
			}
			
			JSONArray banreasons = (JSONArray) data.get("reason");
			
			String word;
			if (banreasons.size() > 1) word = "reasons";
			else word = "reason";
			
			String dd = "";
			int n = 0;
			for (Object i : banreasons) {
				
				n ++;
				
				if (n >= 6) {
					dd += "\n§eand " + String.valueOf(banreasons.size() - n) + " other reasons. §7(Details in appeal page)";
					break;
				}
				
				String lol = (String) i;
				dd += "\n§e• §b" + lol;
			}
			
			String mutereason;
	    	
	    	if (dotime) {
	    		if ((boolean) data.get("final")) {
		    		mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been permanently suspended to access our server."
			    			+ "\n\n§7This punishment is recorded as §e@" + ((String) data.get("id"))
							+ "\n\n§cYou have been suspended due to the following " + word + ":" + dd
							+ "\n\n"
							+ "§7This ban decision is §cFINAL§7, which no appeals are accepted and no"
							+ "\n§7exceptions will be tolerated. This might be a result of obvious rule"
							+ "\n§7breaking or continuous spamming appeals."
							
							+ "\n\n§bAppeals are not accepted for this ban.";
		    	} else {
		    		 mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been permanently suspended to access our server."
							+ "\n\n§7This punishment is recorded as §e@" + ((String) data.get("id"))
							+ "\n\n§cYou have been suspended due to the following " + word + ":" + dd
							+ "\n\n"
							+ "§7To submit an appeal, you will be required to provide your current"
							+ "\n§7Ban ID (§e@" + ((String) data.get("id")).toUpperCase() + "§7) and"
							+ " a sufficient amount of evidence."
							+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
							+ "\n§7decision will be final and appeals will no longer be accepted."
							+ "\n\n§aAppeals avaliable at §2https://mc.itsrelizc.net/appeals.";
		    	}
	    	} else {
	    		if ((boolean) data.get("final")) {
		    		mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been temporarily suspended to access our server for"
			    			+ "\n§b§l"  + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
			    			+ "\n\n§7This punishment is recorded as §e@" + ((String) data.get("id"))
							+ "\n\n§cYou have been suspended due to the following " + word + ":" + dd
							+ "\n\n"
							+ "§7This ban decision is §cFINAL§7, which no appeals are accepted and no"
							+ "\n§7exceptions will be tolerated. This might be a result of obvious rule"
							+ "\n§7breaking or continuous spamming appeals."
							
							+ "\n\n§bAppeals are not accepted for this ban.";
		    	} else {
		    		 mutereason = "§cDue to your violation of our \"Terms Of Services\"§r§c, your account"
			    			+ "\n§chas been temporarily suspended to access our server for"
							+ "\n§b§l"  + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
							+ "\n\n§7This punishment is recorded as §e@" + ((String) data.get("id"))
							+ "\n\n§cYou have been suspended due to the following " + word + ":" + dd
							+ "\n\n"
							+ "§7To submit an appeal, you will be required to provide your current"
							+ "\n§7Ban ID (§e@" + ((String) data.get("id")).toUpperCase() + "§7) and"
							+ " a sufficient amount of evidence."
							+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
							+ "\n§7decision will be final and appeals will no longer be accepted."
							+ "\n\n§aAppeals avaliable at §2https://mc.itsrelizc.net/appeals.";
		    	}
	    	}
	    	
			
			event.getConnection().disconnect(mutereason);
			
			return true;
		}
		
		return false;
	}

//	public static void createBan(Player player, String reason, Long secondsOffset) {
//		JSONObject hash = new JSONObject();
//		
//		JSONObject obj = JSON.loadDataFromDataBase("banned-players.json");
//		
//		hash.put("reason", reason);
//		hash.put("expires", (System.currentTimeMillis() / 1000) + secondsOffset);
//    	hash.put("id", MuteUtils.generateRandomID());
//    	
//    	obj.put(player.getUniqueId().toString(), hash);
//    	
//    	JSON.saveDataFromDataBase("banned-players.json", obj);
//    	
//    	long days = secondsOffset / 86400;
//    	long hours = (secondsOffset - (days * 86400)) / 3600;
//    	long minutes = (secondsOffset - (days * 86400) - (hours * 86400)) / 60;
//    	long seconds = secondsOffset % 60;
//    	
//    	String mutereason = "§cYou have been banned §r§cfrom joining §esmp.itsrelizc.net§r§c.\n"
//				+ "§eRemaining Time: §b§l" + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
//				+ "\n\n§eBanned Reason: §b" + hash.get("reason")
//				+ "\n§eAssociated Ban ID: §b#" + ((String) hash.get("id")).toUpperCase()
//				+ "\n\n"
//				+ "§7To appeal for your ban, please visit the link below. You are required"
//				+ "\n§7to provide your Ban ID and sufficient evidence."
//				+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
//				+ "\n§7decision will be final and appeals will no longer be accepted."
//				+ "\n\n§aAppeals avaliable at §2§nhttps://sfn.gg/appeals §aor via §9Microsoft Teams";
//    	
//    	player.kickPlayer(mutereason);
//    	
//    	ChatUtils.broadcastSystemMessage("§c§lBAN", "§b" + player.getDisplayName() + " §ehad been banned! Shame on him!");
//    	ChatUtils.broadcastSystemMessage("§c§lBAN", "§eUse §b/report §eto report more rule breakers!");
//	}
	
//	public static void createBan(Player player, String reason) {
//		createBan(player, reason, (long) (1 * 24 * 60 * 60));
//	}

}
