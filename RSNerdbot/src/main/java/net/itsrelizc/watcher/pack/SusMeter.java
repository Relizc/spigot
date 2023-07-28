package net.itsrelizc.watcher.pack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.entity.Player;

import net.itsrelizc.global.BanUtils;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.watcher.Profile;
import net.itsrelizc.watcher.Violations;

public class SusMeter {
	
	public static Map<Player, Double> susmap = new HashMap<Player, Double>();
	
	public static void addValue(Player player, Double sus, Violations vio) {
		Profile.profilemap.get(player).addSus(sus, vio);
		if (Profile.profilemap.get(player).getSus() >= 1) {
			if (player.isOp()) {
				ChatUtils.systemMessage(player, "§c§lBANS", "§aYou just bypassed " + "§d[NAV-" + Profile.profilemap.get(player).getReportID() + "] §r§b" + Profile.profilemap.get(player).getFullVio());
				Profile.profilemap.get(player).setSus(0);
			} else {
				BanUtils.createBan(player, "§d[NAV-" + Profile.profilemap.get(player).getReportID() + "] §r§b" + Profile.profilemap.get(player).getFullVio(), (long) 259200);
				Profile.profilemap.remove(player);
			}
		}
	}
}
