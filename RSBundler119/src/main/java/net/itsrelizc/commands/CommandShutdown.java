package net.itsrelizc.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.server.ShutdownManager;
import net.itsrelizc.server.ShutdownManager.ShutdownType;

public class CommandShutdown implements IBaseCommand {
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (ShutdownManager.timeleft != -1) {
			ChatUtils.systemMessage(sender, "§b§lSERVER", "§cThe universe is already about to collapse!");
			return true;
		}
		
		ShutdownManager.shutdown(ShutdownType.OTHER);
		
		return true;
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return ChatUtils.fromNewList();
	}

}
