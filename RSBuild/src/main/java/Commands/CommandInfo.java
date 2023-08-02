package Commands;

import net.itsrelizc.bookutils.BookUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CommandInfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        List<String> normalPages = new ArrayList<>();
        String page1 = "§0§lCURRENT PROJECT:§r\n§9§nBattle Royale!§r§0 Help us build the map for the upcoming gamemode, §nbattle royale!§r You can theme your builds anyway" +
                "you like but §nmake sure that it is not out of place with the builds surrounding it.§r You can separate the map into different sections with each" +
                "section having";
        String page1_5 = "a different theme.(like §ofortnite§r)";
        String page2 = "List of usable commands:\n" +
                "/tp\n" +
                "/give" +
                "\n/setblock" +
                "\n/fill" +
                "\n/summon" +
                "\n/lobby" +
                "\n/fastfill" +
                "\n/givewand" +
                "\n/fastclone" +
                "\n/placecopied" +
                "\n/rotateleft&/rotatevertical";
        normalPages.add(page1);
        normalPages.add(page1_5);

        normalPages.add(page2);
        meta.setPages(normalPages);

//        List<IChatBaseComponent> pages;
//
//        try {
//            pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(meta);
//        } catch (ReflectiveOperationException ex) {
//            ex.printStackTrace();
//
//            return false;
//        }
        meta.setTitle("The Build Information Booklet");
        meta.setAuthor("The Relizc Network Team");

        book.setItemMeta(meta);
        try {
            BookUtils.openBook(book,p);
        } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InvocationTargetException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
