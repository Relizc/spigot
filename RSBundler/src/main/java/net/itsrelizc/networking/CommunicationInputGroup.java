package net.itsrelizc.networking;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommunicationInputGroup {
	
	private DataInputStream input;
	private short size;
	private List<CommunicationInput> packets = new ArrayList<CommunicationInput>();

	public CommunicationInputGroup(DataInputStream input) {
		this.input = input;
		this.size = (short) this.readShort();
		for (int i = 0; i < this.size; i ++) {
			int bytesize = this.readShort();
			byte[] bytearr = new byte[bytesize];
			for (int x = 0; x < bytesize; x ++) {
				bytearr[x] = this.readByte();
			}
			packets.add(new CommunicationInput(new DataInputStream(new ByteArrayInputStream(bytearr))));
		}
	}
	
	public List<CommunicationInput> getAllPackets() {
		return this.packets;
	}
	
	public byte readByte() {
		try {
			return this.input.readByte();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 0x00;
		}
	}
	
	public short readShort() {
		short n = 0;
		for(int exp = 0; exp < 2; exp ++) {
			n += Byte.toUnsignedInt(this.readByte()) * (Math.pow(256, exp));
		}
		return n;
	}
}
