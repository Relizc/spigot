package net.itsrelizc.commands.tests;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.commands.IBaseCommand;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.templates.TemplateBlockSelector;

public class CommandOpenSelectorMenu implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		TemplateBlockSelector temp = new TemplateBlockSelector();
		
		ClassicMenu menu = new ClassicMenu((Player) sender, 6, "Block Selector", temp);
		menu.show();
		
		temp.initlizeBlocks();
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}

}
