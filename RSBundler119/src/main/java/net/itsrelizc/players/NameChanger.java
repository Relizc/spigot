//package net.itsrelizc.players;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Collection;
//
//import org.bukkit.Bukkit;
//import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
//import org.bukkit.entity.Player;
//
//import com.mojang.authlib.GameProfile;
//
//import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
//
///**
// * @author Shawckz
// * http://shawckz.com
// */
//public class NameChanger {
//
//    /*
//    a = (String)name
//    b = (String)displayname
//    c = (String)prefix
//    d = (String)suffix
//    e = (Enum)EnumTagVisibility
//    f = (int)EnumChatColor id
//    h = (int)join/quit/ (0 or 2)
//    i = (int)whether to allow friendly fire / packetdata
//     */
//	
//	@Deprecated
//	public static void changeName(Player player, String name) {
//        
//    }
//
//    private PacketPlayOutScoreboardTeam packet;
//
//    public static enum NameTagColor {
//
//        BLACK("BLACK", '0', 0),
//        DARK_BLUE("DARK_BLUE", '1', 1),
//        DARK_GREEN("DARK_GREEN", '2', 2),
//        DARK_AQUA("DARK_AQUA", '3', 3),
//        DARK_RED("DARK_RED", '4', 4),
//        DARK_PURPLE("DARK_PURPLE", '5', 5),
//        GOLD("GOLD", '6', 6),
//        GRAY("GRAY", '7', 7),
//        DARK_GRAY("DARK_GRAY", '8', 8),
//        BLUE("BLUE", '9', 9),
//        GREEN("GREEN", 'a', 10),
//        AQUA("AQUA", 'b', 11),
//        RED("RED", 'c', 12),
//        LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
//        YELLOW("YELLOW", 'e', 14),
//        WHITE("WHITE", 'f', 15),
//        RESET("RESET", 'r', -1);
//
//        private final String name;
//        private final char c;
//        private final int id;
//
//        NameTagColor(String name, char c, int id){
//            this.name = name;
//            this.c = c;
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public char getChar() {
//            return c;
//        }
//
//        public int getId() {
//            return id;
//        }
//    }
//
//    /**
//     * Construct what color you want the player's name tag to be, as well as team name and team display name (which aren't very important)
//     * @param teamName
//     * @param displayName
//     * @param color
//     */
//    @Deprecated
//    public NameChanger(String teamName, String displayName, NameTagColor color){
//       
//    }
//
//    /**
//     * Construct the team name, display name, prefix and suffix of that player's name tag (prefix and suffix will appear on player's name tag and in tab list)
//     * @param teamName
//     * @param displayName
//     * @param prefix
//     * @param suffix
//     */
//    
//    @Deprecated
//    public NameChanger(String teamName, String displayName, String prefix, String suffix){
//        
//    }
//
//    /**
//     * Construct the team name, display name and prefix (prefix will appear on player's name tag and in the tab list)
//     * @param teamName
//     * @param displayName
//     * @param prefix
//     */
//    @Deprecated
//    public NameChanger(String teamName, String displayName, String prefix){
//        
//    }
//
//    /**
//     * Add a player to the team, you must add the player to the team if you wish their name to get colored/to get their prefix set
//     * @param pl
//     */
//    public void addPlayer(Player pl){
//        try {
//            add(pl);
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//            pl.sendMessage("Failed to add you to team");
//        }
//    }
//
//    /**
//     * Send it to a specific player
//     * @param pl
//     */
//    @Deprecated
//    public void sendToPlayer(Player pl){
//        
//    }
//
//    /**
//     * Send it to all players, only players who have been added to the team will get their name changed.
//     */
//    public void updateAll(){
//        for(Player pl : Bukkit.getOnlinePlayers()){
//            sendToPlayer(pl);
//        }
//    }
//
//    private void setField(String field, Object value) {
//        try {
//            Field f = packet.getClass().getDeclaredField(field);
//            f.setAccessible(true);
//            f.set(packet, value);
//            f.setAccessible(false);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void add(Player pl) throws NoSuchFieldException, IllegalAccessException{
//        Field f = packet.getClass().getDeclaredField("g");
//        f.setAccessible(true);
//        ((Collection) f.get(packet)).add(pl.getName());
//    }
//
//
//}
