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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.itsrelizc.watcher.Profile;
import net.itsrelizc.watcher.Violations;
import net.itsrelizc.watcher.pack.SusMeter;

public class ClickGap implements Listener {
	
	private Map<Player, Long> detectgap = new HashMap<Player, Long>();
	private Map<Player, List<Long>> detectargs = new HashMap<Player, List<Long>>();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void detect(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) return;
		if (!detectgap.containsKey(event.getPlayer())) {
			detectgap.put(event.getPlayer(), System.currentTimeMillis());
			List<Long> gap = new ArrayList<Long>();
			detectargs.put(event.getPlayer(), gap);
		}
		Long result = System.currentTimeMillis() - detectgap.get(event.getPlayer());
		if (result != 0) {
			detectargs.get(event.getPlayer()).add(result);
		}
		
		long avgGap = -1;
		
		if (detectargs.get(event.getPlayer()).size() > 7 && result != 0) {
			while (detectargs.get(event.getPlayer()).size() > 7) {
				detectargs.get(event.getPlayer()).remove(0);
			}
			
			
			
			Long maxgap = (long) 0;
			
			for (int i = 0; i < (detectargs.get(event.getPlayer()).size() - 1); i ++) {
				maxgap = Math.max(maxgap, detectargs.get(event.getPlayer()).get(i) - detectargs.get(event.getPlayer()).get(i + 1));
			}
			
			long t = 0;
			for (Long l : detectargs.get(event.getPlayer())) {
				t += l;
			}
			
			avgGap = t / detectargs.get(event.getPlayer()).size();
			
			if (maxgap < 25 && avgGap < 63 && avgGap >= 0) {
				SusMeter.addValue(event.getPlayer(), 0.1, Violations.AUTOCLICKER);
			}
			
		}
		detectgap.put(event.getPlayer(), System.currentTimeMillis());
		
	}
	
}
