package net.itsrelizc.nerdbot.interaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.nerdbot.OutboundDetection;

public class BlockOutboundChecker implements Listener {
	
	@EventHandler
	public void place(BlockPlaceEvent event) {
		if (OutboundDetection.block != null) {
			if (OutboundDetection.block.isOutside(event.getBlockPlaced().getLocation())) {
				event.setCancelled(true);
				ChatUtils.systemMessage(event.getPlayer(), OutboundDetection.boundChannelName, "§cYou cannot place blocks here!");
			}
		}
	}
	
}
