package net.saltyfishstudios.bungee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.JsonObject;

import net.itsrelizc.bungee.commands.CommandAcceptInvite;
import net.itsrelizc.bungee.commands.Invite;
import net.itsrelizc.bungee.utils.Announcements;
import net.itsrelizc.copy.PlayerProfile;
import net.itsrelizc.copy.ProfileUtils;
import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.players.Grouping;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.ServerCollector;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ClientConnectEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.saltyfishstudios.global.Exceptions;

public class Main extends Plugin implements Listener {
	
	private HashMap<ProxiedPlayer, Long> playerwarptimeout;
	public ArrayList<String> serverLobby;
	public String maintence;
	public String maintencetime;
	
	public static Plugin plugin;

	@Override
    public void onEnable(){

		WarpUtils.init();
		
		plugin = this;
		
		getProxy().getPluginManager().registerListener(this, this);
		
		ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {

			@Override
			public void run() {
				Communication ping = new Communication(CommunicationType.BUNGEE_PING);
				ping.writeShort((short) getProxy().getPlayers().size());
				try {
					ping.sendMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}, 0, 500, TimeUnit.MILLISECONDS);
		
		Communication ping = new Communication(CommunicationType.BUNGEE_READY);
		try {
			ping.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProxyServer.getInstance().getPluginManager().registerListener(this, new Grouping());
		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Invite());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandAcceptInvite());
		
		Announcements.init();
    }
	
	@EventHandler
	public void log(PreLoginEvent event) {
		//BanUtils.checkLogin(event);
		boolean f = false;
		for (String n : WarpUtils.allConnectedNames) {
			if (n.equalsIgnoreCase(event.getConnection().getName())) {
				ProxyServer.getInstance().getPlayer(n).disconnect("§eSomebody else logged in!\n\n§bThere was another player with the same username as you!");
				f = true;
				break;
			}
		}
		if (f) {
			WarpUtils.allConnectedNames.add(event.getConnection().getName().toLowerCase());
		}
		
		
	}
	
	@EventHandler
	public void kick(ServerKickEvent event) {
		event.setCancelled(true);
		List<ServerInfo> s = WarpUtils.servers.get(ServerCategory.LOBBY_MAIN);
		ServerInfo d = ServerCollector.randomServerWithoutPlayer(s, event.getPlayer());
		if (d == null) {
			event.getPlayer().disconnect(event.getKickReason());
		} else {
			TextComponent a = new TextComponent("§cYou were kicked from the server that you were in, so we moved you to the Main Lobby! ");
			TextComponent b = new TextComponent("§7[More Information]");
			ChatUtils.attachHover(b, 
					"§7Kicked Reason: " + event.getKickReason().replaceAll("\n+", " ") + "\n" +
					"\n" +
					"§aIn case you want to know more, please §eclick this\n" +
					"§etext §aor visit §nhttps://www.itsrelizc.net/kicks");
			ChatUtils.attachLink(b, "https://www.itsrelizc.net/kicks");
			a.addExtra(b);
			ChatUtils.systemMessage(event.getPlayer(), "§b§lSERVER", a);
			event.setCancelServer(d);
		}
	}
	
	@EventHandler
	public void post(PostLoginEvent event) {
		
	}
	
	@EventHandler
	public void dis(PlayerDisconnectEvent event) {
		WarpUtils.allConnectedNames.remove(event.getPlayer().getName().toLowerCase());
	}
	
	@EventHandler
	public void conn(LoginEvent event) {
		String unique = event.getConnection().getUniqueId().toString();
		
		if (!BanUtils.checkLogin(event)) {
			Communication ping = new Communication(CommunicationType.BUNGEE_PLAYER_INFO);
			ping.writeByte((byte) 0x00);
			ping.writeString(event.getConnection().getName());
			ping.writeString(event.getConnection().getUniqueId().toString());
			try {
				ping.sendMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	@EventHandler
	public void conn(ServerConnectEvent event) {
		if (event.getPlayer().getServer() == null) {
			System.out.print(ProxyServer.getInstance().getPlayers().contains(event.getPlayer()));
			ServerInfo targ = WarpUtils.getRandomDestination(ServerCategory.LOBBY_MAIN);
			event.setTarget(targ);
		} else if (event.getPlayer().getServer().getInfo() == event.getTarget()) {
			event.setCancelled(true);
			ChatUtils.systemMessage(event.getPlayer(), "§a§lWARP", "§cCannot connect to §7[RS-" + event.getTarget().getName() + "]§c!");
			ChatUtils.systemMessage(event.getPlayer(), "§a§lWARP", "§cLooks like you are already on that server!");
		}
		System.out.print("Event destination: " + event.getTarget().getName() + " " + event.getTarget().getSocketAddress() + " from " + event.getPlayer().getServer());
		event.setTarget(event.getTarget());
	}
	
//	@EventHandler
//	public void postLogin(PostLoginEvent event) {
//		PlayerProfile.checkAccountExsistsThenCreate(event.getPlayer());
//		ProfileUtils.addPlayerProfile(new PlayerProfile(event.getPlayer()));
//	}
	
//	@EventHandler
//	public void disconnect(PlayerDisconnectEvent event) {
//		ProfileUtils.removePlayerProfile(ProfileUtils.getProfileByPlayer(event.getPlayer()));
//	}
	
	@EventHandler
	public void onPing(ProxyPingEvent event) {
		ServerPing ping = event.getResponse();
        ServerPing.Players player = ping.getPlayers();
        ServerPing.Protocol vers = ping.getVersion();
        
        ping.getPlayers().setMax(114514);
        ping.getModinfo().setType("VANILLA");
        
        Boolean wrong = false;
        String yourver = "Unknown";
        
        if (vers.getProtocol() >= 47 && vers.getProtocol() <= 758) {
        	vers.setProtocol(vers.getProtocol());
        } else {
        	if (vers.getProtocol() == 6) {
        		yourver = "1.7.10";
        	} else if (vers.getProtocol() == 5) {
        		yourver = "1.7.6 ~ 1.7.10";
        	} else if (vers.getProtocol() == 4) {
        		yourver = "1.7.2 ~ 1.7.5";
        	} else if (vers.getProtocol() == 47) {
        		yourver = "1.8 ~ 1.8.9";
        	} else if (vers.getProtocol() == 107) {
        		yourver = "1.9";
        	} else if (vers.getProtocol() == 108) {
        		yourver = "1.9.1";
        	} else if (vers.getProtocol() == 109) {
        		yourver = "1.9.2";
        	} else if (vers.getProtocol() == 110) {
        		yourver = "1.9.3 ~ 1.9.4";
        	} else if (vers.getProtocol() == 210) {
        		yourver = "1.10 ~ 1.10.2";
        	} else if (vers.getProtocol() == 315) {
        		yourver = "1.11";
        	} else if (vers.getProtocol() == 316) {
        		yourver = "1.11.1 ~ 1.11.2";
        	} else if (vers.getProtocol() == 335) {
        		yourver = "1.12";
        	} else if (vers.getProtocol() == 338) {
        		yourver = "1.12.1";
        	} else if (vers.getProtocol() == 340) {
        		yourver = "1.12.2";
        	} else if (vers.getProtocol() == 393) {
        		yourver = "1.13";
        	} else if (vers.getProtocol() == 401) {
        		yourver = "1.13.1";
        	} else if (vers.getProtocol() == 404) {
        		yourver = "1.13.2";
        	} else if (vers.getProtocol() == 477) {
        		yourver = "1.14";
        	} else if (vers.getProtocol() == 480) {
        		yourver = "1.14.1";
        	} else if (vers.getProtocol() == 485) {
        		yourver = "1.14.2";
        	} else {
        		yourver = "Some Snapshot Version";
        	}
        	vers.setProtocol(340);
        	wrong = true;
        }
        
        vers.setName("I'm on MC 1.8 - 1.19!");
        
        
        
        String[][] list = {{"I don't really like this.", "IM GETTING PISSED"}, {"Fuck roger"}, {"Please text me when I am busy!"}, {"Lets get some more players."}, {"qwertyuiopasdfghjklzxcvbnm"}};
        Random r = new Random();
        
        String[] pick = list[r.nextInt(list.length)];
        
        String l1 = pick[0];
        String l2 = null;
        if (pick.length == 2) {
        	l2 = pick[1];
        }
        
        ping.setDescription("     §r§7§kL§r §e§lMC.ITSRELIZC.NET §r§b| §r§b§lRELIZC NETWORK §r§7§kL§r\n        §d§lPREPARE OF THE ZOMBIE GAME");
        
        if (wrong) {
        	if (l2 != null) {
        		player.setSample(new ServerPing.PlayerInfo[] {
                        new ServerPing.PlayerInfo("        §6§lMC.SFN.GG §r§a(1.8 - 1.18)", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§nhttps://discord.gg/JfUbrRAnGw", UUID.randomUUID()),
                        new ServerPing.PlayerInfo(" ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a----------------------------------", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§6" + l1, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§6" + l2, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a---------------------------------- ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("  ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a§lLATEST NEWS", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§7§oThere are currently no news.", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("   ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lPlayers Online: §r§6" + getProxy().getPlayers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lServers Online: §r§6" + getProxy().getServers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("    ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lYour version: §r§6" + yourver, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("    ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§cThis server is build on Minecraft version", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§c1.8 to 1.18! You are currently playing on", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§can incompatible version of Minecraft.", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§cPlease change to a compatible version to", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§cplay SFN! Thank you!", UUID.randomUUID()),
                    });
        	} else {
        		player.setSample(new ServerPing.PlayerInfo[] {
                        new ServerPing.PlayerInfo("        §6§lMC.SFN.GG §r§a(1.8 - 1.18)", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§nhttps://discord.gg/JfUbrRAnGw", UUID.randomUUID()),
                        new ServerPing.PlayerInfo(" ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a----------------------------------", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§6" + l1, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a---------------------------------- ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("  ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a§lLATEST NEWS", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§7§oThere are currently no news.", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("   ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lPlayers Online: §r§6" + getProxy().getPlayers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lServers Online: §r§6" + getProxy().getServers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("    ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lYour version: §r§6" + yourver, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("    ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§cThis server is build on Minecraft version", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§c1.8 to 1.18! You are currently playing on", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§can incompatible version of Minecraft.", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§cPlease change to a compatible version to", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§cplay SFN! Thank you!", UUID.randomUUID()),
                    });
        	}
        } else {
        	if (l2 != null) {
        		player.setSample(new ServerPing.PlayerInfo[] {
                        new ServerPing.PlayerInfo("        §6§lMC.SFN.GG §r§a(1.8 - 1.18)", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§nhttps://discord.gg/JfUbrRAnGw", UUID.randomUUID()),
                        new ServerPing.PlayerInfo(" ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a----------------------------------", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§6" + l1, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§6" + l2, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a---------------------------------- ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("  ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a§lLATEST NEWS", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§7§oThere are currently no news.", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("   ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lPlayers Online: §r§6" + getProxy().getPlayers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lServers Online: §r§6" + getProxy().getServers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("    ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lYour version: §r§6" + yourver, UUID.randomUUID()),
                    });
        	} else {
        		player.setSample(new ServerPing.PlayerInfo[] {
                        new ServerPing.PlayerInfo("        §6§lMC.SFN.GG §r§a(1.8 - 1.18)", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§nhttps://discord.gg/JfUbrRAnGw", UUID.randomUUID()),
                        new ServerPing.PlayerInfo(" ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a----------------------------------", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§6" + l1, UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a---------------------------------- ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("  ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§a§lLATEST NEWS", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§7§oThere are currently no news.", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("   ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lPlayers Online: §r§6" + getProxy().getPlayers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lServers Online: §r§6" + getProxy().getServers().size(), UUID.randomUUID()),
                        new ServerPing.PlayerInfo("    ", UUID.randomUUID()),
                        new ServerPing.PlayerInfo("§b§lYour version: §r§6" + yourver, UUID.randomUUID()),
                    });
        	}
        }
        
        
	}
}
