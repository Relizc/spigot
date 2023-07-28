package net.itsrelizc.lang;

import org.bukkit.entity.Player;

public class Lang {
	
	public enum LangType {
		EN_US(0L, "English", "United States"),
		ZH_CN(1L, "简体中文", "中国"),
		ZH_TW(2L, "繁體中文", "中國");
		
		private Long value;
		private String languageName;
		private String countryName;

		private LangType(Long value, String languageName, String countryName) {
			this.value = value;
			this.languageName = languageName;
			this.countryName = countryName;
		}
		
		public Long getValue() {
			return this.value; 	
		}
		
		public String getLanguageName() {
			return this.languageName;
		}
		
		public String getCountryName() {
			return this.countryName;
		}
	}
	
	public enum Language {
		DEFAULT_UNKNOWN,
		TABLIST_TITLE_LINE1,
		TABLIST_TITLE_LINE2,
		JOIN_TITLE_WAIT,
		JOIN_SUBTITLE_WAIT,
		WARP_CHANNEL_NAME,
		WARP_ATTEMPT, 
		WARP_FAIL, WARP_FAIL_ALREADY_AT;
	}
	
	public enum LangValue {
		
		DEFAULT_UNKNOWN("", ""),
		TABLIST_TITLE_LINE1("§e§lRELIZC NETWORK§r\n§bmc.itsrelizc.net\n", "LOSER"),
		TABLIST_TITLE_LINE2("\n§b§lDiscord: §ediscord.gg/mfRkr2av\n§eServer: §7{0}\n\n§aDon't forget to tell your friends!", "LOSER"),
		JOIN_TITLE_WAIT("§eChecking connection...", ""),
		JOIN_SUBTITLE_WAIT("§eYou will be warped soon!", ""),
		WARP_CHANNEL_NAME("§a§lWARP", ""),
		WARP_ATTEMPT("§7Attempting to connect you to {0}", ""),
		WARP_FAIL("§cUnable to warp you to another server!", ""),
		WARP_FAIL_ALREADY_AT("§cYou are already in the server you want to connect!", "");
		
		private String enUsValue;
		private String zhCnValue;

		private LangValue(String enUsValue, String zhCnValue) {
			this.enUsValue = enUsValue;
			this.zhCnValue = zhCnValue;
		}
		
		public String getEnUsValue() {
			return this.enUsValue;
		}
		
		public String getZhCnValue() {
			return this.zhCnValue;
		}
	}
	
	public static void getPlayerLanguage(Player player) {
		
	}

}
