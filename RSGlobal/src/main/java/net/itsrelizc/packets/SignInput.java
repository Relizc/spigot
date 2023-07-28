package net.itsrelizc.packets;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;

public class SignInput {
	private Player player;
	private Block block;
	private Runnable completefunction;
	private String[] lines;

	public SignInput(Plugin plugin, Player player) {
		Block block = player.getLocation().getWorld().getBlockAt(player.getLocation());
		block.setType(Material.SIGN_POST);
		
		this.player = player;
		this.block = block;
		
		
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		
		manager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.UPDATE_SIGN) {

			@Override
			public void onPacketReceiving(PacketEvent event) {
				lines = event.getPacket().getStringArrays().getValues().get(0);
				completefunction.run();
			}
		});
	}
	
	public String[] getLines() {
		return lines;
	}
	
	public void setCompleteFunction(Runnable runnable) {
		this.completefunction = runnable;
	}
	
	public void open() {
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
		packet.getBlockPositionModifier().write(0, new BlockPosition(this.block.getLocation().getBlockX(), this.block.getLocation().getBlockY(), this.block.getLocation().getBlockZ()));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		this.block.setType(Material.AIR);
	}
}
