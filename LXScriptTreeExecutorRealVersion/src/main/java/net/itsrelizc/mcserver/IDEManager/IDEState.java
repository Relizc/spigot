package net.itsrelizc.mcserver.IDEManager;

import net.itsrelizc.mcserver.lxscripttreeexecutorrealversion.FileIOManager;
import net.itsrelizc.mcserver.lxscripttreeexecutorrealversion.LXScriptTreeExecutorRealVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IDEState {
    public Player p;
    public List<String> commands = new ArrayList<>();
    public IDEState(Player p){
        this.p = p;
    }
    public void openIDE(){
        ControlIDEs.players.put(this.p,this);

    }
    public void exitIDE(){
        ControlIDEs.players.remove(this.p);

    }
    public void openFile(String fname){

        String s = FileIOManager.getStringFromFile("//scripts//"+this.p.getRealUUID()+"//"+fname+".lx");


        List<String> d = Arrays.asList(
                s.split("\n")
        );
        ControlIDEs.players.put(this.p,this);
        System.out.println("D"+d.get(0));
        this.commands.clear();
        this.commands.addAll(d);


        this.updateChat();
    }
    public void updateChat(){
        ControlIDEs.clearChat(this.p);
        Integer d = 1;
        for(String i:this.commands){
            p.sendMessage(ChatColor.RED + Integer.toString(d) +" "+ChatColor.RESET+ i);
            d++;

        }
    }
}
