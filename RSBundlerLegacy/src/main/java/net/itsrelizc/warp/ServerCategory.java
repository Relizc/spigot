package net.itsrelizc.warp;

import net.itsrelizc.networking.CommunicationType;

public enum ServerCategory {
	
	VERIFICATION((byte) 0x00, (short) 0, true),
	LOBBY_MAIN((byte) 0x01, (short) 0, true),
	LOBBY_BRIDGE((byte) 0x01, (short) 31, true),
	LOBBY_DEATHSWAP((byte) 0x01, (short) 42, true),
	LOBBY_SMP((byte) 0x01, (short) 1, true),
	SMP((byte) 0x02, (short) 0, false);
	
	public byte data;
	public short sub;
	public boolean shuffle;

	private ServerCategory(byte catid, short sub, boolean shuffle) {
		this.data = catid;
		this.sub = sub;
		this.shuffle = shuffle;
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
