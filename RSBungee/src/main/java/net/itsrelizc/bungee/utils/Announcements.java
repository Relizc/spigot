package net.itsrelizc.bungee.utils;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import net.md_5.bungee.api.ProxyServer;
import net.saltyfishstudios.bungee.DataManager;
import net.saltyfishstudios.bungee.Main;

public class Announcements {
	
	public static String announcement;
	
	public static void announce(String s) {
		ProxyServer.getInstance().broadcast(" §e§m------------------- §r§e§lSERVER NEWS §r§e§m-------------------§r\n " + s + "\n §e§m----------------------------------------------------");
	}
	
	public static void init() {
		JSONObject d = DataManager.loadPureJsonFromDb("wideinfo.json");
		announcement = (String) d.get("announcement");
		
		startTicking();
	}
	
	public static void startTicking() {
		ProxyServer.getInstance().getScheduler().schedule(Main.plugin, new Runnable() {

			@Override
			public void run() {
				announce(announcement);
			}
			
		}, 0L, 5L, TimeUnit.MINUTES);
	}
	
}
