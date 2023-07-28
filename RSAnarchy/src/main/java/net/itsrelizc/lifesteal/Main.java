package net.itsrelizc.lifesteal;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import net.itsrelizc.commands.AuctionHouseCommand;
import net.itsrelizc.commands.BanCommand;
import net.itsrelizc.commands.GiveMeAHeart;
import net.itsrelizc.commands.HowManyHearts;
import net.itsrelizc.commands.InvestigateCommand;
import net.itsrelizc.commands.MusicCommand;
import net.itsrelizc.commands.MuteCommand;
import net.itsrelizc.commands.RankCommand;
import net.itsrelizc.commands.RecipeCommand;
import net.itsrelizc.commands.ReviveCommand;
import net.itsrelizc.commands.RulesBook;
import net.itsrelizc.commands.SetHeart;
import net.itsrelizc.commands.ShopCommand;
import net.itsrelizc.commands.TPACommand;
import net.itsrelizc.commands.TPAccept;
import net.itsrelizc.commands.TPDeny;
import net.itsrelizc.commands.UpdateBook;
import net.itsrelizc.commands.Whisper;
import net.itsrelizc.commands.Withdraw;
import net.itsrelizc.utils.BanUtils;
import net.itsrelizc.utils.Investigator;
import net.itsrelizc.utils.MuteUtils;
import net.itsrelizc.utils.PlayerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class Main extends JavaPlugin implements Listener {
	
	private Map<Player, Integer> cooldown = new HashMap<Player, Integer>();

	@Override	 
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		this.getCommand("ban").setExecutor(new BanCommand());
		this.getCommand("hearts").setExecutor(new HowManyHearts());
		this.getCommand("douloveme").setExecutor(new GiveMeAHeart());
		this.getCommand("withdraw").setExecutor(new Withdraw());
		this.getCommand("revive").setExecutor(new ReviveCommand());
		this.getCommand("updates").setExecutor(new UpdateBook());
		this.getCommand("setheart").setExecutor(new SetHeart());
		this.getCommand("whisper").setExecutor(new Whisper());
		this.getCommand("shop").setExecutor(new ShopCommand());
		this.getCommand("music").setExecutor(new MusicCommand());
		this.getCommand("tpa").setExecutor(new TPACommand());
		this.getCommand("tpy").setExecutor(new TPAccept());
		this.getCommand("tpn").setExecutor(new TPDeny());
		this.getCommand("recipe").setExecutor(new RecipeCommand());
		this.getCommand("rules").setExecutor(new RulesBook());
		this.getCommand("investigate").setExecutor(new InvestigateCommand());
		this.getCommand("mute").setExecutor(new MuteCommand());
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("auctionhouse").setExecutor(new AuctionHouseCommand());
		
		
		
		ShapedRecipe recipe = new ShapedRecipe(Items.getHeartFragmentItem());
		recipe.shape("aba", "bcb", "aba");
		recipe.setIngredient('a', Material.IRON_BLOCK);
		recipe.setIngredient('b', Material.REDSTONE_BLOCK);
		recipe.setIngredient('c', Material.DIAMOND_BLOCK);
		Bukkit.addRecipe(recipe);
		
		ShapedRecipe r = new ShapedRecipe(Items.getHeartItem());
		r.shape("aba", "bcb", "aba");
		r.setIngredient('a', Items.getHeartFragmentItem().getData());
		r.setIngredient('b', Material.GRASS_BLOCK);
		r.setIngredient('c', Material.NETHERITE_INGOT);
		Bukkit.addRecipe(r);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			cooldown.put(p, 0);
			PlayerManager.loginCheck(p);
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Player p : cooldown.keySet()) {
					cooldown.put(p, cooldown.get(p) - 1);
				}
				
				for (Player cdp : TPACommand.cd.keySet()) {
					if (TPACommand.cd.get(cdp) - 1 <= 0) {
						TPACommand.cd.remove(cdp);
						continue;
					}
					TPACommand.cd.put(cdp, TPACommand.cd.get(cdp) - 1);
				}
			}
			
		}, 0L, 20L);
		
		JSONObject things = JSON.loadDataFromDataBase("cold-jokes.json");
		final JSONArray tips = (JSONArray) things.get("tips");
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				int i = new Random().nextInt(tips.size());
				String t = (String) tips.get(i);
				ChatUtils.broadcastSystemMessage("§b§lTIPS", "§a" + t);
			}
			
		}, 0L, 6000L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Player p : Investigator.inv.keySet()) {
					if (Investigator.spec.keySet().contains(p)) {
						Player target = Investigator.spec.get(p);
						if (!target.isOnline()) {
							Investigator.spec.remove(p);
							ChatUtils.systemMessage(p, "§6§lINVESTIGATE", "§cTarget lost! Reverting back to normal investigation mode...");
						}
						CraftPlayer cp = (CraftPlayer) target;
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eIM:§b" + Investigator.spec.get(p).getDisplayName() + " " +
						"§ep:§a" + target.getPing() + " " +
					"§eP:§a" + target.getLocation().getBlockX() + "," + target.getLocation().getBlockY() + "," + target.getLocation().getBlockZ() + " " +
								"§eV:§a" + Investigator.round(target.getVelocity().getX(), 2) + "," + Investigator.round(target.getVelocity().getY(), 2) + "," + Investigator.round(target.getVelocity().getZ(), 2) + " " + 
						"§eH:§a" + Investigator.round(target.getHealth(), 2) + "," + Investigator.round(target.getMaxHealth(), 2) + " " + 
						"§eh:§a" + target.getFoodLevel() + "," + Investigator.round(target.getSaturation(), 2) + "," + Investigator.round(target.getExhaustion(), 2) + "," + Investigator.round(target.getSaturatedRegenRate(), 2)));
					} else {
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eInvestigation Mode"));
					}
					
				}
			}
			
		}, 0L, 0L);
		
		Music.load();
	}
	
	@EventHandler
	public void ServerListPing(ServerListPingEvent event) {
		JSONObject things = JSON.loadDataFromDataBase("cold-jokes.json");
		boolean maintence = (boolean) JSON.loadDataFromDataBase("cold-jokes.json").get("maintence");
		if (maintence) {
			JSONArray msg = (JSONArray) things.get("maintence_data");
			int select = new Random().nextInt(msg.size());
			String m = (String) msg.get(select);
		    event.setMotd("            §8- §r§7§lRELIZC LIFESTEAL SMP §8-\n§b" + " ".repeat((54 - m.length()) / 2) + m);
		} else {
			JSONArray msg = (JSONArray) things.get("data");
			int select = new Random().nextInt(msg.size());
			String m = (String) msg.get(select);
		    event.setMotd("            §7§kL §r§e§lRELIZC LIFESTEAL SMP §7§kL\n§b" + " ".repeat((54 - m.length()) / 2) + m);
		}
		
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
	    if (Investigator.inv.keySet().contains(event.getPlayer())) {
	    	event.setCancelled(true);
	    }
	}

	
	@EventHandler
    public void craft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getRecipe().getResult().getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart")) {
        	if (!event.getClickedInventory().getItem(1).getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart Fragment") ||
        			!event.getClickedInventory().getItem(3).getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart Fragment") ||
        			!event.getClickedInventory().getItem(7).getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart Fragment") ||
        			!event.getClickedInventory().getItem(9).getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart Fragment")) {
        		event.setCancelled(true);
        		ChatUtils.systemMessage(player, "§a§lLIFESTEAL", "§cDon't try making a heart with diamonds! >:)");
        	}
        }
    }
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", "§eWelcome to lifesteal SMP! §7(Version 1.3.0)");
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", "§aThis map will be archived after this season is over! §7(Current Season: December 2022)");
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText("§e§lRELIZC §d§lLIFESTEAL §r§7(Only LifeSteal)§r\n§bsmp.itsrelizc.net\n")).write(1, WrappedChatComponent.fromText("\n§b§lNews: §r§eANNOUNCE THE RELEASE!\n§eServer: §7(Undedicated)\n\n§aDon't forget to tell your friends!"));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		TextComponent msg1 = new TextComponent(TextComponent.fromLegacyText("§eUpdates v1.5§7: §cModeration Update"));
		TextComponent msg2 = new TextComponent(TextComponent.fromLegacyText("§a§l[Click me to view updates...]"));
		msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/updates"));
		msg2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§eClick me to run command: §b/updates")));
		msg1.addExtra(msg2);
		
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", msg1);
		
		TextComponent msg3 = new TextComponent(TextComponent.fromLegacyText("§a§l[Rules are updated in this server! Click here]"));
		msg3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rules"));
		msg3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§eClick me to run command: §b/rules")));
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", msg3);
		
		event.setJoinMessage("§b" + event.getPlayer().getDisplayName() + " §ejoined the game! §a(" + Bukkit.getOnlinePlayers().size() + "§b/§a" + Bukkit.getMaxPlayers() + "§a)");
		
		cooldown.put(event.getPlayer(), 0);
	}
	
	
	@EventHandler
	public void playerleave(PlayerQuitEvent event) {
		if (Investigator.inv.containsKey(event.getPlayer())) {
			Investigator.removeInvestigator(event.getPlayer());
		}
		event.setQuitMessage("§b" + event.getPlayer().getDisplayName() + " §eleft the game! §a(" + (Bukkit.getOnlinePlayers().size() - 1) + "§b/§a" + Bukkit.getMaxPlayers() + "§a)");
	}
	
	@EventHandler
	public void daage(EntityDamageByEntityEvent event) {
		Investigator.dmg(event);
	}
	
	@EventHandler
	public void cmd(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/me") || event.getMessage().equalsIgnoreCase("/minecraft:me")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void daage(PlayerPickupItemEvent event) {
		Investigator.PickupItem(event);
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("op")) {
            if (sender instanceof Player) {
            	Player player = (Player) sender;
            	if (!player.getUniqueId().toString().equalsIgnoreCase("b115215d-3e85-4dc0-bf11-654b9d1308bd")) {
            		return false;
            	}
            	return true;
            }
        }
        return false;    
    }
	
	@EventHandler
	public void playerConnectEvent(PlayerLoginEvent event) {
		BanUtils.checkLogin(event);
		boolean maintence = (boolean) JSON.loadDataFromDataBase("cold-jokes.json").get("maintence");
		if (maintence) {
			event.disallow(Result.KICK_OTHER, "§cThis server is currently in Maintence Mode!\n§cCheck back later!");
		}
		
		PlayerManager.loginCheck(event.getPlayer());
	}
	
//	@EventHandler
//	public void craft(CraftItemEvent event) {
//		Inventory inv = event.getInventory();
//		if (event.getRecipe().getResult().)
//	}
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {
		if (MuteUtils.chatEvent(event)) {
			return;
		}
		PlayerManager.chatEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void targetEvent(EntityTargetEvent event) {
        Investigator.targetEvent(event);
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void targetEvent(EntityDamageEvent event) {
        Investigator.damageEvent(event);
    }
	
	@EventHandler
	public void onPlayerDamage(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		player.getWorld().dropItemNaturally(player.getLocation(), Items.getHeartItem());
		
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
	
	@EventHandler
    public void onClickSend(InventoryClickEvent event) {
		String title = event.getWhoClicked().getOpenInventory().getTitle();
		if (Investigator.inv.keySet().contains(event.getWhoClicked())) {
			event.setCancelled(true);
		}
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
		} else if (event.getWhoClicked().getOpenInventory().getTitle().startsWith("Heart Shop")) {
			Shop.clickEvent(event);
		} else if (event.getWhoClicked().getOpenInventory().getTitle().startsWith("Recipe")) {
			Recipe.clickEvent(event);
		} else if (title.startsWith("Investigation")) {
			InsMenu.clickEvent(event);
		}
		
    }
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event){
	    final Player player = event.getPlayer();
	    
	    Investigator.interactEvent(event);
	 
	    if (player.getItemInHand().getType() == Material.COMMAND_BLOCK || player.getItemInHand().getType() == Material.REDSTONE_BLOCK){
	    	if (player.getGameMode() != GameMode.SPECTATOR) {
	    		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
	    			if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cHeart")) {
	    				event.setCancelled(true);
	    				if (cooldown.get(player) > 0) {
	    					ChatUtils.systemMessage(player, "§c§lHEARTS", "§cWhoa slow down! You can consume a heart again in " + cooldown.get(player) + " second(s).");
	    					return;
	    				}
	    				cooldown.put(player, 3);
			        	Bukkit.getScheduler().runTaskLater(this, new Runnable() {

							@Override
							public void run() {
								player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
								player.setMaxHealth(player.getMaxHealth() + 2);
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1);
								ChatUtils.systemMessage(player, "§c§lHEARTS", "§aYou consumed a heart!");
								Integer heart = ((int) player.getMaxHealth()) / 2;
								ChatUtils.systemMessage(player, "§c§lHEARTS", "§aNow you have §c" + heart + " ❤§a, which gives you §c" + heart * 2 + " ❤ Max Health§a.");
							}
			        		
			        	}, 1L);
			        }
	    		}
	    	}
	    }
	    if (player.getItemInHand().getItemMeta().getDisplayName().startsWith("§aInvestigation")) {
	    	InsMenu menu = new InsMenu(player);
	    	menu.openShop();
	    }
	}
}
