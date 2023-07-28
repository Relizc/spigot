package net.itsrelizc.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class Timer {
	private Integer time;
	private int taskID;
	private Plugin plugin;
	private HashMap<Integer, Runnable> runmap;
	private List<Runnable> tickevents;

	public Timer(Plugin plugin, Integer amount) {
		this.time = amount;
		this.plugin = plugin;
		this.runmap = new HashMap<Integer, Runnable>();
		this.tickevents = new ArrayList<Runnable>();
	}
	
	public void addTimeStampEvent(Integer at, Runnable runnable) {
		this.runmap.put(at, runnable);
	}
	
	public void addTimeTickEvent(Runnable runnable) {
		this.tickevents.add(runnable);
	}
	
	public void timerStartEvent(Runnable runnable) {
		this.runmap.put(-1, runnable);
	}
	
	public void timerEndEvent(Runnable runnable) {
		this.runmap.put(0, runnable);
	}
	
	public void startTimer() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
   	 	if (this.runmap.get(-1) != null) {
   	 		this.runmap.get(-1).run();
   	 	}
		taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                 time -= 1;
                 for (Runnable runnable : tickevents) {
                	 runnable.run();
                 }
                 if (runmap.get(time) != null && time != 0) {
                	 runmap.get(time).run();
                 }
                 if (time == 0) {
                	 Bukkit.getScheduler().cancelTask(taskID);
                	 if (runmap.get(0) != null) {
                		 runmap.get(0).run();
                	 }
                 }
            }
        }, 0L, 20L);
	}
	
	public void stopTimer() {
		Bukkit.getScheduler().cancelTask(taskID);
	}
}
