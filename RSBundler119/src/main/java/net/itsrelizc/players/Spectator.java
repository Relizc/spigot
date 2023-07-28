package net.itsrelizc.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.itsrelizc.global.ChatUtils;

public class Spectator {
	
	public static List<Player> spectators = new ArrayList<Player>();
	public static Map<Player, Integer> revive = new HashMap<Player, Integer>();
	
	public static String boundChannelName = "";
	
	public static SpectatorRunnable reviveTask;
	
	public static void startTicking() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSBundler"), new Runnable() {

			@Override
			public void run() {
				for (Player p : revive.keySet()) {
					revive.put(p, revive.get(p) - 1);
					
					if (revive.get(p) <= 5) {
						ChatUtils.systemMessage(p, boundChannelName, "You are reviving in §a" + revive.get(p) + " seconds!");
						
						ChatUtils.sendTitle(p, "§c§l" + revive.get(p), "§eReviving!", 0, 200, 0);
					}
					
					if (revive.get(p) == 0) {
						ChatUtils.systemMessage(p, boundChannelName, "You are revived!");
						
						ChatUtils.sendTitle(p, "", "", 0, 1, 0);
						revive(p);
					}
				}
			}
			
		}, 0L, 20L);
	}
	
	public static void makeSpectator(Player player, Integer r) {
		player.getInventory().clear();
		
		spectators.add(player);
		revive.put(player, r);
		
		player.setAllowFlight(true);
		player.setFlying(true);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player) {
				p.hidePlayer(player);
			}
		}
		
		ChatUtils.sendTitle(player, "§cYou died!", "§eYou are now a spectator!", 10, 100, 10);
			
	}
	
	public static void revive(Player player) {
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player) {
				p.showPlayer(player);
			}
		}
		
		spectators.remove(player);
		revive.remove(player);
		
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setVelocity(new Vector(0, 0, 0));
		player.setFallDistance(0f);
		
		player.setHealth(20.0);
		player.setSaturation(20f);
		
		reviveTask.run(player);
	}
	
}
