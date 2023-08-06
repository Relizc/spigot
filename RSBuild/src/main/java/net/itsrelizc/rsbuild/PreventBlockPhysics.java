package net.itsrelizc.rsbuild;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PreventBlockPhysics implements Listener {

	@EventHandler
	public void onBlockPhysicsEvent(BlockPhysicsEvent e) {
	    if (e.getBlock().getType() == Material.PORTAL) e.setCancelled(true);
	}
	
}
