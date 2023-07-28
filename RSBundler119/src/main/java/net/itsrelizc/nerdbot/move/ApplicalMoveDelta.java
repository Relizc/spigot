package net.itsrelizc.nerdbot.move;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ApplicalMoveDelta {
	
	public Player a;
	public double da;
	public double db;
	public double dc;
	
	public ApplicalMoveDelta(Player n) {
		this.a = n;
	}
	
	public void update(Location from, Location to) {
		this.da = from.getX() - to.getX();
		this.db = from.getY() - to.getY();
		this.dc = from.getZ() - to.getZ();
		
		this.da *= -1;
		this.db *= -1;
		this.dc *= -1;
	}
	
	public void compareBukkit() {
		//Bukkit.broadcastMessage(this.a.getName() + " " + this.da + " " + this.db + " " + this.dc + " - " + a.getVelocity().getX() + " " + a.getVelocity().getY() + " " + a.getVelocity().getZ());
	}

}
