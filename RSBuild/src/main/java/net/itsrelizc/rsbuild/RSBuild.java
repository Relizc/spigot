package net.itsrelizc.rsbuild;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Commands.*;
import Items.BuildersWand;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.players.Grouping;
import net.itsrelizc.players.Profile;
public final class RSBuild extends JavaPlugin implements Listener {
    private List<Player> opPlayers = new ArrayList<>();
    private String[] viableCommands = {
            "tp",
            "setblock",
            "fill",
            "summon",
            "give",
            "buildinfo",
            "lobby",
            "fastfill",
            "givewand",
            "undoplacement",
            "l",
            "ragequit",
            "setpos",
            "fastclone",
            "placecopied",
            "rotateleft",
            "rotatevertical",
            "spawnstruct"
    };
    private String[] ungoodArguments = {
            "PrimedTnt",
            "tnt"


    };

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new BuildersWand(),this);
        Bukkit.getPluginCommand("buildinfo").setExecutor(new CommandInfo());
        Bukkit.getPluginCommand("fastfill").setExecutor(new CommandFill());
        Bukkit.getPluginCommand("givewand").setExecutor(new CommandGiveWand());
        Bukkit.getPluginCommand("undoplacement").setExecutor(new CommandUndo());
        Bukkit.getPluginCommand("setpos").setExecutor(new CommandSetpos());
        Bukkit.getPluginCommand("fastclone").setExecutor(new CommandfastCopy());
        Bukkit.getPluginCommand("placecopied").setExecutor(new Commandplacecopied());
        Bukkit.getPluginCommand("rotateleft").setExecutor(new CommandRotateLeft());

        Bukkit.getPluginCommand("head").setExecutor(new CommandHead());

        Bukkit.getPluginCommand("rotatevertical").setExecutor(new CommandRotateVertical());
        Bukkit.getPluginCommand("spawnstruct").setExecutor(new CommandSpawnStruct());


        Grouping.showPrefix = true;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSBuild"), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("§5SERVER SAVING");
            }
        },600,12000);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSBuild"), new Runnable() {
            @Override
            public void run() {
                Integer pos1x=-1,pos1y= -1,pos1z= -1,pos2x= -1,pos2y= -1,pos2z = -1;
                for(Player p:Bukkit.getOnlinePlayers()){
                    if(!BuildersWand.playerSpaceHashMap.isEmpty()){
                        if(BuildersWand.playerSpaceHashMap.containsKey(p)){

                            pos1x = BuildersWand.playerSpaceHashMap.get(p).pos1x;
                            pos1y = BuildersWand.playerSpaceHashMap.get(p).pos1y;
                            pos1z = BuildersWand.playerSpaceHashMap.get(p).pos1z;
                            pos2x = BuildersWand.playerSpaceHashMap.get(p).pos2x;
                            pos2y = BuildersWand.playerSpaceHashMap.get(p).pos2y;
                            pos2z = BuildersWand.playerSpaceHashMap.get(p).pos2z;
                        }
                    }
                    ChatUtils.sendActionBar(p,"P1:"+pos1x+" "+pos1y+" "+pos1z+"|P2:"+pos2x+" "+pos2y+" "+pos2z);
                }

            }
        },0,1);
    }

    public void saveWorlds(String world) {
        getLogger().info("Saving and Copying " + world);
        Bukkit.getServer().unloadWorld(Bukkit.getWorld(world), true);
        File sourceDirectory = new File(System.getProperty("user.dir") + "\\" + world);
        File destinationDirectory = new File(new File(System.getProperty("user.dir")).getParentFile().getParentFile().toString() + "\\templates\\_worlds\\_" + world + "_buildpublic");
        try {
            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
        } catch (IOException ignored) {

        }
    }

    public void end() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer("§cServer is closing!");
        }
        for (World world : Bukkit.getWorlds()) {
        	world.save();
            saveWorlds(world.getName());
        }
        getLogger().info("All worlds saved! to templates for later loading");
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.CREATIVE);
        Player p = (Player) event.getPlayer();
        if(!p.isOp()) p.setOp(true);
        else opPlayers.add(p);

        event.setJoinMessage(Profile.coloredName(event.getPlayer()) + " §bjoined the public building server! §8(" + Bukkit.getOnlinePlayers().size() + " player" + ChatUtils.plural(Bukkit.getOnlinePlayers().size()) +")");
    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void join(PlayerQuitEvent event) {
        if(!opPlayers.contains(event.getPlayer())) {event.getPlayer().setOp(false);event.getPlayer().setGameMode(GameMode.SURVIVAL);}
        event.setQuitMessage(Profile.coloredName(event.getPlayer()) + " §bleft the public building server! §8(" + (Bukkit.getOnlinePlayers().size() - 1) + " player" + ChatUtils.plural(Bukkit.getOnlinePlayers().size() - 1) +")");
    }
    @EventHandler
    public void commandPreProcess(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(!opPlayers.contains(p)){
            String betterMessage = e.getMessage().substring(1);
            String[] args1 = (e.getMessage().split(" "));
            List<String> args = new ArrayList<>();
            for(String i:args1){
                if(!i.startsWith("/")){
                    args.add(i);

                }
            }
            List<String> canRun = new ArrayList<>();
            for(String i:viableCommands){
                canRun.add(i);

            }
            List<String> notGoodArgs = new ArrayList<>();
            for(String i:ungoodArguments) notGoodArgs.add(i);

            if(canRun.contains(betterMessage)){
                if(!notGoodArgs.contains(args.get(0))){
                    e.setCancelled(false);
                    return;
                }
            }
            ChatUtils.sendActionBar(p,"You can't send that command!");
            e.setCancelled(true);
        }
    }
    @Override
    public void onDisable() {
        end();
    }
}
