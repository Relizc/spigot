package net.itsrelizc.nerdbot;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import net.itsrelizc.nerdbot.move.UpdateClientVelocity;
import net.itsrelizc.nerdbot.move.flight.Flight;

public class Watcher implements Listener {
	
	public static Map<Player, Integer> suspoints = new HashMap<Player, Integer>();
	
	public static void initlize(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new Flight(), plugin);
	}
	
	public static void registerAllWatchers(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new UpdateClientVelocity(), plugin);
	}
	
	public static void moreSus(Player player, Integer pts) {
		suspoints.put(player, suspoints.get(player) + pts);
	}
	
	public void join(PlayerJoinEvent event) {
		suspoints.put(event.getPlayer(), 0);
	}
	
	public void leave(PlayerQuitEvent event) {
		suspoints.remove(event.getPlayer());
	}
	
}
