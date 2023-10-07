package net.itsrelizc.mcserver.IDEManager;

import net.itsrelizc.mcserver.lxscripttreeexecutorrealversion.FileIOManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.naming.ldap.Control;
import java.util.List;

public class CommandQuit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            final Player p = (Player) commandSender;
            p.removePotionEffect(PotionEffectType.BLINDNESS);
            ControlIDEs.clearChat(p);
            final IDEState state = ControlIDEs.players.get(p);
            state.exitIDE();
            final List<String> result = state.commands;
            final StringBuilder realResult = new StringBuilder();
            for(String i:result){
                realResult.append("\n");
                realResult.append(i);

            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    String fName = ControlIDEs.awaitChatResponse(p);

                    FileIOManager.saveStringToFile("//scripts//"+p.getRealUUID()+"//"+fName+".lx", realResult.toString());

                    cancel();

                }
            }.runTask(Bukkit.getPluginManager().getPlugin("LXScriptTreeExecutorRealVersion"));

        }
        return false;
    }
}
