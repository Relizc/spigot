package net.itsrelizc.mcserver.lxscripttreeexecutor.IDE;

import org.bukkit.entity.Player;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class IDEManager {
    public static List<Player> playersInIDE  = new ArrayList<>();
    public static void clearChat(Player p){
        for(int i=0;i<200;i++){
            p.sendMessage("");

        }
    }
    public IDEManager(Player p){
        this.player = p;

    }
    public void disPlaySelectScripts

}
