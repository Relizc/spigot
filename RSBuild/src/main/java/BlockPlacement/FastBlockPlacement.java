package BlockPlacement;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class FastBlockPlacement {
    public static void setBlockInNativeChunk(World world, int x, int y, int z, Material material) {
        net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_8_R3.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = (IBlockData) net.minecraft.server.v1_8_R3.Block.getById(material.getId());

        nmsChunk.a(bp, ibd);
    }
    public static void setBlockInNativeChunk(World world, int x, int y, int z, Integer id,byte data) {
        net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_8_R3.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = (IBlockData) net.minecraft.server.v1_8_R3.Block.getByCombinedId(id+(data<<12));

        nmsChunk.a(bp, ibd);
    }
}
