package Commands;

import BlockPlacement.BlockAction;
import BlockPlacement.BlockPlaceUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandplacecopied implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!BlockPlaceUtils.playerCloneStorage.isEmpty()&&BlockPlaceUtils.playerCloneStorage.containsKey(p)){
                BlockAction action = BlockPlaceUtils.playerCloneStorage.get(p);
                BlockPlaceUtils.placeStructure(action,p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ(),p);
            }
        }
        return true;
    }
}
