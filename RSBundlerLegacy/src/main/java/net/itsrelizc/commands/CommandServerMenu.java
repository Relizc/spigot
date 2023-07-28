package net.itsrelizc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.templates.TemplateServerMenu;

public class CommandServerMenu implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		ClassicMenu menu = new ClassicMenu(player, 6, "Server Menu", new TemplateServerMenu());
		menu.show();
		
		return true;
	}
	
	
	
}
