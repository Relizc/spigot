package net.itsrelizc.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.itsrelizc.tablist.Title;

public class PlayerConnectionHandle implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		PlayerProfile.checkAccountExsistsThenCreate(event.getPlayer());
		PlayerProfile profile = new PlayerProfile(event.getPlayer());
		ProfileUtils.addPlayerProfile(profile);
		if (profile.getRankType().getPermission() > 224) {
			event.getPlayer().setOp(true);
		} else {
			event.getPlayer().setOp(false);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Title.sendDefaultTitle(event.getPlayer());
		PlayerProfile.checkAccountExsistsThenCreate(event.getPlayer());
	}
	
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent event) {
		PlayerProfile profile = ProfileUtils.getProfileByPlayer(event.getPlayer());
		ProfileUtils.removePlayerProfile(profile);
		
	}
}
