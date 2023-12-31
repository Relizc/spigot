package net.itsrelizc.warp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerCollector {
	
	public List<ServerInfo> servers = new ArrayList<ServerInfo>();
	
	public ServerCollector() {
		
	}
	
	public void addServer(ServerInfo server) {
		this.servers.add(server);
	}
	
	public void removeServer(ServerInfo server) {
		this.servers.remove(server);
	}
	
	public ServerInfo randomServer() {
		return this.servers.get(new Random().nextInt(this.servers.size()));
	}
	
	public static ServerInfo randomServerWithoutPlayer(List<ServerInfo> serv, ProxiedPlayer player) {
		if (serv.size() <= 1) return null;
		ServerInfo target = player.getServer().getInfo();
		while (target == player.getServer().getInfo()) {
			target = serv.get(new Random().nextInt(serv.size()));
		}
		return target;
	}
}
