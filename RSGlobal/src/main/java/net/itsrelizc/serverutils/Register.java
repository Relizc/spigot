package net.itsrelizc.serverutils;

import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;

public class Register {
	
	private static Integer failed = 0;
	
	public static void register(final ServerType server) {
		Bukkit.getLogger().info("Attempting to register server to bungeecord...");
		try {
			URL url = new URL("http://127.0.0.1:65534/addServer?ip=127.0.0.1&port=" + Bukkit.getPort() + "&code=" + Namespace.getFullCode() + "&type=" + server.getName());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			System.out.println("Register Response Code - " + responseCode);
			
		} catch (Exception e) {
			failed++;
			Bukkit.getLogger().warning("Unable to register. Failed: {" + failed + "}");
			e.printStackTrace();
			Bukkit.getLogger().info("Reattempting again in 10 seconds.");
			
			if (failed >= 3) {
				Bukkit.getLogger().info("Regiseration attempt failed due to consequtive fails. Please restart bukkit to register again");
				Bukkit.shutdown();
			} else {
				register(server);
			}
			
			
		}
		Bukkit.getLogger().info("Sucessfully registered!");
	}
	
}
