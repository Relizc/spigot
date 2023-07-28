package net.itsrelizc.networking.executors;

import net.itsrelizc.networking.CommunicationInput;
import net.itsrelizc.warp.ServerCollector;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.saltyfishstudios.bungee.ChatUtils;

public class ConnectPlayer implements Runnable {
	
	private CommunicationInput input;

	public ConnectPlayer(CommunicationInput input) {
		this.input = input;
	}

	@Override
	public void run() {
		String des = input.readString();
		String p = input.readString();
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(p);
		
		boolean found = false;
		
		for (ServerCollector c : WarpUtils.servers.values()) {
			for (ServerInfo s : c.servers) {
				System.out.print(s.getName());
				System.out.print(des);
				System.out.print(s.getName().equalsIgnoreCase(des));
				if (s.getName().equalsIgnoreCase(des)) {
					ChatUtils.systemMessage(player, "§a§lWARP", "§7Sending you to [RS-" + des + "]...");
					player.connect(s);
					found = true;
				}
			}
		}
		
		if (!found) {
			CreateServer.destWaitCreate.put(player, des);
		}
	}

}
