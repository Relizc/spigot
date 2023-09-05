package net.itsrelizc.gamebase;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import net.itsrelizc.global.ChatUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class GameUtils implements Listener {
	
	public static String game = null;
	
	@EventHandler
	public void a(PlayerJoinEvent event) {
		if (game != null) {
			TextComponent s = new TextComponent("§aBy playing on our officially hosted game universe, you agree to our ");
			TextComponent b = new TextComponent("§2§nFair Play Policy");
			ChatUtils.attachOpenURL(b, "https://itsrelizc.net/r/948");
			TextComponent c = new TextComponent(" §aand ");
			TextComponent d = new TextComponent("§2§nTerms of Services");
			ChatUtils.attachOpenURL(d, "https://itsrelizc.net/r/2");
			
			s.addExtra(b);
			s.addExtra(c);
			s.addExtra(d);
			
			ChatUtils.systemMessage(event.getPlayer(), game, s);
		}
	}
	
	public static void init(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new GameUtils(), plugin);
	}
	
}
