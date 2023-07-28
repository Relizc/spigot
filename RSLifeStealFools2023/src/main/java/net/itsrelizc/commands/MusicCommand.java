package net.itsrelizc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.Music;

public class MusicCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if (Music.rsp.getPlayerUUIDs().contains(player.getUniqueId())) {
			Music.removePlayer(player);
			ChatUtils.systemMessage(player, "§2§lMUSIC", "§cStopped playing music!");
		} else {
			Music.addPlayer(player);
			ChatUtils.systemMessage(player, "§2§lMUSIC", "§aStarted playing music! §7" + Music.rsp.getSong().getTitle() + " - " + Music.rsp.getSong().getOriginalAuthor());
		}
		
		return true;
	}

}
