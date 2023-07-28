package net.itsrelizc.lifesteal;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	
	public static ItemStack getHeartItem() {
		ItemStack item = new ItemStack(Material.COMMAND_BLOCK);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§cHeart");
		
		String[] l = {"§7Hello, I'm a heart!", "§7You can find me when", "§7you are dating!", " ", "§8§oEw go get a room."};
		List<String> lore = Arrays.asList(l);
		im.setLore(lore);
		
		im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getHeartFragmentItem() {
		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§cHeart Fragment");
		
		String[] l = {"§7Dude! This is a heart", "§7fragment! Not a diamond!", "§7But you can still use", "§7it as a diamond. Use", "§7this to craft hearts.", " ", "§8§oWhen you break up with", "§8§oyour girlfriend, your", "§8§oheart turns into a", "§8§omillion pieces."};
		List<String> lore = Arrays.asList(l);
		im.setLore(lore);
		
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getTeleportTicket() {
		ItemStack item = new ItemStack(Material.NAME_TAG);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§aTeleport Ticket");
		
		im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		String[] l = {"§7What do you think this", "§7is for? Obviously for", "§7teleporting people!", " ", "§7Consume on accepted", "§7TPA teleports!"};
		List<String> lore = Arrays.asList(l);
		im.setLore(lore);
		
		item.setItemMeta(im);
		
		return item;
	}
	
	public static Integer countTicket(Player player) {
		int amount = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) continue;
			if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§aTeleport Ticket")) {
				amount += item.getAmount();
			}
		}
		return amount;
	}
	
	public static void subTicket(Player player, Integer amount) {
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) continue;
			if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§aTeleport Ticket")) {
				if (amount >= item.getAmount()) {
					amount -= item.getAmount();
					item.setAmount(0);
				} else {
					item.setAmount(item.getAmount() - amount);
					amount = 0;
				}
				if (amount == 0) {
					break;
				}
			}
		}
	}
	
}
