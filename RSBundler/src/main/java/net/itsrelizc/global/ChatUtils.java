package net.itsrelizc.global;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.itsrelizc.players.Profile;
import net.itsrelizc.players.Rank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class ChatUtils {
	
	public enum Colors {
		DARK_BLUE("1"),
		DARK_GREEN("2"),
		DARK_AQUA("3"),
		DARK_RED("4"),
		DARK_PURPLE("5"),
		GOLD("6"),
		GRAY("7"),
		DARK_GRAY("8"),
		BLUE("9"),
		GREEN("a"),
		AQUA("b"),
		RED("c"),
		MAGENTA("d"),
		YELLOW("e"),
		WHITE("f");
		
		
		Colors(String string) {
			this.v = "§" + string;
		}
		
		@Override
		public String toString() {
			return this.v;
		}

		private String v;
	}
	
	public static String emptyColorString(int length) {
		String b = "";
		for (int i = 0; i < length; i ++) {
			int index = new Random().nextInt(Colors.values().length);
			b += Colors.values()[index];
		}
		return b;
	}
	
	public static void sayAsPlayer(Player player, String message) {
		Profile p = Profile.findByOwner(player);
		Bukkit.broadcastMessage(Rank.findByPermission(p.permission).displayName + " " + player.getDisplayName() + "§7: §r" + message);
	}
	
	public static void systemMessage(Player player, String message) {
		player.sendMessage("§l§dSYSTEM §r§8> §r" + message);
	}
	
	public static void systemMessage(Player player, String channel, String message) {
		player.sendMessage(channel.toUpperCase() + " §r§8> §r" + message);
	}

	public static void systemMessage(CommandSender sender, String channel, String message) {
		sender.sendMessage(channel.toUpperCase() + " §r§8> §r" + message);
	}
	
	public static String plural(Integer number) {
		if (number > 1) return "s";
		else return "";
	}
	
	public static void broadcastSystemMessage(String channel, String message) {
		Bukkit.broadcastMessage(channel.toUpperCase() + " §r§8> §r" + message);
	}
	
	public static void broadcastSystemMessage(String channel, TextComponent content) {
		TextComponent c = new TextComponent(TextComponent.fromLegacyText(channel.toUpperCase() + " §r§8> §r"));
		c.addExtra(content);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.spigot().sendMessage(c);
		}
	}
	
	public static void npcMessage(Player player, String name, String content) {
		player.sendMessage("§d[NPC] " + name + "§7:§r " + content);
	}
	
	public static void adminCommand(Player player, Command command) {
		player.sendMessage("§7§o[" + player.getDisplayName() + ": ");
	}
	
	public static void attachCommand(TextComponent component, String command, String altHoverText) {
		component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));
		if (altHoverText == null) {
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClick me to run the following command:\n\n§b/" + command).create()));
		} else {
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§a" + altHoverText).create()));
		}
	}
	
	public static void attachHover(TextComponent component, String hover) {
		if (hover == null) {
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eTheres nothing to see here!").create()));
		} else {
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
		}
	}
	
	public static String randomString(int len) {
		return RandomStringUtils.randomAlphabetic(len);
	}
	
	public static List<String> fromGeneticString(String list[]) {
		List<String> a = new ArrayList<String>();
		for (int i = 0; i < list.length; i ++) {
			a.add(list[i]);
		}
		return a;
	}
	
	public static List<String> fromArgs(String... list) {
		return fromGeneticString(list);
	}
	
	public static List<String> fromNewList() {
		return new ArrayList<String>();
	}
	
	public static List<String> fromObjects(Object[] objs) {
		List<String> a = new ArrayList<String>();
		for (Object obj : objs) {
			a.add(objs.toString().toLowerCase());
		}
		return a;
	}

	public static void systemMessage(Player player, String channel, TextComponent content) {
		TextComponent c = new TextComponent(TextComponent.fromLegacyText(channel.toUpperCase() + " §r§8> §r"));
		c.addExtra(content);
		player.spigot().sendMessage(c);
	}
	
	public static void sendTitle(Player p, String title, String subtitle, int in, int stay, int out) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		PacketPlayOutTitle t = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		
		IChatBaseComponent b = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		PacketPlayOutTitle b2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, b);
		
		PacketPlayOutTitle length = new PacketPlayOutTitle(in, stay, out);


		((CraftPlayer) p).getHandle().playerConnection.sendPacket(t);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(b2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
	}
	
	public static void sendActionBar(Player p, String text) {
		PacketPlayOutChat a = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(a);
	}
	
	public static void systemActionBar(Player p, String channel, String message) {
		sendActionBar(p, channel + "!§r " + message);
	}
	
	public static void broadcastActionBar(String text) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			sendActionBar(player, text);
		}
	}

	public static void broadcastTitle(String string, String string2, int i, int j, int k) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			sendTitle(player, string, string2, i, j, k);
		}
	}
}
