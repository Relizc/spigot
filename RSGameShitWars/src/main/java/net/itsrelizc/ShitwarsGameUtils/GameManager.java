package net.itsrelizc.ShitwarsGameUtils;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.items.ItemGobackCompass;
import net.itsrelizc.mcserver.rsgameshitwars.RSGameShitwars;
import net.itsrelizc.menus.ClassicMenu;
import net.itsrelizc.menus.ObjectFunction;
import net.itsrelizc.menus.RunnableArgumentHolder;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager implements Listener {
    private List<Player> cops;private List<Player> bandits;private List<Player> hostages;
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
    public List<Player> selectingPlayers = new ArrayList<>();
    private List<Player> hostagesAlive;
    private Map<Player,Integer> kills = new HashMap<>();
    Scoreboard bigBoard;
    private Plugin thisPlugin;
    private Integer rounds = 5;
    private Integer copsScore = 0;
    private Integer banditsScore = 0;
    private int task1;

    public GameManager( List<Player> cops2,List<Player> bandits2,List<Player> hostages2){
        this.cops = cops2;
        this.bandits = bandits2;
        this.hostages = hostages2;
        this.hostagesAlive = hostages2;
        this.thisPlugin = Bukkit.getPluginManager().getPlugin("RSGameShitwars");
        bigBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        Bukkit.getPluginManager().registerEvents(this,this.thisPlugin);
        //registering cops
        Objective obj1 = bigBoard.registerNewObjective("RSGameShitwars","e");
        obj1.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj1.setDisplayName("SHITWARS");
        obj1.getScore("rounds left: "+this.rounds).setScore(4);
        obj1.getScore("cops score: "+this.copsScore).setScore(3);
        obj1.getScore("bandits score: "+this.banditsScore).setScore(2);
        obj1.getScore("hostages left: "+this.hostagesAlive.size()).setScore(1);






        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.thisPlugin, new Runnable() {
            @Override
            public void run() {
                Objective obj1 = bigBoard.registerNewObjective("RSGameShitwars","e");
                obj1.setDisplaySlot(DisplaySlot.SIDEBAR);
                obj1.setDisplayName("SHITWARS");
                obj1.getScore("rounds left: "+rounds).setScore(4);
                obj1.getScore("cops score: "+copsScore).setScore(3);
                obj1.getScore("bandits score: "+banditsScore).setScore(2);
                obj1.getScore("hostages left: "+hostagesAlive.size()).setScore(1);
                for(Player p:cops){
                    p.setScoreboard(bigBoard);
                    kills.put(p,0);
                }
                for(Player p:bandits){
                    p.setScoreboard(bigBoard);
                    kills.put(p,0);
                }
                for(Player p:hostages){
                    p.setScoreboard(bigBoard);
                    kills.put(p,0);
                }
            }
        },20L,1L);

    }
    public void newRound(){
        this.rounds -= 1;
        this.copsScore += this.hostagesAlive.size();
        this.banditsScore += this.hostages.size() - this.hostagesAlive.size();

        this.hostagesAlive = this.hostages;
        if(rounds <= 0 ){
            ChatUtils.broadcastSystemMessage("E","THE GAME HAS BECOME OVER!");
            if(this.copsScore > this.banditsScore){
                ChatUtils.broadcastSystemMessage("E","THE COPS WIN OZO");
            }else if(this.banditsScore > this.copsScore){
                ChatUtils.broadcastSystemMessage("E","bandits win");
            }else{
                ChatUtils.broadcastSystemMessage("e","draw");
            }
            for(Player p:Bukkit.getOnlinePlayers()){
                p.setGameMode(GameMode.SURVIVAL);
                p.setAllowFlight(true);
                p.getInventory().clear();
                p.getInventory().setItem(9, ItemGobackCompass.getItem());
                p.getInventory().setHeldItemSlot(9);

            }
            return;
        }
        for(Player cur:this.cops){
            cur.teleport(new Location(cur.getWorld(),-36d,65d,3d));
            cur.getInventory().clear();
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
        for(Player cur:this.bandits){
            cur.teleport(new Location(cur.getWorld(),32d,65d,-1d));
            cur.getInventory().clear();
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
        for(Player cur:this.hostages){
            cur.getInventory().clear();
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

        }
        final Integer[] selection = {60};
        task1 =  Bukkit.getScheduler().scheduleSyncRepeatingTask(this.thisPlugin, new Runnable() {
            @Override
            public void run() {
                selection[0] -= 1;
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

                    Bukkit.getScheduler().cancelTask(task1);
                }
            }
        },20L,20L);

    }
    @EventHandler
    public void onPlayerDeath(EntityDamageEvent e){
        Player p = e.getEntity() instanceof Player? (Player) e.getEntity() : null;
        if(p==null) return;
        if(e.getDamage() > p.getHealth()){
            e.setCancelled(true);
            p.setHealth(20d);
            ChatUtils.broadcastSystemMessage("SHITWARs","§7"+p.getName()+" HAS DIED!");
            p.setGameMode(GameMode.SPECTATOR);

            if(this.hostages.contains(p)){
                this.hostagesAlive.remove(p);
            }
            if(this.hostagesAlive.size() == 0){
                for(Player p1:Bukkit.getOnlinePlayers()){
                    p1.setGameMode(GameMode.SURVIVAL);

                }
                //New round.
                this.newRound();

            }
        }
    }
    @EventHandler
    public void onRightClickCompass(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(e.getItem()!=null&&e.getItem().equals(ItemGobackCompass.getItem())){
                WarpUtils.send(p,ServerCategory.LOBBY_MAIN);

            }
        }
    }

}
