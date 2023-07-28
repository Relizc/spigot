package net.itsrelizc.game.bridge;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Items {
	
	public static ItemStack getRedCap() {
		ItemStack a = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(255, 0, 0));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getRedChestplate() {
		ItemStack a = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(255, 0, 0));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}

	public static ItemStack getRedLegs() {
		ItemStack a = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(255, 0, 0));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getRedBoots() {
		ItemStack a = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(255, 0, 0));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getBlueCap() {
		ItemStack a = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(0, 0, 255));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getBlueChestplate() {
		ItemStack a = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(0, 0, 255));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}

	public static ItemStack getBlueLegs() {
		ItemStack a = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(0, 0, 255));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getBlueBoots() {
		ItemStack a = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta m = (LeatherArmorMeta) a.getItemMeta();
		m.setColor(Color.fromRGB(0, 0, 255));
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getIronSword() {
		ItemStack a = new ItemStack(Material.IRON_SWORD);
		ItemMeta m = a.getItemMeta();
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getDiaPick() {
		ItemStack a = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta m = a.getItemMeta();
		m.addEnchant(Enchantment.DIG_SPEED, 5, true);
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
	
	public static ItemStack getRedClay() {
		ItemStack a = new ItemStack(Material.STAINED_CLAY, 64, (byte) 14);
		return a;
	}
	
	public static ItemStack getBlueClay() {
		ItemStack a = new ItemStack(Material.STAINED_CLAY, 64, (byte) 11);
		return a;
	}
	
	public static ItemStack getBow() {
		ItemStack a = new ItemStack(Material.BOW);
		ItemMeta m = a.getItemMeta();
		m.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		m.spigot().setUnbreakable(true);
		a.setItemMeta(m);
		return a;
	}
}
