package net.itsrelizc.networking.executors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationInput;
import net.itsrelizc.networking.CommunicationType;
import net.md_5.bungee.api.ProxyServer;

public class KickPlayer implements Runnable {
	
	private CommunicationInput input;

	public KickPlayer(CommunicationInput input) {
		this.input = input;
	}

	@Override
	public void run() {
		
		ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("RSBungee"), new Runnable() {

			private CommunicationInput inp = input;

			@Override
			public void run() {
				String p = this.inp.readString();
				ProxyServer.getInstance().getPlayer(p).disconnect(this.inp.readString());
				
				Properties prop = new Properties();
				FileInputStream fis = null;
				try {
					fis = new FileInputStream("server.properties");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					prop.load(fis);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Communication ok = new Communication(CommunicationType.ALERT);
				ok.writeByte((byte) 0x00);
				ok.writeString(prop.getProperty("sid"));
				ok.writeByte((byte) 0x00);
				ok.writeString("Scuessfully kicked player " + p);
				try {
					ok.sendMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}, 0, TimeUnit.SECONDS);
	}
	
}
