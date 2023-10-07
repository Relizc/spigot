package net.itsrelizc.mcserver.Commands;

import net.itsrelizc.mcserver.lxscripttreeexecutorrealversion.FileIOManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetOnLoad implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length ==1){
            if(commandSender instanceof Player){
                Player p = (Player) commandSender;
                FileIOManager.saveStringToFile("properties.lxp",strings[0]);

            }
        }
        return false;
    }
}
