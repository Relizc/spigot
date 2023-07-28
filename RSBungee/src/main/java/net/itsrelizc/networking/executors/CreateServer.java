package net.itsrelizc.networking.executors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationInput;
import net.itsrelizc.networking.CommunicationType;
import net.itsrelizc.warp.ServerCategory;
import net.itsrelizc.warp.WarpUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.saltyfishstudios.bungee.ChatUtils;
import net.saltyfishstudios.bungee.Main;

public class CreateServer implements Runnable {

	private CommunicationInput input;
	
	public static Map<ProxiedPlayer, String> destWaitCreate = new HashMap<ProxiedPlayer, String>();

	public CreateServer(CommunicationInput input) {
		this.input = input;
	}

	@Override
	public void run() {
		ProxyServer.getInstance().getScheduler().schedule(Main.plugin, new Runnable() {

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
					int port = inp.readShort();
					
					byte category = inp.readByte();
					int subcat = inp.readShort();
					
					ServerCategory cat = ServerCategory.getTypeByByte(category, subcat);
					
					ServerInfo comp = WarpUtils.addServer(cat, a, sid, port);
					
					System.out.print("Server added! " + a + sid + " " + port);
					
					for (ProxiedPlayer p : destWaitCreate.keySet()) {
						if (destWaitCreate.get(p).equalsIgnoreCase(a + sid)) {
							destWaitCreate.remove(p);
							ChatUtils.systemMessage(p, "§a§lWARP", "§7Sending you to [RS-" + a + sid + "]...");
							p.connect(comp);
						}
					}
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
