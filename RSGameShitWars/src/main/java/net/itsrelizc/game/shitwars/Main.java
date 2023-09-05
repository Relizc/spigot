package net.itsrelizc.game.shitwars;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.itsrelizc.gamebase.GameUtils;

public class Main extends JavaPlugin implements Listener {
	
	public static boolean competitive = false;
	
	@Override
	public void onEnable() {
		Boards.initBoards(this);
		Boards.initWaiting();
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Boards.startTickingWaitTimer(this);
		
		GameUtils.game = "§b§lSHITWARS";
		
		final Properties prop = new Properties();
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
		
		String t = prop.getProperty("type");
		
		competitive = t.startsWith("competitive");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void j(PlayerJoinEvent event) {
		Boards.waitingBoard.setAccessible(event.getPlayer());
	}
	
}
