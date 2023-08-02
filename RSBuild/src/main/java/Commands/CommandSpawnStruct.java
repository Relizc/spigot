package Commands;

import BlockPlacement.StructSpawnUtils;
import BlockPlacement.StructTypes;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSpawnStruct implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(strings.length < 2){
                p.sendMessage("§4Wrong arguments!");
                return true;
            }
            byte data;
            Material mat;
            String blockString;
            switch(strings[0]){
                case "rect":
                    if(strings.length<5){
                        p.sendMessage("§4Not enough/too much arguments!");
                        return true;
                    }
                    data = 1;
                    blockString = strings[1];
                    mat = Material.matchMaterial(blockString);
                    if(mat==null){
                        p.sendMessage("§4No block named "+blockString);
                        return true;
                    }
                    for(int i=2;i<strings.length;i++){
                        try {
                            Integer d = Integer.parseInt(strings[i]);
                        }catch(NumberFormatException e){
                            p.sendMessage("§Wrong args!");
                            return true;
                        }
                    }
                    System.out.println(strings.length);

                    if(strings.length==6){
                        data = (byte) Integer.parseInt(strings[2]);
                        StructSpawnUtils.spawnStructure(StructTypes.RECT,mat,data,p, Arrays.copyOfRange(strings,3,6));
                    }else{

                        StructSpawnUtils.spawnStructure(StructTypes.RECT,mat,data,p, Arrays.copyOfRange(strings,2,5));
                    }

                    break;
                case "sphere":
                    if(strings.length<4){
                        p.sendMessage("§4Not enough/too mcuh arguments!");
                        return true;
                    }
                    data = 1;
                    blockString = strings[1];
                    mat = Material.matchMaterial(blockString);
                    if(mat==null){
                        p.sendMessage("§4No block named "+blockString);
                        return true;
                    }
                    for(int i=2;i<strings.length;i++){
                        try {
                            Integer d = Integer.parseInt(strings[i]);
                        }catch(NumberFormatException e){
                            p.sendMessage("§Wrong args!");
                            return true;
                        }
                    }
                    if(strings.length==5){
                        data = (byte) Integer.parseInt(strings[2]);
                        StructSpawnUtils.spawnStructure(StructTypes.CIRCULAR,mat,data,p, Arrays.copyOfRange(strings,3,5));
                    }else{
                        StructSpawnUtils.spawnStructure(StructTypes.CIRCULAR,mat,data,p, Arrays.copyOfRange(strings,2,4));
                    }
                case "cyl":
                    if(strings.length<5){
                        p.sendMessage("§4Not enough/too much arguments!");
                        return true;
                    }
                    data = 1;
                    blockString = strings[1];
                    mat = Material.matchMaterial(blockString);
                    if(mat==null){
                        p.sendMessage("§4No block named "+blockString);
                        return true;
                    }
                    for(int i=2;i<strings.length;i++){
                        try {
                            Integer d = Integer.parseInt(strings[i]);
                        }catch(NumberFormatException e){
                            p.sendMessage("§Wrong args!");
                            return true;
                        }
                    }
                    if(strings.length==6){
                        data = (byte) Integer.parseInt(strings[2]);
                        StructSpawnUtils.spawnStructure(StructTypes.CYLINDER,mat,data,p, Arrays.copyOfRange(strings,3,6));
                    }else{
                        StructSpawnUtils.spawnStructure(StructTypes.CYLINDER,mat,data,p, Arrays.copyOfRange(strings,2,5));
                    }

            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> ret=new ArrayList<>();
        List<Material> blocks = new ArrayList<>();
        for(Material i:Material.values()){
            if(i.isBlock()) blocks.add(i);
        }
        List<String> blockNames = new ArrayList<>();
        for(Material i:blocks){
            blockNames.add(i.toString());

        }
        if(strings.length >=1){
            return blockNames;
        }
        return null;
    }
}
