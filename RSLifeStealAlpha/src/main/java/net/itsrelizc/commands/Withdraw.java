package net.itsrelizc.commands;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.itsrelizc.lifesteal.ChatUtils;

public class Withdraw implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if (player.getGameMode() == GameMode.SPECTATOR) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§cYou are not allowed to withdraw hearts as a spectator!");
			return true;
		}
		
		Integer hearts;
		
		if (args.length > 0) {
			hearts = Integer.valueOf(args[0]);
		} else {
			hearts = 1;
		}
		
		if (hearts * 2 >= player.getMaxHealth()) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§eBro you can't withdraw that much hearts. Look at what you've got.");
			return true;
		}
		
		ItemStack item = new ItemStack(Material.REDSTONE_BLOCK, hearts);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§cHeart");
		item.setItemMeta(im);
		((Player) sender).getInventory().addItem(item);
		
		player.setMaxHealth(player.getMaxHealth() - 2 * hearts);
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1f, 2);
		
		ChatUtils.systemMessage(player, "§c§lHEARTS", "§aYou withdrawed §c" + hearts + " ❤§a!");
		Integer heart = ((int) player.getMaxHealth()) / 2;
		ChatUtils.systemMessage(player, "§c§lHEARTS", "§aNow you have §c" + heart + " ❤§a, which gives you §c" + heart * 2 + " ❤ Max Health§a.");
		return true;
	}

}
