package net.itsrelizc.watcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.itsrelizc.checks.ChatFilter;
import net.itsrelizc.checks.ClickGap;
import net.itsrelizc.checks.KillAura;
import net.itsrelizc.checks.Move;
import net.itsrelizc.checks.Reach;
import net.itsrelizc.global.BanUtils;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.Global;
import net.itsrelizc.global.JSON;


public class Main extends JavaPlugin implements Listener, PluginMessageListener{

	@Override	
	public void onEnable() {
	
		getLogger().info("Patrol Initlized!");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClickGap(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Reach(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new KillAura(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChatFilter(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Move(), this);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			boolean f = false;
			
			for (Entry<Player, Profile> profile : Profile.profilemap.entrySet()) {
				if (profile.getKey() == p) {
					f = true;
					break;
				}
			}
			
			if (!f) {
				Profile profile = new Profile(p);
			}
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Profile p : Profile.profilemap.values()) {
					p.addSus(-0.1);
					if (p.getSus() < 0) {
						p.setSus(0);
					}
				}
				
			}}, 0L, 600L);
		
//		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.POSITION_LOOK) {
//	            @Override
//	            public void onPacketReceiving(PacketEvent event) {
//	            	Bukkit.broadcastMessage(event.getPacket().getFloat().getValues().toString());
////	                if (event.getPacketType() == PacketType.Play.Client.POSITION) {
////	                	Bukkit.broadcastMessage(event.getPacket().getShorts().toString());
////	                }
//	            }
//        });
//		
//		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
//            @Override
//            public void onPacketReceiving(PacketEvent event) {
//            	Bukkit.broadcastMessage(event.getPacket().getEnumEntityUseActions().getValues().get(0).getAction().toString());
////                if (event.getPacketType() == PacketType.Play.Client.POSITION) {
////                	Bukkit.broadcastMessage(event.getPacket().getShorts().toString());
////                }
//            }
//    });
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event){
//		
//		String filter = BanUtils.joinFilter(event.getPlayer());
//		
//		if (filter != null) {
//			event.disallow(Result.KICK_BANNED, filter);
//		}
		
		boolean f = false;
		
		for (Entry<Player, Profile> profile : Profile.profilemap.entrySet()) {
			if (profile.getKey() == event.getPlayer()) {
				f = true;
				break;
			}
		}
		
		if (!f) {
			Profile profile = new Profile(event.getPlayer());
		}
			
	}

	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		System.out.print(arg2);
	}
}
