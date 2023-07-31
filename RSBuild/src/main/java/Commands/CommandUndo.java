package Commands;

import BlockPlacement.BlockPlaceUtils;
import net.minecraft.server.v1_8_R3.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUndo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(BlockPlaceUtils.playerBlockactions!=null && BlockPlaceUtils.playerBlockactions.containsKey(p)){
                if(!BlockPlaceUtils.playerBlockactions.get(p).empty()){
                    BlockPlaceUtils.undo(p);
                    return true;
                }

            }
            p.sendMessage("§You don't have any past actions!");
        }
        return true;
    }

}
