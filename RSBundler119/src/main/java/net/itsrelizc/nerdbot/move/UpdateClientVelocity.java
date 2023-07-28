package net.itsrelizc.nerdbot.move;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class UpdateClientVelocity implements Listener {
	
	public static List<ApplicalMoveDelta> p = new ArrayList<ApplicalMoveDelta>();
	
	@EventHandler
	public void join(PlayerLoginEvent event) {
		p.add(new ApplicalMoveDelta(event.getPlayer()));
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		boolean applicalMove = false;
		
		//Range
		if (event.getFrom().distance(event.getTo()) <= 0.8) applicalMove = true;
		if (!applicalMove) return;
		
		//Bukkit.broadcastMessage(String.valueOf(event.getFrom().distance(event.getTo())) + " " + applicalMove);
		
		ApplicalMoveDelta f = null;
		
		for (ApplicalMoveDelta q: p) {
			if (q.a == event.getPlayer()) {
				f = q;
				break;
			}
		}
		
		if (f == null) event.getPlayer().kickPlayer("Sorry, our AntiCheat occured an error!\nPlease re-login, thank you!");
		
		f.update(event.getFrom(), event.getTo());
		f.compareBukkit();
	}
	
}
