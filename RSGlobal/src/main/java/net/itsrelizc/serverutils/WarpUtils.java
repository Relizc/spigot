package net.itsrelizc.serverutils;

import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.itsrelizc.lang.Lang.Language;
import net.itsrelizc.player.ProfileUtils;

public class WarpUtils {
	
	public static void sendToServerByCode(Player player, String code) {
		try {
			URL url = new URL("http://127.0.0.1:65534/sendPlayer?name=" + player.getName() + "&byCode=false&target=" + code);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			System.out.println("Register Response Code - " + responseCode);
			
		} catch (Exception e) {
			Bukkit.getLogger().warning("Unable to register warp task (Using Genetic Code): " + player + " to server " + code);
			e.printStackTrace();
		}
	}
	
	public static void sendToServerByCode(Player player, ServerType category) {
		try {
			
			URL url = new URL("http://127.0.0.1:65534/sendPlayer?name=" + player.getName() + "&byCode=false&target=" + category.getName());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			if (responseCode == 403) {
				ChatUtils.systemMessage(player, ProfileUtils.a(player).b(Language.WARP_CHANNEL_NAME), ProfileUtils.a(player).b(Language.WARP_FAIL));
				ChatUtils.systemMessage(player, ProfileUtils.a(player).b(Language.WARP_CHANNEL_NAME), ProfileUtils.a(player).b(Language.WARP_FAIL_ALREADY_AT));
			}
			
		} catch (Exception e) {
			Bukkit.getLogger().warning("Unable to register warp task (Using server category): " + player + " to serverType " + category.getName());
			e.printStackTrace();
		}
	}
}
