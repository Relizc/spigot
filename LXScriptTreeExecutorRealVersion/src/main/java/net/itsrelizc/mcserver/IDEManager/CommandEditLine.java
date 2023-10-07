package net.itsrelizc.mcserver.IDEManager;

import net.itsrelizc.mcserver.lxscripttreeexecutorrealversion.LXScriptTreeExecutorRealVersion;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandEditLine implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            final Player p = (Player) commandSender;
            if(strings.length == 1){
                final Integer line = Integer.parseInt(strings[0]);
                final IDEState state = ControlIDEs.players.get(p);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        String response = ControlIDEs.awaitChatResponse(p);
                        System.out.println(line-1);
                        System.out.println(response);

                        state.commands.set(line-1,response);
                        state.updateChat();
                        cancel();

                    }
                }.runTask(Bukkit.getPluginManager().getPlugin("LXScriptTreeExecutorRealVersion"));




            }
        }
        return false;
    }
}
