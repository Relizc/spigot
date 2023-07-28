package net.itsrelizc.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.ChatUtils;

public class HowManyHearts implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		Player player = (Player) sender;
		if (player.getGameMode() == GameMode.SPECTATOR) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§cYou are currently a spectator.");
			return true;
		}
		Integer heart = ((int) player.getMaxHealth()) / 2;
		ChatUtils.systemMessage(player, "§c§lHEARTS", "§aYou currently have §c" + heart + " ❤§a, which gives you §c" + heart * 2 + " ❤ Max Health§a.");
		return true;
	}

}
