package BlockPlacement;

import net.minecraft.server.v1_8_R3.Block;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BlockPlaceUtils {
    public static Map<Player,Stack<BlockAction>> playerBlockactions = new HashMap<>();
    public static Map<Player,Stack<Location>> playerUndoBlockLocations = new HashMap<>();
    public static Map<Player,BlockAction> playerCloneStorage = new HashMap<>();


    public static void undo(Player p){
        if(playerBlockactions!=null &&playerBlockactions.containsKey(p)&&!playerBlockactions.get(p).empty()&&playerUndoBlockLocations!=null&&playerUndoBlockLocations.containsKey(p)){
            BlockAction action = playerBlockactions.get(p).pop();
            Location loc = playerUndoBlockLocations.get(p).pop();
            placeStructure(action, (int) loc.getX(), (int) loc.getY(), (int) loc.getZ(),p);


        }
    }

    public static void placeStructure(BlockAction toPlace, Integer x,Integer y,Integer z,Player placer){
        for(int i=1;i<=toPlace.structure[0].length;i++){
            for(int j=1;j<=toPlace.structure[0][0].length;j++){
                for(int k=1;k<=toPlace.structure.length;k++){
                    if(toPlace.structure[k-1][i-1][j-1]!=null){
                        placer.getWorld().getBlockAt(x+i,y+k,z+j).setType(toPlace.structure[k-1][i-1][j-1].material);
                        placer.getWorld().getBlockAt(x+i,y+k,z+j).setData(toPlace.structure[k-1][i-1][j-1].data);
                    }
                }
            }
        }
    }

}
