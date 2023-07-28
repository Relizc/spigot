package net.itsrelizc.serverutils;

public enum ServerType {
	
	LOBBY_DEATHSWAP("LobbyDeathSwap"),
	LOBBY_HANDLER("LobbyHandler"),
	LOBBY_MAIN("LobbyMain");
	
	private String name;

	private ServerType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
