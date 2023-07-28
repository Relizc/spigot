package net.itsrelizc.players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.Global;
import net.itsrelizc.global.Me;

public class GameScoreboard {
	
	public class addLineCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			
			return true;
		}
		
	}
	
	public static List<GameScoreboard> scoreboards = new ArrayList<GameScoreboard>();
	
	private String gamename;
	private List<Player> accessible;
	
	private List<String> contentlines = new ArrayList<String>();
	private Scoreboard scoreboard;
	private Objective objective;
	
	private int spaces;

	public GameScoreboard(String gamename) {
		this.gamename = gamename;
		this.accessible = new ArrayList<Player>();
		
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.objective = this.scoreboard.registerNewObjective(Global.randomString(6), "dummy");
		objective.setDisplayName("§e§l" + gamename.toUpperCase());
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		addLine("§7[RS-" + Me.getStaticCode() + "] §8(" + Bukkit.getServer().getPort() + ")");
		addSpaceLine();
		addSpaceLine();
		addLine("§6mc.itsrelizc.net");
		
		scoreboards.add(this);
	}
	
	public void setAccessible(Player player) {
		this.accessible.add(player);
		player.setScoreboard(scoreboard);
	}
	
	public void setNotAccessible(Player player) {
		this.accessible.remove(player);
	}
	
	public void addLine(String s) {
		contentlines.add(s);
		show();
	}
	
	public void removeLine(int index) {
		String before = contentlines.get(index);
		scoreboard.resetScores(before);
		contentlines.remove(index);
		show();
	}
	
	public void insertLine(String s, int index) {
		contentlines.add(index, s);
		show();
	}
	
	public String getLine(int i) {
		return this.contentlines.get(i);
	}
	
	public void editLine(int i, String ne) {
		String before = contentlines.get(i);
		contentlines.add(i, ne);
		contentlines.remove(i + 1);
		scoreboard.resetScores(before);
		objective.getScore(ne).setScore(contentlines.size() - i);
	}
	
	public void addSpaceLine() {
		String next = ChatUtils.emptyColorString(10);
		while (scoreboard.getEntries().contains(next)) {
			next = ChatUtils.emptyColorString(10);
		}
		this.contentlines.add(next);
		show();
	}
	
	public void addSpaceLine(int index) {
		String next = ChatUtils.emptyColorString(10);
		while (scoreboard.getEntries().contains(next)) {
			next = ChatUtils.emptyColorString(10);
		}
		this.contentlines.add(index, next);
		show();
	}
	
	public void show() {
		int i = contentlines.size();
		for (String s : contentlines) {
			this.objective.getScore(s).setScore(i);
			i --;
		}
	}
	
}
