package net.itsrelizc.mcserver.lxscripttreeexecutor;

import net.itsrelizc.mcserver.lxscripttreeexecutor.Commands.CommandEnterIDE;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LXScriptTreeExecutor extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginCommand("enteride").setExecutor(new CommandEnterIDE());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
