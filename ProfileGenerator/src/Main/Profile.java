package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Profile {
	
	public static List<String> GIRL_FIRST = new ArrayList<String>();
	
	public static List<String> BOY_FIRST = new ArrayList<String>();
	
	public static List<String> LAST = new ArrayList<String>();
	
	public static List<String> STATES = new ArrayList<String>();
	
	public static void addGirl(String name) {
		GIRL_FIRST.add(name);
	}
	
	public static void addBoy(String name) {
		BOY_FIRST.add(name);
	}
	
	public static void addSur(String name) {
		LAST.add(name);
	}
	
	public static void addState(String name) {
		STATES.add(name);
	}
	
	public static int countBoy() {
		return BOY_FIRST.size();
	}
	
	public static int countGirl() {
		return GIRL_FIRST.size();
	}
	
	public static int countSur() {
		return LAST.size();
	}
	
	public static boolean randomBool(double chance) {
		double range = Math.random();
		
		if (range < chance) return true;
		else return false;
	}
	
	
	private String full;
	private String bday;
	private boolean isGirl;
	private String state;
	private double crime;
	
	public Profile(String first, String last, boolean isGirl, boolean junior, int month, int day, int year, String state, double crime) {
		String jr = "";
		if (junior) jr = " Jr.";
		
		this.full = first + " " + last + jr;
		this.bday = month + "/" + day + "/" + year;
		this.isGirl = isGirl;
		
		this.state = state;
		this.crime = crime;
	}
	
	public String toString() {
		String q;
		if (isGirl) q = "girl";
		else q = "boy";
		
		String crimelore;
		if (this.crime < 0.1) crimelore = "This " + q + " is completely safe and trustable!";
		else if (this.crime < 0.3) crimelore = "This " + q + " behaves very well!"; 
		else if (this.crime < 0.5) crimelore = "This " + q + " is a normal citizen."; 
		else if (this.crime < 0.8) crimelore = "This " + q + " requires more caution."; 
		else if (this.crime < 0.95) crimelore = "This " + q + " is really dangerous";
		else crimelore = "You should arrest this " + q + " right now!!"; 
		
		return this.full + "\nBirthday: " + this.bday + "\n" + this.full + " is a " + q + " living in " + this.state + " state.\n" + "This person's crime rate is: " + String.format("%.2f", this.crime * 100) + "% (" + crimelore + ")";
	}
	
	public static Profile random() {
		
		String first;
		
		boolean girl = randomBool(0.5);
		if (girl) first = GIRL_FIRST.get(new Random().nextInt(GIRL_FIRST.size()));
		else first = BOY_FIRST.get(new Random().nextInt(BOY_FIRST.size()));

		String last = LAST.get(new Random().nextInt(LAST.size()));
		
		boolean isJunior = randomBool(0.2);
		
		int year = 1900 + new Random().nextInt(124); // 1900 - 2024
		int month = new Random().nextInt(12) + 1; // 1 - 12
		int day;
		if (month % 2 == 0 && month != 2) day = new Random().nextInt(30) + 1; // 1 - 30
		else if (month % 2 == 1) day = new Random().nextInt(31) + 1; // 1 - 31
		else { // Feburary
			if (year % 4 == 0) day = 29; // Leap year
			else day = 28; // Regular
		}; 
		
		String state = STATES.get(new Random().nextInt(50));
		
		double crime = Math.random();
		
		return new Profile(first, last, girl, isJunior, month, day, year, state, crime);
		
	}
	
}
