package net.itsrelizc.copy;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProfileUtils {
	
	private static List<PlayerProfile> profiles = new ArrayList<PlayerProfile>();

	public static void addPlayerProfile(PlayerProfile profile) {
		profiles.add(profile);
	}
	
	public static List<PlayerProfile> getPlayerProfiles() {
		return profiles;
	}
	
	public static PlayerProfile getProfileByPlayer(ProxiedPlayer player) {
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
	
	public static PlayerProfile a(ProxiedPlayer player) {
		return getProfileByPlayer(player);
	}
}
