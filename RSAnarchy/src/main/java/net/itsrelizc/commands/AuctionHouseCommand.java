package net.itsrelizc.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.AHMenu;
import net.itsrelizc.lifesteal.ChatUtils;

public class AuctionHouseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		Player player = (Player) sender;
		
		AHMenu menu = new AHMenu(player);
		menu.openShop();
		return true;
	}

}
