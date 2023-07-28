package net.itsrelizc.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ProfileUtils {
	
	private static List<PlayerProfile> profiles = new ArrayList<PlayerProfile>();

	public static void addPlayerProfile(PlayerProfile profile) {
		profiles.add(profile);
	}
	
	public static List<PlayerProfile> getPlayerProfiles() {
		return profiles;
	}
	
	public static PlayerProfile getProfileByPlayer(Player player) {
		for (PlayerProfile profile : getPlayerProfiles()) {
			if (profile.getPlayer() == player) {
				return profile;
			}
		}
		return null;
	}
	
	public static void removePlayerProfile(PlayerProfile profile) {
		profiles.remove(profile);
	}
	
	public static PlayerProfile a(Player player) {
		return getProfileByPlayer(player);
	}
}
