package net.itsrelizc.menus.templates;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.ItemGenerator;

public class TemplateMapCreator extends MenuTemplate {
	
	public TemplateMapCreator() {
		
	}
	
	@Override
	public void loadTemplate(ClassicMenu menu) {
		menu.fillEmpty();
		menu.leaveMiddleAreaWithDimGlass();
		menu.putClose();
		
		menu.setItem(4, ItemGenerator.generate(new ItemStack(Material.MAP, 1), 1, "§6§lMAP CONTRIBUTION", 
				"§7Start contributing and building maps for",
				"§7the Relizc Network Community! Create,",
				"§7find, or join a map creation squad"
				));
		
		menu.setItem(20, ItemGenerator.generate(new ItemStack(Material.FURNACE, 1), 1, "§eMaps that I am involved in", 
				"§7Hey there! you looking to contribute ",
				"§7to the server by building?",
				"",
				"§aClick to join the current project!"
				));
		
		menu.setItem(22, ItemGenerator.generate(new ItemStack(Material.BEDROCK, 1), 1, "§ePending Invites",
				"§7WIP"

				));
		
		menu.setItem(24, ItemGenerator.generate(new ItemStack(Material.BEDROCK, 1), 1, "§eJoin a map",
				"§7WIP"
				));
		
		
	}
	
	@Override
	public void onClick(InventoryClickEvent event) {
		if (event.getSlot() == 20) {
			TemplateMapCreatorMapBrowser a = new TemplateMapCreatorMapBrowser();
			ClassicMenu menu = new ClassicMenu((Player) event.getWhoClicked(), 6, "Joined Maps", a);
			menu.show();
			a.load(menu);
		}
	}
}
