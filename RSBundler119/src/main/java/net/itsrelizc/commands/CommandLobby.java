package net.itsrelizc.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;

public class CommandLobby implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;
		
		WarpUtils.send(player, ServerCategory.LOBBY_MAIN);
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		List<String> string = ChatUtils.fromNewList();
		
		for (ServerCategory cat : ServerCategory.values()) {
			if (cat.display != null) {
				string.add(cat.display);
			}
		}
		
		return string;
	}

}
