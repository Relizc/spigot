package net.itsrelizc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.Items;

public class TPAccept implements CommandExecutor {

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
		
		target.teleport(player);
		ChatUtils.systemMessage(player, "§d§lTELEPORT", "§aTeleported §b" + target.getDisplayName() + " §ato you!");
		ChatUtils.systemMessage(target, "§d§lTELEPORT", "§b" + player.getDisplayName() + " §aaccepted your teleport request!");
		Items.subTicket(target, 1);
		ChatUtils.systemMessage(target, "§d§lTELEPORT", "§bWe also consumed one of your teleport tickets! Thank you!");
		
		TPACommand.tpas.remove(target);
		
		return true;
	}

}
