package net.itsrelizc.commands;

import net.itsrelizc.gamemodes.mapbuilding.UMapBuilding;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.List;

public class CommandAcceptGuidelines implements IBaseCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        try {
            UMapBuilding.addGuideLinePlayer(p);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        p.openInventory(Bukkit.createInventory(null,9));
        p.closeInventory();


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
