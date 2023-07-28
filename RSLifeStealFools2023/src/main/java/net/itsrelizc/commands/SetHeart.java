package net.itsrelizc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.ChatUtils;

public class SetHeart implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		target.setMaxHealth(Double.valueOf(args[1]) * 2);
		ChatUtils.systemMessage(sender, "§d§l", "Setted heart to " + args[1] + " for " + target.getDisplayName());
		return true;
	}

}
