package net.itsrelizc.game.shitwars;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import net.itsrelizc.players.GameScoreboard;

public class Boards implements Listener {
	
	public static int waitingTimeLeft = 420;
	
	public static GameScoreboard waitingBoard;
	
	public static GameScoreboard banditsBoard;
	
	public static GameScoreboard copBoard;
	
	@EventHandler
	public void op(PlayerJoinEvent e) {
		waitingBoard.editLine(5, "Players: §a" + Bukkit.getOnlinePlayers().size() + "/13");
	}
	
	@EventHandler
	public void op(PlayerQuitEvent e) {
		waitingBoard.editLine(5, "Players: §a" + (Bukkit.getOnlinePlayers().size() - 1) + "/13");
	}
	
	public static void initWaiting() {
		waitingBoard.insertLine("Map: §aAlumj §e(☀ Day)", 2);
		waitingBoard.insertLine("Mode: §bNormal", 3);
		waitingBoard.addSpaceLine(4);
		waitingBoard.insertLine("Players: §a0/13", 5);
		waitingBoard.addSpaceLine(6);
		waitingBoard.insertLine("Waiting for more players", 7);
		waitingBoard.insertLine("to join the game", 8);
		waitingBoard.addSpaceLine(9);
		waitingBoard.insertLine("Game session will collapse", 10);
		waitingBoard.insertLine("in §e7:00 §rif there are", 11);
		waitingBoard.insertLine("not enough players.", 12);
	}
	
	public static void startTickingWaitTimer(final Plugin plugin) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				tickStep(plugin);
			}
			
		}, 20L);
	}
	
	private static void tickStep(final Plugin plugin) {
		
		waitingTimeLeft --;
		int min = waitingTimeLeft / 60;
		int sec = waitingTimeLeft % 60;
		
		String tim = min + ":" + String.format("%02d", sec);
		
		waitingBoard.editLine(11, "in §e" + tim + " §rif there are");
		
		if (waitingTimeLeft == 0) {
			return;
		}
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				tickStep(plugin);
			}
			
		}, 20L);
	}

	public static void initBoards(Plugin plugin) {
		waitingBoard = new GameScoreboard("SHITWARS");
		waitingBoard.startScoreboardNameEffect(plugin, 7);
		banditsBoard = new GameScoreboard("SHITWARS");
		//banditsBoard.startScoreboardNameEffect(plugin, 7);
		copBoard = new GameScoreboard("SHITWARS");
		//copBoard.startScoreboardNameEffect(plugin, 7);
	}
	
}
