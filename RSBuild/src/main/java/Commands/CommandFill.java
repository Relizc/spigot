package Commands;

import Items.BuildersWand;
import Items.Space;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandFill implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(BuildersWand.playerSpaceHashMap.keySet().contains(p)){
                Space space = BuildersWand.playerSpaceHashMap.get(p);
                if(space.getPos1()==null){
                    p.sendMessage("§4Missing position 1");
                    return false;
                }
                if(space.getPos2() == null){
                    p.sendMessage("§4Missing position 2");
                    return false;
                }
                if(strings.length == 0){
                    p.sendMessage("§4Specify the block!");
                    return false;
                }
                String blockString = strings[0];
                Material block = Material.matchMaterial(blockString);
                if(block==null){
                    p.sendMessage("§4No block named "+blockString);
                    return false;
                }
                p.sendMessage("§gFilling between "+space.pos1x+" "+space.pos1y+" "+space.pos1z+
                        " and "+space.pos2x+" "+space.pos2y+" "+space.pos2z+"("+space.getVolume()+" blocks"+") with "+block.name());
                if(strings.length ==2){
                    byte data = 0;
                    try {
                        data = (byte) Integer.parseInt(strings[1]);
                    }catch (NumberFormatException e) {
                        p.sendMessage("§Data is not an Integer!");
                        p.sendMessage(e.getMessage());
                        return false;
                    }
                    space.fill(p,block,data);
                    return true;


                }
                space.fill(p,block);
                return true;




            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<Material> blocks = new ArrayList<>();
        for(Material i:Material.values()){
            if(i.isBlock()) blocks.add(i);
        }
        List<String> blockNames = new ArrayList<>();
        for(Material i:blocks){
            blockNames.add(i.toString());

        }
        if(strings.length == 1){
            if(Material.matchMaterial(strings[0])!=null){
                List<String> ret = new ArrayList<>();
                ret.add("<a number>");
                return ret;
            }
        }
        return blockNames;
    }
}
