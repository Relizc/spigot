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
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.JsonObject;

import net.itsrelizc.copy.PlayerProfile;
import net.itsrelizc.copy.ProfileUtils;
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
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.saltyfishstudios.bungee.commands.Lobby;
import net.saltyfishstudios.global.ChatUtils;
import net.saltyfishstudios.global.Exceptions;

public class Main extends Plugin implements Listener{
	
	private HashMap<ProxiedPlayer, Long> playerwarptimeout;
	private BungeeCordHTTPServer httpserver;
	public ArrayList<String> serverLobby;
	public String maintence;
	public String maintencetime;

	@Override
    public void onEnable(){
		getProxy().getPluginManager().registerListener(this, this);
		getProxy().registerChannel("NerdBungee");
		
		this.playerwarptimeout = new HashMap<ProxiedPlayer, Long>();
		
		try {
			this.httpserver = new BungeeCordHTTPServer(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.serverLobby = new ArrayList<String>();
		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Lobby());
    }
	
	@EventHandler
	public void postLogin(PostLoginEvent event) {
		PlayerProfile.checkAccountExsistsThenCreate(event.getPlayer());
		ProfileUtils.addPlayerProfile(new PlayerProfile(event.getPlayer()));
	}
	
	@EventHandler
	public void disconnect(PlayerDisconnectEvent event) {
		ProfileUtils.removePlayerProfile(ProfileUtils.getProfileByPlayer(event.getPlayer()));
	}
	
	public void sendCustomData(ProxiedPlayer player, String channelname, String[] data) {
		
		Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
	    if (networkPlayers == null || networkPlayers.isEmpty()) {
	        return;
	    }
	    
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF(channelname); // the channel could be whatever you want	
	    for (String s : data) {
	    	out.writeUTF(s);
	    }
	    
	    player.getServer().getInfo().sendData(channelname, out.toByteArray());
		
	}
	
	@EventHandler
    public void on(PluginMessageEvent event) {
		
        if (!event.getTag().equalsIgnoreCase("NerdBungee")) {
            return;
        }
        
        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

        String[] data = in.readUTF().split("@");
        
        
        String subChannel = data[0];
        
        
        if (subChannel.equalsIgnoreCase("connectToServer")) {
            if (event.getReceiver() instanceof ProxiedPlayer ) {
                ProxiedPlayer receiver = (ProxiedPlayer) event.getReceiver();
                if ((Instant.now().getEpochSecond() - this.playerwarptimeout.get((ProxiedPlayer) event.getReceiver())) < 10) {
                	if (data.length > 2) {
                		try {
                			System.out.print(data[2].equalsIgnoreCase("forcesend"));
                			if (!data[2].equalsIgnoreCase("forcesend")) {
                    			ChatUtils.systemMessage(receiver, "§a§lWARP", "§cUnable to warp you! §7§o" + String.valueOf(Exceptions.SERVER_WARP_THROTTLED));
                            	ChatUtils.systemMessage(receiver, "§a§lWARP", "§cYou are warping too fast! Slow down!");
                            	return;
                    		}
                		} catch (Exception e) {
                			System.out.print("forcesend false");
                			ChatUtils.systemMessage(receiver, "§a§lWARP", "§cUnable to warp you! §7§o" + String.valueOf(Exceptions.SERVER_WARP_THROTTLED));
                        	ChatUtils.systemMessage(receiver, "§a§lWARP", "§cYou are warping too fast! Slow down!");
                        	return;
                		}
                	}
                }
                ServerInfo info = ProxyServer.getInstance().getServerInfo(data[1]);
                try {
                	if (receiver.getServer().getInfo() == info) {
                		ChatUtils.systemMessage(receiver, "§a§lWARP", "§cYou are already on the server that you want to connect!");
                		return;
                	}
                	if (info.isRestricted()) {
                		ChatUtils.systemMessage(receiver, "§a§lWARP", "§cUnable to warp you! §7§o" + String.valueOf(Exceptions.SERVER_CONNECTION_REFUSED));
                    	ChatUtils.systemMessage(receiver, "§a§lWARP", "§cThe server is restricted. Only whitelisted players can join.");
                    	return;
                	}
                	this.playerwarptimeout.put((ProxiedPlayer) event.getReceiver(), Instant.now().getEpochSecond());
                	receiver.connect(info);
                	
                } catch (Exception error) {
                	Exceptions e = null;
                	if (error instanceof java.lang.NullPointerException) {
                		e = Exceptions.SERVER_NOT_FOUND;
                	} else {
                		e = Exceptions.UNKNOWN_DYNAMIC_ERROR;
                	}
                	ChatUtils.systemMessage(receiver, "§a§lWARP", "§cUnable to warp you! §7§o" + String.valueOf(e));
                	ChatUtils.systemMessage(receiver, "§a§lWARP", "§cCannot find the specified server!");
                	getLogger().warning("Failed to send player " + receiver.getDisplayName() + " with UUID " + receiver.getUUID() + " to server " + data[1] + "! Be aware of that!");
                	error.printStackTrace();
                	return;
                }
                
            }
            if (event.getReceiver() instanceof Server) {
                
            }
        }
        else if (subChannel.equalsIgnoreCase("kickPlayer")) {
            for (ProxiedPlayer player : getProxy().getPlayers()) {
                if (player.getName().equals(data[1])) {
                	player.disconnect(data[2]);
                }
            }
            if (event.getSender() instanceof ProxiedPlayer) {
            	((ProxiedPlayer) event.getSender()).sendMessage("§aSucessfully kicked player!");
            }
        }
    }
	
	@EventHandler
	public void onPing(ProxyPingEvent event) {
		ServerPing ping = event.getResponse();
        ServerPing.Players player = ping.getPlayers();
        ServerPing.Protocol vers = ping.getVersion();
        
        ping.getPlayers().setMax(114514);
        
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
        
        vers.setName("I'm on MC 1.8 - 1.18!");
        
        
        
        String[] list = {"The journey of a thousand miles\nbegins with one step.", "STOP BEING SO\nCRINGE", "Do you like yougurt?\nNO", "We definitely need\nmore players.", "Hypixel\nSuck"};
        Random r = new Random();
        
        String pick = list[r.nextInt(list.length)];
        
        String l1 = pick.split("\n")[0];
        String l2 = pick.split("\n")[1];
        
        ping.setDescription("      §r§7§kL§r §6§lMC.SFN.GG §r§b| §r§a§lSALTY FISH NETWORK §r§7§kL§r\n        §d§lPREPARE OF THE ZOMBIE GAME");
        
        if (wrong) {
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
        }
        
        
	}
}
