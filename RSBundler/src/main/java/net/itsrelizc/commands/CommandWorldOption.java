package net.itsrelizc.commands;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.world.StandardWorldManager;

public class CommandWorldOption implements IBaseCommand {
	
	public static ArgumentInfo[] arginf = {
			new ArgumentInfo() {
				public Class<?> type = String.class;
				public String argname = "optionName";
				public boolean required = true;
				public String description = "The option that you want to set for all the server worlds.";
			},
			new ArgumentInfo() {
				public String customClassName = "Any";
				public String argname = "value";
				public boolean required = true;
				public String description = "The value that should be set to the option you specified.";
			}
	};

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length < 2) {
			ChatUtils.systemMessage((Player) sender, "§e§lWORLD", BaseCommand.missingArguments(label, arginf));
			return true;
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length < 2) {
			return ChatUtils.fromArgs(StandardWorldManager.Q);
		} else if (args.length == 2) {
			
			Field field;
			
			try {
				field = StandardWorldManager.class.getField(args[0].toUpperCase());
			} catch (NoSuchFieldException | SecurityException e) {
				return ChatUtils.fromArgs("[The option you entered does not exist!]");
			}
			
			if (field.getType() == int.class) {
				return ChatUtils.fromArgs("(int32:any)");
			} else if (field.getType() == long.class) {
				return ChatUtils.fromArgs("(int64:any)");
			} else if (field.getType() == short.class) {
				return ChatUtils.fromArgs("(int16:any)");
			} else if (field.getType() == byte.class) {
				return ChatUtils.fromArgs("(int8:any|byte:any)");
			} else if (field.getType() == boolean.class) {
				return ChatUtils.fromArgs("true", "false");
			} else if (field.getType() == String.class) {
				return ChatUtils.fromArgs("(String:any)");
			} else {
				return ChatUtils.fromArgs("(" + field.getType().getName() + ":any)");
			}
		} else {
			return ChatUtils.fromNewList();
		}
	}

	

}
