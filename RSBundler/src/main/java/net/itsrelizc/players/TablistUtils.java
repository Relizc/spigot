package net.itsrelizc.players;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.global.Me;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

public class TablistUtils {
	
	public static String[] stuffz = {
			"Your fun starts here!",
			"Pigs are awesome animals!",
			"§e/p §ais an alias for §e/party§a!",
			"§e/c §ais an alias for §e/company§a!",
			"§e/f §ais an alias for §e/friend§a!",
			"§e/w §ais an alias for §e/whisper§a!",
			"Someone hacking?\n§e/report <Player>§a to report!",
			"Spamming fake reports might cause a ban!",
			"Join us and catch hackers! §e/join§a!",
			"Invite others to your server!\n§e/invite <Player> §a!",
			"We need some jokes here.",
			"§bhttps://youtu.be/dQw4w9WgXcQ",
			"Saying inappropriate things?\n§e/report <Player> §ato report!",
			"Yo have fun here!",
			"§bb2ggaGkgbmVyZA"
	};
	
	public static String additional = "";
	public static String p = stuffz[new Random().nextInt(stuffz.length)];
	
	public static void startTicking() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Me.getPlugin(), new Runnable() {

			@Override
			public void run() {
				tipUpdate();
				for (Player player : Bukkit.getOnlinePlayers()) {
					update(player);
				}
			}
			
		}, 600L, 600L);
	}
	
	public static void initlizeDefault(Player player) {
		PacketPlayOutPlayerListHeaderFooter tab = new PacketPlayOutPlayerListHeaderFooter();
		
		try {
			
			Field a = tab.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(tab, ChatSerializer.a("{\"text\": \"\\u00a7e\\u00a7lRELIZC NETWORK\\u00a7r\\n\\u00a7bmc.itsrelizc.net\\n\"}"));
			a.setAccessible(false);
			
			Field b = tab.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(tab, ChatSerializer.a("{\"text\": \"" + "\n§b§lNews: §r§eWelcome to Beta Testing!\n\n§a" + p + "\n"  + additional + "\n§7Server: §8[RS-" + Me.getStaticCode() + "]" + "\"}"));
			b.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(tab);
		
		
	}
	
	public static void tipUpdate() {
		p = stuffz[new Random().nextInt(stuffz.length)];
	}
	
	public static void update(Player player) {
		
		PacketPlayOutPlayerListHeaderFooter tab = new PacketPlayOutPlayerListHeaderFooter();
		
		try {
			
			
			
			Field a = tab.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(tab, ChatSerializer.a("{\"text\": \"\\u00a7e\\u00a7lRELIZC NETWORK\\u00a7r\\n\\u00a7bmc.itsrelizc.net\\n\"}"));
			a.setAccessible(false);
			
			Field b = tab.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(tab, ChatSerializer.a("{\"text\": \"" + "\n§b§lNews: §r§eWelcome to Beta Testing!\n\n§a" + p + "\n" + additional + "\n§7Server: §8[RS-" + Me.getStaticCode() + "]" + "\"}"));
			b.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(tab);
	}
	
}
