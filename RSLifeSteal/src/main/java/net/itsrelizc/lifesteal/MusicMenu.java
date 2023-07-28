package net.itsrelizc.lifesteal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MusicMenu {
	
	private Player player;
	private Inventory inventory;
	private static Map<Player, MusicMenu> shops = new HashMap<Player, MusicMenu>();

	public MusicMenu(Player player) {
		this.player = player;
		shops.put(player, this);
	}
	
	public void openShop() {
		this.inventory = Bukkit.createInventory(player, 54, "Vote Music");
		this.createBorder();
		this.player.openInventory(inventory);
		this.loadItems();
		refreshMenu(player);
	}
	
	public void loadItems() {
		this.inventory.setItem(4, getTitle());
	}
	
	public void createBorder() {
		for (int i = 0; i < 54; i ++) {
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
	
	public static void refreshMenu(Player player) {
		MusicMenu menu = shops.get(player);
		
		List<Double> ratio = new ArrayList<Double>();
		
		for (int slot = 0; slot <= 2; slot ++) {
			menu.inventory.setItem(20 + (slot * 2), getSongItem(player, slot));
			ratio.add(Music.songVotes.get(slot).size() * 1.0 / countTotalVotes());
		}
		
		Double maxVal = Collections.max(ratio);
		boolean diff = false;
		for (Double d : ratio) {
			if (d != maxVal) diff = true;
		}
		
		if (!diff) {
			for (int i = 0; i <= 2; i ++) {
				menu.inventory.setItem(29 + (i * 2), getSongStatus(i, ratio.get(i), Material.RED_STAINED_GLASS_PANE));
			}
		} else {
			for (int i = 0; i <= 2; i ++) {
				if (ratio.get(i) == maxVal) {
					menu.inventory.setItem(29 + (i * 2), getSongStatus(i, ratio.get(i), Material.LIME_STAINED_GLASS_PANE));
				} else {
					menu.inventory.setItem(29 + (i * 2), getSongStatus(i, ratio.get(i), Material.RED_STAINED_GLASS_PANE));
				}
			}
		}
	}
	
	public static Integer countTotalVotes() {
		int x = 0;
		for (List<Player> l : Music.songVotes) {
			x += l.size();
		}
		return x;
	}
	
	public static List<String> formatPlayerLore(List<Player> players) {
		List<String> s = new ArrayList<String>();
		
		int linelen = 0;
		boolean shift = true;
		String line = "";
		for (Player player : players) {
			if (linelen + player.getDisplayName().length() <= 32) {
				shift = true;
				line += "§b" + player.getDisplayName() + "§7, ";
				linelen += player.getDisplayName().length();
			} else {
				shift = false;
				s.add(line);
				line = "§b" + player.getDisplayName() + "§7, ";
				linelen = player.getDisplayName().length();
			}
		}
		
		if (shift) {
			try {
				line = line.substring(0, line.length() - 2);
				s.add(line);
			} catch (Exception e) {
				s.add("§7§oNo Voters! :(");
				return s;
			}
		} else {
			s.set(s.size() - 1, s.get(s.size() - 1).substring(0, line.length() - 2));
		}
		
		return s;
	}
	
	public static ItemStack getSongItem(Player player, Integer index) {
		ItemStack item = new ItemStack(Music.discTextures.get(index), 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§b" + Music.nextSongChoice.get(index).getTitle() + " - " + Music.nextSongChoice.get(index).getOriginalAuthor());
		List<String> lore = new ArrayList<String>();
		if (Music.songVotes.get(index).contains(player)) {
			lore.add("§7You are currently voting for");
			lore.add("§7this song!");
			im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		} else {
			lore.add("§7Click me to vote for this");
			lore.add("§7song!");
		}
		im.setLore(lore);
		
		im.addItemFlags(ItemFlag.values());
		
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getSongStatus(Integer index, Double ratio, Material color) {
		ItemStack item = new ItemStack(color, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§aCurrent Voters: §d" + Music.songVotes.get(index).size() + "/" + countTotalVotes() + " §b(" + Math.round(ratio * 10000) / 100 + "%)");
		
		List<String> lore = new ArrayList<String>();
		
		for (String l : formatPlayerLore(Music.songVotes.get(index))) {
			lore.add(l);
		}
		
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
	}
	
	public static void clickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getCurrentItem() != null) {
			
			if (event.getSlot() == 20) {
				if (Music.songVotes.get(0).contains(player)) {
					ChatUtils.systemMessage(player, "§2§lMUSIC", "§cYou already voted for this music!");
					event.setCancelled(true);
					return;
				}
				
				for (List<Player> p : Music.songVotes) {
					if (p.contains(player)) {
						p.remove(player);
						Music.songVotes.set(Music.songVotes.indexOf(p), p);
					}
				}
				
				List<Player> v1 = Music.songVotes.get(0);
				v1.add(player);
				Music.songVotes.set(0, v1);
				ChatUtils.systemMessage(player, "§2§lMUSIC", "§aYou changed your vote to the song §b" + Music.nextSongChoice.get(0).getTitle() + " - " + Music.nextSongChoice.get(0).getOriginalAuthor() + "§a!");
				
			} else if (event.getSlot() == 22) {
				if (Music.songVotes.get(1).contains(player)) {
					ChatUtils.systemMessage(player, "§2§lMUSIC", "§cYou already voted for this music!");
					event.setCancelled(true);
					return;
				}
				
				for (List<Player> p : Music.songVotes) { 
					if (p.contains(player)) {
						p.remove(player);
						Music.songVotes.set(Music.songVotes.indexOf(p), p);
					}
				}
				
				List<Player> v1 = Music.songVotes.get(1);
				v1.add(player);
				Music.songVotes.set(1, v1);
				ChatUtils.systemMessage(player, "§2§lMUSIC", "§aYou changed your vote to the song §b" + Music.nextSongChoice.get(1).getTitle() + " - " + Music.nextSongChoice.get(1).getOriginalAuthor() + "§a!");
			} else if (event.getSlot() == 24) {
				if (Music.songVotes.get(2).contains(player)) {
					ChatUtils.systemMessage(player, "§2§lMUSIC", "§cYou already voted for this music!");
					event.setCancelled(true);
					return;
				}
				
				for (List<Player> p : Music.songVotes) {
					if (p.contains(player)) {
						p.remove(player);
						Music.songVotes.set(Music.songVotes.indexOf(p), p);
					}
				}
				
				List<Player> v1 = Music.songVotes.get(2);
				v1.add(player);
				Music.songVotes.set(2, v1);
				ChatUtils.systemMessage(player, "§2§lMUSIC", "§aYou changed your vote to the song §b" + Music.nextSongChoice.get(2).getTitle() + " - " + Music.nextSongChoice.get(2).getOriginalAuthor() + "§a!");
			}
			
			refreshMenu(player);
			event.setCancelled(true);
		}
	}
	
	public static ItemStack getTitle() {
		ItemStack item = new ItemStack(Material.JUKEBOX, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§eVote for the Next Song!");
		List<String> lore = new ArrayList<String>();
		lore.add("§7The next song is about");
		lore.add("§7to be played! Vote for");
		lore.add("§7the song you want to listen!");
		lore.add(" ");
		lore.add("§7Songs marked with the color");
		lore.add("§agreen §7is the current song");
		lore.add("§7with the most votes!");
		lore.add(" ");
		lore.add("§7Songs marked with the color");
		lore.add("§cred §7is songs that has less");
		lore.add("§7votes than other songs!");
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
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
