package net.itsrelizc.game.bridge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.JSON;
import net.itsrelizc.nerdbot.OutboundDetection;
import net.itsrelizc.players.GameScoreboard;
import net.itsrelizc.players.Grouping;
import net.itsrelizc.players.NameChanger;
import net.itsrelizc.players.Spectator;
import net.itsrelizc.players.SpectatorRunnable;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class Main extends JavaPlugin implements Listener {
	
	private GameScoreboard board;
	
	private static int waiting = 10;
	
	private boolean counting = false;
	private int task = 0;
	
	private boolean game = false;
	
	public static Map<Player, GameScoreboard> boardmap = new HashMap<Player, GameScoreboard>();
	
	public static Map<Player, Integer> killmap = new HashMap<Player, Integer>();
	public static Map<Player, Integer> deathmap = new HashMap<Player, Integer>();
	
	public Team red;
	public Location redspawn;
	public int redwon = 0;
	
	public Team blue;
	public Location bluespawn;
	public int bluewon = 0;
	
	public static String mapname;
	
	public int roundtime = 61;

	private int timetick;

	protected int roundnum = 0;
	
	class ReviveTask implements SpectatorRunnable {

		@Override
		public void run(Player player) {
			if (teamContains(red, player)) {
				player.teleport(redspawn);
				armRed(player);
			} else {
				player.teleport(bluespawn);
				armBlue(player);
			}
		}
		
	}

	@Override
	public void onEnable() {
		this.board = new GameScoreboard("bridge");
		
		this.board.insertLine("Waiting for more players", 2);
		this.board.insertLine("to join the game!", 3);
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Grouping.showPrefix = false;
		
		JSONObject obj = JSON.pathLoadData(Bukkit.getWorld("world").getWorldFolder() + "\\worldmetadata.rsd");
		
		JSONArray r = (JSONArray) ((JSONArray) obj.get("position")).get(0);
		JSONArray b = (JSONArray) ((JSONArray) obj.get("position")).get(1);
		
		redspawn = new Location(Bukkit.getWorld("world"), (double) r.get(0), (double) r.get(1), (double) r.get(2), Float.parseFloat(String.valueOf(r.get(3))), Float.parseFloat(String.valueOf(r.get(4))));
		bluespawn = new Location(Bukkit.getWorld("world"), (double) b.get(0), (double) b.get(1), (double) b.get(2), Float.parseFloat(String.valueOf(b.get(3))), Float.parseFloat(String.valueOf(b.get(4))));
		
		red = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("red");
		red.setAllowFriendlyFire(false);
		blue = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("blue");
		blue.setAllowFriendlyFire(false);
		
		mapname = (String) ((JSONObject) obj.get("world")).get("display");
		
		Spectator.reviveTask = new ReviveTask();
		Spectator.boundChannelName = "§d§lBRIDGE";
		Spectator.startTicking();
		
		OutboundDetection.enableMe(this);
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		if (event.getTo().getY() < 52) {
			if (Spectator.spectators.contains(event.getPlayer())) {
				
			} else if (game) {
				damage(event.getPlayer(), null, Double.MAX_VALUE, DamageCause.VOID, null);
			} else {
				event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 66, 0.5, 90f, 0f));
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
			}
		}
		
		if (event.getTo().getBlock().getType() == Material.ENDER_PORTAL) {
			if (Spectator.spectators.contains(event.getPlayer())) {
				return;
			}
			if (!game) return;
			if (redspawn.distance(event.getTo()) <= 12) {
				if (teamContains(red, event.getPlayer())) {
					ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§cWhy did you jump into your own portal? LOL");
					event.getPlayer().teleport(event.getTo().add(0, -4d, 0));
				} else {
					newRound(1, event.getPlayer());
				}
				
			} else {
				if (teamContains(blue, event.getPlayer())) {
					ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§cWhy did you jump into your own portal? LOL");
					event.getPlayer().teleport(event.getTo().add(0, -4d, 0));
				} else {
					newRound(0, event.getPlayer());
				}
				
			}
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		this.board.setAccessible(event.getPlayer());
		event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 66, 0.5, 90f, 0f));
		
		if (Bukkit.getOnlinePlayers().size() >= 2 && !counting && !game) {
			
			ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§rThe game will start in §a60 seconds§r!");
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
			}
			
			this.board.editLine(2, "Starting in §a" + waiting + "s§r!");
			this.board.editLine(3, "§r§a§l§r§d");
			this.board.insertLine("Waiting for more players", 4);
			this.board.insertLine("to join the game!", 5);
			
			counting = true;
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					waiting -= 1;
					
					if (waiting == 0) {
						Bukkit.getScheduler().cancelTask(task);
						startGame();
						
						for (Player p : Bukkit.getOnlinePlayers()) {
							IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"§c§l" + "" + "\"}");
							PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
							
							IChatBaseComponent b = ChatSerializer.a("{\"text\": \"\"}");
							PacketPlayOutTitle b2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, b);
							
							PacketPlayOutTitle length = new PacketPlayOutTitle(0, 5, 0);


							((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(b2);
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
						}
						
						return;
					}
					
					
					board.editLine(2, "Starting in §a" + waiting + "s§r!");
					
					if (waiting == 30) {
						ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§rThe game will start in §230 seconds§r!");
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
						}
					}
					else if (waiting == 10) {ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§rThe game will start in §610 seconds§r!");
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
					}}
					else if (waiting <= 5) {
						ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§rThe game will start in §c" + waiting + " seconds§r!");
						for (Player p : Bukkit.getOnlinePlayers()) {
							IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"§c§l" + waiting + "\"}");
							PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
							
							IChatBaseComponent b = ChatSerializer.a("{\"text\": \"§eTime to start!\"}");
							PacketPlayOutTitle b2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, b);
							
							PacketPlayOutTitle length = new PacketPlayOutTitle(0, 200, 0);


							((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(b2);
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
							
							p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
						}
					}
				}
				
			}, 20L, 20L);
		}
		
		if (game) {
			ChatUtils.broadcastSystemMessage("§d§lBRIDGE", event.getPlayer().getPlayerListName() + "§e reconnected!");
			damage(event.getPlayer(), null, Double.MAX_VALUE, DamageCause.CUSTOM, null);
		}
	}
	
	@EventHandler
	public void leave(PlayerQuitEvent event) {
		if (Bukkit.getOnlinePlayers().size() - 1 < 2 && counting && !game) {
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.WOLF_WHINE, 1f, 1f);
			}
			
			this.board.editLine(2, "Waiting for more players");
			this.board.editLine(3, "to join the game!");
			
			this.board.removeLine(4);
			this.board.removeLine(4);
			
			counting = false;
			waiting = 60;
			Bukkit.getScheduler().cancelTask(task);
		}
		if (game) {
			ChatUtils.broadcastSystemMessage("§d§lBRIDGE", event.getPlayer().getPlayerListName() + "§e disconnected!");
		}
	}
	
	@EventHandler
	public void damage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		if (event.getCause() == DamageCause.ENTITY_ATTACK) return;
		if (event.getCause() == DamageCause.FALL) event.setCancelled(true);
		if (game) {
			damage((Player) event.getEntity(), null, event.getDamage(), event.getCause(), event);
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		if (!(event.getDamager() instanceof Player)) return;
		if (game) {
			damage((Player) event.getEntity(), (Player) event.getDamager(), event.getDamage(), event.getCause(), event);
		} else {
			event.setCancelled(true);
		}
	}
	
	public void damage(Player player, Player damager, double damage, DamageCause cause, Cancellable event) {
		if (damage >= player.getHealth()) {
			Spectator.makeSpectator(player, 5);
			
			int f = deathmap.get(player) + 1;
			
			player.getWorld().strikeLightningEffect(player.getLocation());
			
			deathmap.put(player, f);
			
			if (damager != null) {
				int f1 = killmap.get(damager) + 1;
				killmap.put(damager, f1);
			}
			
			GameScoreboard b = boardmap.get(player);
			b.editLine(8, "Kills: §a" + killmap.get(player) + " §8|§r Deaths: §c" + deathmap.get(player));
			
			if (damager != null) {
				GameScoreboard c = boardmap.get(damager);
				c.editLine(8, "Kills: §a" + killmap.get(damager) + " §8|§r Deaths: §c" + deathmap.get(damager));
			}
			
			if (damager != null) {
				ChatUtils.broadcastSystemMessage("§d§lBRIDGE", player.getPlayerListName() + " §ewas rickrolled by " + damager.getPlayerListName());
				event.setCancelled(true);
			} else {
				if (cause == DamageCause.VOID) {
					ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§eThe void fucked " + player.getPlayerListName() + "§e hardly.");
				} else if (cause == DamageCause.CUSTOM) {
					
				} else {
					ChatUtils.broadcastSystemMessage("§d§lBRIDGE", player.getPlayerListName() + " §esomehow died.");
					event.setCancelled(true);
				}
			}
			
		}
	}
	
	public void joinEvenly() {
		List<Player> players = new ArrayList<Player>();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			players.add(p);
			deathmap.put(p, 0);
			killmap.put(p, 0);
		}
		
		Collections.shuffle(players);
		
		int q = 0;
		
		for (int i = 0; i < Math.floor(players.size() / 2.0); i ++) {
			red.addPlayer(players.get(i));
			NameChanger.changeName(players.get(i), "§c" + players.get(i).getDisplayName());
			players.get(i).setPlayerListName("§c" + players.get(i).getDisplayName());
			q = i;
		}
		
		for (int i = q + 1; i < players.size(); i ++) {
			blue.addPlayer(players.get(i));
			NameChanger.changeName(players.get(i), "§9" + players.get(i).getDisplayName());
			players.get(i).setPlayerListName("§9" + players.get(i).getDisplayName());
		}
	}
	
	public void armRed(Player player) {
		player.getInventory().setHelmet(Items.getRedCap());
		player.getInventory().setChestplate(Items.getRedChestplate());
		player.getInventory().setLeggings(Items.getRedLegs());
		player.getInventory().setBoots(Items.getRedBoots());
		
		player.getInventory().setItem(0, Items.getIronSword());
		player.getInventory().setItem(1, Items.getDiaPick());
		player.getInventory().setItem(2, Items.getRedClay());
		player.getInventory().setItem(3, Items.getRedClay());
		player.getInventory().setItem(4, Items.getBow());
	}
	
	public void armBlue(Player player) {
		player.getInventory().setHelmet(Items.getBlueCap());
		player.getInventory().setChestplate(Items.getBlueChestplate());
		player.getInventory().setLeggings(Items.getBlueLegs());
		player.getInventory().setBoots(Items.getBlueBoots());
		
		player.getInventory().setItem(0, Items.getIronSword());
		player.getInventory().setItem(1, Items.getDiaPick());
		player.getInventory().setItem(2, Items.getBlueClay());
		player.getInventory().setItem(3, Items.getBlueClay());
		player.getInventory().setItem(4, Items.getBow());
	}
	
	public static boolean teamContains(Team team, Player player) {
		for (OfflinePlayer p : team.getPlayers()) {
			if (p.getName().equalsIgnoreCase(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public void newRound() {
		roundnum ++;
		game = true;
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.getInventory().clear();
			if (teamContains(red, player)) {
				armRed(player);
				player.teleport(redspawn);
			} else {
				armBlue(player);
				player.teleport(bluespawn);
			}
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1.414214f);
		}
		
		ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "Round " + roundnum + " begins now!");
		
		roundtime = 300;
		
		timetick = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public String roundcolor = "§a";

			@Override
			public void run() {
				roundtime -= 1;
				
				if (roundtime == 60) {
					ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "This round will become a draw in §e60 seconds§r!");
					roundcolor = "§e";
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 0.529732f);
					}
				} else if (roundtime == 30) {
					ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "This round will become a draw in §630 seconds§r!");
					roundcolor = "§6";
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 0.529732f);
					}
				} else if (roundtime <= 10) {
					if (roundtime % 2 == 0) {
						roundcolor = "§c";
					} else {
						roundcolor = "§r";
					}
					if (roundtime == 0) {
						newRound(2, null);
					} else {
						ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "This round will become a draw in §c" + roundtime + " seconds§r!");
					}
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 0.529732f);
					}
				}
				
				int mins = roundtime / 60;
				int secs = roundtime % 60;
				
				DecimalFormat formatter = new DecimalFormat("00");
				
				for (Player p : boardmap.keySet()) {
					GameScoreboard board = boardmap.get(p);
					board.editLine(3, "Time Left: " + roundcolor + formatter.format(mins) + ":" + formatter.format(secs));
				}
			}
			
		}, 20L, 20L);
	}
	 
	public void newRound(int type, Player who) {
		// 0 = red
		// 1 = blue
		// 2 = draw
		Bukkit.getScheduler().cancelTask(timetick);
		game = false;
		
		
		if (type == 0) {
			ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§cRed Team§r won Round " + roundnum + "!");
			ChatUtils.broadcastTitle("§cRed Team §rwon!", who.getPlayerListName() + "§e scored the point!", 10, 100, 0);
			redwon ++;
		} else if (type == 1) {
			ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "§9Blue Team§r won Round " + roundnum + "!");
			ChatUtils.broadcastTitle("§9Blue Team §rwon!", who.getPlayerListName() + "§e scored the point!", 10, 100, 0);
			bluewon ++;
		} else if (type == 2) {
			ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "Round " + roundnum + " is a §aDRAW§r!");
			ChatUtils.broadcastTitle("It's a §aDraw§r!", "§eNobody scored the point!", 10, 100, 0);
			
		}
		
		String[] buttons = {"➀", "➁", "➂"};
		
		for (Player player : boardmap.keySet()) {
			GameScoreboard board = boardmap.get(player);
			
			String j = "";
			String k = "";
			
			if (teamContains(red, player)) j = " (Your Team)";
			else k = " (Your Team)";
			
			String red = "§cR ";
			String blue = "§9B ";
			for (int i = 0; i < 3; i ++) {
				if (i == redwon) red += "§7";
				if (i == bluewon) blue += "§7";
				
				red += buttons[i];
				blue += buttons[i];
			}
			
			board.editLine(3, "Time Left: §a05:00");
			board.editLine(5, red + j);
			board.editLine(6, blue + k);
		}
		
		if (who != null) {
			who.getWorld().strikeLightningEffect(who.getLocation());
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 0f);
			}
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.WOLF_WHINE, 1f, 1f);
			}
		}
		
		
		
		
		ChatUtils.broadcastSystemMessage("§d§lBRIDGE", "Round " + (roundnum + 1) + " will start in §a5 seconds§r!");
		Bukkit.getScheduler().cancelTask(timetick);
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {

			@Override
			public void run() {
				newRound();
			}
			
		}, 100L);
	}
	
	public void startGame() {
		game = true;
		joinEvenly();
		
		
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			GameScoreboard b = new GameScoreboard("bridge");
			b.setAccessible(player);
			boardmap.put(player, b);
			
			killmap.put(player, 0);
			deathmap.put(player, 0);
			
			b.insertLine("Map: §e" + mapname, 2);
			b.insertLine("Time Left: §a05:00", 3);
			this.board.addSpaceLine(4);
			
			String j = "";
			String k = "";
			
			if (teamContains(red, player)) j = "(Your Team)";
			else k = "(Your Team)";
			
			b.insertLine("§cR §7➀➁➂ " + j, 5);
			b.insertLine("§9B §7➀➁➂ " + k, 6);
			b.addSpaceLine(7);
			b.insertLine("Kills: §a0 §8|§r Deaths: §c0", 8);
			b.addSpaceLine(9);
		}
		
		
		
		newRound();
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
		}
	}
	
}
