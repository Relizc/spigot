package net.itsrelizc.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.templates.TemplateServerMenu;

public class CommandServerMenu implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		ClassicMenu menu = new ClassicMenu(player, 6, "Server Menu", new TemplateServerMenu());
		menu.show();
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return ChatUtils.fromNewList();
	}
	
	
	
}
