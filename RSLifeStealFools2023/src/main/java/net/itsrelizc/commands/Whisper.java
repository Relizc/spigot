package net.itsrelizc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.itsrelizc.lifesteal.ChatUtils;
import net.itsrelizc.lifesteal.JSON;

public class Whisper implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			JSONObject obj = JSON.loadDataFromDataBase("muted-players.json");
			
			if (obj.containsKey(player.getUniqueId().toString())) {
				
				JSONObject data = (JSONObject) obj.get(player.getUniqueId().toString());
				
				long secondsOffset = (((long) data.get("expires")) * 1000 - System.currentTimeMillis()) / 1000;
				
				if (secondsOffset <= 0) {
					obj.remove(player.getUniqueId().toString());
					JSON.saveDataFromDataBase("muted-players.json", obj);
				} else {
					long days = secondsOffset / 86400;
			    	long hours = (secondsOffset - (days * 86400)) / 3600;
			    	long minutes = (secondsOffset - (days * 86400) - (hours * 3600)) / 60;
			    	long seconds = secondsOffset % 60;
					
					String mutereason = "§c§m" + "-".repeat(50)
							+ "\n§cYou have been muted."
							+ "\n "
							+ "\n§eRemaining Time: §b" + days + "d " + hours + "h " + minutes + "m " + seconds + "s"
							+ "\n "
							+ "\n§eMute Reason: §b" + data.get("reason")
							+ "\n§eAssociated Mute ID: §b#" + ((String) data.get("id")).toUpperCase()
							+ "\n "
							+ "\n§aAppeals avaliable at §2§nhttps://relizc.github.io/appeals§r"
							+ "\n§c§m" + "-".repeat(50);
					
					player.sendMessage(mutereason);
					return true;
				}
				
				
			}
		}
			
		if (args.length < 2) {
			return false;
		}
		String senderName = "";
		if (sender instanceof Player) {
			senderName = ((Player) sender).getDisplayName();
		} else {
			senderName = "Console";
		}
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			ChatUtils.systemMessage(sender, "§3§lMESSAGE", "§cThat player isn't online!");
			return true;
		}
		
		String msg = "";
		for (int i = 1; i < args.length; i ++) {
			msg += args[i];
			if (i != args.length - 1) {
				msg += " ";
			}
		}
		ChatUtils.systemMessage(sender, "§3§lMESSAGE", "§eYou whisper to §b" + target.getDisplayName() + "§7: " + msg);
		ChatUtils.systemMessage(target, "§3§lMESSAGE", "§b" + senderName + " §ewhispers to you§7: " + msg);
		return true;
	}

}
