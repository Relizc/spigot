package net.itsrelizc.mcserver.IDEManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlIDEs implements Listener {
    public static Map<Player,Boolean> sentMsg = new HashMap<>();
    public static Map<Player,String> sentMessageStrings = new HashMap<>();
    public static Map<Player,Thread> playerThreads = new HashMap<>();

    public static Map<Player,IDEState> players = new HashMap<>();
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(sentMsg.containsKey(p)){
            e.setCancelled(true);
            sentMsg.put(p,true);



            sentMessageStrings.put(p,e.getMessage());
            playerThreads.get(p).resume();
            playerThreads.remove(p);
            return;
        }

        if(players.containsKey(p)){
            e.setCancelled(true);
            IDEState state = players.get(p);
            state.commands.add(e.getMessage());
            p.sendMessage(ChatColor.RED + Integer.toString(state.commands.size()) +" "+ChatColor.RESET+ e.getMessage());

        }

    }
    public static void clearChat(Player p){
        for(int i=0;i<=200;i++){
            p.sendMessage("");
        }
    }
    public static String liner(String s,Integer line){
        return ChatColor.RED + Integer.toString(line) +" "+ChatColor.RESET+ s;
    }

    public static String awaitChatResponse(Player p){
        sentMsg.put(p,false);

        int i;
        playerThreads.put(p,Thread.currentThread());

        System.out.println("Suspending thread");

        Thread.currentThread().suspend();
        System.out.println("Resuming thread");


//        while(!sentMsg.get(p)){
//            i=1;
//        }
        if(sentMsg.get(p)){
            sentMsg.remove(p);
            String ret = sentMessageStrings.get(p);
            sentMessageStrings.remove(p);
            return ret;
        }
        return "";
    }


    public static String awaitChatResponse(Player p,long duration){
        sentMsg.put(p,false);
        int i;
        long cur = System.currentTimeMillis();
        while(!sentMsg.get(p)){
            if(System.currentTimeMillis() - cur >= duration) break;
            i=1;
        }
        if(sentMsg.get(p)){
            sentMsg.remove(p);

            String ret = sentMessageStrings.get(p);
            sentMessageStrings.remove(p);
            return ret;
        }
        return "";
    }

}
