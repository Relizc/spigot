package net.itsrelizc.smp;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.players.Grouping;
import net.itsrelizc.players.Profile;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Grouping.showPrefix = false;
	}
	
	public void saveWorlds(String world) {
		getLogger().info("Saving and Copying " + world);
		Bukkit.getServer().unloadWorld(Bukkit.getWorld(world), true);
		File sourceDirectory = new File(System.getProperty("user.dir") + "\\" + world);
		File destinationDirectory = new File(new File(System.getProperty("user.dir")).getParentFile().getParentFile().toString() + "\\templates\\_worlds\\_" + world + "_smpsaved");
		try {
			FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
		} catch (IOException e) {
			
		}
	}
	
	public void end() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer("§cServer is closing!");
		}
		for (World world : Bukkit.getWorlds()) {
			saveWorlds(world.getName());
		}
		getLogger().info("All worlds saved! to templates for later loading");
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		event.setJoinMessage(Profile.coloredName(event.getPlayer()) + " §bjoined the SMP! §8(" + Bukkit.getOnlinePlayers().size() + " player" + ChatUtils.plural(Bukkit.getOnlinePlayers().size()) +")");
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void join(PlayerQuitEvent event) {
		event.setQuitMessage(Profile.coloredName(event.getPlayer()) + " §bleft the SMP! §8(" + (Bukkit.getOnlinePlayers().size() - 1) + " player" + ChatUtils.plural(Bukkit.getOnlinePlayers().size() - 1) +")");
	}
	
	@Override
	public void onDisable() {
		end();
	}
	
}
