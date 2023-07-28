package net.itsrelizc.workblocks;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.block.CraftBlock;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.BlockBase.BlockData;
import net.minecraft.world.level.block.state.IBlockData;

public class BasicWorkBlock {
	
	public Material TEXTURE = Material.BARRIER;
	
	public BasicWorkBlock(Location loc) {
		
		if (loc.getBlock().getType() != this.TEXTURE) {
			loc.getBlock().setType(TEXTURE);
		}
			
		loc.getBlock().setMetadata("indus_id", null);
	}
}
