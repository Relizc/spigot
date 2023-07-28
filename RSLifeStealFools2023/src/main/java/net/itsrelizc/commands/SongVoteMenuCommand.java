package net.itsrelizc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.MusicMenu;

public class SongVoteMenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		MusicMenu menu = new MusicMenu((Player) sender);
		menu.openShop();
		return true;
	}

}
