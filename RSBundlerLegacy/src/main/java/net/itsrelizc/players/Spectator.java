package net.itsrelizc.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.itsrelizc.global.ChatUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class Spectator {
	
	public static List<Player> spectators = new ArrayList<Player>();
	public static Map<Player, Integer> revive = new HashMap<Player, Integer>();
	
	public static String boundChannelName = "";
	
	public static SpectatorRunnable reviveTask;
	
	public static void startTicking() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSBundler"), new Runnable() {

			@Override
			public void run() {
				for (Player p : revive.keySet()) {
					revive.put(p, revive.get(p) - 1);
					
					if (revive.get(p) <= 5) {
						ChatUtils.systemMessage(p, boundChannelName, "You are reviving in §a" + revive.get(p) + " seconds!");
						
						IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"§c§l" + revive.get(p) + "\"}");
						PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
						
						IChatBaseComponent b = ChatSerializer.a("{\"text\": \"§eReviving!\"}");
						PacketPlayOutTitle b2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, b);
						
						PacketPlayOutTitle length = new PacketPlayOutTitle(0, 200, 0);


						((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(b2);
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
					}
					
					if (revive.get(p) == 0) {
						ChatUtils.systemMessage(p, boundChannelName, "You are revived!");
						
						IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"\"}");
						PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
						
						IChatBaseComponent b = ChatSerializer.a("{\"text\": \"\"}");
						PacketPlayOutTitle b2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, b);
						
						PacketPlayOutTitle length = new PacketPlayOutTitle(0, 10, 0);


						((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(b2);
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
						revive(p);
					}
				}
			}
			
		}, 0L, 20L);
	}
	
	public static void makeSpectator(Player player, Integer r) {
		player.getInventory().clear();
		
		spectators.add(player);
		revive.put(player, r);
		
		player.setAllowFlight(true);
		player.setFlying(true);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player) {
				p.hidePlayer(player);
			}
		}
		
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"§c" + "You died!" + "\"}");
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		
		IChatBaseComponent b = ChatSerializer.a("{\"text\": \"§eYou are now a spectator!\"}");
		PacketPlayOutTitle b2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, b);
		
		PacketPlayOutTitle length = new PacketPlayOutTitle(10, 100, 10);


		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(b2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
			
	}
	
	public static void revive(Player player) {
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player) {
				p.showPlayer(player);
			}
		}
		
		spectators.remove(player);
		revive.remove(player);
		
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setVelocity(new Vector(0, 0, 0));
		player.setFallDistance(0f);
		
		player.setHealth(20.0);
		player.setSaturation(20f);
		
		reviveTask.run(player);
	}
	
}
