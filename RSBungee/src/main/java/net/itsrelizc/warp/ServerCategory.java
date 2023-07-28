package net.itsrelizc.warp;

import net.itsrelizc.networking.CommunicationType;
import net.md_5.bungee.api.config.ServerInfo;

public enum ServerCategory {
	
	VERIFICATION((byte) 0x00, (short) 0),
	LOBBY_MAIN((byte) 0x01, (short) 0),
	LOBBY_BRIDGE((byte) 0x01, (short) 31),
	LOBBY_DEATHSWAP((byte) 0x01, (short) 42),
	LOBBY_SMP((byte) 0x01, (short) 1),
	GAME_BRIDGE((byte) 0x03, (short) 13),
	SMP((byte) 0x02, (short) 0);
	
	public byte data;
	public short sub;

	private ServerCategory(byte catid, short sub) {
		this.data = catid;
		this.sub = sub;
	};
	
	public static ServerCategory getTypeByByte(byte packetId, int subcat) {
		for (ServerCategory t : values()) {
			if (t.data == packetId && t.sub == subcat) {
				return t;
			}
		}
		return null;
	}
}
