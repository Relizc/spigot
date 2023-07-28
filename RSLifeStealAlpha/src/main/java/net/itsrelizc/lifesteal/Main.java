package net.itsrelizc.lifesteal;

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

import net.itsrelizc.commands.BanCommand;
import net.itsrelizc.commands.GiveMeAHeart;
import net.itsrelizc.commands.HowManyHearts;
import net.itsrelizc.commands.ReviveCommand;
import net.itsrelizc.commands.Withdraw;


public class Main extends JavaPlugin implements Listener {

	@Override	 
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		this.getCommand("ban").setExecutor(new BanCommand());
		this.getCommand("hearts").setExecutor(new HowManyHearts());
		this.getCommand("douloveme").setExecutor(new GiveMeAHeart());
		this.getCommand("withdraw").setExecutor(new Withdraw());
		this.getCommand("revive").setExecutor(new ReviveCommand());
		
		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§cHeart Fragment");
		item.setItemMeta(im);
		
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("rrr", "rdr", "rrr");
		recipe.setIngredient('r', Material.REDSTONE_BLOCK);
		recipe.setIngredient('d', Material.EMERALD_BLOCK);
		Bukkit.addRecipe(recipe);
		
		ItemStack h = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta him = h.getItemMeta();
		him.setDisplayName("§cHeart");
		h.setItemMeta(him);
		
		ShapedRecipe r = new ShapedRecipe(h);
		r.shape("fdf", "dgd", "fdf");
		r.setIngredient('f', item.getData());
		r.setIngredient('d', Material.EMERALD_BLOCK);
		r.setIngredient('g', Material.FERMENTED_SPIDER_EYE);
		Bukkit.addRecipe(r);
	}
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", "§eThe LifeSteal SMP is currently in alpha test, and problems may occur.");
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", "§eContact Relizc or Lionwhisker in Teams to report errors.");
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText("§e§lRELIZC §d§lLIFESTEAL §r§7(Only LifeSteal)§r\n§bsmp.itsrelizc.net\n")).write(1, WrappedChatComponent.fromText("\n§b§lNews: §r§eANNOUNCE THE RELEASE!\n§eServer: §7(Undedicated)\n\n§aDon't forget to tell your friends!"));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		if (event.getPlayer().getUniqueId().toString().equalsIgnoreCase("b115215d-3e85-4dc0-bf11-654b9d1308bd")) { //Relizc
			event.getPlayer().setOp(true);
		}
	}
	
	@EventHandler
	public void playerConnectEvent(PlayerLoginEvent event) {
		JSONObject obj = JSON.loadDataFromDataBase("banned.json");
		if (obj.containsKey(event.getPlayer().getUniqueId().toString())) {
			JSONObject data = (JSONObject) obj.get(event.getPlayer().getUniqueId().toString());
			String mutereason = "§cYou have been §lBANNED §r§cfrom joining §e§lSMP.ITSRELIZC.NET§r§c.\n"
					+ "§eRemaining Time: §7(Sorry, SMP bans are permanent. Or Im just too lazy to make this)"
					+ "\n\n§eBanned Reason: §b" + data.get("reason")
					+ "\n§eAssociated Ban ID: §b#" + ((String) data.get("id")).toUpperCase()
					+ "\n\n"
					+ "§7To appeal for your ban, please visit the link below. You are required"
					+ "\n§7to provide your Ban ID and sufficient evidence."
					+ "\n\n§7Spamming and resubmitting refused appeals will cause your ban's"
					+ "\n§7decision will be final and appeals will no longer be accepted."
					+ "\n\n§aAppeals avaliable at §2§nhttps://sfn.gg/appeals §aor via §9Microsoft Teams";
			event.disallow(Result.KICK_BANNED, mutereason);
		}
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
			Player player = (Player) event.getEntity();
			if (event.getDamage() >= player.getHealth()) {
				ItemStack h = new ItemStack(Material.REDSTONE_BLOCK);
				ItemMeta him = h.getItemMeta();
				him.setDisplayName("§cHeart");
				h.setItemMeta(him);
				
				player.getWorld().dropItemNaturally(player.getLocation(), h);
				
				Double healthleft = player.getMaxHealth() - 2;
				
				ChatUtils.systemMessage(player, "§c§lHEARTS", "§cYou died and lost a heart. §7(It is dropped on the ground! Maybe you can find it again...)");
				
				if (healthleft == 0) {
					player.setMaxHealth(20);
					player.setGameMode(GameMode.SPECTATOR);
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), "entity.wither.spawn", 1f, 1);
					}
					player.getWorld().strikeLightningEffect(player.getLocation());
					ChatUtils.systemMessage(player, "§c§lHEARTS", "§cYou now become a spectator of the world. You can get revived by your teammate using §a/revive " + player.getDisplayName() + "§c. This will cost them 1 heart.");
					ChatUtils.broadcastSystemMessage("§c§lHEARTS", "§b" + player.getDisplayName() + " §cran out of hearts! Revive them using §a/revive " + player.getDisplayName() + "§c.");
				} else {
					player.setMaxHealth(healthleft);
					Integer heart = ((int) player.getMaxHealth()) / 2;
					ChatUtils.systemMessage(player, "§c§lHEARTS", "§aNow you have §c" + heart + " ❤§a, which gives you §c" + heart * 2 + " ❤ Max Health§a.");
				}
				
				
			}
		}
	}
	
	@EventHandler
    public void onClickSend(InventoryClickEvent event) {
		if (event.getWhoClicked().getOpenInventory().getTitle().startsWith("Reviving")) {
			String[] names = event.getWhoClicked().getOpenInventory().getTitle().split(" ");
			if (event.getCurrentItem().getType() == Material.GREEN_TERRACOTTA) {
				event.getWhoClicked().setMaxHealth(event.getWhoClicked().getMaxHealth() - 2);
				Integer heart = ((int) event.getWhoClicked().getMaxHealth()) / 2;
				
				Player p = Bukkit.getPlayer(names[1]);
				p.setMaxHealth(2);
				ChatUtils.systemMessage(p, "§d§lREVIVE", "§aYou are revived by §b" + event.getWhoClicked().getName());
				p.setGameMode(GameMode.SURVIVAL);
				p.teleport(event.getWhoClicked());
				
				ChatUtils.systemMessage(event.getWhoClicked(), "§d§lREVIVE", "§aYou revived §b" + names[1] + " §aand lost a heart.");
				ChatUtils.systemMessage(event.getWhoClicked(), "§d§lREVIVE", "§aNow you have §c" + heart + " ❤§a, which gives you §c" + heart * 2 + " ❤ Max Health§a.");
				event.getWhoClicked().closeInventory();
				ChatUtils.broadcastSystemMessage("§d§lREVIVE", "§b" + names[1] + " §ais revived by §b" + event.getWhoClicked().getName() + "§b!");
				for (Player p1 : Bukkit.getOnlinePlayers()) {
					p1.playSound(p1.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 0);
				}
			} else if (event.getCurrentItem().getType() == Material.RED_TERRACOTTA) {
				event.getWhoClicked().closeInventory();
				ChatUtils.systemMessage(event.getWhoClicked(), "§d§lREVIVE", "§aIt's okay, you can think about it.");
			}
			event.setCancelled(true);
		}
    }
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event){
	    final Player player = event.getPlayer();
	 
	    if (player.getItemInHand().getType() == Material.REDSTONE_BLOCK){
	    	if (player.getGameMode() != GameMode.SPECTATOR) {
	    		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
	    			if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart")) {
			        	Bukkit.getScheduler().runTaskLater(this, new Runnable() {

							@Override
							public void run() {
								player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								player.setMaxHealth(player.getMaxHealth() + 2);
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1);
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 3));
								ChatUtils.systemMessage(player, "§c§lHEARTS", "§aYou consumed a heart!");
								Integer heart = ((int) player.getMaxHealth()) / 2;
								ChatUtils.systemMessage(player, "§c§lHEARTS", "§aNow you have §c" + heart + " ❤§a, which gives you §c" + heart * 2 + " ❤ Max Health§a.");
							}
			        		
			        	}, 1L);
			        	
			        	event.setCancelled(true);
			        }
	    		}
	    	}
	    }
	}
}
