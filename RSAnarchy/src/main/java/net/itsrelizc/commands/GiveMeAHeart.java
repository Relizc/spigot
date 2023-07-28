package net.itsrelizc.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.Items;

public class GiveMeAHeart implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		((Player) sender).getInventory().addItem(Items.getHeartItem());
		ChatUtils.systemMessage((Player) sender, "Yes! For sure...");
		
		return false;
	}

}
