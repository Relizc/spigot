package net.itsrelizc.server;

import org.bukkit.Bukkit;

import net.itsrelizc.global.Me;

public class QuickScheduler {
	
	public static abstract class Action implements Runnable {
		public abstract void run();
	}
	
	public static void scheduleDelay(Action action, long ticks) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Me.plugin, action, ticks);
	}
	
}
