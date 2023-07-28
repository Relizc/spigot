package net.itsrelizc.players.locale;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.itsrelizc.players.DataManager;

public class Locale {
	
	
	
	public static Map<String, LocaleItem> lang = new HashMap<String, LocaleItem>();
	
	public static void loadLocales() {
		System.out.print("Loading locales");
		
		File f = DataManager.openFileFromDb("locale.db");
		Scanner d = null;
		try {
			d = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String reading = null;
		LocaleItem loc = new LocaleItem();
		String fields[] = {"en", "cn", "cn_tr", "ja", "ko", "ru", "de"};
		int field = 0;
		
		while (d.hasNextLine()) {
			System.out.print("Loading locales");
			String data = d.nextLine().trim();
			
			if (data.length() == 0) continue;
				System.out.println("Skip");
			if (data.charAt(0) == '#') {
				System.out.println("Comment");
			} else if (data.charAt(0) == '@') { // declare localization
				String name = data.substring(1);
				System.out.println(name);
				if (name != "end") {
					reading = name;
					
				} else {
					
					lang.put(reading, loc);
					reading = null;
					loc = new LocaleItem();
					field = 0;
					System.out.println("End");
					break;
				}
				
				if (reading != null) {
					
					String ff = fields[field];
					
					System.out.println(ff + " " + data);
					
					Field ok = null;
					try {
						ok = loc.getClass().getField(fields[field]);
					} catch (NoSuchFieldException | SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ok.set(loc, data);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		}
	}
	
}
