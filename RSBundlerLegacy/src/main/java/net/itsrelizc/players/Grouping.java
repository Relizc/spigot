package net.itsrelizc.players;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;

public class Grouping implements Listener {
	
	public static Map<Rank, Team> rankedTeam = new HashMap<Rank, Team>();
	public static Scoreboard board;
	
	public static boolean showPrefix = true;
	
	public static void initlizeRankGroups() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		
		for (Rank r : Rank.values()) {
			
			String name;
			
			if (r.toString().length() > 12) {
				name = r.toString().substring(0, 12);
			} else {
				name = r.toString();
			}
			
			String n = r.permission + name;
			
			Team team = board.registerNewTeam(n.substring(0, Math.min(n.length(), 15)));
			rankedTeam.put(r, team);
		}
		
		Bukkit.getPluginManager().registerEvents(new Grouping(), Bukkit.getPluginManager().getPlugin("RSBundler"));
	}
	
	@EventHandler
	public void _a(PlayerLoginEvent event) {
		
		Communication com = new Communication(CommunicationType.PLAYER_GET_INFO);
		
		com.writeString(event.getPlayer().getUniqueId().toString());
		
		try {
			com.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Profile p = Profile.createProfile(event.getPlayer());
		Rank r = Rank.findByPermission(p.permission);
		
		if (showPrefix) {
			event.getPlayer().setPlayerListName(r.displayName + " " + event.getPlayer().getName());
		} else {
			event.getPlayer().setPlayerListName(r.displayName.substring(0, 2) + event.getPlayer().getName());
		}
		
		event.getPlayer().setOp(r.useop);
	}
	
	@EventHandler
	public void _aO(PlayerJoinEvent event) {
		event.getPlayer().setScoreboard(board);
	}
	
	@EventHandler
	public void _b(PlayerQuitEvent event) {
		Profile.removeProfile(Profile.findByOwner(event.getPlayer()));
	}
	
	@EventHandler
	public void _b(AsyncPlayerChatEvent event) {
		Profile p = Profile.findByOwner(event.getPlayer());
		event.setFormat(Rank.findByPermission(p.permission).displayName + " " + event.getPlayer().getDisplayName() + "§7: §r" + event.getMessage());
	}
	
}
