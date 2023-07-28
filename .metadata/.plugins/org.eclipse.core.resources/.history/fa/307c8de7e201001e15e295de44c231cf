package net.itsrelizc.warp;

import net.itsrelizc.networking.CommunicationType;

enum ShuffleType {
	RANDOM, MORE_PLAYERS_FIRST, LESS_PLAYERS_FIRST, MORE_HARDWARE_FIRST, LESS_HARDWARE_FIRST;
}

public enum ServerCategory {
	
	VERIFICATION((byte) 0x00, (short) 0, true),
	LOBBY_MAIN((byte) 0x01, (short) 0, true),
	LOBBY_BRIDGE((byte) 0x01, (short) 31, true),
	LOBBY_DEATHSWAP((byte) 0x01, (short) 42, true),
	LOBBY_SMP((byte) 0x01, (short) 1, true),
	GAME_BRIDGE((byte) 0x03, (short) 13, true, ShuffleType.MORE_PLAYERS_FIRST),
	SMP((byte) 0x02, (short) 0, false);
	
	public byte data;
	public short sub;
	public boolean shuffle;
	public ShuffleType shuffletype;

	private ServerCategory(byte catid, short sub, boolean shuffle, ShuffleType shuffletype) {
		this.data = catid;
		this.sub = sub;
		this.shuffle = shuffle;
		this.shuffletype = shuffletype;
	};
	
	private ServerCategory(byte catid, short sub, boolean shuffle) {
		this.data = catid;
		this.sub = sub;
		this.shuffle = shuffle;
		this.shuffletype = ShuffleType.RANDOM;
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
