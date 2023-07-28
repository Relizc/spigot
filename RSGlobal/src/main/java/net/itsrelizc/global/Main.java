package net.itsrelizc.global;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import net.itsrelizc.packets.SignInput;
import net.itsrelizc.player.PlayerChatHandler;
import net.itsrelizc.player.PlayerConnectionHandle;
import net.itsrelizc.player.PlayerProfile;
import net.itsrelizc.player.ProfileUtils;
import net.itsrelizc.serverutils.Namespace;
import net.itsrelizc.serverutils.Register;
import net.itsrelizc.serverutils.ServerType;
import net.itsrelizc.tablist.Title;
import net.itsrelizc.utils.RandomString;

public class Main extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new PlayerConnectionHandle(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerChatHandler(), this);
	}
}
