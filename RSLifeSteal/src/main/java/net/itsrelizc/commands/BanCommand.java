package net.itsrelizc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.JSON;
import net.itsrelizc.utils.BanUtils;
import net.itsrelizc.utils.PlayerManager;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) {
    		Player p = (Player) sender;
    		if (!PlayerManager.ranks.get(p).useop) {
    			ChatUtils.systemMessage(p, "§c§lCOMMAND", "§cYou do not have permission to do this!");
    			return true;
    		}
    		
    		Player c = Bukkit.getPlayer(args[0]);
        	if (c == null) {
        		ChatUtils.systemMessage(sender, "§6§lRANKS", "§cPlayer §7" + args[0] + "§c is currently offline or had never joined this smp yet. :(");
        		return true;
        	}
    		if (PlayerManager.ranks.get(p).level <= PlayerManager.ranks.get(c).level) {
    			ChatUtils.systemMessage(p, "§c§lCOMMAND", "§cYou do not have permission to change this player's rank!");
    			return true;
    		}
    	}
    	
    	if (args.length < 2) {
    		return false;
    	}
    	
    	Player player = Bukkit.getPlayer(args[0]);
    	if (player == null) {
    		ChatUtils.systemMessage(sender, "§e§lBANS", "§cPlayer §7" + args[0] + "§c is currently offline or had never joined this smp yet. :(");
    		return true;
    	}
    	
    	String reason = "";
    	for (int i = 1; i < args.length; i ++) {
    		reason += args[i] += " ";
    	}
    	
    	BanUtils.createBan(player, "\u00a7bBanned by an operator: \u00a7e" + reason);
    	ChatUtils.systemMessage(sender, "§e§lBANS", "§aSucessfully banned player " + player.getDisplayName() + " §7(UUID: " + player.getUniqueId().toString() + ")");
    	
        return true;
    }
}