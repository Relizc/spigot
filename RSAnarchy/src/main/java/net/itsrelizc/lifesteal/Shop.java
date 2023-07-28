package net.itsrelizc.lifesteal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shop {
	
	private Player player;
	private Inventory inventory;
	private static Map<Player, Shop> shops = new HashMap<Player, Shop>();

	public Shop(Player player) {
		this.player = player;
		shops.put(player, this);
	}
	
	public void openShop() {
		this.inventory = Bukkit.createInventory(player, 54, "Heart Shop");
		this.createBorder();
		this.player.openInventory(inventory);
		this.loadItems();
	}
	
	public void loadItems() {
		this.inventory.setItem(10, getHeartFragmentItem());
		this.inventory.setItem(11, getTeleportTicket());
	}
	
	public void createBorder() {
		for (int i = 0; i < 9; i ++) {
			this.inventory.setItem(i, getEmptyGlass());
		}
		for (int i = 9; i < 44; i += 9) {
			this.inventory.setItem(i, getEmptyGlass());
			this.inventory.setItem(i + 8, getEmptyGlass());
		}
		for (int i = 45; i < 54; i ++) {
			this.inventory.setItem(i, getEmptyGlass());
		}
		this.inventory.setItem(49, getClose());
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
	
	public static Integer countHearts(Player player) {
		int amount = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) continue;
			if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart")) {
				amount += item.getAmount();
			}
		}
		return amount;
	}
	
	public static Integer countMats(Player player, Material material) {
		int amount = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) continue;
			if (item.getType() == material) {
				amount += item.getAmount();
			}
		}
		return amount;
	}
	
	public static void subHeart(Player player, Integer amount) {
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) continue;
			if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart")) {
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
	
	public static void subMat(Player player, Material material, Integer amount) {
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) continue;
			if (item.getType() == material) {
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
	
	public static boolean checkEmpty(Player player) {
		if (!isEmpty(player)) {
			ChatUtils.systemMessage(player, "§6§lSHOP", "§cYou need some empty space in your inventory!");
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 2f);
			return false;
		}
		return true;
	}
	
	public static void clickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getCurrentItem() != null) {
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 2f);
				ChatUtils.systemMessage(player, "§6§lSHOP", "§eSee you next time customer!");
				player.closeInventory();
				event.setCancelled(true);
				return;
			}
			
			
			
			// SHOP
			if (event.getSlot() == 10 && event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§7×")) {
				if (!checkEmpty(player)) {
					event.setCancelled(true);
					return;
				}
				if (countHearts(player) >= 1) {
					ItemStack i = Items.getHeartFragmentItem();
					i.setAmount(3);
					player.getInventory().addItem(i);
					subHeart(player, 1);
					ChatUtils.systemMessage(player, "§6§lSHOP", "§eYou purchased §73× §cHeart Fragment§e!");
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					ChatUtils.systemMessage(player, "§6§lSHOP", "§cYou can't afford the cost to purchase this item!");
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 2f);
				}
			} else if (event.getSlot() == 11 && event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§7×")) {
				if (!checkEmpty(player)) {
					event.setCancelled(true);
					return;
				}
				if (countMats(player, Material.DIAMOND) >= 1) {
					player.getInventory().addItem(Items.getTeleportTicket());
					subMat(player, Material.DIAMOND, 1);
					ChatUtils.systemMessage(player, "§6§lSHOP", "§eYou purchased §71× §aTeleport Ticket§e!");
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				} else {
					ChatUtils.systemMessage(player, "§6§lSHOP", "§cYou can't afford the cost to purchase this item!");
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 2f);
				}
			}
			
			event.setCancelled(true);
		}
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
	
	public static boolean isEmpty(Player player) {
		boolean empty = false;
		Inventory inv = player.getInventory();
		for (int i = 9; i < 36; i ++) {
			if (inv.getContents()[i] == null) {
				empty = true;
				break;
			}
		}
		return empty;
	}
	
	public static ItemStack getHeartFragmentItem() {
		ItemStack item = Items.getHeartFragmentItem();
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§7×3 §cHeart Fragment");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Keep in mind that you");
		lore.add("§7need 4 heart fragments");
		lore.add("§7and bunch of other stuff");
		lore.add("§7to make a heart.");
		lore.add(" ");
		lore.add("§8§m---------------");
		lore.add("§eCost:");
		lore.add("§7×1 §cHeart");
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getTeleportTicket() {
		ItemStack item = Items.getTeleportTicket();
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§7×1 §aTeleport Ticket");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Whoosh Woosh Wooooosh");
		lore.add("§7Be blaa da bi la ba ba");
		lore.add("§7Thank you for using");
		lore.add("§7Relizc Fast Travel™");
		lore.add(" ");
		lore.add("§8§m---------------");
		lore.add("§eCost:");
		lore.add("§7×1 §fDiamond");
		im.setLore(lore);
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
