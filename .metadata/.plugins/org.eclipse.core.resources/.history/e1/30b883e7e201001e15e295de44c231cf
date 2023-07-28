package net.itsrelizc.global;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;



public class Global implements PluginMessageListener, Listener{
	
	private static JavaPlugin plugin;
	public static List<String> playerList;
	private static Map<Long, String> rankcolormap;
	private static String serverCode = null;
	private static Map<Player, Objective> boardmaps;
	private static Team spectators;
	private static List<String> allowedPlayers;
	
	public static JavaPlugin getPlugin() {
        return plugin;
    }

	private static Boolean setRecorded = false;
	private static String plusServerCode;
	private static String identificalServerCode;
	
	public static void setRecorded(Boolean bool) {
		setRecorded = bool;
	}
	
	public static Boolean isRecorded() {
		return setRecorded;
	}
	
	public static String getPlayerRankPrefix(Long id) {
		if (id == 0) {
			return "§a";
		} else
		if (id == 1) {
			return "§c[OWNER] ";
		} else
		if (id == 2) {
			return "§c[MOD] ";
		} else
		if (id == 3) {
			return "§e[VIP] ";
		} else
		if (id == 4) {
			return "§e[VIP§a+§e] ";
		} else
		if (id == 5) {
			return "§b[MVP] ";
		} else
		if (id == 6) {
			return "§b[MVP§a+§b] ";
		} else
		if (id == 7) {
			return "§d[SF§b+§d] ";
		}
		return "§r";
	}
	
	public static List<Integer> urlScan(String url) {
		// https://stackoverflow.com/questions/5713558/detect-and-extract-url-from-a-string
		Pattern urlPattern = Pattern.compile(
		        "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
		                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
		                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
		        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = urlPattern.matcher(url);
		Integer matchStart = null;
		Integer matchEnd = null;
		while (matcher.find()) {
			matchStart = matcher.start(1);
			matchEnd = matcher.end();
		}
		List<Integer> l = new ArrayList<Integer>();
		l.add(matchStart);
		l.add(matchEnd);
		return l;
	}
	
	@Deprecated
	public static void createScoreBoard(String gamename, Player player, List<String> lines) { 
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
		org.bukkit.scoreboard.Objective objective = board.registerNewObjective("gg." + Global.randomString(5), "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("§a§l" + gamename.toUpperCase() + "");
		
		//serverinfo
		
		int count = 1;
		org.bukkit.scoreboard.Score score1 = objective.getScore("§6§lmc.sfn.gg");
		score1.setScore(1);
		player.setScoreboard(board);
		
		Collections.reverse(lines);
		
		for (String s : lines) {
			count += 1;
			org.bukkit.scoreboard.Score scorewhatever = objective.getScore(s);
			scorewhatever.setScore(count);
		}
		
		
		org.bukkit.scoreboard.Score score = objective.getScore("§8 [SFN-" + Global.getServerCode() + "] §71.0.0");
		score.setScore(count + 1);
		
		
		Global.boardmaps.put(player, objective);
	}
	
	public static String rangeString(String string, Integer start, Integer end) {
		String genetic = "";
		for (int i = start; i < end; i ++) {
			genetic += string.charAt(i);
		}
		return genetic;
	}
	
	public static int portByServername(String name) {
		JSONObject allserver = Global.JSONloadData("servers.json");
		boolean found = false;
		int port = -1;
		for (Object obj : allserver.keySet()) {
			JSONObject thisdata = (JSONObject) ((allserver).get(obj.toString()));
			if (thisdata.get("id") == null) {
				continue;
			}
			if (thisdata.get("id").toString().equals(name)) {
				port = Integer.parseInt(obj.toString());
			}
		}
		return port;
	}
	
	public static String IDByServerport(int port) {
		JSONObject allserver = Global.JSONloadData("servers.json");
		return (String) ((JSONObject) allserver.get(String.valueOf(port))).get("id");
	}
	
	public static void initlize() {
		Global.rankcolormap = new HashMap<Long, String>();
		Global.rankcolormap.put((long) 0, "§a");
		Global.rankcolormap.put((long) 1, "§c");
		Global.rankcolormap.put((long) 2, "§c");
		Global.rankcolormap.put((long) 3, "§e");
		Global.rankcolormap.put((long) 4, "§e");
		Global.rankcolormap.put((long) 5, "§b");
		Global.rankcolormap.put((long) 6, "§b");
		Global.rankcolormap.put((long) 7, "§d");
		
		if (Global.serverCode == null) {
			Global.serverCode = randomString(5);
			
			Double ram = Runtime.getRuntime().maxMemory() / 1048576.0;
			if (ram >= 2048) {
				Global.plusServerCode = "G";
			} else if (ram >= 1024 && ram < 2048) {
				Global.plusServerCode = "B";
			} else if (ram >= 512 && ram < 1024) {
				Global.plusServerCode = "M";
			} else if (ram >= 256 && ram < 512) {
				Global.plusServerCode = "S";
			} else {
				Global.plusServerCode = "T";
			}
			Global.identificalServerCode = Global.plusServerCode + Global.serverCode;
			Bukkit.getLogger().warning("SERVER CODE HAS BEEN SET! THE SERVER CODE WILL NO LONGER BE GENERATED");
		} else {
			Bukkit.getLogger().warning("SERVER CODE SET IGNORED!");
		}
		
		Global.allowedPlayers = new ArrayList<String>();
		
		Global.boardmaps = new HashMap<Player, Objective>();
		
		ScoreboardManager sm = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard scoreboard = sm.getNewScoreboard();
	}
	
	public static boolean playerInAllowedList(String name) {
		return Global.allowedPlayers.contains(name);
	}
	
	public static List<String> getAllowedList() {
		return Global.allowedPlayers;
	}
	
	public static void makeSpectator(Player player) {
		Global.spectators.addPlayer(player);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(player);
		}
		
		for (OfflinePlayer p : Global.spectators.getPlayers()) {
			player.showPlayer(p.getPlayer());
			p.getPlayer().showPlayer(player);
		}
	
		player.setHealth(20);
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255), true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255), true);
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		player.setFlying(true);
		
		//QUIT GAME
		ItemStack quitgame = new ItemStack(Material.REDSTONE);
		ItemMeta quitgamemeta = quitgame.getItemMeta();
		quitgamemeta.setDisplayName("§cLeave Game! §6§lCLICK!");
		quitgame.setItemMeta(quitgamemeta);
		player.getInventory().setItem(0, quitgame);
		
		//PLAY AGAIN
		ItemStack playagain = new ItemStack(Material.WATCH);
		ItemMeta playagainmeta = playagain.getItemMeta();
		playagainmeta.setDisplayName("§aPlay Again! §6§lCLICK!");
		playagain.setItemMeta(playagainmeta);
		player.getInventory().setItem(1, playagain);
		
		//SPECTATOR SETTINGS
		ItemStack specsets = new ItemStack(Material.DIODE);
		ItemMeta specsetsmeta = specsets.getItemMeta();
		specsetsmeta.setDisplayName("§eSpectator Menu! §6§lCLICK!");
		specsets.setItemMeta(specsetsmeta);
		player.getInventory().setItem(4, specsets);
		
		//PLAYER TP
		ItemStack playertp = new ItemStack(Material.COMPASS);
		ItemMeta playertpmeta = playertp.getItemMeta();
		playertpmeta.setDisplayName("§dPlayer Teleporter! §6§lCLICK!");
		playertp.setItemMeta(playertpmeta);
		player.getInventory().setItem(8, playertp);
	}
	
	public static String getPlayerPrefixColor(Player player) {
		JSONObject json = (JSONObject) Global.JSONloadData("players.json").get(player.getUniqueId().toString());
		long inte = (long) json.get("rank");
		return Global.rankcolormap.get(inte);
	}
	
	public static void noSpectator(Player player) {
		Global.spectators.removePlayer(player);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.showPlayer(player);
		}
	
		player.setHealth(20);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(false);
		player.setFlying(false);
	}
	
	public static List<Player> getSpectators() {
		List<Player> spec = new ArrayList<Player>();
		for (OfflinePlayer p : Global.spectators.getPlayers()) {
			if (p.getPlayer() instanceof Player) {
				spec.add(p.getPlayer());
			}
		}
		return spec;
	}
	
	public static String getServerCode() {
		return Global.identificalServerCode;
	}
	
	public static String getGeneticServerCode() {
		return Global.serverCode;
	}
	
	public static String getServerCodeType() {
		return Global.plusServerCode;
	}
	
	public static String getJoinMessagePrefix(Long id) {
		return Global.rankcolormap.get(id);
	}
	
	public static void connectTo(Player player, String servername) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("connectToServer@" + servername);

		player.sendPluginMessage(plugin, "NerdBungee", out.toByteArray());
	}
	
	public static void connectToForce(Player player, String servername) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("connectToServer@" + servername + "@forcesend");

		player.sendPluginMessage(plugin, "NerdBungee", out.toByteArray());
	}

	public static String givenUsingApache_whenGeneratingRandomAlphanumericString_thenCorrect() {
	    String generatedString = RandomStringUtils.randomAlphanumeric(7);

	    return generatedString;
	}
	
	public static String randomString(Integer length) {
	    String generatedString = RandomStringUtils.randomAlphanumeric(length);

	    return generatedString;
	}
	public static void createHologram(Location location, String text) {
		ArmorStand stand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(location, EntityType.ARMOR_STAND);
		stand.setVisible(false);
		stand.setGravity(false);
		stand.setCustomNameVisible(true);
		stand.setCustomName(text);
	}
	
	public static void createArmorStand(Location location, String name) {
		ArmorStand stand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(location, EntityType.ARMOR_STAND);
		stand.setVisible(false);
		stand.setGravity(false);
		stand.setCustomNameVisible(false);
		stand.setCustomName(name);
	}
	
	
	public static Integer crossServerMessage(Player player, String playername, String content) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Message");
		
		//check if user is online
		boolean found = false;
		boolean isonline = false;
		JSONObject thisobj = null;
		
		JSONObject players = Global.JSONloadData("players.json");
		for (Object obj: players.keySet()) {
			if (obj instanceof String) {
				thisobj = (JSONObject) players.get(obj);
				if (thisobj.get("lname").toString().equalsIgnoreCase(playername.toLowerCase().toString())) {
					found = true;
					isonline = (Boolean) thisobj.get("online");
				}
			}
		}
		
		if (!found) {
			return 1;
		}
		
		if (!isonline) {
			return 2;
		}
		
		out.writeUTF(playername);
		out.writeUTF("§bA whisper from §r§a" + player.getDisplayName() + "§r§b: §r§7" + content);
		
		player.sendMessage("§bA whisper to §r§a" + thisobj.get("name") + "§r§b: §r§7" + content);

		player.sendPluginMessage(plugin, "NerdBungee", out.toByteArray());
		return 0;
	}
	
	public static Integer crossServerMessageRaw(String playername, String content) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("MessageRaw");
		
		//check if user is online
		boolean found = false;
		boolean isonline = false;
		JSONObject thisobj = null;
		
		JSONObject players = Global.JSONloadData("players.json");
		for (Object obj: players.keySet()) {
			if (obj instanceof String) {
				thisobj = (JSONObject) players.get(obj);
				if (thisobj.get("lname").toString().equalsIgnoreCase(playername.toLowerCase().toString())) {
					found = true;
					isonline = (Boolean) thisobj.get("online");
				}
			}
		}
		
		if (!found) {
			return 1;
		}
		
		if (!isonline) {
			return 2;
		}
		
		out.writeUTF(playername);
		out.writeUTF(content);
		
		Bukkit.getServer().sendPluginMessage(plugin, "NerdBungee", out.toByteArray());
		return 0;
	}
	
	
	public static void setPlugin(final JavaPlugin plugin) {
		Global.plugin = plugin;
    }
	
	public static void sendMsg(Player player, String msg) {
		player.sendMessage(msg);
	}
	
	public static void setRank(Player player, Integer value) {
		Map<Integer, String> ranknames = new HashMap<Integer, String>();
		ranknames.put(0, "§7DEFAULT");
		ranknames.put(1, "§bOWNER");
		ranknames.put(2, "§eMODERATOR");
		JSONObject file = Global.JSONloadData("players.json");
		JSONObject playerdat = (JSONObject) file.get(player.getUniqueId().toString());
		playerdat.put("rank", value);
		file.put(player.getUniqueId().toString(), playerdat);
		Global.JSONsavedata(file, "players.json");
		player.sendMessage("§bHello §a" + player.getDisplayName() + "§b, your rank upgrade (" + ranknames.get(value) + "§b) had been sucessfully redeemed! You will need to rejoin this lobby/server to access the rank's perks! Thanks for supporting Salty Fish Network!");
	}
	
	public static boolean mutePlayer(Player player, String reason) {
		if (reason == null) {
			JSONObject set = new JSONObject();
			set.put("reason", "Muted by an operator with no reasons provided.");
			set.put("id", Global.randomString(6));
			
			JSONObject bef = Global.JSONloadData("muted-players.json");
			bef.put(player.getUniqueId().toString(), set);
			Global.JSONsavedata(bef, "muted-players.json"); 
		} else {
			JSONObject set = new JSONObject();
			set.put("reason", reason);
			set.put("id", Global.randomString(6));
			
			JSONObject bef = Global.JSONloadData("muted-players.json");
			bef.put(player.getUniqueId().toString(), set);
			Global.JSONsavedata(bef, "muted-players.json");
		}
		return true;
	}
	
	public static boolean checkGlobalCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("message")) {
			if (args.length < 2) {
				return false;
			}
			List<String> a = Arrays.asList(args);
			List<String> b = a.subList(1, a.size());
					Integer response = Global.crossServerMessage((Player) sender, args[0], String.join(" ", b));
					if (response == 1) {
						((Player) sender).sendMessage("§cPlayer does not exsist!");
					}
					if (response == 2) {
						((Player) sender).sendMessage("§cPlayer is not online!");
					}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("rank")) {
			if (args.length < 2) {
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage("§cCannot find player!");
				return true;
			}
			Global.setRank(target, Integer.parseInt(args[1]));
			sender.sendMessage("§aUpgraded " + target.getDisplayName() + " (§b" + target.getUniqueId().toString() + "§a)'s rank to §b" + args[1]);
			
		}
		return false;
	}
	
	public static JSONObject JSONloadData(String filename) {
		JSONParser parser = new JSONParser();
		try {
			Object a = parser.parse(new FileReader("d:\\mcs\\data\\" + filename));
			JSONObject jsobj =  (JSONObject) a;
			
			return jsobj;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void JSONsavedata(JSONObject object, String filename) {
		FileWriter file;
		try {
			file = new FileWriter("d:\\mcs\\data\\" + filename);
			file.write(object.toJSONString());
	        file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		System.out.print("RECIEVED2");
		if (!channel.equals("BungeeCord")) {
		      return;
		    }
		    ByteArrayDataInput in = ByteStreams.newDataInput(message);
		    String subchannel = in.readUTF();
		    Global.playerList = Arrays.asList(subchannel.split(", "));
		    System.out.print(Global.playerList);
		
	}

	public static void crossServerKickPlayer(String uuid, String reason) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("kickPlayer@" + uuid + "@" + reason);
		
		Bukkit.getServer().sendPluginMessage(plugin, "NerdBungee", out.toByteArray());
	}

	public static void notifyRecording(Player player) {
		if (Global.isRecorded()) {
			ChatUtils.systemMessage(player, "§9§lRECORDING", "§eThis game's chat, events, and movements will be recorded! §6[Privacy Policy]");
		}
	}
	
	
	
	
	
}
