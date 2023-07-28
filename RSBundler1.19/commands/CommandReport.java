package net.itsrelizc.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.nerdbot.report.ReportTypes;

public class CommandReport implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		if (args.length >= 1) {
			
			List<String> types = ChatUtils.fromNewList();
			
			for (ReportTypes r : ReportTypes.values()) {
				types.add(r.name);
			}
			
			return types;
			
		}
		
		return null;
		
	}

}
