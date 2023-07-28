package net.itsrelizc.global;

public class Me {
	
	public static String code;
	public static byte rambyte;
	
	public static void setCode(String c) {
		code = c;
	}
	
	public static void setRAMByte(byte r) {
		rambyte = r;
	}
	
	public static String getStaticCode() {
		String[] names = {"T", "S", "M", "B", "G"};
		return names[(int) rambyte] + code.toLowerCase();
	}
	
}
