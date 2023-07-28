package net.itsrelizc.commands.tests;

import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.commands.IBaseCommand;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.templates.TemplateServerWarp;

public class OpenDemoMenu implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ClassicMenu menu = new ClassicMenu((Player) sender, 6, "Demo", new TemplateServerWarp());
		menu.show();
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
