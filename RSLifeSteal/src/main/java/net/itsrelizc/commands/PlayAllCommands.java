package net.itsrelizc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.JSON;
import net.itsrelizc.utils.BanUtils;
import net.itsrelizc.utils.PlayerManager;
import net.itsrelizc.utils.Rank;
import net.minecraft.world.level.biome.CaveSound;

public class PlayAllCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	for (Player p : Bukkit.getOnlinePlayers()) {
    		for (int i = 0; i < org.bukkit.Sound.values().length; i ++) {
    			p.playSound(p.getLocation(), org.bukkit.Sound.values()[i], 100f, 1f);
    		}
    	}
    	return true;
    }
}