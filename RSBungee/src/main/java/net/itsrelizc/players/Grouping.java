package net.itsrelizc.players;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Grouping implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void _aO(PostLoginEvent event) {
		
		Profile p = Profile.createProfile(event.getPlayer());
		
		Rank r = Rank.findByPermission(p.permission);
		
		event.getPlayer().setPermission("main.admin", r.useop);
	}
	
	@EventHandler
	public void _b(PlayerDisconnectEvent event) {
		Profile.removeProfile(Profile.findByOwner(event.getPlayer()));
	}
	
}
