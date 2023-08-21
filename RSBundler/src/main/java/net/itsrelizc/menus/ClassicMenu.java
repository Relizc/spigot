package net.itsrelizc.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.itsrelizc.menus.templates.MenuTemplate;
import net.itsrelizc.menus.templates.TemplateBase;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class ClassicMenu implements Listener {
	
	public Inventory menu;
	public int row;
	public Player holder;
	private TemplateBase template;
	private ClassicMenu previoustemp;
	private ObjectFunction run = null;



	public ClassicMenu(Player holder, int row, String name) {
		this.menu = Bukkit.createInventory(holder, row * 9, name);
		this.holder = holder;
		this.row = row;
		TemplateBase MenuTemplate = new MenuTemplate();
		this.template = MenuTemplate;
		
		this.previoustemp = null;
		this.run = null;
	}
	
	public ClassicMenu(Player player, int row, String name, TemplateBase template) {
		this(player, row, name);
		this.template = template;
		this.run = null;
	}
	
	public void setPreviousPage(ClassicMenu menu) {
		this.previoustemp = menu;
	}
	
	public void setItem(int slot, ItemStack item) {
		this.menu.setItem(slot, item);
	}
	
	public void fillEmpty() {
		ItemStack it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(" ");
		it.setItemMeta(im);
		for (int i = 0; i < this.row * 9; i ++) {
			this.menu.setItem(i, it);
		}
	}
	
	public void leaveMiddleAreaWith(ItemStack is) {
		for (int i = 10; i < this.row * 9 - 10; i ++) {
			if ((i - 8) % 9 == 0 || (i - 8) % 9 == 1) continue;
			this.menu.setItem(i, is);
		}
	}
	
	public void leaveMiddleAreaWithDimGlass() {
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(" ");
		i.setItemMeta(im);
		leaveMiddleAreaWith(i);
	}
	
	public void leaveMiddleArea() {
		for (int i = 10; i < this.row * 9 - 10; i ++) {
			if ((i - 8) % 9 == 0 || (i - 8) % 9 == 1) continue;
			this.menu.clear(i);
		}
	}
	
	public void putClose() {
		ItemStack it = new ItemStack(Material.BARRIER, 1);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName("§cClose");
		it.setItemMeta(im);
		this.menu.setItem(this.row * 9 - 5, it);
		
		if (this.previoustemp != null) {
			ItemStack it2 = new ItemStack(Material.FEATHER, 1);
			ItemMeta im2 = it2.getItemMeta();
			im2.setDisplayName("§ePrevious Page");
			it2.setItemMeta(im2);
			this.menu.setItem(this.row * 9 - 6, it2);
		}
	}
	public void setClick(ObjectFunction runnable){
		this.run = runnable;
	}
	public void show() {
		this.fillEmpty();
		this.putClose();
		this.holder.openInventory(this.menu);
		if (this.template != null) {
			this.template.loadTemplate(this);
		}
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("RSBundler"));
	}
	
	public void close() {
		this.holder.closeInventory();
	}
	
	@EventHandler
	public void click(InventoryClickEvent event) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
		event.setCancelled(true);
		if (event.getClick() == ClickType.DOUBLE_CLICK) {
			return;
			
		};
		if (event.getCurrentItem() == null) return;
		
		if (event.getCurrentItem().getItemMeta() == null) return;
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")) {
			this.close();
			return;
		} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§ePrevious Page")) {
			this.previoustemp.show();
			return;
		}
		if(this.run==null) this.template.onClick(event);
		else{
			this.run.acceptArgs(1,event);
			this.run.run();
		}
	}
	
	@EventHandler
	public void invclose(InventoryCloseEvent event) {
		System.out.print("Close");
		InventoryClickEvent.getHandlerList().unregister(this);
		InventoryCloseEvent.getHandlerList().unregister(this);
	}
	
}
