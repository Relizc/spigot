package net.itsrelizc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.InsMenu;
import net.itsrelizc.lifesteal.JSON;

public class Investigator {
	
	public static Map<Player, ItemStack[]> inv = new HashMap<Player, ItemStack[]>();
	public static Map<Player, Player> spec = new HashMap<Player, Player>();
	public static Map<Player, Collection<PotionEffect>> befeffects = new HashMap<Player, Collection<PotionEffect>>();
	public static Map<Player, Double> befheart = new HashMap<Player, Double>();
	public static Map<Player, Location> befloc = new HashMap<Player, Location>();
	
	public static void investiage(Player player) {
		if (inv.containsKey(player)) {
			removeInvestigator(player);
			ChatUtils.systemMessage(player, "§6§lINVESTIGATE", "§cDisabled investigation mode!");
		} else {
			addInvestigator(player);
			ChatUtils.systemMessage(player, "§6§lINVESTIGATE", "§aEnabled investigation mode!");
		}
	}
	
	public static void checkProfileExists(Player player) {
		JSONObject data = JSON.loadDataFromDataBase("investigate.json");
		if (!data.containsKey(player.getUniqueId().toString())) {
			JSONObject hash = new JSONObject();
			
			hash.put("submitted", 0);
			hash.put("correct", 0);
			hash.put("banned", 0);
			hash.put("muted", 0);
			hash.put("credit", 1.0);
			
			JSONArray abuse = new JSONArray();
			hash.put("abuse", abuse);
			
			JSONArray submit = new JSONArray();
			hash.put("submit", submit);
			
			data.put(player.getUniqueId().toString(), hash);
			
			JSON.saveDataFromDataBase("investigate.json", data);
		}
	}
	
	public static int getPing(Player player) {
        try {
            Method handle = player.getClass().getMethod("getHandle");
            Object nmsHandle = handle.invoke(player);
            Field pingField = nmsHandle.getClass().getField("ping");
            return pingField.getInt(nmsHandle);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException
                | NoSuchFieldException e) {
            Bukkit.broadcastMessage(e.getMessage());
        }
       
        return -1;
    }
	
	public static void addInvestigator(Player player) {
		checkProfileExists(player);
		
		inv.put(player, player.getInventory().getContents());
		befeffects.put(player, player.getActivePotionEffects());
		befheart.put(player, player.getMaxHealth());
		befloc.put(player, player.getLocation());
		for (PotionEffect e : player.getActivePotionEffects()) {
			player.removePotionEffect(e.getType());
		}
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 255));
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255));
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255));
		player.getInventory().clear();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player) {
				p.hidePlayer(player);
			}
		}
		
		player.setMaxHealth(20);
		player.setHealth(20);
		
		player.setGameMode(GameMode.CREATIVE);
		player.setGameMode(GameMode.ADVENTURE);
		
		player.setAllowFlight(true);
		
		ChatUtils.systemMessage(player, "§6§lINVESTIGATE", "§aYou are currently in investigation mode!");
		ChatUtils.systemMessage(player, "§6§lINVESTIGATE", "§aClick the §bNether Star §ain your hotbar to begin investigating players!");
		
		player.getInventory().setItem(4, InsMenu.getMenuStar());
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static void removeInvestigator(Player player) {
		for (PotionEffect e : player.getActivePotionEffects()) {
			player.removePotionEffect(e.getType());
		}
		player.getInventory().clear();
		
		for (PotionEffect eff : befeffects.get(player)) {
			player.addPotionEffect(eff);
		}
		for (int i = 0; i < 36; i ++) {
			player.getInventory().setItem(i, inv.get(player)[i]);
		}
		player.setMaxHealth(befheart.get(player));
		player.teleport(befloc.get(player));
		
		inv.remove(player);
		befeffects.remove(player);
		befheart.remove(player);
		befloc.remove(player);
		if (spec.containsKey(player)) {
			spec.remove(player);
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player) {
				p.showPlayer(player);
			}
		}
		
		player.setGameMode(GameMode.SURVIVAL);
		
		player.setAllowFlight(false);
	}
	
	public static void investigate(Player player, Player target) {
		player.teleport(target);
		spec.put(player, target);
		ChatUtils.systemMessage(player, "§6§lINVESTIGATE", "§aYou are currently investigating §b" + target.getDisplayName() + "§a!");
	}

	public static void targetEvent(EntityTargetEvent event) {
		if (event.getTarget() instanceof Player) {
			Player player = (Player) event.getTarget();
			if (inv.keySet().contains(player)) {
				event.setCancelled(true);
			}
		}
	}

	public static void interactEvent(PlayerInteractEvent event) {
		if (inv.containsKey(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	public static void damageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (inv.containsKey((Player) event.getEntity())) {
				event.setCancelled(true);
			}
		}
	}
	
    public static void PickupItem(PlayerPickupItemEvent event) {
        if (inv.containsKey(event.getPlayer())) event.setCancelled(true);
    }
	
	public static void dmg(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (inv.containsKey((Player) event.getDamager())) {
				event.setCancelled(true);
			}
		}
	}
}
