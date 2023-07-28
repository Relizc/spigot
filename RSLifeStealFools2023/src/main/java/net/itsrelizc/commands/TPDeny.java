package net.itsrelizc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.ChatUtils;

public class TPDeny implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1) {
			return false;
		}
		
		Player player = (Player) sender;
		Player target = Bukkit.getPlayer(args[0]);
		
		if (target == null) {
			ChatUtils.systemMessage(player, "§d§lTELEPORT", "§b" + args[0] + " §cisn't online!");
			return true;
		}
		if (!TPACommand.tpas.containsKey(target)) {
			ChatUtils.systemMessage(player, "§d§lTELEPORT", "§b" + target.getDisplayName() + " §cdidn't sent you any teleport request!");
			return true;
		}
		
		ChatUtils.systemMessage(player, "§d§lTELEPORT", "§aRefused §b" + target.getDisplayName() + "§a's teleport request!");
		ChatUtils.systemMessage(target, "§d§lTELEPORT", "§b" + player.getDisplayName() + " §cdenied your teleport request!");
		
		TPACommand.tpas.remove(target);
		
		return true;
	}

}
