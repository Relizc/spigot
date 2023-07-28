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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.itsrelizc.global.MuteUtils;
import net.itsrelizc.watcher.Profile;
import net.itsrelizc.watcher.Violations;
import net.itsrelizc.watcher.pack.SusMeter;

public class ChatFilter implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void detect(AsyncPlayerChatEvent event) {
		String finalmsg = event.getMessage().replace(" ", "").toLowerCase();
		if (finalmsg.contains("maka")) {
			event.setCancelled(true);
			MuteUtils.createMute(event.getPlayer(), "§d[NAV-" + Profile.profilemap.get(event.getPlayer()).getReportID() + "] §r§bMajor Chat Violation", 86400L);
		}
	}
	
}
