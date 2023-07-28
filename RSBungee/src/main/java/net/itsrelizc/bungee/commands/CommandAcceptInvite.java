package net.itsrelizc.bungee.commands;

import net.itsrelizc.players.InviteUtils;
import net.itsrelizc.players.InviteUtils.InviteToken;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.saltyfishstudios.bungee.ChatUtils;

public class CommandAcceptInvite extends Command {

	public CommandAcceptInvite() {
		super("acceptinvite");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (args.length >= 1) {
			String token = args[0];
			
			InviteToken status = InviteUtils.redeemToken((ProxiedPlayer) sender, token);
			
			if (status == null) {
				TextComponent a = new TextComponent("§cCannot accept invite! ");
				TextComponent b = new TextComponent("§7[Why can't I accept?]");
				ChatUtils.attachHover(b, "§eHere are the reasons about why an invite acception could fail:\n" + 
						"§b1. §eExpired! §7Each invitation can only last for one minute!\n" +
						"§b2. §eMigrated! §7The sender moved to another universe!\n" +
						"§b3. §eWrong One!? §7Probably you accepted the wrong invitation!\n\n" + 
						"§aStill unclear? Check out §e§nhttps://www.itsrelizc.net/ez-160§r§a");
				ChatUtils.attachLink(b, "https://www.itsrelizc.net/ez-160");
				a.addExtra(b);
				ChatUtils.systemMessage((ProxiedPlayer) sender, "§9§lINVITE", a);
				return;
			}
			
			WarpUtils.connectTo((ProxiedPlayer) sender, status.target);
			
			return;
		}
		
		ChatUtils.systemMessage((ProxiedPlayer) sender, "§9§lINVITE", "§cYou must specify an invite token!");
		
	}
	
	
	
}
