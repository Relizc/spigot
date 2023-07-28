package net.itsrelizc.menus.templates;

import org.bukkit.Material;
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
		
		menu.setItem(4, ItemGenerator.generate(new ItemStack(Material.FILLED_MAP, 1), 1, "§6§lMAP CONTRIBUTION", 
				"§7Start contributing and building maps for",
				"§7the Relizc Network Community! Create,",
				"§7find, or join a map creation squad"
				));
		
		menu.setItem(20, ItemGenerator.generate(new ItemStack(Material.FURNACE, 1), 1, "§eMaps that I am involved in", 
				"§7Oh I see... You were a professional",
				"§7builder that already started building!",
				"",
				"§aClick to see a list of joinable maps!"
				));
		
		menu.setItem(22, ItemGenerator.generate(new ItemStack(Material.BOOKSHELF, 1), 1, "§ePending Invites", 
				"§7Someone wants you to work with them?",
				"§7Then they must have sent you an invite",
				"",
				"§aClick to see a list of invitations!"
				));
		
		menu.setItem(24, ItemGenerator.generate(new ItemStack(Material.IRON_SHOVEL, 1), 1, "§eJoin a map", 
				"§7Options 1 and 2 doesn't work for you?",
				"§7Seems like this is the last option for",
				"§7you!",
				"",
				"§7If your partner sent you a link or a",
				"§7code, you should enter it here!",
				"",
				"§aClick to enter an invitation code or link!"
				));
		
		
	}
}
