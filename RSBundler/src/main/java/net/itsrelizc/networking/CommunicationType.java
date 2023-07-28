package net.itsrelizc.networking;

public enum CommunicationType {
	
	HANDSHAKE_LAUNCH_SUCESS((byte) 0x01),
	HANDSHAKE_CLOSE_SCUESS((byte) 0x02),
	ACTIVE_INFO_BROADCAST((byte) 0xa0),
	ACTIVE_LOG((byte) 0xa1),
	ACTIVE_ERROR_BROADCAST((byte) 0xa2),
	ACTIVE_CHANGE_INFO((byte) 0x51),
	KICK_PLAYER_FROM_PROXY((byte) 0xb0),
	ALERT((byte) 0xa0),
	PING((byte) 0xf0),
	NOT_FOUND((byte) 0xc4),
	OK((byte) 0xc0),
	FALSE((byte) 0x00),
	BUNGEE_PING((byte) 0xe0),
	BUNGEE_READY((byte) 0xe1),
	BUNGEE_PLAYER_INFO((byte) 0x83),
	BUNGEE_CREATE_SERVER((byte) 0xe2),
	BUNGEE_REMOVE_SERVER((byte) 0xe3),
	BUNGEE_LIST_SERVERS((byte) 0xe2),
	INCOMING_BUNGEE_LIST_SERVERS((byte) 0xe4),
	ACTIVE_SHUT_DOWN((byte) 0xaf), 
	CLOSING_SHUTDOWN_OK((byte) 0xae), 
	PLAYER_CONNECT((byte) 0xe8),
	PLAYER_GET_INFO((byte) 0xea);
	
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
