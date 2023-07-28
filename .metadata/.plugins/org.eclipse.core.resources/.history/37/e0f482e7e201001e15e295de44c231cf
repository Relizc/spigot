package net.itsrelizc.menus.templates;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.warp.WarpUtils;

public class TemplateServerWarp extends MenuTemplate {
	
	public TemplateServerWarp() {
		
	}
	
	@Override
	public void loadTemplate(ClassicMenu menu) {
		menu.leaveMiddleArea();
		
		Communication com = new Communication(CommunicationType.BUNGEE_LIST_SERVERS);
		com.writeString(menu.holder.getName());
		try {
			com.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(InventoryClickEvent event) {
		
		String destination;
		
		try {
			destination = event.getCurrentItem().getItemMeta().getDisplayName().substring(10, 15);
		} catch (Exception e) {
			return;
		}
		
		WarpUtils.send((Player) event.getWhoClicked(), destination);
		
	}
	
}
