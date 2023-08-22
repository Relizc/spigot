package net.itsrelizc.warp;

import net.itsrelizc.networking.CommunicationType;

enum ShuffleType {
	RANDOM, MORE_PLAYERS_FIRST, LESS_PLAYERS_FIRST, MORE_HARDWARE_FIRST, LESS_HARDWARE_FIRST;
}

public enum ServerCategory {
	
	VERIFICATION((byte) 0x00, (short) 0, true, null),
	LOBBY_MAIN((byte) 0x01, (short) 0, true, "main"),
	LOBBY_BRIDGE((byte) 0x01, (short) 31, true, "bridge"),
	LOBBY_DEATHSWAP((byte) 0x01, (short) 42, true, "deathswap"),
	LOBBY_SMP((byte) 0x01, (short) 1, true, "smp"),
	GAME_BRIDGE((byte) 0x03, (short) 13, true, null, ShuffleType.MORE_PLAYERS_FIRST),
	SMP((byte) 0x02, (short) 0, false, null),
	PUBLIC_BUILD((byte) 0x04, (short) 1, false, null),
	CLASSIC_SHITWARS((byte) 0x05, (short) 0, true, null, ShuffleType.MORE_PLAYERS_FIRST),
	COMPETITIVE_SHITWARS((byte) 0x05, (short) 1, true, null, ShuffleType.MORE_PLAYERS_FIRST);

	public byte data;
	public short sub;
	public boolean shuffle;
	public ShuffleType shuffletype;
	public String display;

	private ServerCategory(byte catid, short sub, boolean shuffle, String display, ShuffleType shuffletype) {
		this.data = catid;
		this.sub = sub;
		this.shuffle = shuffle;
		this.shuffletype = shuffletype;
		this.display = display;
	};
	
	private ServerCategory(byte catid, short sub, boolean shuffle, String display) {
		this.data = catid;
		this.sub = sub;
		this.shuffle = shuffle;
		this.shuffletype = ShuffleType.RANDOM;
		this.display = display;
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
