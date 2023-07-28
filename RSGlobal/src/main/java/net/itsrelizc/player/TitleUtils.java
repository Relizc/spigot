package net.itsrelizc.player;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;

public class TitleUtils {
	
	public static void sendTitle(Player player, String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TITLE);
		packet.getTitleActions().write(0, TitleAction.TITLE);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText(title));
		packet.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);
		
		PacketContainer sub = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TITLE);
		sub.getTitleActions().write(0, TitleAction.SUBTITLE);
		sub.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));
		sub.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, sub);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
