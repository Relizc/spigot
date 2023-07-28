package net.itsrelizc.nerdbot.report;

public enum ReportTypes {
	
	HACKS(-1666441065, "hacking", "hack", "hacking", "hacks", "hax"),
	FLIGHT(-1404150632, "flight", "fly", "flying", "f"),
	KILLAURA(-1404150632, "killaura", "ka", "killcircle"),
	REACH(88631469, "reach", "r", "re", "longhands", "longhand");
	
	public int id;
	public String[] display;
	public boolean requireReplay;
	public String name;
	
	private ReportTypes(int id, String name, String... displayname) {
		this(id, name, false, displayname);
	}
	
	private ReportTypes(int id, String name, boolean requireReplay, String... displayname) {
		this.id = id;
		this.display = displayname;
		this.requireReplay = requireReplay;
		this.name = name;
	}
	
}
