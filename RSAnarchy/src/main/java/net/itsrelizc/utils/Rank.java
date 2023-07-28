package net.itsrelizc.utils;

public enum Rank {
	
	NONE(0, 0, "§7[TRASH]", false),
	LEVEL_1(17, 1, "§a[DONOR]", false),
	LEVEL_2(18, 1, "§b[VIP]", false),
	LEVEL_3(19, 1, "§d[PATREON]", false),
	LEVEL_4(20, 1, "§6[MVP]", false),
	HAS_A_FUCKER_FUCKED_AVA(21, 1, "§d[AVA]", false),
	MODERATOR(65, 3, "§e[MODERATOR]", true),
	ADMIN(66, 3, "§c[ADMIN]", true),
	AVA(67, 2, "§d[AVA]", true),
	DICK(68, 3, "§d§3[HOT §kDICK§r§3]", true),
	VIRGIN(69, 3, "§b[Makayla]", true),
	OWNER(127, 4, "§c[OWNER]", true);
	
	public final Integer permission;
	public final String displayName;
	public final Boolean useop;
	public final Integer level;
	
	private Rank(Integer permission, Integer level, String displayName, Boolean canUseOpCommands) {
		this.displayName = displayName;
		this.permission = permission;
		this.useop = canUseOpCommands;
		this.level = level;
	}
	
	public static Rank findByPermission(Integer value){
	    for (Rank r : values()) {
	        if (r.permission == value) {
	            return r;
	        }
	    }
	    return null;
	}
}
