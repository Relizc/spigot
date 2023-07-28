package net.itsrelizc.commands.tests;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.templates.TemplateServerWarp;

public class OpenDemoMenu implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ClassicMenu menu = new ClassicMenu((Player) sender, 6, "Demo", new TemplateServerWarp());
		menu.show();
		return true;
	}

}
