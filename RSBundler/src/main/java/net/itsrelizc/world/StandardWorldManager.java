package net.itsrelizc.world;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

import net.itsrelizc.global.ChatUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class StandardWorldManager implements Listener {
	
	public static String[] Q = {"CHANGE_WEATHER"};
	
	public static boolean CHANGE_WEATHER = false;
	
	public static boolean BLOCK_PHYSICS_UPDATE = false;
	
	public static void init(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new StandardWorldManager(), plugin);
	}
	
	@EventHandler
	public void a(WeatherChangeEvent event) {
		event.setCancelled(!CHANGE_WEATHER);
	}
	
	@EventHandler
	public void a(PlayerCommandPreprocessEvent event) {
		if ((!CHANGE_WEATHER) && event.getPlayer().isOp() && event.getMessage().startsWith("/weather")) {
			event.getPlayer().sendMessage("bt"+ ChatColor.GOLD+ "§cThe weather command might not work! §7Renable weather cycle by using the /worldoption command!");
			
			TextComponent t = new TextComponent("§7[You can also click here to enable weathering]");
			ChatUtils.attachCommand(t, "worldoption CHANGE_WEATHER true", null);
			
			ChatUtils.systemMessage(event.getPlayer(), "§6§l", t);
		}
	}
	
}
