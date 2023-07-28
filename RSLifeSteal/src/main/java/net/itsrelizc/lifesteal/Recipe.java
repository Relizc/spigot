package net.itsrelizc.lifesteal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipe {
	
	private Player player;
	private Inventory inventory;
	private static Map<Player, Recipe> shops = new HashMap<Player, Recipe>();

	public Recipe(Player player) {
		this.player = player;
		shops.put(player, this);
	}
	
	public void openRecipe(Integer recipeIndex) {
		this.inventory = null;
		if (recipeIndex == 0) {
			this.inventory = Bukkit.createInventory(player, 45, "Recipe for Heart Fragment");
			this.createBorder();
			
			this.inventory.setItem(10, new ItemStack(Material.IRON_BLOCK, 1));
			this.inventory.setItem(11, new ItemStack(Material.REDSTONE_BLOCK, 1));
			this.inventory.setItem(12, new ItemStack(Material.IRON_BLOCK, 1));
			
			this.inventory.setItem(19, new ItemStack(Material.REDSTONE_BLOCK, 1));
			this.inventory.setItem(20, new ItemStack(Material.DIAMOND_BLOCK, 1));
			this.inventory.setItem(21, new ItemStack(Material.REDSTONE_BLOCK, 1));
			
			this.inventory.setItem(28, new ItemStack(Material.IRON_BLOCK, 1));
			this.inventory.setItem(29, new ItemStack(Material.REDSTONE_BLOCK, 1));
			this.inventory.setItem(30, new ItemStack(Material.IRON_BLOCK, 1));
			
			this.inventory.setItem(24, Items.getHeartFragmentItem());
			
			this.inventory.setItem(44, getNextPage());
		} else if (recipeIndex == 1) {
			this.inventory = Bukkit.createInventory(player, 45, "Recipe for Heart");
			this.createBorder();
			
			this.inventory.setItem(10, Items.getHeartFragmentItem());
			this.inventory.setItem(11, new ItemStack(Material.GRASS_BLOCK, 1));
			this.inventory.setItem(12, Items.getHeartFragmentItem());
			
			this.inventory.setItem(19, new ItemStack(Material.GRASS_BLOCK, 1));
			this.inventory.setItem(20, new ItemStack(Material.NETHERITE_INGOT, 1));
			this.inventory.setItem(21, new ItemStack(Material.GRASS_BLOCK, 1));
			
			this.inventory.setItem(28, Items.getHeartFragmentItem());
			this.inventory.setItem(29, new ItemStack(Material.GRASS_BLOCK, 1));
			this.inventory.setItem(30, Items.getHeartFragmentItem());
			
			this.inventory.setItem(24, Items.getHeartItem());
			
			this.inventory.setItem(36, getLastPage());
		}
		this.player.openInventory(inventory);
	}
	
	public void createBorder() {
		for (int i = 0; i < 45; i ++) {
			this.inventory.setItem(i, getEmptyGlass());
		}
		this.inventory.setItem(40, getClose());
	}
	
	public void closeShop() {
		shops.remove(this.player);
	}
	
	public static ItemStack getEmptyGlass() {
		ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(" ");
		item.setItemMeta(im);
		
		return item;
	}
	
	public static void clickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getCurrentItem() != null) {
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")) {
				player.closeInventory();
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aNext Recipe")) {
				player.closeInventory();
				shops.get(player).openRecipe(1);
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLast Recipe")) {
				player.closeInventory();
				shops.get(player).openRecipe(0);
			}
		}
		event.setCancelled(true);
	}
	
	public static ItemStack getDisabledBlock() {
		ItemStack item = new ItemStack(Material.RED_CONCRETE, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§cShop ain't open.");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Fuck off");
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getNextPage() {
		ItemStack item = new ItemStack(Material.ARROW, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§aNext Recipe");
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getLastPage() {
		ItemStack item = new ItemStack(Material.ARROW, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§aLast Recipe");
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getClose() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§cClose");
		item.setItemMeta(im);
		
		return item;
	}
}
