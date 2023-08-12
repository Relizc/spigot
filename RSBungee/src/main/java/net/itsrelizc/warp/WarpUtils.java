package net.itsrelizc.warp;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.saltyfishstudios.bungee.ChatUtils;

public class WarpUtils {
	
	public static Map<ServerCategory, List<ServerInfo>> servers = new HashMap<ServerCategory, List<ServerInfo>>();
	public static List<String> allConnectedNames = new ArrayList<String>();
	
	public static ServerInfo addServer(ServerCategory category, String ramId, String sid, int port) {
		InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", port);

	    ServerInfo info = ProxyServer.getInstance().constructServerInfo(ramId + sid, socketAddress, "Relizc Proxy Hehehe", false);
	    ProxyServer.getInstance().getServers().put(ramId + sid, info);
	    
	    if (servers.get(category) == null) {
		    servers.put(category, new ArrayList<ServerInfo>());
	    }
	    
	    servers.get(category).add(info);
	    
	    return info;
	}
	
	public static void removeServer(String ramId, String sid) {
		
		ServerInfo target = null;
		List<ServerInfo> n = null;
		
		ServerCategory b = null;
		int r = 0;
		
		boolean f = false;
		
		for (ServerCategory col : servers.keySet()) {
			
			for (ServerInfo inf : servers.get(col)) {
				if (inf.getName().equalsIgnoreCase(ramId + sid)) {
					target = inf;
					n = servers.get(col);
					f = true;
					break;
				}
			}
			if (f) {
				break;
			};
		}
		
		if (f) {
			n.remove(target);
		}
		
	}
	
	public static void connectTo(ProxiedPlayer player, String id) {
		ChatUtils.systemMessage(player, "§a§lWARP", "§7Queueing connection to [RS-" + id + "]...");
		ServerInfo target = ProxyServer.getInstance().getServerInfo(id);
		player.connect(target);
	}

	public static void init() {
		for (ServerCategory c : ServerCategory.values()) {
			servers.put(c, new ArrayList<ServerInfo>());
		}
	}
	
	public static ServerInfo getRandomDestination(ServerCategory cat) {
		ServerInfo col = servers.get(cat).get(new Random().nextInt(servers.get(cat).size()));
		return col;
	}

	public static void connectTo(ProxiedPlayer sender, ServerInfo target) {
		ChatUtils.systemMessage(sender, "§a§lWARP", "§7Queueing connection to [RS-" + target.getName() + "]...");
		sender.connect(target);
		
	}
	
}
