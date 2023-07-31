package Commands;

import Items.BuildersWand;
import Items.Space;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandSetpos implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){

            Player p = (Player) commandSender;
            if(strings.length ==0){
                p.sendMessage("§4Please enter what position to set.");
                return true;
            }
            if(strings.length >1){
                p.sendMessage("§4Too many arguments!(expecting 1, got "+strings.length+")");
                return true;
            }
            if(BuildersWand.playerSpaceHashMap==null||!BuildersWand.playerSpaceHashMap.containsKey(p))BuildersWand.playerSpaceHashMap.put(p,new Space());

            if(Objects.equals(strings[0], "1")){
                BuildersWand.playerSpaceHashMap.get(p).setPos1(p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ());
                p.sendMessage("§gSet position 1 to:"+p.getLocation().getBlockX()+" "+p.getLocation().getBlockY()
                        +" "+p.getLocation().getBlockZ());
            }else if(Objects.equals(strings[0], "2")){
                BuildersWand.playerSpaceHashMap.get(p).setPos2(p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ());
                p.sendMessage("§gSet position 2 to:"+p.getLocation().getBlockX()+" "+p.getLocation().getBlockY()
                        +" "+p.getLocation().getBlockZ());
            }else{
                p.sendMessage("§Unrecognized argument.");
                return true;
            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> ret = new ArrayList<>();
        ret.add("1");
        ret.add("2");
        return ret;
    }
}
