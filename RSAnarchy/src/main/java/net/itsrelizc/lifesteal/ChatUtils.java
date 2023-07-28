package net.itsrelizc.lifesteal;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.md_5.bungee.api.chat.TextComponent;

public class ChatUtils {
	
	public static void systemMessage(Player player, String message) {
		player.sendMessage("§l§dSYSTEM §r§8> §r" + message);
	}
	
	public static void systemMessage(Player player, String channel, String message) {
		player.sendMessage(channel.toUpperCase() + " §r§8> §r" + message);
	}

	public static void systemMessage(CommandSender sender, String channel, String message) {
		sender.sendMessage(channel.toUpperCase() + " §r§8> §r" + message);
	}
	
	public static void broadcastSystemMessage(String channel, String message) {
		Bukkit.broadcastMessage(channel.toUpperCase() + " §r§8> §r" + message);
	}
	
	public static void npcMessage(Player player, String name, String content) {
		player.sendMessage("§d[NPC] " + name + "§7:§r " + content);
	}
	
	public static void adminCommand(Player player, Command command) {
		player.sendMessage("§7§o[" + player.getDisplayName() + ": ");
	}

	public static void systemMessage(Player player, String channel, TextComponent content) {
		TextComponent c = new TextComponent(TextComponent.fromLegacyText(channel.toUpperCase() + " §r§8> §r"));
		c.addExtra(content);
		player.spigot().sendMessage(c);
	}
}
