package net.saltyfishstudios.bungee;

import java.util.ArrayList;
import java.util.List;

public class ServerTypeManager {
	
	private static List<String> serverLobby = new ArrayList<String>();
	private static List<String> serverDeathSwapLobby = new ArrayList<String>();
	
	public static void addServerLobby(String code) {
		serverLobby.add(code);
	}
	
	public static List<String> getServerLobby() {
		return serverLobby;
	}
	
	public static void removeServerLobby(String code) {
		serverLobby.remove(code);
	}
	
	public static void addDeathSwapLobby(String code) {
		serverDeathSwapLobby.add(code);
	}
	
	public static List<String> getDeathSwapLobby() {
		return serverDeathSwapLobby;
	}
	
	public static void removeDeathSwapLobby(String code) {
		serverDeathSwapLobby.remove(code);
	}
}
