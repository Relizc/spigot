package net.itsrelizc.lobby.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.citizensnpcs.trait.SkinTrait;
import net.itsrelizc.serverutils.Namespace;
import net.itsrelizc.serverutils.Register;
import net.itsrelizc.serverutils.ServerType;
import net.itsrelizc.utils.RandomString;


public class Main extends JavaPlugin implements Listener {

	@Override	
	public void onEnable() {
		NPC classic = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, RandomString.randomClassic16String());
		classic.data().set(NPC.NAMEPLATE_VISIBLE_METADATA, false);
		SkinTrait skinTrait = classic.getTrait(SkinTrait.class);
		skinTrait.setSkinName("Relizc");
		classic.spawn(new Location(Bukkit.getWorld("world"), 0.5, 6, 10.5, 180f, 0f));
		
		Equipment equipment = classic.getTrait(Equipment.class);
		equipment.set(EquipmentSlot.HAND, new ItemStack(Material.GRASS, 1));
		
		Namespace.setServerCode(RandomString.randomString(4));
		Register.register(ServerType.LOBBY_DEATHSWAP);
	}

	@Override	
	public void onDisable() {
		CitizensAPI.getNPCRegistry().deregisterAll();
	}
}
