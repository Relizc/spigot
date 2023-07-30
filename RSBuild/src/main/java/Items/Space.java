package Items;

import BlockPlacement.FastBlockPlacement;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Space {
    public Integer pos1x;
    public Integer pos1y;
    public Integer pos1z;
    public Integer pos2x;
    public Integer pos2y;
    public Integer pos2z;

    public Integer[] getPos1() {
        if (pos1x != null && pos1y != null && pos1z != null) return new Integer[]{pos1x, pos1y, pos1z};
        else return null;
    }

    public Integer[] getPos2() {
        if (pos2x != null && pos2y != null && pos2z != null) return new Integer[]{pos2x, pos2y, pos2z};
        else return null;
    }

    public void setPos1(Integer x, Integer y, Integer z) {
        pos1x = x;
        pos1y = y;
        pos1z = z;
    }

    public void setPos2(Integer x, Integer y, Integer z) {
        pos2x = x;
        pos2y = y;
        pos2z = z;
    }

    public Integer getVolume() {
        Integer height = Math.abs(pos1y - pos2y);
        Integer width = Math.abs(pos1x - pos2x);
        Integer length = Math.abs(pos1z - pos2z);
        return height * width * length;
    }

    public void fill(Player user, Material material) {
        if (pos1x == null || pos1y == null || pos1z == null || pos2x == null || pos2y == null || pos2z == null) {
            user.sendMessage("§4 ERROR: missing coordinates");
            return;
        }
        Integer minx = Math.min(pos1x, pos2x);
        Integer minY = Math.min(pos1y, pos2y);
        Integer minZ = Math.min(pos1y, pos2y);
        Integer maxX = Math.max(pos1y, pos2y);
        Integer maxY = Math.max(pos1y, pos2y);
        Integer maxZ = Math.max(pos1y, pos2y);
        for (int i = minx; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                for (int k = minZ; k <= maxZ; j++) {
                    FastBlockPlacement.setBlockInNativeChunk(user.getWorld(), i, j, k, material);
                }
            }
        }


    }
    public void fill(Player user, Material material,byte data) {
        if (pos1x == null || pos1y == null || pos1z == null || pos2x == null || pos2y == null || pos2z == null) {
            user.sendMessage("§4 ERROR: missing coordinates");
            return;
        }
        Integer minx = Math.min(pos1x, pos2x);
        Integer minY = Math.min(pos1y, pos2y);
        Integer minZ = Math.min(pos1y, pos2y);
        Integer maxX = Math.max(pos1y, pos2y);
        Integer maxY = Math.max(pos1y, pos2y);
        Integer maxZ = Math.max(pos1y, pos2y);
        for (int i = minx; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                for (int k = minZ; k <= maxZ; j++) {
                    FastBlockPlacement.setBlockInNativeChunk(user.getWorld(), i, j, k, material.getId(),data);
                }
            }
        }


    }


}
