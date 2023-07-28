package net.itsrelizc.hunters;

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

import net.itsrelizc.commands.UCommandInitlizer;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.players.Grouping;
import net.itsrelizc.players.Profile;
import net.itsrelizc.smp.commands.CommandTellCoords;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		UCommandInitlizer.registerCommand("tellcoords", new CommandTellCoords());
	}
	
}
