package net.itsrelizc.nerdbot.move.flight;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.nerdbot.Watcher;

public class Flight implements Listener {

    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String playername = player.getName();
        Block blockatfeet = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        
        Location fromloc = event.getFrom();
        Location toloc = event.getTo();
        
        System.out.print(fromloc + " " + toloc + " " + blockatfeet.getType());
        
        if ( blockatfeet.getType() == Material.AIR )
        {
            

            if ( ( fromloc.getBlockX() != toloc.getBlockX() || fromloc.getBlockZ() != toloc.getBlockZ() ) && fromloc.getBlockY() == toloc.getBlockY() )
            {
            	
            	event.setCancelled(true);
            	
                if( Watcher.suspoints.get(player) >= 100 )
                {
                    ChatUtils.systemMessage(player, "FLYING LLLLLL");
                }

                else
                {

                	RecordViolation(player, 1);
                    player.sendMessage( ChatColor.RED + "Warning" + ChatColor.WHITE + ": You've been acused of flying and have earned a violation point.");
                    // log.info( playername + " has earned a fly violation.");
                }

	}}}

	public static void RecordViolation(Player player, int penalty) {
		Watcher.moreSus(player, penalty);
	}
}