package net.itsrelizc.networking;

public enum CommunicationValue {
	
	TRUE((byte) 0x01),
	FALSE((byte) 0x00);
	
	public byte data;

	private CommunicationValue(byte data) {
		this.data = data;
	};
}
