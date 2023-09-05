package net.itsrelizc.protocol.matchmaker;

import java.io.IOException;

import net.itsrelizc.protocol.matchmaker.server.ProxyServer;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Matchmaking instance starting... ");
		System.out.println("NerdMatcher(TM) Version 1.0.0 Build 1\n");
		System.out.println("Starting matchmaking server...");
		
		ProxyServer p = new ProxyServer();
		try {
			p.start(124);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
