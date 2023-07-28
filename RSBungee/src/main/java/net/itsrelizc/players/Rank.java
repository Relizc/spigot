package net.itsrelizc.players;

public enum Rank {
	
	NONE(1L, 1L, "§7[TRASH]", false),
	LOADING(-1L, -1L, "§8[...]", false),
	NPC(0L, 0L, "§8[NPC]", false),
	LEVEL_1(17L, 1L, "§a[DONOR]", false),
	LEVEL_2(18L, 1L, "§b[VIP]", false),
	LEVEL_3(19L, 1L, "§d[PATREON]", false),
	LEVEL_4(20L, 1L, "§6[MVP]", false),
	SUNNY(23L, 1L, "§e[SUN LOVER]", false),
	ANARCHY(22L, 1L, "§e[ANARCHY]", true),
	MODERATOR(65L, 3L, "§e[MODERATOR]", true),
	ADMIN(66L, 3L, "§c[ADMIN]", true),
	AVA(67L, 4L, "§d[AVA]", true),
	OWNER(127L, 127L, "§c[OWNER]", true);
	
	public final Long permission;
	public final String displayName;
	public final Boolean useop;
	public final Long level;
	
	private Rank(Long permission, Long level, String displayName, Boolean canUseOpCommands) {
		this.displayName = displayName;
		this.permission = permission;
		this.useop = canUseOpCommands;
		this.level = level;
	}
	
	public String rankColor() {
		return this.displayName.substring(0, 2);
	}
	
	public static Rank findByPermission(Long value){
	    for (Rank r : values()) {
	        if (r.permission == value) {
	            return r;
	        }
	    }
	    return null;
	}
}
