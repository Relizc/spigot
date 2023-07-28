package net.itsrelizc.npc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import net.itsrelizc.external.packetwrapper.WrapperPlayServerEntityMetadata;
import net.itsrelizc.external.packetwrapper.WrapperPlayServerSpawnEntity;

public class Hologram {
	
	private ArmorStand armorStand;
	private static List<Hologram> hologramList = new ArrayList<Hologram>();

	public Hologram(Location location, String message) {
		this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		
		armorStand.setCustomName(message);
		armorStand.setCustomNameVisible(true);
		armorStand.setGravity(false);
		armorStand.setVisible(false);
	}
	
	public Hologram(Location location, String message, Player player) {
		
	}
	
	public ArmorStand getArmorStand() {
		return this.armorStand;
	}
	
	public void kill() {
		this.armorStand.remove();
		hologramList.remove(this);
	}
	
	public static Hologram createHologram(Location location, String message) {
		Hologram hologram = new Hologram(location, message);
		hologramList.add(hologram);
		return hologram;
	}
	
	public static void createAbstractHologram(Location location, String message, Player player) {
		Integer id = (int) (Math.random() * Integer.MAX_VALUE);
//		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);
//
//        // Entity ID
//        packet.getIntegers().write(0, id);
//        // Entity Type
//        packet.getIntegers().write(6, 78);
//        // Set optional velocity (/8000)
//        packet.getIntegers().write(1, 0);
//        packet.getIntegers().write(2, 0);
//        packet.getIntegers().write(3, 0);
//        // Set yaw pitch
//        packet.getIntegers().write(4, 0);
//        packet.getIntegers().write(5, 0);
//        // Set object data
//        packet.getIntegers().write(7, 0);
//        // Set location
//        packet.getDoubles().write(0, location.getX());
//        packet.getDoubles().write(1, location.getY());
//        packet.getDoubles().write(2, location.getZ());
//        // Set UUID
//        packet.getUUIDs().write(0, UUID.randomUUID());
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        packet.getIntegers().write(0, id);
        packet.getUUIDs().write(0, UUID.randomUUID());
        packet.getIntegers().write(1, 1);
        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());
        packet.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);

	}
	
	public static List<Hologram> getHolograms() {
		return hologramList;
	}
	
	public static void killAll() {
		List<Hologram> list = new ArrayList<Hologram>(hologramList);
		for (Hologram hologram : list) {
			hologram.kill();
		}
	}
}
