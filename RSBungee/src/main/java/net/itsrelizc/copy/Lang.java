package net.itsrelizc.copy;

import net.md_5.bungee.api.connection.ProxiedPlayer;

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
		WARP_CHANNEL_NAME,
		WARP_CONNECTING;
	}
	
	public enum LangValue {
		
		WARP_CHANNEL_NAME("§e§lWARP", "§e§l转接"),
		WARP_CONNECTING("§7Attempting to connect you to §7§l[RS-{0}]", "§7正在将您转接到 §7§l{0}");
		
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
	
	public static void getPlayerLanguage(ProxiedPlayer player) {
		
	}

}
