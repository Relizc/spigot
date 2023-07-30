package net.itsrelizc.gamemodes.mapbuilding;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.itsrelizc.debugger.Debugger;
import net.itsrelizc.menus.ItemGenerator;
import net.itsrelizc.players.DataManager;

public class UMapBuilding {


	public static ItemStack b(boolean n, String id) {
		ItemStack i;
		if (n) {
			i = ItemGenerator.generate(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2), 1, "§cVanished Map", "§7This map does not exist!",
					"",
					"§7The owner might have deleted",
					"§7this map, or this map violates",
					"§7Relizc Network's \"Appropriate",
					"§7Gaming Policies\".",
					"",
					"§aClick to remove this map from your list!",
					"",
					"§8[Technical Data]",
					"§8id=" + id);
		} else {
			i = ItemGenerator.generate(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2), 1, "§cVanished Map", "§7This map does not exist!",
					"",
					"§7The owner might have deleted",
					"§7this map, or this map violates",
					"§7Relizc Network's \"Appropriate",
					"§7Gaming Policies\".",
					"",
					"§aClick to remove this map from your list!");
		}
		
		return i;
	}
	
	public static ItemStack b() {
		ItemStack i = ItemGenerator.generate(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2), 1, "§cVanished Map", "§7This map does not exist!",
				"",
				"§7The owner might have deleted",
				"§7this map, or this map violates",
				"§7Relizc Network's \"Appropriate",
				"§7Gaming Policies\".",
				"",
				"§aClick to remove this map from your list!");
		return i;
	}

	public static List<ItemStack> a(Player player) {
		JSONArray all = (JSONArray) DataManager.loadPureJsonFromDb("map_contrib_db\\involvement.json").get(player.getRealUUID());

		if (all == null) {
			return null;
		}
		
		List<ItemStack> d = new ArrayList<ItemStack>();
		
		boolean n = Debugger.hasDebug(player);
		
		for (Object q : all) {
			String id = (String) q;
			
			JSONObject mapdata = DataManager.loadPureJsonFromDb("community_worlds\\" + id + "\\meta.json");
			
			
			
			if (mapdata == null) {
				d.add(b(n, id));
			}
		}
		
		return d;
	}
	public static Set guideLinePlayers(){
		JSONObject players = (JSONObject) DataManager.loadPureJsonFromDb("map_contrib_db\\guidelines.json");


		if(players==null) return null;

		return  (players.keySet());

	}
	public static JSONObject guideLinePlayers(String condition){
		if(Objects.equals(condition, "JSONObject")){
			return DataManager.loadPureJsonFromDb("map_contrib_db\\guidelines.json");
		}
		return null;
	}
	public static void addGuideLinePlayer(Player p) throws FileNotFoundException {
		JSONObject obj = guideLinePlayers("JSONObject");
		if(obj!=null)  obj.put(p.getRealUUID(),true);
		else {obj = new JSONObject();obj.put(p.getRealUUID(),true);}
		//SESSION EXPIRES IN 5 MINUTES ERIC!!!!



		DataManager.savePureJsonToDb("map_contrib_db\\guidelines.json",obj);
	}
	
}
