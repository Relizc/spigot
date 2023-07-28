package net.itsrelizc.buildtools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BuildTools {
	
	public static void registerAllFunctions(Plugin plugin) {
		Bukkit.getPluginCommand("/cholo").setExecutor(new SummonColorHologram());
	}
	
}
