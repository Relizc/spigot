package net.itsrelizc.server;

import java.io.IOException;

import net.itsrelizc.global.Me;
import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.networking.Components;

public class InfoChanger {
	
	private static Communication change(byte field, Object serializer) {
		Communication com = new Communication(CommunicationType.ACTIVE_CHANGE_INFO);
		com.writeByte(field);
		com.writeByte(Me.rambyte);
		com.writeString(Me.code);
		
		return com;
	}
	
	public static void changeStatus(String status) {
		Communication com = change((byte) 0x01, status);
		com.writeString(status);
		try {
			com.sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
