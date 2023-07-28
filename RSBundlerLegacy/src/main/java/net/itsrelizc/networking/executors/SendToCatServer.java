package net.itsrelizc.networking.executors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.itsrelizc.global.ChatUtils;
import net.itsrelizc.networking.CommunicationInput;
import net.itsrelizc.networking.Components;
import net.itsrelizc.warp.WarpUtils;

public class SendToCatServer implements Runnable {
	
	private CommunicationInput input;

	public SendToCatServer(CommunicationInput input) {
		this.input = input;
	}

	@Override
	public void run() {
		short amount = (short) this.input.readShort();
		Player player = Bukkit.getPlayer(this.input.readString());
		
		if (amount == 0) {
			ChatUtils.systemMessage(player, "§a§lWARP", "§6There are currently no avaliable servers!");
			ChatUtils.systemMessage(player, "§a§lWARP", "§6Nerdbot will try his best to open up more instances!");
			ChatUtils.systemMessage(player, "§a§lWARP", "§7§oJust give him a minute!");
			return;
		}
			
		
		byte ram = this.input.readByte();
		String code = this.input.readString();
		
		
		WarpUtils.send(player, Character.toUpperCase(Components.fromByteRAM(ram)) + code.toLowerCase());
	}

}
