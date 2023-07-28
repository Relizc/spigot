package net.itsrelizc.global;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TPSUtils {
	
	private static Double currenttps = 20.00;
	private static Long lastms = System.currentTimeMillis();
	private static Double lastgaps = 1.00;
	
	private static Long lastminms = System.currentTimeMillis();
	private static Integer violations = 0;
	
	private static Double expectedminTPS = 10.00;
	
	public static void startRecording(Plugin plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				Long thisms = System.currentTimeMillis();
				Float gapms = (float) (thisms - lastms);
				Float gaps = (float) (gapms / 1000.00);
				if (gaps < 0.5) {
					return;
				}
				currenttps = (double) (20 / gaps);
				lastms = System.currentTimeMillis();
			}
			
		}, 0L, 20L);
	}
	
	public static Float getTPS() {
		return currenttps.floatValue();
	}
}
