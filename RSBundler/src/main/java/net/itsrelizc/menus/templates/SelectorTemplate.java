package net.itsrelizc.menus.templates;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.ItemGenerator;

public class SelectorTemplate implements TemplateBase {
	
	public int arrowright;
	public int arrowleft;
	
	public int curpage = 0;
	public int allpage = 0;
	
	public int PAGE_SIZE_PER_PAGE = 28;
	
	private int x = 0;
	
	public List<ItemStack> added = new ArrayList<ItemStack>();
	
	public ClassicMenu menu;
	
	public ItemStack standardLeftArrow() {
		ItemStack is = ItemGenerator.generate(Material.ARROW, 1, "§7Page §e" + (curpage + 1) + "§7/§e" + (allpage + 1), 
				"",
				"§aLeft Click to go to the previous page!",
				"§eRight Click to go §a5 §epages back!",
				"§bShift Click to go to the first page!");
		return is;
	}
	
	public ItemStack standardRightArrow() {
		ItemStack is = ItemGenerator.generate(Material.ARROW, 1, "§7Page §e" + (curpage + 1) + "§7/§e" + (allpage + 1), 
				"",
				"§aLeft Click to go to the next page!",
				"§eRight Click to go §a5 §epages more!",
				"§bShift Click to go to the last page!");
		return is;
	}

	@Override
	public void onClick(InventoryClickEvent event) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
		
		if (event.getSlot() == arrowleft && curpage != 0) {
			curpage --;
			
			update();
		}
		
		if (event.getSlot() == arrowright && curpage != allpage) {
			curpage ++;
			
			update();
		}
		
		selectorClickEvent(event);
	}
	
	public void selectorClickEvent(InventoryClickEvent event) {
		
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadTemplate(ClassicMenu classicMenu) {
		menu = classicMenu;
		arrowleft = (classicMenu.row - 2) * 9;
		arrowright = arrowleft + 8;
		classicMenu.fillEmpty();
		classicMenu.leaveMiddleArea();
	}
	
	public void updateArrowsAndInfo() {
		if (curpage != 0) {
			menu.setItem(arrowleft, standardLeftArrow());
		} else {
			ItemStack it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
			ItemMeta im = it.getItemMeta();
			im.setDisplayName(" ");
			it.setItemMeta(im);
			menu.setItem(arrowleft, it);
		}
		
		if (curpage != allpage) {
			menu.setItem(arrowright, standardRightArrow());
		} else {
			ItemStack it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
			ItemMeta im = it.getItemMeta();
			im.setDisplayName(" ");
			it.setItemMeta(im);
			menu.setItem(arrowright, it);
		}
	}
	
	public void update() {
		for (int i = 0; i < 28; i ++) {
			
			int trueslot = ((i / 7) + 1) * 9 + (i % 7) - (i / 28) * 28 + 1;
			if (i + curpage * 28 >= added.size()) {
				menu.menu.clear(trueslot);
				continue;
			};
			menu.setItem(trueslot, added.get(i + curpage * 28));
		}
		updateArrowsAndInfo();
	}
	
	public void addItem(ItemStack item) {
		added.add(item);
		allpage = x / 28;
		if (x < (curpage + 1) * 28 && x >= (curpage * 28)) {
			int trueslot = 10 + (x / 7) * 9 + (x % 7);
			System.out.print(trueslot);
			menu.setItem(trueslot, item);
		} else {
			updateArrowsAndInfo();
		}
		x ++;
	}

}
