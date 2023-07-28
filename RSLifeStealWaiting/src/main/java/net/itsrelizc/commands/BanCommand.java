package net.itsrelizc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.itsrelizc.lifesteal.waiting.ChatUtils;
import net.itsrelizc.lifesteal.waiting.JSON;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (args.length < 3) {
    		return false;
    	}
    	JSONObject obj = JSON.loadDataFromDataBase("banned-players.json");
    	
    	Player player = Bukkit.getPlayer(args[0]);
    	if (player == null) {
    		ChatUtils.systemMessage(sender, "§e§lBANS", "§cPlayer §7" + args[0] + "§c is currently offline or had never joined this smp yet. :(");
    		return true;
    	}
    	
    	JSONObject hash = new JSONObject();
    	String reason = "";
    	for (int i = 1; i < args.length; i ++) {
    		reason += args[i] += " ";
    	}
    	hash.put("reason", "\u00a7bBanned by an operator: \u00a7e" + reason);
    	hash.put("id", "rickastley");
    	
    	obj.put(player.getUniqueId().toString(), hash);
    	
    	JSON.saveDataFromDataBase("banned-players.json", obj);
    	
    	String mutereason = "§cYou have been §lBANNED §r§cfrom joining §e§lSMP.ITSRELIZC.NET§r§c.\n"
				+ "§eRemaining Time: §7(Sorry, SMP bans are permanent. Or Im just too lazy to make this)"
				+ "\n\n§eBanned Reason: §b" + hash.get("reason")
				+ "\n§eAssociated Ban ID: §b#" + ((String) hash.get("id")).toUpperCase()
				+ "\n\n"
				+ "§7To appeal for your ban, please visit the link below. You are required"
				+ "\n§7to provide your Ban ID and sufficient evidence."
				+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
				+ "\n§7decision will be final and appeals will no longer be accepted."
				+ "\n\n§aAppeals avaliable at §2§nhttps://sfn.gg/appeals §aor via §9Microsoft Teams";
    	
    	player.kickPlayer(mutereason);
    	
    	ChatUtils.systemMessage(sender, "§e§lBANS", "§aSucessfully banned player " + player.getDisplayName() + " §7(UUID: " + player.getUniqueId().toString() + ")");
    	
        return true;
    }
}