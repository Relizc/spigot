package net.itsrelizc.mcserver.lxscripttreeexecutor.Commands;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.mcserver.lxscripttreeexecutor.IDE.IDEManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandEnterIDE implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,9999999,1));
        ChatUtils.systemMessage(p,"You are in the IDE now. Open your chat to start coding.");
        IDEManager.playersInIDE.add(p);
        IDEManager.clearChat(p);
        IDEManager thisManager = new IDEManager(p);
        return true;

    }
}


