package net.itsrelizc.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemGobackCompass {
    public static ItemStack getItem(){
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("§7Return to lobby");
        compass.setItemMeta(meta);
        return compass;
    }
}
