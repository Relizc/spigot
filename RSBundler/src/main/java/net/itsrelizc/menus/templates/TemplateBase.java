package net.itsrelizc.menus.templates;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import net.itsrelizc.menus.ClassicMenu;

public interface TemplateBase {
	
	public void onClick(InventoryClickEvent event);
	
	public void onClose(InventoryCloseEvent event);

	public void loadTemplate(ClassicMenu classicMenu);
}
