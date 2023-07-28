package net.itsrelizc.watcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import net.itsrelizc.global.MuteUtils;

public class Profile {
	
	public static Map<Player, Profile> profilemap = new HashMap<Player, Profile>();
	
	private Player player;
	private double sus;
	private String reportId;
	private List<Violations> violations;

	public Profile(Player player) {
		this.player = player;
		this.sus = 0.00;
		this.reportId = MuteUtils.generateRandomID();
		this.violations = new ArrayList<Violations>();
		
		profilemap.put(player, this);
	}
	
	public String getReportID() {
		return this.reportId;
	}
	
	public double getSus() {
		return this.sus;
	}
	
	public void addSus(double sus, Violations violation) {
		this.sus += sus;
		if (!this.violations.contains(violation)) {
			this.violations.add(violation);
		}
	}
	
	public void addSus(double sus) {
		this.sus += sus;
	}
	
	public void setSus(double sus) {
		this.sus = sus;
	}
	
	public String getFullVio() {
		String d = "";
		int c = 0;
		
		for (Violations vio : this.violations) {
			d += vio.name;
			c ++;
			if (c >= 3) {
				d += ", ";
				d += "... ";
				String g = "";
				if (this.violations.size() - c > 1) g = "s";
				d += "(" + String.valueOf(this.violations.size() - c) + " Other Violation" + g + ")";
				break;
			}
			if (c != this.violations.size()) {
				d += ", ";
			}
		}
		
		return d;
	}
}
