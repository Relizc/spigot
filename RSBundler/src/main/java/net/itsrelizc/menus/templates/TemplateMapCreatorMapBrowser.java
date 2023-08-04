package net.itsrelizc.menus.templates;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.itsrelizc.bookutils.BookUtils;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.itsrelizc.gamemodes.mapbuilding.UMapBuilding;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.ItemGenerator;
import org.bukkit.inventory.meta.BookMeta;

public class TemplateMapCreatorMapBrowser extends SelectorTemplate {

	public void load(ClassicMenu menu) {
		List<ItemStack> bb = UMapBuilding.a(this.menu.holder);
		
		if (bb == null) {
			menu.setItem(22, a());
			if(UMapBuilding.guideLinePlayers() == null || UMapBuilding.guideLinePlayers().contains(menu.holder.getRealUUID())){
				menu.setItem(31,a(1,""));

			} else{
				menu.setItem(31, a(1,""));
			}

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
	public ItemStack a(int a,String bbbbb) {
		ItemStack b = ItemGenerator.generate(Material.SIGN, 1, "§aRead the \"Building Guidelines\"!",
				"§7Click to start building!"
				);

		return b;
	}
	public void onClick(InventoryClickEvent e) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
		if(e.getSlot() == 31){
			Player p = (Player) e.getWhoClicked();
			if(UMapBuilding.guideLinePlayers()==null||!Objects.requireNonNull(UMapBuilding.guideLinePlayers()).contains(p.getRealUUID())){

				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta bookMeta = (BookMeta) book.getItemMeta();

				List<String> pages1 = new ArrayList<String>();
				pages1.add("§3§lRELIZC NETWORK §r§2§lGUIDELINES\n\n§6Building Guidelines\n§7(L.A. Jul 30, 2023)\n\n§rTo associate with the §6Community Building Universia§r, you are required to follow all printed guidelines in this document.\n\n      §d§lFLIP PAGE");
				pages1.add("§6§lDEFINITIONS\n\n§1Blueprint§r - Tells you about the goal and the theme of a map hire.\n§1Suspension§r - The action of temporary forbidding a player to build. §7(§nServer Suspension§r§7 - Ban, suspended from joining server)");
				pages1.add("§6§lRULE 1\n§5§lTHEME CONTROL\n\n§rAll builders should follow and stick to the main theme of blueprint. Builds that are off-topic will be undone and builders might recieve a suspension.");
				pages1.add("§6§lRULE 2\n§5§lBEHAVIOR\n\n§rAll builders are required to build respectfully and appropriately. Inappropriate builds will not result in a build suspension §cBUT §ra §cSERVER SUSPENSION§r.");
				pages1.add("§6§lRULE 3\n§5§lRESOURCES\n\n§rAll builders should NOT apply stress to the server, such as building lag machines. Builders that intend to stress the server will recieve a server suspension.");
				pages1.add("§6§lRULE 4\n§5Wow\n\n§rAll builders should have fun while building and should enjoy the building process! Builders should also be creative and imaginative!");
				pages1.add("§6The map that builders engage in will be voted by the community!\nThe most voted map will be selected by the server and put into a real offical map!\n\n\n\n\n\n      §d§lFLIP PAGE");

				bookMeta.setPages(pages1);

				List<IChatBaseComponent> pages;

				try {
					pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
				} catch (ReflectiveOperationException ex) {
					ex.printStackTrace();
					return;
				}
				
				TextComponent a = new TextComponent("§6In order to contribute to the Community Building Universia, you have to agree to this document.\n\n");
				
				TextComponent text = new TextComponent("§2[I accept all the guidelines above]\n\n");
				ChatUtils.attachCommand(text, "acceptguideline 7168f93eff95a2f229d86f69a48019c070661730f2fddfd076ace00214fada02", "§aClick here to accept all guidelines");
				TextComponent text2 = new TextComponent("§4[I do not accept the guidelines above]");
				ChatUtils.attachCommand(text2, "denyguideline b289bd5a92acea6583e8510d951bd5c1ca7cf0f4faccc7d7778b598fef76982c", "§cClick here to deny all guidelines");
				
				a.addExtra(text);
				a.addExtra(text2);

				IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(a));
				pages.add(page);

				bookMeta.setTitle(ChatUtils.randomString(31));
				bookMeta.setAuthor("LBozoRatio");
				book.setItemMeta(bookMeta);
				p.closeInventory();
//				((CraftPlayer) p).getHandle().openBook(CraftItemStack.asNMSCopy(book));
				BookUtils.openBook(book, p);

			}else if(Objects.requireNonNull(UMapBuilding.guideLinePlayers()).contains(p.getRealUUID())){
				WarpUtils.send(p, ServerCategory.PUBLIC_BUILD);

			}
		}
	}



	
}
 