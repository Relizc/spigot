package net.itsrelizc.lifesteal.waiting;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONObject;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import net.itsrelizc.commands.BanCommand;
import net.itsrelizc.commands.GiveMeAHeart;
import net.itsrelizc.commands.HowManyHearts;
import net.itsrelizc.commands.ReviveCommand;
import net.itsrelizc.commands.Withdraw;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class Main extends JavaPlugin implements Listener {

	private long totalSec;
	private RadioSongPlayer radio;

	@Override	 
	
	public void onEnable() {
		this.totalSec = (1671368400000L - System.currentTimeMillis()) / 1000;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				long hours = totalSec / 3600;
				long minutes = (totalSec % 3600) / 60;
				long seconds = totalSec % 60;

				
				for (Player player : Bukkit.getOnlinePlayers()) {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§e§lServer is starting in: §b" + hours + "h " + minutes + "m " + seconds + "s"));
				}
				
				totalSec --;
				
				if (totalSec == 30) {
					ChatUtils.broadcastSystemMessage("§e§lLIFESTEAL", "§bServer is staring in 30 seconds!");
					ChatUtils.broadcastSystemMessage("§e§lLIFESTEAL", "§bYou will get kicked after 30 seconds and please rejoin for the offical lifesteal SMP!");
					ChatUtils.broadcastSystemMessage("§e§lLIFESTEAL", "§bSee you!");
				}	
				
				if (totalSec == 5) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.kickPlayer("§aServer is starting! It might take a while!");
					}
				}
			}
			
		}, 0L, 20L);
		Bukkit.getPluginManager().registerEvents(this, this);
		Song song0 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\14th Song.nbs"));
		Song song1 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\A Nightmare Before Christmas.nbs"));
		Song song2 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Africa.nbs"));
		Song song3 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Animals.nbs"));
		Song song4 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Another One Bites the Dust.nbs"));
		Song song5 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Auld Lang Syne.nbs"));
		Song song6 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Axel F - Beverly Hills Cop.nbs"));
		Song song7 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Axel F - Remastered.nbs"));
		Song song8 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Beat it.nbs"));
		Song song9 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Billie Jean.nbs"));
		Song song10 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Can You Feel the Love.nbs"));
		Song song11 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Canon in D.nbs"));
		Song song12 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Carol of the Bells.nbs"));
		Song song13 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Cat's In the Cradle.nbs"));
		Song song14 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Centerfold.nbs"));
		Song song15 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Demons.nbs"));
		Song song16 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Dixie Land.nbs"));
		Song song17 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Downtown.nbs"));
		Song song18 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Dream Lover.nbs"));
		Song song19 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Dueling Banjos.nbs"));
		Song song20 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Everybody Dance Now.nbs"));
		Song song21 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Fairy Tail Theme.nbs"));
		Song song22 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Flight of The Bumblebee.nbs"));
		Song song23 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Float On.nbs"));
		Song song24 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Footloose.nbs"));
		Song song25 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Friends in Low Places.nbs"));
		Song song26 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Frosty the Snowman.nbs"));
		Song song27 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Fugue In D Minor.nbs"));
		Song song28 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Fur Elise.nbs"));
		Song song29 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\GerudoValley.nbs"));
		Song song30 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Ghostbusters.nbs"));
		Song song31 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Grandma Got Run Over by a Reindeer.nbs"));
		Song song32 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Guren No Yumiya.nbs"));
		Song song33 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Happy.nbs"));
		Song song34 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Hotel California.nbs"));
		Song song35 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\I Am the Doctor.nbs"));
		Song song36 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\In the Hall of the Mountain King.nbs"));
		Song song37 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Joy to the World.nbs"));
		Song song38 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Let It Be.nbs"));
		Song song39 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Levels.nbs"));
		Song song40 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Luigi's Mansion.nbs"));
		Song song41 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\MortalKombat.nbs"));
		Song song42 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Never Gonna Give You Up.nbs"));
		Song song44 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Numb.nbs"));
		Song song45 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Nutcracker Dance of the Sugar Plum Fairies.nbs"));
		Song song46 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Nutcracker Russian Dance.nbs"));
		Song song47 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Nutcracker Waltz.nbs"));
		Song song48 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Ob-La-Di, Ob-La-Da.nbs"));
		Song song49 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Ode to Joy.nbs"));
		Song song50 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Pallet Town Theme.nbs"));
		Song song51 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Papermoon.nbs"));
		Song song52 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Peanut's Theme.nbs"));
		Song song53 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Pokemon Red-Blue Title.nbs"));
		Song song54 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Radioactive.nbs"));
		Song song55 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\RainbowTylenol.nbs"));
		Song song56 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Reptilia.nbs"));
		Song song57 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Resonance.nbs"));
		Song song58 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Rock and Roll All Night.nbs"));
		Song song59 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Santeria.nbs"));
		Song song60 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Star Spangled Banner.nbs"));
		Song song61 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\StarFox64Theme.nbs"));
		Song song62 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Steins Gate.nbs"));
		Song song63 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Still Alive.nbs"));
		Song song64 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Superstition.nbs"));
		Song song65 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Sweet Child of Mine.nbs"));
		Song song66 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Take On Me.nbs"));
		Song song67 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\The Entertainer.nbs"));
		Song song68 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\The Final Countdown.nbs"));
		Song song69 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\The Pretender.nbs"));
		Song song70 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Through the Fire and Flames.nbs"));
		Song song71 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\TownMarket.nbs"));
		Song song72 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Want You Gone - Simplified.nbs"));
		Song song73 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\We're not Gonna Take It.nbs"));
		Song song74 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Where No One Goes.nbs"));
		Song song75 = NBSDecoder.parse(new File("D:\\ServerData\\NBSFiles\\Wizards in Winter.nbs"));
		com.xxmicloxx.NoteBlockAPI.model.Song[] songs = {song0, song1, song2, song3, song4, song5, song6, song7, song8, song9, song10, song11, song12, song13, song14, song15, song16, song17, song18, song19, song20, song21, song22, song23, song24, song25, song26, song27, song28, song29, song30, song31, song32, song33, song34, song35, song36, song37, song38, song39, song40, song41, song42, song44, song45, song46, song47, song48, song49, song50, song51, song52, song53, song54, song55, song56, song57, song58, song59, song60, song61, song62, song63, song64, song65, song66, song67, song68, song69, song70, song71, song72, song73, song74, song75};
		Playlist playlist = new Playlist(songs);
		this.radio = new RadioSongPlayer(playlist);
		radio.setPlaying(true);
		radio.setLoop(true);
	}
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", "§eYou are in the waiting room!");
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText("§e§lRELIZC §d§lLIFESTEAL §r§7(Only LifeSteal)§r\n§bsmp.itsrelizc.net\n")).write(1, WrappedChatComponent.fromText("\n§b§lYou are currently in the waiting room!\n§eServer: §7(Undedicated)\n\n§aDon't forget to tell your friends!"));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		if (event.getPlayer().getUniqueId().toString().equalsIgnoreCase("b115215d-3e85-4dc0-bf11-654b9d1308bd")) { //Relizc
			event.getPlayer().setOp(true);
		}
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
		event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -16.5, 88.1, 45.5));
		radio.addPlayer(event.getPlayer());
	}
	
//	@EventHandler
//	public void craft(CraftItemEvent event) {
//		Inventory inv = event.getInventory();
//		if (event.getRecipe().getResult().)
//	}
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {
		String prefix = "";
		if (event.getPlayer().getUniqueId().toString().equalsIgnoreCase("b115215d-3e85-4dc0-bf11-654b9d1308bd")) { //Relizc
			prefix = "§c[OWNER] " + event.getPlayer().getDisplayName() + "§7: ";
		} else {
			prefix = "§a[TRASH] " + event.getPlayer().getDisplayName() + "§7: ";
		}
		event.setFormat(prefix + "§r" + event.getMessage());
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}
}
