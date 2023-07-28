package net.itsrelizc.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.itsrelizc.lifesteal.ChatUtils;

public class GiveMeAHeart implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ItemStack item = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§cHeart");
		item.setItemMeta(im);
		((Player) sender).getInventory().addItem(item);
		ChatUtils.systemMessage((Player) sender, "Yes! For sure...");
		
		return false;
	}

}
