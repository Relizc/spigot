package net.itsrelizc.lobby.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.templates.TemplateServerMenu;
import net.itsrelizc.npc.NMSHologram;
import net.itsrelizc.npc.NPC;
import net.itsrelizc.server.QuickScheduler;
import net.itsrelizc.server.QuickScheduler.Action;

public class Main extends JavaPlugin implements Listener {

	private UUID uuid;

	@Override	
	public void onEnable() {
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				NPC npc = NPC.register(Bukkit.getWorld("world"), new Location(Bukkit.getWorld("world"), 30.5, 69, 0.5, 90f, 0f));
			}
			
		}, 0L);
		
	}
	
	@EventHandler		
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().setFoodLevel(20);
		event.getPlayer().setSaturation(20f);
		event.getPlayer().setExhaustion(0f);
		
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
		
		ItemStack i = new ItemStack(Material.COMPASS);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName("§eServer Menu §b§lCLICK!");
		im.setLore(ChatUtils.fromArgs("§7Yo ho ho whats inside?"));
		i.setItemMeta(im);
		event.getPlayer().getInventory().setItem(4, i);
		
		event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 69, 0.5, -90f, 0f));
		
		NMSHologram h = NMSHologram.register(event.getPlayer().getLocation(), event.getPlayer());
		h.startTickingWatcher();
		
	}
	
	@EventHandler
	public void noHunger(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (player.getGameMode() == GameMode.CREATIVE) {
				return;
			}
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {;
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		if (event.getItem() != null) {
			ItemStack item = event.getItem();
			if (item.getItemMeta() != null) {
				if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§eServer Menu §b§lCLICK!")) {
					
					final ClassicMenu menu = new ClassicMenu(event.getPlayer(), 6, "Server Menu", new TemplateServerMenu());
					
					QuickScheduler.scheduleDelay(new Action() {

						@Override
						public void run() {
							menu.show();
						}}, 1L);
				}
			}
			
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void inventory(InventoryClickEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (player.getGameMode() == GameMode.CREATIVE) {
				return;
			}
		}
		event.setCancelled(true);
	}
}
