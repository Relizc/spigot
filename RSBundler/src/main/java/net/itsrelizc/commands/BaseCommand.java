package net.itsrelizc.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.itsrelizc.global.ChatUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class BaseCommand {
	
	
	
	public static TextComponent missingArguments(String commandName, ArgumentInfo... arguments) {
		
		TextComponent a = new TextComponent("§cInvalid usage for this command! §b/" + commandName);
		
		for (int i = 0; i < arguments.length; i ++) {
			ArgumentInfo b = arguments[i];
			String r = "[";
			String s = "]";
			
			if (b.required) {
				r = "(";
				s = ")";
			} 
			
			String f = b.customClassName;
			if (b.type != null) {
				f = b.type.getSimpleName();
			}
			
			TextComponent d = new TextComponent(r + f + " " + b.argname + s);
			
			ChatColor n;
			
			if (b.type == int.class || b.type == long.class || b.type == short.class) {
				n = ChatColor.GREEN;
			} else if (b.type == byte.class) {
				n = ChatColor.GOLD;
			} else if (b.type == String.class) {
				n = ChatColor.GRAY;
			} else {
				n = ChatColor.RESET;
			}
			
			ChatUtils.attachHover(d, n + f + " §r" + b.argname + "\n§7" + "§7" + b.description + "\n\n§eMore information? §a§nhttps://itsrelizc.net/cmd/" + commandName.toLowerCase() + "?ainf=" + b.argname);
			ChatUtils.attachOpenURL(d, "https://itsrelizc.net/cmd/" + commandName.toLowerCase() + "?ainf=" + b.argname);
			
			a.addExtra(" ");
			a.addExtra(d);
		}
		
		return a;
		
	}

}
