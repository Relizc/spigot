package net.itsrelizc.buildtools;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.md_5.bungee.api.chat.TextComponent;

public class SummonColorHologram implements CommandExecutor {
	
	public static CraftArmorStand f(Location location, String string) {
		CraftArmorStand e = (CraftArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		e.getHandle().setInvisible(true);
		e.getHandle().setCustomName(ChatColor.translateAlternateColorCodes('&', string));
		e.getHandle().setCustomNameVisible(true);
		e.setGravity(false);
		return e;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			String uuid = args[1];
			Player player = (Player) sender;
			
			for (Entity en : player.getWorld().getEntities()) {
				if (en.getUniqueId().toString().equalsIgnoreCase(uuid)) {
					en.remove();
					ChatUtils.systemMessage(player, "§6§lBT", "§aRemoved Hologram named " + en.getCustomName() + "§r§a!");
					return true;
				}
			}
			
			ChatUtils.systemMessage(player, "§6§lBT", "§cI cannot find that hologram! But don't panic! This mainly happens because that hologram is already deleted!");
			return true;
		}
		
		if (args.length >= 3) {
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			double z = Double.parseDouble(args[2]);
			String q = "";
			for (int i = 3; i < args.length; i ++) {
				q += args[i];
				if (i != args.length - 1) {
					q += " ";
				}
			}
			Player p = (Player) sender;
			CraftArmorStand e = f(new Location(p.getWorld(), x, y, z), q);
			
			TextComponent a = new TextComponent("§aSummoned new Color Hologram at " + x + " " + y + " " + z + " ");
			TextComponent b = new TextComponent("§c§l[Undo]");
			ChatUtils.attachCommand(b, "/cholo remove " + e.getUniqueId().toString(), null);
			a.addExtra(b);
			
			ChatUtils.systemMessage(p, "§6§lBT", a);
			return true;
		} else if (args.length >= 1) {
			Player p = (Player) sender;
			String q = "";
			for (int i = 0; i < args.length; i ++) {
				q += args[i];
				if (i != args.length - 1) {
					q += " ";
				}
			}
			
			CraftArmorStand e = f(p.getLocation(), q);
			
			double rx = Math.round(p.getLocation().getX() * 100.0) / 100.0;
			double ry = Math.round(p.getLocation().getY() * 100.0) / 100.0;
			double rz = Math.round(p.getLocation().getZ() * 100.0) / 100.0;
			
			TextComponent a = new TextComponent("§aSummoned new Color Hologram at " + rx + " " + ry + " " + rz + " ");
			TextComponent b = new TextComponent("§c§l[Undo]");
			ChatUtils.attachCommand(b, "/cholo remove " + e.getUniqueId().toString(), null);
			a.addExtra(b);
			
			ChatUtils.systemMessage(p, "§6§lBT", a);
			
			return true;
		} else {
			return false;
		}
	}
	
}
