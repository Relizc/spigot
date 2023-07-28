package net.itsrelizc.smp.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.commands.IBaseCommand;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandTellCoords implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;
		
		TextComponent b = new TextComponent("§aMy coords are ");
		TextComponent a = new TextComponent("§b[" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ() + "]");
		String wn;
		if (player.getWorld().getName().equalsIgnoreCase("world")) {
			wn = "§aOverworld";
		} else if (player.getWorld().getName().equalsIgnoreCase("world_nether")) {
			wn = "§6Nether";
		} else if (player.getWorld().getName().equalsIgnoreCase("world_the_end")) {
			wn = "§5The End";
		} else {
			wn = "§bOther §7(" + player.getWorld().getName() + ")";
		}
		ChatUtils.attachHover(a, "§aBlock Coords: §b" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ() + "\n" +
								"§aPosition Coords: §b" + String.format("%.2f", player.getLocation().getX()) + ", " + String.format("%.2f", player.getLocation().getY()) + ", " + String.format("%.2f", player.getLocation().getZ()) + "\n" +
								"§aYaw: §b" + String.format("%.2f", player.getLocation().getYaw()) + "\n" +
								"§aPitch: §b" + String.format("%.2f", player.getLocation().getPitch()) + "\n" +
								"§aWorld: §b" + wn + "\n" +
								"\n§eClick to request teleport.");
		ChatUtils.attachCommand(a, "tpa " + player.getDisplayName());
		b.addExtra(a);
		
		ChatUtils.sayAsPlayer(player, b);
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		return null;
	}

}
