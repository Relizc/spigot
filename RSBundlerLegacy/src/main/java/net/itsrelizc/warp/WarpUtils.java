package net.itsrelizc.warp;

import java.io.IOException;

import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.Me;
import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.networking.Components;

public class WarpUtils {
	
	public static void send(Player player, String codeDestination) {
		Communication com = new Communication(CommunicationType.PLAYER_MOVE);
		
		ChatUtils.systemMessage(player, "§a§lWARP", "§7Queueing connection to [RS-" + codeDestination + "]...");
		
		com.writeByte(Me.rambyte);
		com.writeString(Me.code);
		
		com.writeByte(Components.fromStringRAMChar(codeDestination.charAt(0)));
		com.writeString(codeDestination.substring(1));
		
		com.writeString(player.getName());
		
		try {
			com.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void send(Player player, ServerCategory cat) {
		Communication com = new Communication(CommunicationType.PLAYER_MOVE_BY_CAT);
		
		com.writeByte(Me.rambyte);
		com.writeString(Me.code);
		
		com.writeByte(cat.data);
		com.writeShort(cat.sub);
		
		com.writeString(player.getName());
		
		try {
			com.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
