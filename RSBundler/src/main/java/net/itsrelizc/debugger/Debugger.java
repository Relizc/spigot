package net.itsrelizc.debugger;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.RawMain;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class Debugger {
 
	public static List<Player> debug = new ArrayList<Player>();
	
	public static void addDebug(Player player) {
		debug.add(player);
	}
	
	public static void removeDebug(Player player) {
		debug.remove(player);
	}
	
	public static boolean hasDebug(Player player) {
		return debug.contains(player);
	}
	
	private static Map<String, Object> parm(Object obj) {
		Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try { map.put(field.getName(), field.get(obj)); } catch (Exception e) { }
        }
        return map;
	}
	
	public static TextComponent eventStringBuilder(Object event) {
		TextComponent a = new TextComponent("§8§l[Event Details]");
		String q = "§7(" + ((Event) event).getEventName() + ") §aEvent Fields:\n";
		Map<String, Object> b = parm(event);
		for (String field : b.keySet()) {
			q += "\n§a" + field + ": " + b.get(field);
		}
		ChatUtils.attachHover(a, q);
		return a;
	}

	public static void enable(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new ShowInvClickSlot(), plugin);
	}
}
