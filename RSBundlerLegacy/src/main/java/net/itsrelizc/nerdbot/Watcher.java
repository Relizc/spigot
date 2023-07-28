package net.itsrelizc.nerdbot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.itsrelizc.global.JSON;
import net.itsrelizc.nerdbot.move.UpdateClientVelocity;

public class Watcher {
	
	public static void registerAllWatchers(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new UpdateClientVelocity(), plugin);
		
		
	}
	
}
