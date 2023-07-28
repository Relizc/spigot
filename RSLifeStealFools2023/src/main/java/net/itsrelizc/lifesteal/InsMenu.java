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
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;

import net.itsrelizc.utils.Investigator;

public class InsMenu {
	
	private Player player;
	private Inventory inventory;
	private static Map<Player, InsMenu> shops = new HashMap<Player, InsMenu>();
	private static Map<Player, Preset> page = new HashMap<Player, Preset>();

	public InsMenu(Player player) {
		this.player = player;
		shops.put(player, this);
		page.put(player, Preset.INVES_MAIN_MENU);
	}
	
	public void openShop() {
		this.inventory = Bukkit.createInventory(player, 54, "Investigation Menu");
		this.loadPreset(Preset.INVES_MAIN_MENU);
		this.player.openInventory(inventory);
	}
	
	public void loadPreset(Preset preset) {
		page.put(player, preset);
		if (preset == Preset.INVES_MAIN_MENU) {
			createBorder();
			this.inventory.setItem(4, getProfileSkull(this.player));
			this.inventory.setItem(12, getSubmittionRecords());
			this.inventory.setItem(13, getAbuseRecords());
			this.inventory.setItem(14, getSettings());
			this.inventory.setItem(22, getTeleportList());
		} else if (preset == Preset.INVES_PLAYER_LIST) {
			createBorder();
			List<Player> ok = new ArrayList<Player>();
			for (Player p :Bukkit.getOnlinePlayers()) {
				if (!Investigator.inv.keySet().contains(p)) {
					ok.add(p);
				}
			}
			
			int n =0;
			
			for (int i = 10; i <= 37; i += 9) {
				for (int j = 0; j < 7; j ++) {
					if (n < ok.size()) {
						this.inventory.setItem(i + j, getTeleportSkull(ok.get(n)));
						n ++;
					} else {
						this.inventory.setItem(i + j, null);
					}
				}
			}
		}
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
	
	public static void clickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getCurrentItem() != null) {
			event.setCancelled(true);
			
			if (page.get(player) == Preset.INVES_MAIN_MENU) {
				if (event.getSlot() == 22) {
					shops.get(player).loadPreset(Preset.INVES_PLAYER_LIST);
				}
			} else if (page.get(player) == Preset.INVES_PLAYER_LIST) {
				if (event.getCurrentItem() != null) {
					String[] args = event.getCurrentItem().getItemMeta().getDisplayName().split(" ");
					if (args.length >= 3) {
						Player target = Bukkit.getPlayer(args[2].substring(2));
						Investigator.investigate(player, target);
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	public static ItemStack getEmptyGlass() {
		ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(" ");
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getInvesSettings() {
		ItemStack item = new ItemStack(Material.REDSTONE, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(" ");
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

	public static ItemStack getTeleportSkull(Player player) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(player.getName());
		meta.setDisplayName("§eTeleport to §b" + player.getDisplayName());
		skull.setItemMeta(meta);
		
		return skull;
	}
	
	
	public static ItemStack getProfileSkull(Player player) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(player.getName());
		meta.setDisplayName("§aYour Profile");
		
		JSONObject data = (JSONObject) JSON.loadDataFromDataBase("investigate.json").get(player.getUniqueId().toString());
		
		List<String> lore = new ArrayList<String>();
		lore.add("§eReports Investigated: §b" + data.get("submitted"));
		lore.add("§eCorrect Reports: §b" + data.get("correct"));
		lore.add("§eIncorrect Reports: §b" + ((long) data.get("submitted") - (long) data.get("correct")));
		lore.add("§eReport Accuracy: §b" + ((long) data.get("correct")) * 1.0 / (long) data.get("submitted"));
		lore.add(" ");
		lore.add("§ePlayers Muted: §b" + data.get("muted"));
		lore.add("§ePlayers Banned: §b" + data.get("banned"));
		lore.add(" ");
		
		double reliability = ((double) data.get("credit")) * 100.0;
		String c = "";
		
		if (reliability >= 90) {
			c = "§a";
		} else if (reliability >= 70) {
			c = "§e";
		} else if (reliability >= 60) {
			c = "§6";
		} else {
			c = "§c";
		}
		lore.add("§eModeration Reliability: " + c + ((double) data.get("credit")) * 100.0 + "%");
		lore.add(" ");
		if (reliability >= 100) {
			lore.add("§7Your current reliability shows");
			lore.add("§7that you don't have any form");
			lore.add("§7of admin abusing recently!");
			lore.add("§7You are such a nice admin!");
		} else if (reliability >= 90) {
			lore.add("§7Your current reliability shows");
			lore.add("§7that you only have a few form");
			lore.add("§7of admin abusing. Continue to");
			lore.add("§7be a wonderful admin!");
		} else if (reliability >= 70) {
			lore.add("§6Your current reliability shows");
			lore.add("§6that you have a some form of ");
			lore.add("§6admin abusing recently.");
			lore.add(" ");
			lore.add("§6Current penalties:");
			lore.add("§6● Cannot instant ban / mute player.");
		} else if (reliability >= 60) {
			lore.add("§6Your current reliability shows");
			lore.add("§6that you have been admin abusing");
			lore.add("§6frequently.");
			lore.add(" ");
			lore.add("§6Current penalties:");
			lore.add("§6● Cannot instant ban / mute player.");
			lore.add("§6● Cannot use advantageous commands");
			lore.add("§6  (/give, /kill, /tp, ...)");
		} else {
			lore.add("§6Your current reliability shows");
			lore.add("§6that you are a really terrible");
			lore.add("§6admin that likes to torture");
			lore.add("§6innocent players. Please change");
			lore.add("§6the way of how you moderate the");
			lore.add("§6server.");
			lore.add(" ");
			lore.add("§6Current penalties:");
			lore.add("§6● Punishments require other admin's");
			lore.add("§6  verifications.");
			lore.add("§6● Cannot use advantageous commands");
			lore.add("§6  (/give, /kill, /tp, ...)");
		}
		meta.setLore(lore);
		skull.setItemMeta(meta);
		
		return skull;
	}
	
	public static ItemStack getMenuStar() {
		ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§aInvestigation Menu §b§lCLICK!");
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getSubmittionRecords() {
		ItemStack item = new ItemStack(Material.EMERALD, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§bSubmittion Records");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Click me to view your");
		lore.add("§7submittion records!");
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getSettings() {
		ItemStack item = new ItemStack(Material.COMPARATOR, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§eInvestigation Settings");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Click me to edit your");
		lore.add("§7investigation settings!");
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getTeleportList() {
		ItemStack item = new ItemStack(Material.COMPASS, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§dStart Tracking");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Click me to being");
		lore.add("§7tracking a player!");
		lore.add(" ");
		lore.add("§ePlayer Properties:");
		lore.add("§eIM: §aCurrent Investigating Player §7(string name)");
		lore.add("§ep: §aPing §7(int ping)");
		lore.add("§eP: §aPosition §7(int x, int y, int z)");
		lore.add("§eV: §aServer-side Velocity §7(double x, double y, double z)");
		lore.add("§eH: §aHealth §7(double current, double maxHealth)");
		lore.add("§eh: §aSaturation Information §7(int foodLevel, double saturation, double exhausion, double saturationRegenRate)");
		
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
	}
	
	public static ItemStack getAbuseRecords() {
		ItemStack item = new ItemStack(Material.TNT, 1);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§cAbuse Records");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Click me to view your");
		lore.add("§7abuse records!");
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
