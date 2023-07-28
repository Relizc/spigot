package net.itsrelizc.nerdbot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.JSON;
import net.itsrelizc.nerdbot.interaction.BlockOutboundChecker;
import net.itsrelizc.nerdbot.interaction.PlayerOutboundChecker;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class OutboundDetection {
	
	public static OutboundPair block = null;
	public static OutboundPair player = null;
	public static String boundChannelName = "";
	
	public static class OutboundPair {
		
		public Location a;
		public Location b;

		public List<Player> highlightfor;
		
		public double mx;
		public double my;
		public double mz;
		
		public double sx;
		public double sy;
		public double sz;
		
		public float c1;
		public float c2;
		public float c3;

		
		
		public OutboundPair(Location a, Location b, float c1, float c2, float c3) {
			this.a = a;
			this.b = b;
			
			this.mx = Math.max(a.getX(), b.getX());
			this.my = Math.max(a.getY(), b.getY());
			this.mz = Math.max(a.getZ(), b.getZ());
			
			this.sx = Math.min(a.getX(), b.getX());
			this.sy = Math.min(a.getY(), b.getY());
			this.sz = Math.min(a.getZ(), b.getZ());
			
			this.c1 = c1;
			this.c2 = c2;
			this.c3 = c3;
			
			this.highlightfor = new ArrayList<Player>();
			
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSBundler"), new Runnable() {

				@Override
				public void run() {
					for (Player p : highlightfor) {
						
						for (double x = sx; x <= mx + 1; x += 1) {
							packet(p, x, sy, sz);
							packet(p, x, sy, mz + 1);
							packet(p, x, my + 1, sz);
							packet(p, x, my + 1, mz + 1);
						}
						
						for (double x = sy; x <= my + 1; x += 1) {
							packet(p, sx, x, sz);
							packet(p, sx, x, mz + 1);
							packet(p, mx + 1, x, sz);
							packet(p, mx + 1, x, mz + 1);
						}
						
						for (double x = sz; x <= mz + 1; x += 1) {
							packet(p, sx, sy, x);
							packet(p, sx, my + 1, x);
							packet(p, mx + 1, sy, x);
							packet(p, mx + 1, my + 1, x);
						}
						
						
						
					}
					
				}
				
			}, 0L, 5L);
		}
		
		public void packet(Player p, double x, double y, double z) {
			PacketPlayOutWorldParticles a = new PacketPlayOutWorldParticles(
					EnumParticle.REDSTONE,
					true,
					(float) x,
					(float) y,
					(float) z,
					c1,
					c2,
					c3,
					1f,
					0
				);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(a);
		}
		
		public void highLightfor(Player player) {
			this.highlightfor.add(player);
		}
		
		public void stopHighLightfor(Player player) {
			this.highlightfor.remove(player);
		}
		
		public boolean isOutside(Location loc) {
			return loc.getX() > this.mx || loc.getY() > this.my || loc.getZ() > this.mz || loc.getX() < this.sx || loc.getY() < this.sy || loc.getZ() < this.sz;
		}
		
	}
	
	public static void enableMe(Plugin plugin) {
		JSONObject s = JSON.pathLoadData(Bukkit.getWorld("world").getWorldFolder().toPath().toString() + "\\worldmetadata.rsd");
		if (!s.containsKey("guard")) {
			Bukkit.getLogger().warning("Cannot enable outbound protection as world has no guard coordinates!");
			return;
		}
		JSONObject q = (JSONObject) s.get("guard");
		
		if (q.containsKey("block")) {
			JSONArray b = (JSONArray) q.get("block");
			JSONArray b1 = (JSONArray) b.get(0);
			JSONArray b2 = (JSONArray) b.get(1);
			block = new OutboundPair(new Location(Bukkit.getWorld("world"), ((Double) b1.get(0)).doubleValue(), ((Double) b1.get(1)).doubleValue(), ((Double) b1.get(2)).doubleValue()), new Location(Bukkit.getWorld("world"), 
					((Double) b2.get(0)).doubleValue(), ((Double) b2.get(1)).doubleValue(), ((Double) b2.get(2)).doubleValue()), 1f, 0.3f, 0.01f);
		} else {
			Bukkit.getLogger().warning("Ignoring block outbound protection as no block coordinates are specified!");
		}
		
		if (q.containsKey("player")) {
			JSONArray p = (JSONArray) q.get("player");
			JSONArray p1 = (JSONArray) p.get(0);
			JSONArray p2 = (JSONArray) p.get(1);
			player = new OutboundPair(new Location(Bukkit.getWorld("world"), ((Double) p1.get(0)).doubleValue(), ((Double) p1.get(1)).doubleValue(), ((Double) p1.get(2)).doubleValue()), new Location(Bukkit.getWorld("world"), 
					((Double) p2.get(0)).doubleValue(), ((Double) p2.get(1)).doubleValue(), ((Double) p2.get(2)).doubleValue()), 0.01f, 1f, 0.01f);
		} else {
			Bukkit.getLogger().warning("Ignoring player outbound protection as no block coordinates are specified!");
		}
		
		Bukkit.getPluginManager().registerEvents(new PlayerOutboundChecker(), plugin);
		Bukkit.getPluginManager().registerEvents(new BlockOutboundChecker(), plugin);
		
		Bukkit.getPluginCommand("hlplayer").setExecutor(new DebugCommand1());
		Bukkit.getPluginCommand("hlblock").setExecutor(new DebugCommand2());
	}
	
	public static class DebugCommand1 implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (!player.highlightfor.contains((Player) sender)) {
				ChatUtils.systemMessage((Player) sender, "§6§lGUARD", "§aShowing player bound box for you!");
				player.highlightfor.add((Player) sender);
			} else {
				ChatUtils.systemMessage((Player) sender, "§6§lGUARD", "§cStopped showing player bound box for you!");
				player.highlightfor.remove((Player) sender);
			}
			return true;
		}
		
	}
	
	public static class DebugCommand2 implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (!block.highlightfor.contains((Player) sender)) {
				ChatUtils.systemMessage((Player) sender, "§6§lGUARD", "§aShowing block bound box for you!");
				block.highlightfor.add((Player) sender);
			} else {
				ChatUtils.systemMessage((Player) sender, "§6§lGUARD", "§cStopped showing block bound box for you!");
				block.highlightfor.remove((Player) sender);
			}
			return true;
		}
		
	}

}
