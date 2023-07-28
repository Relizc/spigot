package net.itsrelizc.warp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.Me;
import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.networking.Components;
import net.md_5.bungee.api.ChatMessageType;

public class WarpUtils {
	
	public static Map<Player, Integer> ticks = new HashMap<Player, Integer>();
	public static Map<Player, Integer> taskid = new HashMap<Player, Integer>();
	
	public static void send(Player player, String codeDestination) {
		
		if (ticks.keySet().contains(player)) {
			ChatUtils.systemMessage(player, "§a§lWARP", "§cBe patient! I am already sending you to a server!");
			return;
		}
		
		Communication com = new Communication(CommunicationType.PLAYER_CONNECT);
		
		ChatUtils.systemMessage(player, "§a§lWARP", "§7Queueing connection to [RS-" + codeDestination + "]...");
		
		com.writeByte(Me.rambyte);
		com.writeString(Me.code);
		com.writeByte((byte) 0x00);
		
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
		
		if (ticks.keySet().contains(player)) {
			ChatUtils.systemMessage(player, "§a§lWARP", "§cBe patient! I am already sending you to a server!");
			return;
		}
		
		Communication com = new Communication(CommunicationType.PLAYER_CONNECT);
		
		com.writeByte(Me.rambyte);
		com.writeString(Me.code);
		com.writeByte((byte) 0x01);
		
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
	
	public static void kickFromGame(Player player, String reason) {
		
	}
	
	public static void startAwaitNewCreateTick(final Player player) {
		ticks.put(player, 0);
		
		int num = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSBundler"), new Runnable() {

			@Override
			public void run() {
				ticks.put(player, ticks.get(player) + 1);
				ChatUtils.systemActionBar(player, "§a§lWARP", "§7Waiting for new game instance... (" + ticks.get(player) + "s)");
				if (ticks.get(player) >= 30) {
					ticks.remove(player);
					Bukkit.getScheduler().cancelTask(taskid.get(player));
					ChatUtils.systemActionBar(player, "§a§lWARP", "§7Waiting for new game instance... §cCancelled!");
					ChatUtils.systemMessage(player, "§a§lWARP", "§cSorry! The server could not create an instance right now! Please try again later!");
				}
			}
			
		}, 0L, 20L);
		
		taskid.put(player, num);
	}
	
}
