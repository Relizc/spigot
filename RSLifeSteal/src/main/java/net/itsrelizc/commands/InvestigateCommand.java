package net.itsrelizc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.Recipe;
import net.itsrelizc.lifesteal.Shop;
import net.itsrelizc.utils.Investigator;
import net.itsrelizc.utils.PlayerManager;

public class InvestigateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if (PlayerManager.ranks.get(player).permission <= 64) {
			ChatUtils.systemMessage(player, "§c§lCOMMAND", "§cYou do not have permission to do this!");
			return true;
		}
		
		Investigator.investiage(player);
		
		return true;
	}

}
