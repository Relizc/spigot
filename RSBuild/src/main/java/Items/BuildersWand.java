package Items;

import BlockPlacement.FastBlockPlacement;
import net.itsrelizc.global.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildersWand implements Listener {

    public static Map<Player,Space> playerSpaceHashMap = new HashMap<>();
    public static ItemStack getWand(){
        ItemStack wand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName("§k##§r§gBUILDER'S WAND§f§k##");
        List<String> lore = new ArrayList<>();
        lore.add("Works just like the worldedit wand!");
        lore.add("use the command /fillwand to fill in the space.");
        meta.setLore(lore);
        wand.setItemMeta(meta);
        return wand;
    }
    @EventHandler
    public void onDestroy(BlockBreakEvent e){
        if(e.getPlayer().getItemInHand().equals(getWand())){
            e.setCancelled(true);
            Player p = e.getPlayer();
            if(playerSpaceHashMap.isEmpty()||!playerSpaceHashMap.containsKey(e.getPlayer())){
                playerSpaceHashMap.put(e.getPlayer(),new Space());
            }
            if(playerSpaceHashMap.get(p).getPos1()!=null&&playerSpaceHashMap.get(p).getPos2()==null){
                Integer x = e.getBlock().getX();
                Integer y = e.getBlock().getY();
                Integer z = e.getBlock().getZ();

                p.sendMessage("§6Setting position 2 to "+x+" "+y+" "+z);
                p.sendMessage("§4§lPosition 1 and 2 are both set. Shift right click to clear both positions.");
                playerSpaceHashMap.get(p).setPos2(x,y,z);

            }else if(playerSpaceHashMap.get(p).getPos1()==null&&playerSpaceHashMap.get(p).getPos2()==null){
                Integer x = e.getBlock().getX();
                Integer y = e.getBlock().getY();
                Integer z = e.getBlock().getZ();
                p.sendMessage("§6Setting position 1 to "+x+" "+y+" "+z);
                playerSpaceHashMap.get(p).setPos1(x,y,z);
            }else if(playerSpaceHashMap.get(p).getPos1()!=null&&playerSpaceHashMap.get(p).getPos2()!=null){
                Integer x = e.getBlock().getX();
                Integer y = e.getBlock().getY();
                Integer z = e.getBlock().getZ();
                p.sendMessage("§6Setting position 1 to "+x+" "+y+" "+z);
                p.sendMessage("§4 Set position 2 to null(click again to set to a new position)");
                playerSpaceHashMap.get(p).setPos2(null,null,null);

                playerSpaceHashMap.get(p).setPos1(x,y,z);
            }
        }
    }
    @EventHandler
    public void onShiftRightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(p.getItemInHand() == getWand()){
            if(p.isSneaking()){
                playerSpaceHashMap.get(p).setPos1(null,null,null);
                playerSpaceHashMap.get(p).setPos2(null,null,null);
                p.sendMessage("§3Cleared all positions.");

            }
        }
    }

}
