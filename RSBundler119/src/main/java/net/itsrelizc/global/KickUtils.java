package net.itsrelizc.global;

import java.sql.Timestamp;
import java.util.Map.Entry;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class KickUtils {
	
	public enum OtherResults {
		KICK_BANNED_FINAL,
		KICK_BANNED_PERMANENT,
	}
	
	private static String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }
	
	private static void staticKick(String uuid, String reason, Boolean raw) {
		JSONObject tdata = Global.JSONloadData("kick-log.json");
		JSONArray data = (JSONArray) tdata.get("data");
		JSONObject thisdata = new JSONObject();
		thisdata.put("reason", reason);
		thisdata.put("id", getRandomHexString(10));
		data.add(thisdata);
		tdata.put("data", data);
		
		Global.JSONsavedata(tdata, "kick-log.json");
		
		String kickreason = "Disconnected";
		
		if (!raw) {
			kickreason = "§cYou have been §lKICKED §r§cfrom §6MC.SFN.GG§c.\n "
					+ "\n§6Kick Reason: §b" + thisdata.get("reason")
					+ "\n§6Associated Kick ID: §b#" + thisdata.get("id")
					+ "\n\n"
					+ "\n§7This occurs mostly on a server error, or your connection"
					+ "\n§7had occured an error. Please rejoin the server for more"
					+ "\n§7information if needed."
					+ "\n\n§aMore at §2§nhttps://sfn.gg/appeals";
		} else {
			kickreason = reason;
		}
		String name = null;
		JSONObject players = Global.JSONloadData("players.json");
		for (Object obj : players.entrySet()) {
			Entry<String, JSONObject> entry = (Entry<String, JSONObject>) obj;
			if (entry.getKey().equals(uuid)) {
				name = (String) entry.getValue().get("name");
			}
		}
		Global.crossServerKickPlayer(name, kickreason);
	}
 
	public static boolean kick(String uuid, String reason) {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		Long millsec = t.getTime();
		staticKick(uuid, reason, false);
		return true;
	}
	
	public static boolean rawKick(String uuid, String reason) {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		Long millsec = t.getTime();
		staticKick(uuid, reason, true);
		return true;
	}
}
	