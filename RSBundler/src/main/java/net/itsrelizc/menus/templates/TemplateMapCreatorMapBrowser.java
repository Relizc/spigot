package net.itsrelizc.menus.templates;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.itsrelizc.gamemodes.mapbuilding.UMapBuilding;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.ItemGenerator;
import org.bukkit.inventory.meta.BookMeta;

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
	public void onClick(InventoryClickEvent e){
		if(e.getSlot() == 31){
			Player p = (Player) e.getWhoClicked();
			if(!UMapBuilding.guideLinePlayers().contains(p.getRealUUID())){
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta bookMeta = (BookMeta) book.getItemMeta();

				List<String> pages1 = new ArrayList<String>();
				pages1.add("1.No Inappropiate content nor content containing any sensitive information");
				pages1.add("2.No griefing");
				pages1.add("3.Please build appropiately and fittingly to the theme of the project");
				pages1.add("4.Do not build lag machines");
				pages1.add("5.Your build will be checked on by admins, so please build well.");
				pages1.add("The highest-contributing players will receive a reward.");
				bookMeta.setPages(pages1);

				List<IChatBaseComponent> pages;

				try {
					pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
				} catch (ReflectiveOperationException ex) {
					ex.printStackTrace();
					return;
				}
				TextComponent text = new TextComponent("Accept");
				text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/acceptGuideLine"));
				text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("click to accept").create()));

				IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(text));
				pages.add(page);

				bookMeta.setTitle("GUIDELINES");
				bookMeta.setAuthor("The Relizc Network");

				((CraftPlayer) p).getHandle().openBook(CraftItemStack.asNMSCopy(book));

			}
		}
	}
	
}
 