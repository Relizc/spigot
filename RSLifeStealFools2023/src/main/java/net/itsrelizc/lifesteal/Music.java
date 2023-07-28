package net.itsrelizc.lifesteal;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

public class Music {
	
	public static List<Song> songs = new ArrayList<Song>();
	public static Song currentplaying = null;
	public static RadioSongPlayer rsp;
	
	public static List<Song> nextSongChoice = new ArrayList<Song>();
	public static List<List<Player>> songVotes = new ArrayList<List<Player>>();
	public static List<Material> discTextures = new ArrayList<Material>();
	public static boolean voting = false;
	
	public static void load() {
		File folder = new File("D:\\ServerData\\NBSFiles");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i ++) {
			if (listOfFiles[i].isFile()) {
				songs.add(NBSDecoder.parse(listOfFiles[i]));
			}
		}
		
		Collections.shuffle(songs);
		
		Playlist playlist = new Playlist(songs.toArray(new Song[0]));
		rsp = new RadioSongPlayer(playlist);
		rsp.setPlaying(true);
		rsp.setLoop(true);
		rsp.setVolume((byte) 60);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RSLifeStealFools2023"), new Runnable() {

			@Override
			public void run() {
				if (rsp.getTick() == 20) {
					scheduleDisplay();
				}
			}
			
		}, 0, 2L);
		
		discTextures.add(Material.MUSIC_DISC_BLOCKS);
		discTextures.add(Material.MUSIC_DISC_OTHERSIDE);
		discTextures.add(Material.MUSIC_DISC_PIGSTEP);
		
		nextSongChoice.add(songs.get(0));
		nextSongChoice.add(songs.get(1));
		nextSongChoice.add(songs.get(2));
		
		List<Player> p1 = new ArrayList<Player>();
		p1.add(Bukkit.getPlayer("Relizc"));
		
		List<Player> p2 = new ArrayList<Player>();
		
		List<Player> p3 = new ArrayList<Player>();
		
		songVotes.add(p1);
		songVotes.add(p2);
		songVotes.add(p3);
	}
	
	public static void scheduleDisplay() {
		for (UUID uuid : rsp.getPlayerUUIDs()) {
			Player p = Bukkit.getPlayer(uuid);
			if (p == null) continue;
			
			ChatUtils.systemMessage(p, "§2§lMUSIC", "§aCurrently Playing: §7" + rsp.getSong().getTitle() + " - " + rsp.getSong().getOriginalAuthor());
		}
	}
	
	public static void addPlayer(Player player) {
		rsp.addPlayer(player);
	}
	
	public static void removePlayer(Player player) {
		rsp.removePlayer(player);
	}
}
