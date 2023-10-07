package net.itsrelizc.mcserver.Commands;

import net.itsrelizc.mcserver.IDEManager.IDEState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandOpenIDE implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,99999999,1));
        for(int i=0;i<=200;i++) p.sendMessage("");
        p.sendMessage("You are in the IDE now. Open your chat box to start typing.");
        IDEState state = new IDEState(p);
        state.openIDE();
        return true;
    }
}
