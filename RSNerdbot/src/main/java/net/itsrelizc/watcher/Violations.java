package net.itsrelizc.watcher;

public enum Violations {
	
	KILLAURA("Kill Aura", "KA"),
	REACH("Reach", "RE"),
	AUTOCLICKER("Auto Clicker", "AC");
	
	
	public String name;
	public String a2;

	private Violations(String name, String alpha2) {
		this.name = name;
		this.a2 = alpha2;
	}
}
