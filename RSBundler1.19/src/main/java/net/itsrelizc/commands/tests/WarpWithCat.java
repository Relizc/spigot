package net.itsrelizc.commands.tests;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.commands.IBaseCommand;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;

public class WarpWithCat implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) return false;
		
		Player player = (Player) sender;
		
		String a = args[0];
		
		
		
		ServerCategory cat = null;
		
		for (ServerCategory c : ServerCategory.values()) {
			if (c.toString().equalsIgnoreCase(a)) {
				cat = c;
			}
		}
		
		if (cat == null) {
			ChatUtils.systemMessage(player, "§a§lWARP", "§cCannot find any server with type " + a);
			return true;
		}

		ChatUtils.systemMessage(player, "§a§lWARP", "§7Finding a specific server with category " + a);
		WarpUtils.send(player, cat);
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		List<String> stuff = ChatUtils.fromNewList();
		
		for (ServerCategory c : ServerCategory.values()) {
			stuff.add(c.toString().toLowerCase());
		}
		
		return stuff;
	}

}
