package net.itsrelizc.menus.templates;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TemplateBlockSelector extends SelectorTemplate {
	
	public void initlizeBlocks() {
		for (Material material : Material.values()) {
			ItemStack is;
			try {
				is = new ItemStack(material);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(material.name());
				is.setItemMeta(im);
			} catch (Exception e) {
				is = new ItemStack(Material.BARRIER);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("§cUnusable Material");
				is.setItemMeta(im);
			}
			this.addItem(is);
		}
	}
	
}
