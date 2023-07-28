package net.itsrelizc.worldloader;

import java.io.File;
import java.io.IOException;

import org.bukkit.Chunk;

public class WorldLoader {
	
	public static void updateBlockContent(Chunk chunk) {
		File worldpath = new File(chunk.getWorld().getWorldFolder().getPath() + "\\rsm");
		worldpath.mkdir();
		
		for (String s : worldpath.list()) {
			System.out.print(s);
		}
	}
	
}
