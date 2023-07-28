package net.itsrelizc.buildtools;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.json.simple.JSONObject;

import net.itsrelizc.global.JSON;

class WorldInfo {
	
	private String name;
	private String dimension;
	private String mapname;
	private World world;

	public WorldInfo(World world, String name, String dimension, String mapname) {
		this.name = name;
		this.dimension = dimension;
		this.world = world;
		this.mapname = mapname;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDimension() {
		return this.dimension;
	}
	
}

public class WorldData {
	
	public static WorldInfo getWorldInfo(String name) {
		World world = Bukkit.getWorld(name);
		JSONObject data = JSON.pathLoadData(world.getWorldFolder().toPath().toString() + "\\worldmetadata.rsd");
		WorldInfo info = new WorldInfo(world, (String) ((JSONObject) data.get("world")).get("id"), (String) ((JSONObject) data.get("world")).get("dimension"), (String) ((JSONObject) data.get("world")).get("display"));
		return info;
	}
	
}
