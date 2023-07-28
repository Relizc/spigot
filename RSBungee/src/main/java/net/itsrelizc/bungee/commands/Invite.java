package net.itsrelizc.bungee.commands;

import net.itsrelizc.players.InviteUtils;
import net.itsrelizc.players.InviteUtils.InviteToken;
import net.itsrelizc.players.Profile;
import net.itsrelizc.players.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.saltyfishstudios.bungee.ChatUtils;

public class Invite extends Command {

	public Invite() {
		super("invite", null, "i", "sendinvite", "sendinvitetoken");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (args.length >= 1) {
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
			
			if (target == null) {
				ChatUtils.systemMessage((ProxiedPlayer) sender, "§9§lINVITE", "§cCan't find a player with that name!");
				return;
			}
			
			Profile tar = Profile.findByOwner(target);
			
			InviteToken t = InviteUtils.generateNewToken((ProxiedPlayer) sender, target, 60);
			
			TextComponent a = new TextComponent("§bYou just sent an universe-o-warp invite to " + Rank.findByPermission(tar.permission).rankColor() + target.getDisplayName() + "§b! ");
			TextComponent b = new TextComponent("§7[Token Information]");
			ChatUtils.attachHover(b, "§aToken: §b" + t.token + "\n§aInviter: §b" + t.sender.getDisplayName() + "\n§aAccepter: §b" + t.reciever.getDisplayName() + "\n\n§eThis token will expire in §b1 minute§e!");
			a.addExtra(b);
			
			ChatUtils.systemMessage((ProxiedPlayer) sender, "§9§lINVITE", a);
			
			String qq = Rank.findByPermission(Profile.findByOwner((ProxiedPlayer) sender).permission).rankColor();
			
			ChatUtils.systemMessage(target, "§9§lINVITE", qq + ((ProxiedPlayer) sender).getDisplayName() + 
					" §bwants you to join their universe!");
			
			TextComponent c = new TextComponent("§bUse ");
			TextComponent f = new TextComponent("§e/invite accept " + ((ProxiedPlayer) sender).getDisplayName());
			ChatUtils.attachCommand(f, "invite accept " + ((ProxiedPlayer) sender).getDisplayName(), null);
			TextComponent g = new TextComponent(" §bor ");
			TextComponent d = new TextComponent("§e[Click Here]");
			ChatUtils.attachCommand(d, "acceptinvite " + t.token, "§bClick here to join " + qq + ((ProxiedPlayer) sender).getDisplayName() + "§b's universe!\n§eThis token will expire in §b1 minute§e!");
			TextComponent e = new TextComponent(" §bto join their universe!");
			
			c.addExtra(f);
			c.addExtra(g);
			c.addExtra(d);
			c.addExtra(e);
			
			ChatUtils.systemMessage(target, "§9§lINVITE", c);
		}
		
		
		
	}
	
	
	
}
