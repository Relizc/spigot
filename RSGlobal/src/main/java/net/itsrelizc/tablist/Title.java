package net.itsrelizc.tablist;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import net.itsrelizc.lang.Lang.Language;
import net.itsrelizc.player.ProfileUtils;
import net.itsrelizc.serverutils.Namespace;

public class Title {
	public static void sendTitle(Player player, String top, String bottom) {
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText(top)).write(1, WrappedChatComponent.fromText(bottom));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendDefaultTitle(Player player) {
		sendTitle(player, 
				ProfileUtils.a(player).b(Language.TABLIST_TITLE_LINE1), 
				ProfileUtils.a(player).b(Language.TABLIST_TITLE_LINE2).replace("{0}", Namespace.getDisplayCode())
				);
	}
}
