package net.itsrelizc.networking;

public enum CommunicationType {
	
	// Types from Regular Protocol are removed.
	
	ADD_PLAYER((byte) 0x01);
	
	public byte data;

	private CommunicationType(byte packetId) {
		this.data = packetId;
	};
	
	public static CommunicationType getTypeByByte(byte packetId) {
		for (CommunicationType t : values()) {
			if (t.data == packetId) {
				return t;
			}
		}
		return null;
	}
}
