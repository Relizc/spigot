package net.itsrelizc.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.trait.SkinTrait;
import net.itsrelizc.utils.RandomString;

public class NPC {
	
	private net.citizensnpcs.api.npc.NPC npc;

	public NPC(Location location, String skinname) {
		this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, RandomString.randomClassic16String());
		npc.spawn(new Location(Bukkit.getWorld("world"), 30.5, 69, 0.5, 90f, 0f));
		
		SkinTrait skin = npc.getOrAddTrait(SkinTrait.class);
		skin.setSkinName(skinname);
		
		npc.data().set(net.citizensnpcs.api.npc.NPC.NAMEPLATE_VISIBLE_METADATA, false);
		
		
	}
	
}
