package Commands;

import Items.BuildersWand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGiveWand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            p.getInventory().addItem(BuildersWand.getWand());
            return true;
        }
        return false;
    }
}
