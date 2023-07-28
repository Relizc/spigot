package net.itsrelizc.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.itsrelizc.lifesteal.ChatUtils;

public class RulesBook implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
	       
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("BookUpdateBook");
        meta.setAuthor("Relizc");
        
        String[] pages = {"§6§lLIFESTEAL RULES\n§7As of Dec 12, 2022\n\n§9● §6Hacking is allowed UNLESS YOU ARE NOT GOING TO GET CAUGHT\n\n§9● §6Any problems caused by anticheat or minecraft will not be tolerated. So be careful!"};
        meta.addPage(pages);
        
        book.setItemMeta(meta);
        
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);
        PacketContainer pc = pm.createPacket(PacketType.Play.Server.OPEN_BOOK);
        try
        {
            pm.sendServerPacket(player, pc);
        } catch (InvocationTargetException e)
        {
            throw new RuntimeException("Cannot send open book packet " + pc, e);
        }
        player.getInventory().setItem(slot, old);
        
        player.getInventory().setItem(slot, old);
		
		return true;
	}

}
