package net.itsrelizc.players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import net.itsrelizc.global.ChatUtils;

public class Commanding implements Listener {
	
	public static List<String> blacklisted = new ArrayList<String>();
	
	public static void initlizeBlacklistCommands(Plugin plugin) {
		blacklisted.add("me");
		blacklisted.add("reload");
		blacklisted.add("restart");
		
		Bukkit.getPluginManager().registerEvents(new Commanding(), plugin);
	}

	@EventHandler
    public void cancelCommand(PlayerCommandPreprocessEvent event) {
        String cmdname = event.getMessage().split(" ")[0].toLowerCase();
        if (cmdname.matches("/(minecraft|bukkit|spigot):[A-Za-z0-9]+")) {
        	ChatUtils.systemMessage(event.getPlayer(), "§6§lCOMMAND", "§cYou are not allowed to run this command!");
        	event.setCancelled(true);
        	return;
        }
        for (String s : blacklisted) {
        	if (cmdname.equalsIgnoreCase("/" + s)) {
        		ChatUtils.systemMessage(event.getPlayer(), "§6§lCOMMAND", "§cYou are not allowed to run this command!");
        		event.setCancelled(true);
        		return;
        	}
        }
    }
	
}
