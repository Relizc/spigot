package net.itsrelizc.industrial;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.itsrelizc.workblocks.BasicWorkBlock;
import net.itsrelizc.worldloader.BlockManager;
import net.itsrelizc.worldloader.WorldLoader;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable(){
        System.out.println("[RSIndustrial] SMP-Industrial enabled");
        
        //BasicWorkBlock work = new BasicWorkBlock(new Location(Bukkit.getWorld("world"), 0, 63, 0));
        
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    
    @EventHandler
    public void PlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Chunk isNewChunk = player.getLocation().getChunk();
        
        if (event.getFrom().getChunk().equals(event.getTo().getChunk())) {
        	// chunk are equal
    	} else {
    		player.sendMessage("You moved!");
    		BlockManager.initlizeBlockInChunk(event.getTo().getChunk());
    	}
    }

}
