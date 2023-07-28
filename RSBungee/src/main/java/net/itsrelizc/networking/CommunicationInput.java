package net.itsrelizc.networking;

import java.io.DataInputStream;
import java.io.IOException;

public class CommunicationInput {
	
	private DataInputStream input;
	private CommunicationType type = null;

	public CommunicationInput(DataInputStream input) {
		this.input = input;
		try {
			this.type = CommunicationType.getTypeByByte(input.readByte());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CommunicationType getType() {
		return this.type;
	}
	
	public byte readByte() {
		try {
			return this.input.readByte();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0x00;
	}
	
	public int readShort() {
		short n = 0;
		for(int exp = 0; exp < 2; exp ++) {
			n += Byte.toUnsignedInt(this.readByte()) * (Math.pow(256, exp));
		}
		return Short.toUnsignedInt(n);
	}
	
	public String readString() {
		int size = this.readShort();
		String r = "";
		for (int i = 0; i < size; i ++) {
			r += (char) this.readByte();
		}
		return r;
	}
}
