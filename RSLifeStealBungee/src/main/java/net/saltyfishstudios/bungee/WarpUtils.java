package net.saltyfishstudios.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.saltyfishstudios.global.ChatUtils;

public class WarpUtils {
	
	public static void sendToServer(ProxiedPlayer player, String code) {
		if (ProxyServer.getInstance().getServerInfo(code).getPlayers().contains(player)) {
			ChatUtils.systemMessage(player, "§a§lWARP", "§cYou are already on the server SFN-" + code + "!");
			return;
		}
		ServerInfo server = ProxyServer.getInstance().getServerInfo(code);
		ChatUtils.systemMessage(player, "§a§lWARP", "§7Connecting you to SFN-" + server.getName());
		player.connect(server);
	}
	
	public static void sendToServer(ProxiedPlayer player, ServerInfo server) {
		if (server.getPlayers().contains(player)) {
			ChatUtils.systemMessage(player, "§a§lWARP", "§cYou are already on the server SFN-" + server.getName() + "!");
			return;
		}
		ChatUtils.systemMessage(player, "§a§lWARP", "§7Connecting you to SFN-" + server.getName());
		player.connect(server);
	}
}
