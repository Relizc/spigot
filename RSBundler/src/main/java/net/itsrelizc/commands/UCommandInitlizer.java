package net.itsrelizc.commands;

import org.bukkit.Bukkit;

import net.itsrelizc.commands.tests.CommandOpenSelectorMenu;
import net.itsrelizc.commands.tests.OpenDemoMenu;
import net.itsrelizc.commands.tests.WarpWithCat;
import net.itsrelizc.global.ChatUtils;

public class UCommandInitlizer {
	
	public static void initlize() {
		registerCommand("menu1", new OpenDemoMenu());
		
		registerCommand("servermenu", new CommandServerMenu());
		
		registerCommand("catwarp", new WarpWithCat());
		
		registerCommand("lobby", new CommandLobby());
		
		registerCommand("stop", new CommandShutdown());
		
		registerCommand("report", new CommandReport());
		
		registerCommand("menu2", new CommandOpenSelectorMenu());
	}

	public static void registerCommand(String s, IBaseCommand command) {
		try {
			Bukkit.getPluginCommand(s).setExecutor(command);
			Bukkit.getPluginCommand(s).setTabCompleter(command);
		} catch (Exception e) {
			Bukkit.getLogger().severe("Failed to load command " + s + ", maybe the command is not initlized in plugin.yml?");
		}
	}
	
}
