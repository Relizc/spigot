package net.itsrelizc.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.itsrelizc.player.Rank.Measurable;

public class PlayerChatHandler implements Listener {
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {
		Measurable rank = ProfileUtils.a(event.getPlayer()).getRankType();
		String code = "";
		if (rank.getDisplayName() != null)  {
			code = "[" + rank.getDisplayName() + "] ";
		}
		event.setFormat(rank.getRankColor() + code + event.getPlayer().getDisplayName() + "§7: §r" + event.getMessage());
	}
}
