package net.itsrelizc.copy;

public class Rank {
	
	public enum Measurable {
		NONE(null, null, "§a", 0L),
		PRO1("PRO", "PRO", "§b", 1L),
		PRO2("PRO+", "PRO+", "§b", 2L),
		EXPERT1("EXPERT", "EXP", "§6", 3L),
		EXPERT2("EXPERT+", "EXP+", "§6", 4L),
		HELPER("HELPER", "HELP", "§9", 254L),
		ADMIN("ADMIN", "ADMIN", "§c", 255L),
		OWNER("OWNER", "OWN", "§c", 256L);
		
		private String displayName = null;
		private String shortName;
		private String colorName;
		private Long permission;
		
		private Measurable(String displayName, String shortName, String colorName, Long permission) {
			this.displayName = displayName;
			this.shortName = shortName;
			this.colorName = colorName;
			this.permission = permission;
		}
		
		public final String getDisplayName() {
			return this.displayName;
		}
		
		public final String getShortName() {
			return this.shortName;
		}
		
		public final String getRankColor() {
			return this.colorName;
		}
		
		public final Long getPermission() {
			return this.permission;
		}
		
		public Measurable[] getAllRanks() {
			return this.values();
		}
	}
	
	public enum Cosmetic {
		
	}
}
