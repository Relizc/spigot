package net.itsrelizc.checks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Move implements Listener {
	
	public Move() {
		
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		double distsimple = event.getFrom().distance(event.getTo());
		
		if (distsimple >= 1) {
			event.getPlayer().teleport(event.getFrom());
			event.setCancelled(true);
		}
	}
	
}
