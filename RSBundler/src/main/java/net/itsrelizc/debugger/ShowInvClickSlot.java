package net.itsrelizc.debugger;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.itsrelizc.global.ChatUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class ShowInvClickSlot implements Listener {
	
	@EventHandler
	public void i(InventoryClickEvent event) {
		if (!Debugger.hasDebug((Player) event.getWhoClicked())) return;
		
		TextComponent a = new TextComponent("§7You clicked inventory! ");
		a.addExtra(Debugger.eventStringBuilder(event));
		
		ChatUtils.systemMessage((Player) event.getWhoClicked(), "§8§lDEBUG", a);
	}
	
}