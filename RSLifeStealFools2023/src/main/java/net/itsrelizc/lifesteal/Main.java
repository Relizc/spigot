package net.itsrelizc.lifesteal;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftSheep;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
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
import net.itsrelizc.commands.SongVoteMenuCommand;
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
	
	public static int zombiehealthreset = 600;

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
		this.getCommand("votesong").setExecutor(new SongVoteMenuCommand());
		
		
		
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
				
				for (World world : Bukkit.getWorlds()) {
					for (LivingEntity e : world.getLivingEntities()) {
						if (e.getCustomName() != null && e.getCustomName().contains("Magical")) return;
						
						if (e instanceof CraftSheep) {
							e.setCustomName("jeb_");
						} else {
							e.setCustomName("Dinnerbone");
						}
						
						e.setCustomNameVisible(false);
						
						if (e instanceof CraftZombie) {
							e.setMaxHealth(300);
							e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 4));
							e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
							e.setCustomName("§c♥ §a" + Math.round(e.getHealth()) + "§7/§a" + Math.round(e.getMaxHealth()) + " §eRIP BOZO LMFAO §7(?s until health reset)");
							e.setCustomNameVisible(true);
						}
						
						
					}
				}
			}
			
		}, 0L, 20L);
		
		
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (World world : Bukkit.getWorlds()) {
					for (LivingEntity e : world.getLivingEntities()) {
						if (e instanceof CraftZombie) {
							
							e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 4));
							e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
							e.setCustomName("§c♥ §a" + Math.round(e.getHealth()) + "§7/§a" + Math.round(e.getMaxHealth()) + " §eRIP BOZO LMFAO §7(" + (double) zombiehealthreset / 10 + "s until health reset)");
							if (zombiehealthreset <= 0) {
								e.setMaxHealth(300);
								e.setHealth(300);
								world.spawnParticle(Particle.END_ROD, e.getLocation(), 500, 0.01, 0.01, 0.01, 0.2);
								world.playSound(e.getLocation(), Sound.ENTITY_WOLF_WHINE, (float) 0.8, 0);
							}
							e.setCustomNameVisible(true);
						}
						
						
					}
				}
				
				if (zombiehealthreset <= 0) {
					zombiehealthreset = 600;
				}
				
				zombiehealthreset -= 1;
			}
			
		}, 0L, 2L);
		
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
		
		
		
		Bukkit.getServer().getLogger().addHandler(new Handler() {
            @Override
            public void close() throws SecurityException {/*Ignore this method*/}
 
            @Override
            public void flush() {/*Ignore this method*/}
 
            @Override
            public void publish(LogRecord logRecord) {
            	
            	if (logRecord.getThrown() != null) {
            		String stack = "";
            		int c = 0;
            		StackTraceElement[] stac;
            		if (logRecord.getThrown().getCause() == null) {
            			stac = logRecord.getThrown().getStackTrace();
            		} else {
            			stac = logRecord.getThrown().getCause().getStackTrace();
            		}
            		
					for (StackTraceElement s : stac) {
            			stack += s.toString() + "\n";
            			c ++;
            			if (c > 16) {
            				break;
            			}
            		}
            		if (logRecord.getLevel() == Level.SEVERE) {
                    	Bukkit.broadcastMessage("§cServer Error! " + logRecord.getThrown().getCause().getMessage() + stack);
                    } else if (logRecord.getLevel() == Level.WARNING) {
                    	Bukkit.broadcastMessage("§eServer Warning! " + stack);
                    }
            	}
                
                
            }
        });
		
		
		
		
		
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {

	    final Projectile projectile;
	    
	    if (event.getEntity() == null) return;

	    projectile = event.getEntity();
	    
	    if (projectile.getShooter() == null) return;

	    if (projectile.getShooter() instanceof Skeleton) {
	    	
	    	final Location loc = projectile.getLocation();
	    	final Vector vec = projectile.getVelocity();
	    	
	    	for (int i = 0; i < 31; i ++) {
	    		Bukkit.getScheduler().runTaskLater(this, new Runnable() {

					@Override
					public void run() {
						projectile.getWorld().spawnArrow(loc, vec, (float) 0.6, 12);
					}
	    			
	    		}, i * 2 + 2);
	    	}
	    } else if (projectile.getShooter() instanceof Player) {
	    	Player p = (Player) projectile.getShooter();
	    	int r = new Random().nextInt(EntityType.values().length);
	    	Entity e = null;
	    	try {
	    		e = projectile.getWorld().spawnEntity(projectile.getLocation(), EntityType.values()[r]);
	    	} catch (Exception f) {
	    		p.sendMessage("§e[NPC] Bow§7: §rI'll shoot an arrow for you this time :D");
	    		return;
	    	}
			e.setVelocity(projectile.getVelocity());
			e.setCustomName("§bMagical " + EntityType.values()[r].getName() + " created by " + p.getDisplayName() + "'s bow");
			e.setCustomNameVisible(true);
	    	String[] msg = {"I prefer to shoot that instead!", "No I want to shoot that idiot", "LOL LOOK AT THAT", EntityType.values()[r].getName() + " is the best mob ever!"};
	    	int r1 = new Random().nextInt(msg.length);
	    	p.sendMessage("§e[NPC] Bow§7: §r" + msg[r1]);
	    	event.setCancelled(true);
	    }
	}
	
	@EventHandler
	public void entitydamage(EntityDamageByEntityEvent event) {
		if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().contains("Magical")) {
			
			event.getEntity().remove();
			event.getEntity().getWorld().spawnParticle(Particle.SPELL_WITCH, event.getEntity().getLocation(), 500, 0.01, 0.01, 0.01, 0.2);
			event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, (float) 0.4, (float) 0.8);
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ServerListPing(ServerListPingEvent event) {
		JSONObject things = JSON.loadDataFromDataBase("cold-jokes.json");
		boolean maintence = (boolean) JSON.loadDataFromDataBase("cold-jokes.json").get("maintence");
		if (maintence) {
			JSONArray msg = (JSONArray) things.get("maintence_data");
			int select = new Random().nextInt(msg.size());
			String m = (String) msg.get(select);
		    event.setMotd("            §8- §r§7§lRELIZC LIFESTEAL SMP §8-\n§b" + " ".repeat(Math.max((54 - m.length()) / 2, 0)) + m);
		} else {
			JSONArray msg = (JSONArray) things.get("data");
			int select = new Random().nextInt(msg.size());
			String m = (String) msg.get(select);
		    event.setMotd("            §7§kL §r§e§lRELIZC LIFESTEAL SMP §7§kL\n§b" + " ".repeat(Math.max((54 - m.length()) / 2, 0)) + m);
		}
		
	}
	
	@EventHandler(priority=EventPriority.HIGH)
    public void onBlockBreak(final BlockBreakEvent event){
        if (event.isCancelled()) return; // skip if the event has been cancelled.
        int r = new Random().nextInt(Material.values().length);
        final Block block = event.getBlock();
        Material m =Material.values()[r];
        if (m == Material.AIR) {
        	event.getPlayer().sendMessage("§e[NPC] Annonymus Block§7: §rYou got nothing lol!");
        } else {
        	if (ThreadLocalRandom.current().nextInt(0, 200 + 1) == 100) {
        		event.getPlayer().sendMessage("§d[GOD] RNGesus§7: §rMy choosen one! The heart rain!");
        		ChatUtils.broadcastSystemMessage("§d§lRNGesus", "§b" + event.getPlayer().getDisplayName() + " was the lucky guy!");
        		event.getPlayer().getWorld().spawnParticle(Particle.HEART, event.getPlayer().getLocation(), 500, 1, 1, 1);
        		for (Player player : Bukkit.getOnlinePlayers()) {
        			player.playSound(event.getPlayer().getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, (float) 1, (float) 1);
        		}
        		
        		final Plugin p = this;
        		final Player player = event.getPlayer();
        		
        		Bukkit.getScheduler().runTaskLater(this, new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i < 64; i ++) {
							Bukkit.getScheduler().runTaskLater(p, new Runnable() {

								@Override
								public void run() {
									if (ThreadLocalRandom.current().nextInt(0, 1 + 1) == 0) {
										block.getLocation().getWorld().dropItemNaturally(block.getLocation(), Items.getHeartFragmentItem());
									} else {
										block.getLocation().getWorld().dropItemNaturally(block.getLocation(), Items.getHeartItem());
									}
									player.playSound(event.getPlayer().getLocation(), Sound.BLOCK_LAVA_POP, (float) 1, (float) 0);
								}
			        			
			        		}, i * 2 + 2);
						}
					}
        			
        		}, 20L);
        	}
        	try {
        		block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(m, 1));
        	} catch (Exception e) {
        		event.getPlayer().sendMessage("§e[NPC] Annonymus Block§7: §rYou got nothing lol!");
        	}
        }
        event.setCancelled(true);  // need to cancel to avoid the default drop
        block.setType(Material.AIR);
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
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", "§eOoga mooga daka mamagaga dada Lifesteal SMP! §7(Version §dOgaoga April Foolia 2023§7)");
		ChatUtils.systemMessage(event.getPlayer(), "§a§lLIFESTEAL", "§aBaga Mada map archive after Sogogoogogogoooooogogo season over!"); // §d§lLIFESTEAL
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText("§e§lRELIZC §d§lLIFESTEAL §r§7(Only LifeSteal)§r\n§bsmp.itsrelizc.net\n")).write(1, WrappedChatComponent.fromText("\n§b§lNews: §r§eIts April Fools!\n§eServer: §7(Undedicated)\n\n§aDon't forget to tell your friends!"));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		TextComponent msg1 = new TextComponent(TextComponent.fromLegacyText("§eUpdates v114.514§7: §d§lCHAOS UPDATE!!!!!!! UwU"));
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
	
//	@EventHandler
//	public void cmd(PlayerCommandPreprocessEvent event) {
//		if (event.getMessage().startsWith("/kick") || event.getMessage().startsWith("/minecraft:kick") || event.getMessage().startsWith("/minecraft:ban") || event.getMessage().startsWith("/ban-ip") || event.getMessage().startsWith("/minecraft:ban-ip")) {
//			event.setCancelled(true);
//			BanUtils.createBan(event.getPlayer(), "§d[NAV-50331889] §bUsage of blacklisted command.", 259200L);
//		}
//	}
	
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
		} else if (title.startsWith("Vote")) {
			MusicMenu.clickEvent(event);
		}
		
    }
	
	
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event){
	    final Player player = event.getPlayer();
	    
	    
	 
	    if (player.getItemInHand().getItemMeta() != null) {
	    	
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
}
