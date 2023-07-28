package net.itsrelizc.menus.templates;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.itsrelizc.gamemodes.mapbuilding.UMapBuilding;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.ItemGenerator;

public class TemplateMapCreatorMapBrowser extends SelectorTemplate {

	public void load(ClassicMenu menu) {
		List<ItemStack> bb = UMapBuilding.a(this.menu.holder);
		
		if (bb == null) {
			menu.setItem(22, a());
			menu.setItem(31, a(1));
			return;
		}
		
		for (ItemStack i : UMapBuilding.a(menu.holder)) {
			this.addItem(i);
		}
	}
	
	public ItemStack a() {
		ItemStack b = ItemGenerator.generate(Material.GLASS_BOTTLE, 1, "§eNo involvement!", 
				"§7Seems like you have not",
				"§7yet signed up for the",
				"§7map building project",
				"§7yet!",
				"",
				"§aClick the sign below to",
				"§asign up for the project!");
		
		return b;
	}
	
	public ItemStack a(int a) {
		ItemStack b = ItemGenerator.generate(Material.SIGN, 1, "§aRead the \"Building Guidelines\"!", 
				"§7You have to agree to the",
				"§7\"Building Guidelines\"",
				"§7to start building!",
				"",
				"§aClick to read the guidelines!");
		
		return b;
	}
	
}
 