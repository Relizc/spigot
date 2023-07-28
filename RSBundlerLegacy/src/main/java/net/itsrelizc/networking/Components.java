package net.itsrelizc.networking;

public class Components {
	
	public static byte formatRAM(Long allowicatedRam) {
		if (allowicatedRam < 262144) return 0x00;
		else if (allowicatedRam < 524288) return 0x01;
		else if (allowicatedRam < 1048756) return 0x02;
		else if (allowicatedRam < 8388608) return 0x03;
		else return 0x04;
	}
	
	public static byte fromStringRAMChar(Character s) {
		if (Character.toLowerCase(s) == 't') return 0x00;
		else if (Character.toLowerCase(s) == 's') return 0x01;
		else if (Character.toLowerCase(s) == 'm') return 0x02;
		else if (Character.toLowerCase(s) == 'b') return 0x03;
		else return 0x04;
	}
	
	public static char fromByteRAM(byte s) {
		if (s == (byte) 0x00) return 't';
		else if (s == (byte) 0x01) return 's';
		else if (s == (byte) 0x02) return 'm';
		else if (s == (byte) 0x03) return 'b';
		else return 'g';
	}
	
}
