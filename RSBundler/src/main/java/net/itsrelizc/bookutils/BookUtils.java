package net.itsrelizc.bookutils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class BookUtils {
    public static ItemStack book(){
        return null;
    }
    private static String getVersion()
    {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
    private static Class<?> getNMSClass(String name)
    {
        try
        {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public static void openBook(ItemStack book, Player p) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int slot = p.getInventory().getHeldItemSlot();
        ItemStack old = p.getInventory().getItem(slot);
        p.getInventory().setItem(slot, book);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte)0);
        buf.writerIndex(1);

        Object packet = getNMSClass("PacketPlayOutCustomPayload")
                .getConstructor(
                        String.class,
                        getNMSClass("PacketDataSerializer")
                ).newInstance("MC|BOpen", getNMSClass("PacketDataSerializer").getConstructor(ByteBuf.class).newInstance(buf));
        Object craftPlayer = p.getClass().getMethod("getHandle").invoke(p);
        Field f = craftPlayer.getClass().getDeclaredField("playerConnection");
        f.setAccessible(true);
        Object playerConnection = f.get(craftPlayer);
        f.getType().getDeclaredMethod("sendPacket", getNMSClass("Packet"))
                .invoke(playerConnection, packet);
        p.getInventory().setItem(slot, old);
    }
}
