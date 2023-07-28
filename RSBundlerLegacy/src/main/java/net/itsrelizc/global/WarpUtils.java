package net.itsrelizc.global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.entity.Player;

import net.itsrelizc.types.ServerType;

public class WarpUtils {
	public static void connectTo(Player player, ServerType type) {
		URL yahoo = null;
		String typ = "";
		if (type == ServerType.PVP_ARENA) {
			typ = "SFNPVPArena";
		} else if (type == ServerType.MAIN_LOBBY) {
			typ = "SFNMainLobby";
		}
		try {
			yahoo = new URL("http://127.0.0.1:65534/sendPlayer?serverType=" + typ + "&target=" + player.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        URLConnection yc = null;
		try {
			yc = yahoo.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader in = null;
		try {
			in = new BufferedReader(
			                        new InputStreamReader(
			                        yc.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String inputLine;

        try {
			while ((inputLine = in.readLine()) != null) 
			    System.out.println(inputLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
