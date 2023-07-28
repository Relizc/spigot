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
import net.itsrelizc.utils.Rank;

public class RankCommand implements CommandExecutor {
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
    		ChatUtils.systemMessage(sender, "§6§lRANKS", "§cPlayer §7" + args[0] + "§c is currently offline or had never joined this smp yet. :(");
    		return true;
    	}
    	
    	JSONObject data = JSON.loadDataFromDataBase("players.json");
    	JSONObject ob = (JSONObject) data.get(player.getUniqueId().toString());
    	ob.put("rank", Integer.valueOf(args[1]));
    	data.put(player.getUniqueId().toString(), ob);
    	JSON.saveDataFromDataBase("players.json", data);
    	
    	player.kickPlayer("§aYour rank has been changed! Please reconnect!");
    	ChatUtils.systemMessage(sender, "§e§lRANKS", "§aSucessfully changed rank of player " + player.getDisplayName() + " §7(UUID: " + player.getUniqueId().toString() + ") §ato rank §7" + Rank.findByPermission(Integer.valueOf(args[1])));
    	
        return true;
    }
}