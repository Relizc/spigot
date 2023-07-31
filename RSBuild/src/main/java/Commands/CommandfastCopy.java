package Commands;

import Items.BuildersWand;
import Items.Space;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandfastCopy implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!BuildersWand.playerSpaceHashMap.isEmpty() && BuildersWand.playerSpaceHashMap.containsKey(p)) {
                Space space = BuildersWand.playerSpaceHashMap.get(p);
                if (space.getPos1() == null) {
                    p.sendMessage("§4Missing position 1");
                    return false;
                }
                if (space.getPos2() == null) {
                    p.sendMessage("§4Missing position 2");
                    return false;
                }
                p.sendMessage("§Copying "+space.getVolume()+" blocks");
                space.quickCopy(p);
                return true;

            }
        }
        return true;
    }
}
