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
import net.saltyfishstudios.bungee.ChatUtils;

public class WarpUtils {
	
	public static Map<ServerCategory, ServerCollector> servers = new HashMap<ServerCategory, ServerCollector>();
	public static List<String> allConnectedNames = new ArrayList<String>();
	
	public static ServerInfo addServer(ServerCategory category, String ramId, String sid, int port) {
		InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", port);

	    ServerInfo info = ProxyServer.getInstance().constructServerInfo(ramId + sid, socketAddress, "Relizc Proxy Hehehe", false);
	    ProxyServer.getInstance().getServers().put(ramId + sid, info);
	    
	    if (servers.get(category) == null) {
	    	servers.put(category, new ServerCollector());
	    }
	    
	    servers.get(category).addServer(info);
	    
	    return info;
	}
	
	public static void removeServer(String ramId, String sid) {
		ServerInfo target = null;
		ServerCollector c = null;
		for (ServerCollector col : servers.values()) {
			boolean f = false;
			for (ServerInfo inf : col.servers) {
				if (inf.getName().equalsIgnoreCase(ramId + sid)) {
					target = inf;
					f = true;
					break;
				}
			}
			if (f) {
				c = col;
			};
		}
		removeServer(c, target);
	}
	
	public static void removeServer(ServerCollector col, ServerInfo targ) {
		col.removeServer(targ);
	}
	
	public static void connectTo(ProxiedPlayer player, String id) {
		ChatUtils.systemMessage(player, "§a§lWARP", "§7Queueing connection to [RS-" + id + "]...");
		ServerInfo target = ProxyServer.getInstance().getServerInfo(id);
		player.connect(target);
	}
	
	public static ServerInfo getRandomDestination(ServerCategory cat) {
		ServerInfo col = servers.get(cat).servers.get(new Random().nextInt(servers.get(cat).servers.size()));
		return col;
	}

	public static void connectTo(ProxiedPlayer sender, ServerInfo target) {
		ChatUtils.systemMessage(sender, "§a§lWARP", "§7Queueing connection to [RS-" + target.getName() + "]...");
		sender.connect(target);
		
	}
	
}
