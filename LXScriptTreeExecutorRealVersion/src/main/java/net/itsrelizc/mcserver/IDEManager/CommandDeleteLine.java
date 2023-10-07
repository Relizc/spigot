package net.itsrelizc.mcserver.IDEManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDeleteLine implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(strings.length == 1){
                Integer line = Integer.parseInt(strings[0]);
                IDEState state = ControlIDEs.players.get(p);
                state.commands.remove(line-1);
                state.updateChat();
            }
        }
        return false;
    }
}
