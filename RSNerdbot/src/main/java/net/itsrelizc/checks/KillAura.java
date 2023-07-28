package net.itsrelizc.checks;

import java.awt.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.itsrelizc.watcher.Profile;
import net.itsrelizc.watcher.Violations;
import net.itsrelizc.watcher.pack.SusMeter;

public class KillAura implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void detect(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			
			float yaw = attacker.getLocation().getYaw();

			Double angle = 90 - Math.atan(Math.abs(event.getEntity().getLocation().getY() - attacker.getLocation().getY()) / Math.abs(event.getEntity().getLocation().getX() - attacker.getLocation().getX()));
			
			Location npcLoc = event.getEntity().getLocation();

	        double xDiff = attacker.getLocation().getX() - npcLoc.getX();
	        double yDiff = attacker.getLocation().getY() - npcLoc.getY();
	        double zDiff = attacker.getLocation().getZ() - npcLoc.getZ();

	        double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
	        double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
	        double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
	        double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
	        if (zDiff < 0.0)
	            newYaw = newYaw + Math.abs(180 - newYaw) * 2;
	        newYaw = (newYaw - 90);

	        yaw = (float) newYaw;

			

//			Bukkit.broadcastMessage(attacker.getDisplayName() + "[KillAura]: " + yaw + " yaw, " + newYaw + 180 + "realyaw");
		}
	}
	
}
