package net.itsrelizc.cs.kevinpunisher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RuleBreaker {
	
	public String name;
	public long date;
	public int severe;
	
	public static List<RuleBreaker> all = new ArrayList<RuleBreaker>();
	
	public RuleBreaker(String name, int severe, long date) {
		this.name = name;
		this.date = date;
		this.severe = severe;
	}
	
	public RuleBreaker(String name, int severe) {
		this(name, severe, System.currentTimeMillis());
	}
	
	public RuleBreaker(String name) {
		this(name, 1, System.currentTimeMillis());
	}
	
	public RuleBreaker() {
		this("Being Naughty", 1, System.currentTimeMillis());
	}
	
	public String toString() {
		
		Date now = new Date(this.date);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dis = formatter.format(now);
		
		return dis + " - " + this.name + " : " + severeIntToStr(this.severe);
	}
	
	public static String severeIntToStr(int severeness) {
		if (severeness == 0) {
			return "Kevin did nothing";
		} else if (severeness == 1) {
			return "Kevin was informed to stop";
		} else if (severeness == 2) {
			return "Kevin was warned";
		} else if (severeness == 3) {
			return "Kevin was punished";
		} else {
			return "Kevin was evil";
		}
	}
	
	public static void addToList(RuleBreaker additional) {
		all.add(additional);
	}
	
}
