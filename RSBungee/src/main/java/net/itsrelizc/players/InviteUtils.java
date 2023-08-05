package net.itsrelizc.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InviteUtils {
	
	public static List<InviteToken> tokens = new ArrayList<InviteToken>();
	
	public static class InviteToken {
		
		public ProxiedPlayer sender;
		public ProxiedPlayer reciever;
		public String token;
		public long expires;
		public ServerInfo target;
		
		private InviteToken(ProxiedPlayer sender, ProxiedPlayer reciever, ServerInfo id, long expireSeconds) {
			this.sender = sender;
			this.reciever = reciever;
			
			this.token = getRandomHexString(16);
			this.expires = System.currentTimeMillis() + expireSeconds * 1000;
			
			this.target = id;
		}
		
		
		
		private String getRandomHexString(int numchars){
	        Random r = new Random();
	        StringBuffer sb = new StringBuffer();
	        while(sb.length() < numchars){
	            sb.append(Integer.toHexString(r.nextInt()));
	        }

	        return sb.toString().substring(0, numchars);
	    }
		
	}
	
	public static InviteToken generateNewToken(ProxiedPlayer sender, ProxiedPlayer reciever, long time) {
		InviteToken t = new InviteToken(sender, reciever, reciever.getServer().getInfo(), time);
		InviteUtils.tokens.add(t);
		
		return t;
	}
	
	public static InviteToken findByToken(String token) {
		for (InviteToken t : tokens) {
			if (t.token.equalsIgnoreCase(token)) {
				return t;
			}
		}
		
		return null;
	}
	
	public static InviteToken redeemToken(ProxiedPlayer redemmer, String token) {
		InviteToken t = findByToken(token);
		
		if (t == null) System.out.print(1);
		if (t.reciever != redemmer) System.out.print(2);
		if (System.currentTimeMillis() > t.expires) System.out.print(3);
		if (t.sender.getServer().getInfo().getName() != t.target.getName()) System.out.print(4);
		
		if (t == null) return null; // Already redeemed!
		if (t.reciever != redemmer) return null; // Not yours!
		if (System.currentTimeMillis() > t.expires) return null; // Expired!
		if (t.sender.getServer().getInfo().getName() != t.target.getName()) return null; // Not the correct destination!
		
		// Add garbage token collector
		
		tokens.remove(t);
		
		return t;
	}
	
	
	
}
