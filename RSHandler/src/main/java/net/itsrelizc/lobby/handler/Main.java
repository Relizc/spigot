package net.itsrelizc.lobby.handler;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.citizensnpcs.trait.SkinTrait;
import net.itsrelizc.lang.Lang.Language;
import net.itsrelizc.player.ProfileUtils;
import net.itsrelizc.player.TitleUtils;
import net.itsrelizc.serverutils.Namespace;
import net.itsrelizc.serverutils.Register;
import net.itsrelizc.serverutils.ServerType;
import net.itsrelizc.serverutils.WarpUtils;
import net.itsrelizc.utils.RandomString;


public class Main extends JavaPlugin implements Listener{

	@Override	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText("Welcome!")).write(1, WrappedChatComponent.fromText("Welcome!"));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
