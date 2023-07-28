package net.itsrelizc.networking.executors;

import java.io.FileNotFoundException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.itsrelizc.global.BanUtils;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.networking.CommunicationInput;
import net.itsrelizc.networking.Components;
import net.itsrelizc.players.DataManager;
import net.itsrelizc.players.Grouping;
import net.itsrelizc.players.Profile;
import net.itsrelizc.players.Rank;
import net.itsrelizc.warp.WarpUtils;

public class SetRealUUID implements Runnable {
	
	private CommunicationInput input;

	public SetRealUUID(CommunicationInput input) {
		this.input = input;
	}

	@Override
	public void run() {
		String name = input.readString();
		UUID realuuid = UUID.fromString(input.readString());
		Player player = Bukkit.getPlayer(name);
		
		JSONObject j = DataManager.loadPureJsonFromDb("players.json");
		
		if (!j.keySet().contains(realuuid.toString())) {
			
			
			
			JSONObject d_general = new JSONObject();
			d_general.put("general-lv", 0);
			d_general.put("general-rank", 1);
			
			JSONArray names = new JSONArray();
			names.add(player.getDisplayName());
			d_general.put("general-namehist", names);
			
			j.put(realuuid.toString(), d_general);
			
			try {
				DataManager.savePureJsonToDb("players.json", j);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Profile.awaitCreation.remove(player);
			player.removePotionEffect(PotionEffectType.BLINDNESS);
			
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
		
		Profile p = Profile.findByOwner(player);
		p.realUUID = realuuid;
		//BanUtils.checkLogin(p);
		
		p.reloadProfile();
		
	}

}
