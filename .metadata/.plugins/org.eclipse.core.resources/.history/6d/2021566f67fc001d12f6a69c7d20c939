package net.itsrelizc.worldloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockManager {
	
	public Map<Location, BlockData> map = new HashMap<Location, BlockData>();
	public Map<Player, Chunk> b = new HashMap<Player, Chunk>();
	
	public static void initlizeBlockInChunk(Chunk chunk) {
		File worldpath = new File(chunk.getWorld().getWorldFolder().getPath() + "\\rsm\\");
		
		Bukkit.getLogger().info(String.valueOf(worldpath.listFiles().length));
		
		for (File s : worldpath.listFiles()) {
			System.out.println(s.getName());
			System.out.println(s.getName().split("\\.").length);
			int chunka = Integer.valueOf(s.getName().split("\\.")[1]);
			int chunkb = Integer.valueOf(s.getName().split("\\.")[2]);
			if (chunka == chunk.getX() && chunkb == chunk.getZ()) {
				InputStreamReader reader = null;
				
				try {
					reader = new InputStreamReader(new FileInputStream(s), "ASCII");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				BufferedReader bufReader = new BufferedReader(reader);
				
				byte by = (byte) 0;
				
				try {
					by = (byte) bufReader.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Bukkit.getLogger().info(String.valueOf(by));
				
				break;
			}
		}
	}
	
	public static void openChunkData(Chunk chunk) {
			
	}
}
