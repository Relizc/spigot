package net.itsrelizc.global;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
/*class StartGameTimer {
	int time;
	int taskID;
	int stage;

	public StartGameTimer() {
		this.time = 10;
	}
	
	public void startTimer(Plugin plugin) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		Bukkit.broadcastMessage("§6The game starts in §c" + time + " §6seconds!");
   	 	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.sendTitle("§l§c" + time, "§6Get Ready!", 0, 100, 0);
		 }
		taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                 time -= 1;
                 if (time <= 5) {
                	 Bukkit.broadcastMessage("§6The game starts in §c" + time + " §6seconds!");
                	 for (Player player : Bukkit.getServer().getOnlinePlayers()) {
         				player.sendTitle("§l§c" + time, "§6Get Ready!", 0, 100, 0);
         			 }
                 }
                 if (time == 0) {
                	 Bukkit.getScheduler().cancelTask(taskID);
                 }
            }
        }, 0L, 20L);
	}
	
	public void stopTimer() {
		Bukkit.getScheduler().cancelTask(taskID);
	}
}
 */

public class Stopwatch {
	private Double time;
	private int taskID;
	private Plugin plugin;
	private HashMap<Double, Runnable> runmap;

	public Stopwatch(Plugin plugin) {
		this.time = (double) 0;
		this.plugin = plugin;
		this.runmap = new HashMap<Double, Runnable>();
	}
	
	public void addTimeStampEvent(Double at, Runnable runnable) {
		this.runmap.put(at, runnable);
	}
	
	public void timerStartEvent(Runnable runnable) {
		this.runmap.put((double) -1, runnable);
	}
	
	public void timerEndEvent(Runnable runnable) {
		this.runmap.put((double) 0, runnable);
	}
	
	public void startTimer() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
   	 	if (this.runmap.get((double) -1) != null) {
   	 		this.runmap.get((double) -1).run();;
   	 	}
		taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                 time += 0.1;
                 if (runmap.get(time) != null && time != 0.0) {
                	 runmap.get(time).run();
                 }
            }
        }, 0L, 2L);
	}
	
	public Double getTime() {
		return this.time;
	}
	
	public void stopTimer() {
		Bukkit.getServer().getScheduler().cancelTask(taskID);
	}
}
