package net.itsrelizc.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.Items;
import net.itsrelizc.lifesteal.Shop;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TPACommand implements CommandExecutor {
	
	public static Map<Player, Player> tpas = new HashMap<Player, Player>();
	public static Map<Player, Integer> expire = new HashMap<Player, Integer>();
	public static Map<Player, Integer> cd = new HashMap<Player, Integer>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1) return false;
		
		final Player player = (Player) sender;
		final Player target = Bukkit.getPlayer(args[0]);
		
		if (Items.countTicket(player) < 1) {
			ChatUtils.systemMessage(player, "§d§lTELEPORT", "§cYou don't have enough §a[Teleport Tickets]§c! Purchase them from the heart shop!");
			return true;
		}
		
		if (target.getName().equalsIgnoreCase(player.getName())) {
			ChatUtils.systemMessage(player, "§d§lTELEPORT", "§cYou can't TPA to yourself!");
			return true;
		}
		
		cd.put(player, 300);
		
		if (target == null) {
			ChatUtils.systemMessage(player, "§d§lTELEPORT", "§cThat player isn't online!");
			return true;
		}
		
		if (tpas.keySet().contains(player)) {
			ChatUtils.systemMessage(player, "§d§lTELEPORT", "§cYou had already sent a TPA request!");
			return true;
		}
		
		tpas.put(player, target);
		
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("RSLifeSteal"), new Runnable() {

			@Override
			public void run() {
				if (tpas.containsKey(player)) {
					ChatUtils.systemMessage(player, "§d§lTELEPORT", "§eYour teleport request to §b" + target.getDisplayName() + " §ehad expired!");
					ChatUtils.systemMessage(target, "§d§lTELEPORT", "§b" + player.getDisplayName() + "§e's teleport request had expired!");
					tpas.remove(player);
				}
			}
			
		}, 1200L);
		
		ChatUtils.systemMessage(player, "§d§lTELEPORT", "§eYou sent a teleport request to §b" + target.getDisplayName());
		
		TextComponent msg1 = new TextComponent(TextComponent.fromLegacyText("§eClick "));
		TextComponent msg2 = new TextComponent(TextComponent.fromLegacyText("§e or click "));
		
		TextComponent yes = new TextComponent(TextComponent.fromLegacyText("§a[Here to accept]"));
		yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpy " + player.getDisplayName()));
		yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§eClick me to run command: §b/tpy " + player.getDisplayName())));
		
		TextComponent no = new TextComponent(TextComponent.fromLegacyText("§c[Here to deny]"));
		no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpn " + player.getDisplayName()));
		no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§eClick me to run command: §b/tpn " + player.getDisplayName())));
		
		msg1.addExtra(yes);
		msg1.addExtra(msg2);
		msg1.addExtra(no);
		
		ChatUtils.systemMessage(target, "§d§lTELEPORT", "§b" + player.getDisplayName() + " §eSent you a teleport request!");
		ChatUtils.systemMessage(target, "§d§lTELEPORT", "§eYou have 60 seconds to accept before it expires!");
		ChatUtils.systemMessage(target, "§d§lTELEPORT", msg1);
		ChatUtils.systemMessage(target, "§d§lTELEPORT", "§eOr type §a/tpy " + player.getDisplayName() + " to accept §e or §c/tpn " + player.getDisplayName() + " §cto deny§e!");
		
		
		
		return true;
	}

}
