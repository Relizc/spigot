package net.itsrelizc.networking.executors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationInput;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.ProxyServer;

public class RemoveServer implements Runnable {

	private CommunicationInput input;

	public RemoveServer(CommunicationInput input) {
		this.input = input;
	}

	@Override
	public void run() {
		ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("RSBungee"), new Runnable() {

			private CommunicationInput inp = input;

			@Override
			public void run() {
				try {
					byte ram = inp.readByte();
					String a = "";
					if (ram == 0) {
						a = "T";
					} else if (ram == 1) {
						a = "S";
					} else if (ram == 2) {
						a = "M";
					} else if (ram == 3) {
						a = "B";
					} else if (ram == 4) {
						a = "G";
					}
					String sid = inp.readString();
					
					WarpUtils.removeServer(a, sid);
					
					System.out.print("Server removed! " + a + sid);
				} catch (Exception e) {
					Communication ok = new Communication(CommunicationType.ALERT);
					ok.writeByte((byte) 0x00);
					ok.writeString(e.getMessage());
					ok.writeByte((byte) 0x00);
					ok.writeString(e.getMessage());
					try {
						ok.sendMessage();
					} catch (IOException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
					e.printStackTrace();
				}
				
			}
			
		}, 0, TimeUnit.SECONDS);
	}

}
