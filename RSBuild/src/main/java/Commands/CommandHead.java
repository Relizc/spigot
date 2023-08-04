package Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.commands.IBaseCommand;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.menus.Skull;

public class CommandHead implements IBaseCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			ChatUtils.systemMessage(sender, "§6§lBT", "§cYou need to specify a minecraft texture ID!");
			return true;
		}
		
		Player player = (Player) sender;
		
		String id = args[0];
		
		player.getInventory().addItem(Skull.getCustomSkull("http://textures.minecraft.net/texture/" + id));
		
		ChatUtils.systemMessage(sender, "§6§lBT", "§aGive skull with ID §b" + id);
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return ChatUtils.fromNewList();
	}
	
}
