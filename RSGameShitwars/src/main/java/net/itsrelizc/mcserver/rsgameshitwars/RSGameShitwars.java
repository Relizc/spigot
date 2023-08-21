package net.itsrelizc.mcserver.rsgameshitwars;

import net.itsrelizc.ShitwarsGameUtils.GameManager;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.ObjectFunction;
import net.itsrelizc.menus.RunnableArgumentHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RSGameShitwars extends JavaPlugin implements Listener {
    public List<Player> selectingPlayers = new ArrayList<>();
    public GameManager manager;


    ObjectFunction func = new ObjectFunction(new Runnable() {
        @Override
        public void run() {
            InventoryClickEvent e = (InventoryClickEvent) RunnableArgumentHolder.arguments.get(1);
            Integer slot = e.getSlot();
            Player p = ((Player) e.getWhoClicked());
            if(slot==1){
                p.teleport(new Location(p.getWorld(),11d,9d,12d));
            }else if(slot==2){
                p.teleport(new Location(p.getWorld(),-30d,72d,17d));
            }else if(slot==3){
                p.teleport(new Location(p.getWorld(),18d,73d,-11d));
            }else if(slot==8){
                selectingPlayers.remove(p);
                p.closeInventory();
            }
        }
    });


    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this,this);
        func.acceptArgs(1,null);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private int task1;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        ChatUtils.broadcastSystemMessage("BAOZ","SUCKY "+e.getPlayer().getName()+" joined!["+Bukkit.getOnlinePlayers().size()+"]");
        if(Bukkit.getOnlinePlayers().size() >=10){
            ChatUtils.broadcastSystemMessage("SHITWARS","§7GAME START!");
            List<Player> ps = ((List<Player>) Bukkit.getOnlinePlayers());


            Collections.shuffle(ps);
            List<Player> cops = new ArrayList<>();
            List<Player> bandits = new ArrayList<>();
            List<Player> hostages = new ArrayList<>();
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
                cops.add(cur);



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
                bandits.add(cur);
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

                selectingPlayers.add(cur);
                hostages.add(cur);








            }
            manager = new GameManager(cops,bandits,hostages);
            final int[] selection = {60};

            task1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSGameShitwars"), new Runnable() {
                @Override
                public void run() {
                    selection[0] --;
                    if(!selectingPlayers.isEmpty()){
                        for(Player i:selectingPlayers){
                            if(selection[0] >= 0){
                                i.sendMessage("you're out of time.");
                                Bukkit.getScheduler().cancelTask(task1);

                            }else{
                            ClassicMenu menu = new ClassicMenu(i,1,"Select where to spawn");
                            menu.setClick(func);
                            menu.show();}
                        }
                    }else if(selectingPlayers.isEmpty()){
                        manager.newRound();
                        //Game start
                        Bukkit.getScheduler().cancelTask(task1);

                    }
                }
            },20L,20L);



            //-36 65 3
            //32 65 -1
            //11 9 12
            //-30 72 17
            //18 73 -11
        }
    }
}
