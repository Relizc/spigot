package net.itsrelizc.global;

public class TimeHandler {
	
	public static String ABBRvalueOf(Long seconds) {
		
		if (seconds < 1) {
			return "About a second";
		}
		long days = seconds / 86400;
		seconds -= days * 86400;
		
		long hours = seconds / 3600;
		seconds -= hours * 3600;
		
		long minutes = seconds / 60;
		seconds -= minutes * 60;
		
		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
	}
	
	public static String FULLvalueOf(Long seconds) {
		if (seconds < 1) {
			return "About a second";
		}
		long days = seconds / 86400;
		seconds -= days * 86400;
		
		long hours = seconds / 3600;
		seconds -= hours * 3600;
		
		long minutes = seconds / 60;
		seconds -= minutes * 60;
		
		return days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds.";
	}
}
