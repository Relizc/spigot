package net.itsrelizc.commands;

import org.bukkit.Bukkit;

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
	}

	public static void registerCommand(String s, IBaseCommand command) {
		Bukkit.getPluginCommand(s).setExecutor(command);
		Bukkit.getPluginCommand(s).setTabCompleter(command);
	}
	
}
