package net.itsrelizc.serverutils;

import org.bukkit.Bukkit;

import net.itsrelizc.utils.RandomString;

public class Namespace {
	
	private static Boolean haveSet = false;
	private static String baseCode = null;
	private static String ramCode = null;
	
	public static void setServerCode() {
		if (haveSet) {
			Bukkit.getLogger().warning("Server code has already been set. Ignoring...");
		}
		
		Bukkit.getLogger().info("Server code has been set. Any further modifications will be ignored.");
		
		baseCode = RandomString.randomString(4);
		
		Double ram = Runtime.getRuntime().maxMemory() / 1048576.0;
		
		String ramc;
		
		if (ram >= 2048) {
			ramc = "G";
		} else if (ram >= 1024 && ram < 2048) {
			ramc = "B";
		} else if (ram >= 512 && ram < 1024) {
			ramc = "M";
		} else if (ram >= 256 && ram < 512) {
			ramc = "S";
		} else {
			ramc = "T";
		}
		
		ramCode  = ramc;
		
		haveSet = true;
	}
	
	public static String getBaseCode() {
		return baseCode;
	}
	
	public static String getRamCode() {
		return ramCode;
	}
	
	public static String getFullCode() {
		return ramCode + baseCode;
	}
	
	public static String getDisplayCode() {
		return "RS-" + ramCode + baseCode;
	}
}
