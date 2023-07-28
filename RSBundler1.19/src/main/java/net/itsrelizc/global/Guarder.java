package net.itsrelizc.global;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class Guarder {
	private int maxX;
	private int maxY;
	private int minY;
	private int maxZ;
	private int minZ;
	private int minX;

	public Guarder(Location start, Location end) {
		this.maxX = Math.max(start.getBlockX(), end.getBlockX());
		this.minX = Math.max(start.getBlockX(), end.getBlockX());
		this.maxY = Math.max(start.getBlockY(), end.getBlockY());
		this.minY = Math.max(start.getBlockY(), end.getBlockY());
		this.maxZ = Math.max(start.getBlockZ(), end.getBlockZ());
		this.minZ = Math.max(start.getBlockZ(), end.getBlockZ());
	}
	
	public void check(PlayerMoveEvent event, Player player) {
		if (player.getLocation().getX() > this.maxX || player.getLocation().getX() < this.minX ||player.getLocation().getY() > this.maxY || player.getLocation().getY() < this.minY ||player.getLocation().getZ() > this.maxZ || player.getLocation().getZ() < this.minZ) {
			event.setCancelled(true);
			ChatUtils.systemMessage(player, "§6§lAREA LIMIT", "§eYou cannot go further!");
		}
	}
}
