package net.itsrelizc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.server.ShutdownManager;
import net.itsrelizc.server.ShutdownManager.ShutdownType;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;

public class CommandShutdown implements IBaseCommand {
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (ShutdownManager.timeleft != -1) {
			ChatUtils.systemMessage(sender, "§b§lSERVER", "§cThe universe is already about to collapse!");
			return true;
		}
		
		ShutdownType t;
		try {
			t = ShutdownType.valueOf(args[0].toUpperCase());
		} catch (Exception e) {
			if (args.length == 0) {
				ShutdownManager.shutdown(ShutdownType.SCHEDULED);
				return true;
			}
			ChatUtils.systemMessage(sender, "§b§lSERVER", "§cNo shutdown type for specified type " + args[0]);
			return true;
		}
		
		ShutdownManager.shutdown(t);
		
		return true;
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length == 1) {
			List<String> a = new ArrayList<String>();
			for (ShutdownType t : ShutdownType.values()) {
				a.add(t.toString().toLowerCase());
			}
			return a;
		}
		return ChatUtils.fromNewList();
	}

}
