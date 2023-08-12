package net.itsrelizc.mcserver.rsgameshitwars;

import net.itsrelizc.global.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RSGameShitwars extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this,this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        ChatUtils.broadcastSystemMessage("BAOZ","SUCKY "+e.getPlayer().getName()+" joined!["+Bukkit.getOnlinePlayers().size()+"]");
        if(Bukkit.getOnlinePlayers().size() >=10){
            ChatUtils.broadcastSystemMessage("SHITWARS","§7GAME START!");
            List<Player> ps = ((List<Player>) Bukkit.getOnlinePlayers());


            Collections.shuffle(ps);
            for(int i=0;i<4;i++){
                Player cur = ps.get(i);
                cur.teleport(new Location(cur.getWorld(),-36d,65d,3d));
                cur.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                cur.getInventory().addItem(new ItemStack(Material.BOW));
                ItemStack arrows = new ItemStack(Material.ARROW);
                arrows.setAmount(64);

                cur.getInventory().addItem(arrows);
                cur.getInventory().setArmorContents(
                        new ItemStack[]{new ItemStack(Material.IRON_HELMET),
                        new ItemStack(Material.IRON_CHESTPLATE),
                                new ItemStack(Material.IRON_LEGGINGS),
                                new ItemStack(Material.IRON_BOOTS)
                        }
                );
                cur.getInventory().addItem(new ItemStack(Material.FISHING_ROD));




            }
            for(int i=4;i<8;i++){
                Player cur = ps.get(i);
                cur.teleport(new Location(cur.getWorld(),32d,65d,-1d));
                cur.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                cur.getInventory().addItem(new ItemStack(Material.BOW));
                ItemStack arrows = new ItemStack(Material.ARROW);
                arrows.setAmount(64);

                cur.getInventory().addItem(arrows);
                cur.getInventory().setArmorContents(
                        new ItemStack[]{new ItemStack(Material.IRON_HELMET),
                                new ItemStack(Material.IRON_CHESTPLATE),
                                new ItemStack(Material.IRON_LEGGINGS),
                                new ItemStack(Material.IRON_BOOTS)
                        }
                );
                cur.getInventory().addItem(new ItemStack(Material.FISHING_ROD));

            }
            for(int i=8;i<10;i++){
                Player cur = ps.get(i);



                cur.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
                cur.getInventory().addItem(new ItemStack(Material.ENDER_PEARL,64));
                cur.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,8));
                cur.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,999999,1));

                cur.getInventory().setArmorContents(
                        new ItemStack[]{new ItemStack(Material.LEATHER_HELMET),
                                new ItemStack(Material.LEATHER_CHESTPLATE),
                                new ItemStack(Material.LEATHER_LEGGINGS),
                                new ItemStack(Material.LEATHER_BOOTS)
                        }
                );



            }



            //-36 65 3
            //32 65 -1
            //11 9 12
            //-30 72 17
            //18 73 -11
        }
    }
}
