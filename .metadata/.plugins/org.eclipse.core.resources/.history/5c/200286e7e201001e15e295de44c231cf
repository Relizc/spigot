package net.itsrelizc.global;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ServerMenu {
	
	private Player player;
	private int currentviewcategory;

	public ServerMenu(Player player, Integer category) {
		this.player = player;
		this.currentviewcategory = category;
	}
	
	public void show() {
		Inventory menu = Bukkit.createInventory(player, 54, "Server Menu");
		
		// icon
		ItemStack item1 = new ItemStack(Material.RAW_FISH, 1);
		ItemMeta meta1 = item1.getItemMeta();
		meta1.setDisplayName("§6§lSALTY FISH NETWORK");
		meta1.setLore(Arrays.asList(new String[] {"§7Fish is fish,", "§7Salty is salty."}));
		item1.setItemMeta(meta1);
		menu.setItem(4, item1);
		
		ItemStack item2 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		item2.setDurability((short) 15);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.setDisplayName(" ");
		item2.setItemMeta(meta2);
		menu.setItem(9, item2);
		
		String[] itemnames = {"§7Category: §6§lGAMES", "§7Category: §6§lSHOP", "§7Category: §6§lLINKS", "§7Category: §6§lPROFILE", "§7Category: §6§lHELP", "§7Category: §a§lCREDITS", "§7Category: §a§lDEVTOOLS"};
		
		ItemStack[] itemstacks = {new ItemStack(Material.RECORD_9), new ItemStack(Material.GOLD_BLOCK), new ItemStack(Material.STRING), new ItemStack(Material.PAPER), new ItemStack(Material.COMPASS), new ItemStack(Material.SIGN), new ItemStack(Material.COMMAND)};
		
		for (int i = 0; i < 7; i++) {
			ItemStack category = Arrays.asList(itemstacks).get(i);
			ItemMeta category2 = category.getItemMeta();
			if (i == this.currentviewcategory) {
				category2.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
				category2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				category2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			}
			category2.setDisplayName(Arrays.asList(itemnames).get(i));
			category.setItemMeta(category2);
			menu.setItem(i + 10, category);
		}
		
		ItemStack item3 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		item3.setDurability((short) 15);
		ItemMeta meta3 = item2.getItemMeta();
		meta3.setDisplayName(" ");
		item3.setItemMeta(meta3);
		menu.setItem(17, item3);
		
		
		
		this.player.openInventory(menu);
	}
	
	public void handleClickEvent(InventoryClickEvent event) {
		event.setCancelled(true);
	}
}
