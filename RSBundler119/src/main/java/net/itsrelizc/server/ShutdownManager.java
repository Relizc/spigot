package net.itsrelizc.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.Me;
import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.players.GameScoreboard;
import net.itsrelizc.players.TablistUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class ShutdownManager {
	
	public static int timeleft = -1;
	public static ShutdownType type = null;
	
	public enum ShutdownType {
		
		OTHER("Other Reason", 120),
		UPDATE("Game Update", 30),
		SCHEDULED("Scheduled Shutdown", 30),
		EMERGENCY("Emergency Shutdown", 30);
		
		public String displayName;
		public int tolerantTime;
		
		private ShutdownType(String displayName, int tolerantTime) {
			this.displayName = displayName;
			this.tolerantTime = tolerantTime;
		}
	}
	
	public static void shutdown(ShutdownType other) {
		timeleft = other.tolerantTime;
		
		type = other;
		
		InfoChanger.changeStatus("Closing: " + type.displayName + " (" + type.tolerantTime + ")");
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			ChatUtils.sendTitle(player, "§6§lUNIVERSE COLLAPSING!", "§eCollapsing in §b" + timeleft + "§e seconds!", 5, 60, 5);
		}
		
		ChatUtils.broadcastSystemMessage("§b§lSERVER", "§6Universe Collapsing! §7(" + type.displayName + ")");
		ChatUtils.broadcastSystemMessage("§b§lSERVER", "§aWe have §e" + type.tolerantTime + "§a seconds left for us to leave!");
		TextComponent a = new TextComponent("§aClick ");
		TextComponent b = new TextComponent("§e[Here]§r ");
		ChatUtils.attachCommand(b, "escape", null);
		a.addExtra(b);
		TextComponent c = new TextComponent("§aor run §e/escape§a to escape from this universe!");
		a.addExtra(c);
		
		ChatUtils.broadcastSystemMessage("§b§lSERVER", a);
		
		startTicking();
	}
	
	public static void startTicking() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Me.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (timeleft == 5) {
					
				} else if (timeleft == 0) {
					
				}
				for (GameScoreboard b : GameScoreboard.scoreboards) {
					b.editLine(0, "§7[RS-" + Me.getStaticCode() + "] §6Collapsing in " + timeleft + "s");
				}
				TablistUtils.additional = "\n§6Collapsing in " + timeleft + " seconds!\n";
				for (Player player : Bukkit.getOnlinePlayers()) {
					TablistUtils.update(player);
				}
				timeleft --;
			}
			
		}, 0L, 20L);
	}
	
	public static void finalShutdown() {
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer("§cServer is closing!");
		}
		
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("server.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Communication ok = new Communication(CommunicationType.CLOSING_SHUTDOWN_OK);
		byte b = 0x00;
		if (prop.getProperty("rid").equalsIgnoreCase("t")) {
			b = 0x00;
		} else if (prop.getProperty("rid").equalsIgnoreCase("s")) {
			b = 0x01;
		} else if (prop.getProperty("rid").equalsIgnoreCase("m")) {
			b = 0x02;
		} else if (prop.getProperty("rid").equalsIgnoreCase("b")) {
			b = 0x03;
		} else if (prop.getProperty("rid").equalsIgnoreCase("g")) {
			b = 0x04;
		}
		ok.writeByte(b);
		ok.writeString(prop.getProperty("sid"));
		try {
			ok.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bukkit.getServer().shutdown();
	}
	
}
