package net.itsrelizc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.itsrelizc.lifesteal.waiting.ChatUtils;

public class ReviveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if (args.length == 0) {
			return false;
		}
		
		if (player.getMaxHealth() <= 2) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§cYou can't revive someone with only 1 hearts!");
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§aConsume hearts to revive people if you have hearts as a item.");
			return true;
		}
		
		Player revive = Bukkit.getPlayer(args[0]);
		if (revive == null) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§cThat player is offline!");
			return true;
		}
		if (revive.getName() == player.getName()) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§cYou can't revive yourself!");
			return true;
		}
		if (player.getGameMode() == GameMode.SPECTATOR) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§cYou revive players as a spectator!");
			return true;
		}
		if (revive.getGameMode() != GameMode.SPECTATOR) {
			ChatUtils.systemMessage(player, "§c§lHEARTS", "§cThat player isn't a spectator!");
			return true;
		}
		
		Inventory inv = Bukkit.createInventory(player, 54, "Reviving " + revive.getDisplayName());
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(revive.getName());
		meta.setDisplayName("§eAre you sure you want to revive §b" + revive.getDisplayName() + "§e?");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Reviving a player will cost you");
		lore.add("§c1 ❤ Heart§7, which is §c2 ❤ Health§7.");
		lore.add("§7The heart will be removed from");
		lore.add("§7health bar, and may make your");
		lore.add("§7gaming expierence harder.");
		lore.add(" ");
		lore.add("§7Are you really sure?");
		meta.setLore(lore);
		skull.setItemMeta(meta);
		for (int slot = 0; slot < 54; slot ++) {
			inv.setItem(slot, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
		}
		inv.setItem(13, skull);
		
		ItemStack green = new ItemStack(Material.GREEN_TERRACOTTA);
		ItemMeta greenm = green.getItemMeta();
		greenm.setDisplayName("§aYes");
		green.setItemMeta(greenm);
		
		ItemStack red = new ItemStack(Material.RED_TERRACOTTA);
		ItemMeta redm = green.getItemMeta();
		redm.setDisplayName("§cNo");
		red.setItemMeta(redm);
		
		inv.setItem(39, green);
		inv.setItem(41, red);
		
		player.openInventory(inv);
		
		return true;
	}

}
