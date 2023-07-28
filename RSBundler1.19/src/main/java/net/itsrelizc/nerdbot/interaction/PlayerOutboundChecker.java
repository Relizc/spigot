package net.itsrelizc.nerdbot.interaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.nerdbot.OutboundDetection;

public class PlayerOutboundChecker implements Listener {
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		if (OutboundDetection.player != null) {
			if (event.getPlayer().isOp()) {
				return;
			}
			if (OutboundDetection.player.isOutside(event.getTo())) {
				event.setCancelled(true);
				ChatUtils.systemMessage(event.getPlayer(), OutboundDetection.boundChannelName, "§cYou cannot move there!");
			}
		}
	}
	
}
