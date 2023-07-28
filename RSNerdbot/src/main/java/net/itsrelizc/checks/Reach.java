package net.itsrelizc.checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.itsrelizc.watcher.Profile;
import net.itsrelizc.watcher.Violations;
import net.itsrelizc.watcher.pack.SusMeter;

public class Reach implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void detect(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			
			Double distance = attacker.getLocation().distance(event.getEntity().getLocation());
			
			if (distance > 4.2) {
				SusMeter.addValue(attacker, 0.13, Violations.REACH);
				event.setCancelled(true);
			}
			
//			Bukkit.broadcastMessage(attacker.getDisplayName() + ": " + distance + "d, " + Profile.profilemap.get(attacker).getSus() + "susval");
		}
	}
	
}
