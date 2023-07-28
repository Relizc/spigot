package net.itsrelizc.npc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.World;

public class NMSHologram extends EntityArmorStand implements Listener {
	
	public Player player;
	public boolean visible;
	public boolean v;
	public int task;
	public Location loc;
	
	public static NMSHologram register(Location location, Player player) {
		World w = ((CraftWorld) location.getWorld()).getHandle();
		
		NMSHologram holo = new NMSHologram(w, location.getX(), location.getY(), location.getZ());
		
		holo.setPosition(location.getX(), location.getY(), location.getZ());
		holo.player = player;
		holo.visible = false;
		holo.v = false;
		holo.loc = location;
//		
//		holo.setCustomNameVisible(true);
//		holo.setCustomName("LOSER");
//		
//		NBTTagCompound tag = new NBTTagCompound();
//		holo.c(tag);
//		
//		Bukkit.broadcastMessage(tag.getString("CustomName"));
//		Bukkit.broadcastMessage(String.valueOf(tag.getBoolean("CustomNameVisible")));
//		
//		tag.setBoolean("CustomNameVisible", true);
//		tag.setString("CustomName", "L Bozo");
//		
//		Bukkit.broadcastMessage(tag.getString("CustomName"));
//		Bukkit.broadcastMessage(String.valueOf(tag.getBoolean("CustomNameVisible")));
//		
//		holo.f(tag);
		
		return holo;
	}
	
	@SuppressWarnings("deprecation")
	public void startTickingWatcher() {
		final NMSHologram s = this;
		
		this.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSBundler"), new Runnable() {

			@Override
			public void run() {
				
				if (s.player.getLocation().getWorld() != s.loc.getWorld()) {
					s.v = false;
					return;
				}
				
				double distance = s.loc.distance(s.player.getLocation());
				
				if (distance > 48) {
					s.v = false;
				} else {
					if (!s.v) {
						s.sendSpawnPacket(player);
						System.out.print("Send");
					}
					s.v = true;
				}
				
				// System.out.print("Broad " + s.v);
				
			}
			
		}, 0L, 2L);
	}
	
	public void sendSpawnPacket(final Player player) {
		this.setCustomName("A");
		this.setCustomNameVisible(true);
		
		PacketPlayOutSpawnEntity a = new PacketPlayOutSpawnEntity(this, 78);
		
		DataWatcher dw = new DataWatcher(this);
		dw.a(0, (byte) 0x20 | 0x01);
		dw.a(2, "L bozo");
		dw.a(3, 1);
		final PacketPlayOutEntityMetadata b = new PacketPlayOutEntityMetadata(this.getId(), dw, false);
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(a);
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("RSBundler"), new Runnable () {

			@Override
			public void run() {
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(b);
			}
			
		}, 20L);
	}

	public NMSHologram(World world, double d0, double d1, double d2) {
		super(world, d0, d1, d2);
	}
	
	public void _a(PlayerJoinEvent event) {
		
	}
	
	public void _b(PlayerQuitEvent event) {
		
	}

}
