package com.legacycraft.Botz147.Features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Golem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Monster;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WaterMob;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.Spout;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenOpenEvent;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.Herochat;
import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.party.HeroParty;
import com.legacycraft.Botz147.Features.Events.MMOHUDEvent;
import com.legacycraft.Botz147.Features.Listener.FeatureHeroesListener;
import com.legacycraft.Botz147.Features.Listener.SpoutListener;
import com.legacycraft.Botz147.Features.Listener.TestListener;
import com.legacycraft.Botz147.Features.Utils.ArrayListString;
import com.legacycraft.Botz147.Features.Utils.GenericLivingEntity;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;


public class Features extends JavaPlugin implements Listener{

	public static Logger log;
	public static Heroes hplugin;
	public static Spout splugin;
	public static WorldGuardPlugin wg;
	public static DisguiseCraftAPI dcApi;
	public static Herochat Hchat;
    private static final HashMap<Player, Widget> chatbar = new HashMap<Player, Widget>();
	public static Boolean showpet = true;
	public static Boolean alwaysshow = true;
	public static Boolean show_display_name = true;
	protected static HashMap<Player, Container> containers = new HashMap<Player, Container>();
	private final ArrayListString members = new ArrayListString();
	public static LinkedHashMap<String, String> default_colours = new LinkedHashMap<String, String>();
	static String config_ui_align = "TOP_LEFT";
	static int config_ui_left = 3;
	static int config_ui_top = 3;
	static int config_ui_maxwidth = 160;
	static int config_max_party_size = 6;
	static Boolean leave_on_quit = true;
	protected static final HashMap<Player, perPlayer> data = new HashMap<Player, perPlayer>();
	static int config_max_range = 15;
	 
	 
	public void onEnable(){
		log = getLogger();
		registerEvents();
		setupHeroes();
		getWorldGuard();
		setupDisguiseCraft();
		log("[" + this.getDescription().getName() + "]" + this.getDescription().getVersion() + " is enabled", Level.INFO);
		this.getServer().getPluginManager().registerEvents(this, this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new Runnable() {
			@Override
			public void run() {	
				updateAll();
				}
			}, 20, 20);
		}
	
	public void onDisable(){
		log = getLogger();
		log("[" + this.getDescription().getName() + "]" + this.getDescription().getVersion() + " is disabled", Level.INFO);
	}
	
	public void log(String msg, Level level){
		 log.log(level, msg);
	 }
	
	public void registerEvents(){
		this.getServer().getPluginManager().registerEvents(new FeatureHeroesListener(this), this);
		this.getServer().getPluginManager().registerEvents(new SpoutListener(this), this);
		this.getServer().getPluginManager().registerEvents(new TestListener(), this);
	}
	
	public void setupHeroes(){
		Plugin heroes = this.getServer().getPluginManager().getPlugin("Heroes");
		   if(heroes == null)
			   return;
		   hplugin = (Heroes) heroes;
		   return;
		
	}
	public void getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return; // Maybe you want throw an exception instead
	    }
	 
	    wg =  (WorldGuardPlugin) plugin;
	}
	public void setupDisguiseCraft() {
		Plugin dc = this.getServer().getPluginManager().getPlugin("DisguiseCraft");
			if (dc == null)
				return;
		dcApi = DisguiseCraft.getAPI();
		return;
	}
	
	public void setupHerochat(){
		Plugin herochat = this.getServer().getPluginManager().getPlugin("Herochat");
		   if(herochat == null)
			   return;
		   Hchat= (Herochat) herochat;
		   return;
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onScreenOpen(final ScreenOpenEvent event) {
		if (!event.isCancelled() && event.getScreenType() == ScreenType.CHAT_SCREEN) {
			Color black = new Color(0f, 0f, 0f, 0.5f), white = new Color(1f, 1f, 1f, 0.5f);
			SpoutPlayer player = event.getPlayer();
			Widget label, bar = chatbar.get(player);
			if (bar == null) {
				bar = new GenericContainer(
						label = new GenericLabel(ChatColor.GRAY + Herochat.getChatterManager().getChatter(player).getActiveChannel().getName()).setResize(true).setFixed(true).setMargin(3, 3, 0, 3),
						new GenericGradient(black).setPriority(RenderPriority.Highest),
						new GenericGradient(white).setMaxWidth(1).setPriority(RenderPriority.High).setVisible(false),
						new GenericGradient(white).setMaxWidth(1).setMarginLeft(label.getWidth() + 5).setPriority(RenderPriority.High),
						new GenericGradient(white).setMaxHeight(1).setPriority(RenderPriority.High)).setLayout(ContainerType.OVERLAY).setAnchor(WidgetAnchor.BOTTOM_LEFT).setY(-27).setX(4).setHeight(13).setWidth(label.getWidth() + 6);
				chatbar.put(player, bar);
				player.getMainScreen().attachWidget(this, bar);
			}
			bar.setVisible(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onScreenClose(final ScreenCloseEvent event) {
		if (!event.isCancelled() && event.getScreenType() == ScreenType.CHAT_SCREEN) {
			Widget bar = chatbar.remove(event.getPlayer());
			if (bar != null) {
				bar.setVisible(false);
			}
		}
	}
	
	
	
	
	
	
	public void update(Player player) {
		Container container = containers.get(player);
		if (container != null) {
			int index = 0;
			Widget[] bars = container.getChildren();
			if (members.size() > 1 || alwaysshow) {
				if (hplugin.getCharacterManager().getHero(player).hasParty()){
					for (Hero h: hplugin.getCharacterManager().getHero(player).getParty().getMembers()){
					String name = h.getPlayer().getName();
					GenericLivingEntity bar;
					if (index >= bars.length) {
						container.addChild(bar = new GenericLivingEntity());
						} else {
							bar = (GenericLivingEntity) bars[index];
							}	
					bar.setEntity(name, (hplugin.getCharacterManager().getHero(this.getServer().getPlayer(name)).getParty().getLeader() == hplugin.getCharacterManager().getHero(this.getServer().getPlayer(name))) ? ChatColor.GREEN + "@" : "");
					bar.setTargets(showpet ? getPets(this.getServer().getPlayer(name)) : null);
					index++;				
					}
				}else{
					String name = player.getName();
					GenericLivingEntity bar;
					if (index >= bars.length) {
						container.addChild(bar = new GenericLivingEntity());
						} else {
							bar = (GenericLivingEntity) bars[index];
							}	
					bar.setEntity(name, ChatColor.GREEN + "@");
					bar.setTargets(showpet ? getPets(this.getServer().getPlayer(name)) : null);
					index++;
				}
			}			
			while (index < bars.length) {
				container.removeChild(bars[index++]);
				}			
			container.updateLayout();
			}	
		}
	 
	
	public static String makeBar(ChatColor prefix, int current)
	  {
	    String bar = "||||||||||";
	    int left = Math.min(Math.max(0, (current + 9) / 10), 10);
	    int right = 10 - left;
	    return new StringBuilder().append(prefix).append(bar.substring(0, left)).append(current > 0 ? ChatColor.DARK_GRAY : ChatColor.BLACK).append(bar.substring(0, right)).append(" ").toString();
	  }
	 
	  public static LivingEntity[] getPets(HumanEntity player)
	  {
	    ArrayList<LivingEntity> pets = new ArrayList<LivingEntity>();
	    String name;
	    if ((player != null) && ((!(player instanceof Player)) || (((Player)player).isOnline()))) {
	      name = player.getName();
	      for (World world : Bukkit.getServer().getWorlds()) {
	        for (LivingEntity entity : world.getLivingEntities()) {
	          if (((entity instanceof Tameable)) && (((Tameable)entity).isTamed()) && ((((Tameable)entity).getOwner() instanceof Player)) && 
	            (name.equals(((Player)((Tameable)entity).getOwner()).getName()))) {
	            pets.add(entity);
	          }
	        }
	      }
	    }

	    LivingEntity[] list = new LivingEntity[pets.size()];
	    pets.toArray(list);
	    return list;
	  }

	    public static int getArmor(Entity player)
	    {
            if ((player != null) && ((player instanceof Player))) {
	        int armor = 0; 
	        ItemStack[] inv = ((Player)player).getInventory().getArmorContents();
	        for (int i = 0; i < inv.length; i++) {
	          int max = inv[i].getType().getId();
	          switch (max) {
	          case 298: armor += 5;
	          break;
	          case 299: armor += 15;
	          break;
	          case 300: armor += 10;
	          break;
	          case 301: armor += 5;
	          break;
	          case 302: armor += 10;
	          break;
	          case 303: armor += 25;
	          break;
	          case 304: armor += 20;
	          break;
	          case 305: armor += 5;
	          break;
	          case 306: armor += 10;
	          break;
	          case 307: armor += 30;
	          break;
	          case 308: armor += 25;
	          break;
	          case 309: armor += 10;
	          break;
	          case 310: armor += 15;
	          break;
	          case 311: armor += 40;
	          break;
	          case 312: armor += 30;
	          break;
	          case 313: armor += 15;
	          break;
	          case 314: armor += 10;
	          break;
	          case 315: armor += 25;
	          break;
	          case 316: armor += 15;
	          break;
	          case 317: armor += 5;
	          break;
	          
	          }
	        }
	      return armor;
        }
		return 0;
    }
	    
	    public static int getHealth(Entity player)
	    {
	      float currenthp = ((hplugin.getCharacterManager().getCharacter(((LivingEntity)player)).getHealth()));
	      float maxhp = (hplugin.getCharacterManager().getCharacter(((LivingEntity)player)).getMaxHealth());
	      float hp = currenthp / maxhp;
	      float hp2 = hp * 100;
	      if ((player != null) && ((player instanceof LivingEntity)))
	        try {
	          return Math.min((int)hp2 , 100);
	        }
	        catch (Exception e) {
	        }
	      return 0;
	    }
	    
	    public static int getMana(Player player)
	    {
	      float currentMana = ((hplugin.getCharacterManager().getHero(player)).getMana());
	      float maxMana = ((hplugin.getCharacterManager().getHero((player))).getMaxMana());
	      float mana = currentMana / maxMana;
	      float mana2 = mana * 100;
	      if ((player != null) && ((player instanceof LivingEntity)))
	        try {
	          return Math.min((int)mana2 , 100);
	        }
	        catch (Exception e) {
	        }
	      return 0;
	    }
	    
	    public static String name(String name)
	    {
	      return name(name, true);
	    }
	    
	    public static String name(String name, boolean online)
	    {
	      return new StringBuilder().append(online ? ChatColor.YELLOW : ChatColor.GRAY).append(name).append(ChatColor.WHITE).toString();
	    }
	    
	    
	    
	    public static String getSimpleName(LivingEntity target, boolean showOwner)
	    {
	      String name = "";
	      if ((target instanceof ComplexLivingEntity)) {
	        if ((target instanceof EnderDragon))
	          name = new StringBuilder().append(name).append("Ender Dragon").toString();
	        else
	          name = new StringBuilder().append(name).append("Complex").toString();
	      }
	      else if ((target instanceof Creature)) {
	        if (((target instanceof Tameable)) && 
	          (((Tameable)target).isTamed())) {
	          if ((showOwner) && ((((Tameable)target).getOwner() instanceof Player))) {
	            Player owner = (Player)((Tameable)target).getOwner();
	            if (show_display_name)
	              name = new StringBuilder().append(name).append(owner.getName()).append("'s ").toString();
	            else
	              name = new StringBuilder().append(name).append(owner.getDisplayName()).append("'s ").toString();
	          }
	          else {
	            name = new StringBuilder().append(name).append("Pet ").toString();
	          }

	        }

	        if ((target instanceof Animals)) {
	          if (!((Ageable)target).isAdult()) name = new StringBuilder().append(name).append("Baby ").toString();
	          if ((target instanceof Chicken))
	            name = new StringBuilder().append(name).append("Chicken").toString();
	          else if ((target instanceof MushroomCow))
	            name = new StringBuilder().append(name).append("Mooshroom").toString();
	          else if ((target instanceof Cow))
	            name = new StringBuilder().append(name).append("Cow").toString();
	          else if ((target instanceof Ocelot))
	            name = new StringBuilder().append(name).append(((Tameable)target).isTamed() ? "Cat" : "Ocelot").toString();
	          else if ((target instanceof Pig))
	            name = new StringBuilder().append(name).append("Pig").toString();
	          else if ((target instanceof Sheep))
	            name = new StringBuilder().append(name).append("Sheep").toString();
	          else if ((target instanceof Wolf))
	            name = new StringBuilder().append(name).append("Wolf").toString();
	          else
	            name = new StringBuilder().append(name).append("Animal").toString();
	        }
	        else if ((target instanceof Golem)) {
	          if ((target instanceof IronGolem))
	            name = new StringBuilder().append(name).append("Iron Golem").toString();
	          else if ((target instanceof Snowman))
	            name = new StringBuilder().append(name).append("Snow Golem").toString();
	          else
	            name = new StringBuilder().append(name).append("Golem").toString();
	        }
	        else if ((target instanceof Monster)) {
	          if ((target instanceof Blaze))
	            name = new StringBuilder().append(name).append("Blaze").toString();
	          else if ((target instanceof Creeper))
	            name = new StringBuilder().append(name).append("Creeper").toString();
	          else if ((target instanceof Enderman))
	            name = new StringBuilder().append(name).append("Enderman").toString();
	          else if ((target instanceof Giant))
	            name = new StringBuilder().append(name).append("Giant").toString();
	          else if ((target instanceof Silverfish))
	            name = new StringBuilder().append(name).append("Silverfish").toString();
	          else if ((target instanceof Skeleton))
	            name = new StringBuilder().append(name).append("Skeleton").toString();
	          else if ((target instanceof CaveSpider))
	            name = new StringBuilder().append(name).append("CaveSpider").toString();
	          else if ((target instanceof Spider))
	            name = new StringBuilder().append(name).append("Spider").toString();
	          else if ((target instanceof PigZombie))
	            name = new StringBuilder().append(name).append("PigZombie").toString();
	          else if ((target instanceof Zombie))
	            name = new StringBuilder().append(name).append("Zombie").toString();
	          else
	            name = new StringBuilder().append(name).append("Monster").toString();
	        }
	        else if ((target instanceof NPC)) {
	          if ((target instanceof Villager))
	            name = new StringBuilder().append(name).append("Villager").toString();
	          else
	            name = new StringBuilder().append(name).append("NPC").toString();
	        }
	        else if ((target instanceof WaterMob)) {
	          if ((target instanceof Squid))
	            name = new StringBuilder().append(name).append("Squid").toString();
	          else
	            name = new StringBuilder().append(name).append("WaterMob").toString();
	        }
	        else {
	          name = new StringBuilder().append(name).append("Creature").toString();
	        }
	      } else if ((target instanceof Flying)) {
	        if ((target instanceof Ghast))
	          name = new StringBuilder().append(name).append("Ghast").toString();
	        else
	          name = new StringBuilder().append(name).append("Flying").toString();
	      }
	      else if ((target instanceof HumanEntity)) {
	        if (((target instanceof Player)) && (!show_display_name))
	          name = new StringBuilder().append(name).append(((Player)target).getDisplayName()).toString();
	        else
	          name = new StringBuilder().append(name).append(((HumanEntity)target).getName()).toString();
	      }
	      else if ((target instanceof Slime)) {
	        if ((target instanceof MagmaCube))
	          name = new StringBuilder().append(name).append("Magma Cube").toString();
	        else
	          name = new StringBuilder().append(name).append("Slime").toString();
	      }
	      else {
	        name = new StringBuilder().append(name).append("Unknown").toString();
	      }
	      return name;
	    }
	    
	    public static String getColor(Player player, LivingEntity target)
	    {
	      if ((target instanceof Player)) {
	        for (String arg : default_colours.keySet()) {
	          if (((arg.equals("op")) && (((Player)target).isOp())) || (arg.equals("default")) || (((Player)target).hasPermission(arg))) {
	            String color = (String)default_colours.get(arg);
	            if (color.matches("[0-9a-f]")) {
	              return new StringBuilder().append("§").append(color).toString();
	            }
	            return ChatColor.valueOf(color.toUpperCase()).toString();
	          }
	        }
	        return ((Player)target).isOp() ? ChatColor.GOLD.toString() : ChatColor.YELLOW.toString();
	      }
	      if ((target instanceof Monster)) {
	        if ((player != null) && (player.equals(((Monster)target).getTarget()))) {
	          return ChatColor.RED.toString();
	        }
	        return ChatColor.YELLOW.toString();
	      }
	      if ((target instanceof WaterMob))
	        return ChatColor.GREEN.toString();
	      if ((target instanceof Flying))
	        return ChatColor.YELLOW.toString();
	      if ((target instanceof Animals)) {
	        if ((player != null) && (player.equals(((Animals)target).getTarget())))
	          return ChatColor.RED.toString();
	        if ((target instanceof Tameable)) {
	          Tameable pet = (Tameable)target;
	          if (pet.isTamed()) {
	            return ChatColor.GREEN.toString();
	          }
	          return ChatColor.YELLOW.toString();
	        }

	        return ChatColor.GRAY.toString();
	      }

	      return ChatColor.GRAY.toString();
	    }
		
	    @EventHandler
	    public void onSpoutcraftEnable(SpoutCraftEnableEvent event) {
	    	SpoutPlayer player = event.getPlayer();
	    	Container container = getContainer(player, config_ui_align, config_ui_left, config_ui_top);
	    	Container members = new GenericContainer();
	    	container.setLayout(ContainerType.HORIZONTAL).addChildren(members, new GenericContainer()).setWidth(config_ui_maxwidth);
	    	containers.put(player, members);
            updateAll(player);
	    	}

 	@EventHandler
 	public void onPlayerQuit(PlayerQuitEvent event) {
 		Player player = event.getPlayer();
 		containers.remove(player);
 		HeroParty party = find(player);
 		if (party != null) {
 						update(party);
 				}		
 			}	
 	
 	
 	  public Container getContainer(SpoutPlayer player, String anchorName, int offsetX, int offsetY)
 	  {
 	    int X = offsetX; int Y = offsetY;
 	    WidgetAnchor anchor = WidgetAnchor.SCALE;
 	    if ("TOP_LEFT".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.TOP_LEFT;
 	    } else if ("TOP_CENTER".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.TOP_CENTER;
 	      X -= 213;
 	    } else if ("TOP_RIGHT".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.TOP_RIGHT;
 	      X = -427 - X;
 	    } else if ("CENTER_LEFT".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.CENTER_LEFT;
 	      Y -= 120;
 	    } else if ("CENTER_CENTER".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.CENTER_CENTER;
 	      X -= 213;
 	      Y -= 120;
 	    } else if ("CENTER_RIGHT".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.CENTER_RIGHT;
 	      X = -427 - X;
 	      Y -= 120;
 	    } else if ("BOTTOM_LEFT".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.BOTTOM_LEFT;
 	      Y = -240 - Y;
 	    } else if ("BOTTOM_CENTER".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.BOTTOM_CENTER;
 	      X -= 213;
 	      Y = -240 - Y;
 	    } else if ("BOTTOM_RIGHT".equalsIgnoreCase(anchorName)) {
 	      anchor = WidgetAnchor.BOTTOM_RIGHT;
 	      X = -427 - X;
 	      Y = -240 - Y;
 	    }
 	    MMOHUDEvent event = new MMOHUDEvent(player, this, anchor, X, Y);
 	    this.getServer().getPluginManager().callEvent(event);
 	    Container container = (Container)new GenericContainer().setAlign(event.getAnchor()).setAnchor(event.getAnchor()).setFixed(true).setX(event.getOffsetX()).setY(event.getOffsetY()).setWidth(427).setHeight(240);
 	    player.getMainScreen().attachWidget(this, container);
 	    return container;
 	  }
 	  
 		public void updateAll() {
 			if (hplugin.getPartyManager().getParties().size() <= 0)
 				return;
 			for (HeroParty party: hplugin.getPartyManager().getParties()) {
 				update(party);
 				}	
 			
 		}
 		
 		public void updateAll(Player player) {
 			if (player != null) {
 				HeroParty party = find(player);
 				update(party);	
 				}	
 			}
 		
 		public HeroParty find(String player) {
 			for (HeroParty party : hplugin.getPartyManager().getParties()) {
 				if (party.getMembers().contains(player)) {
 					return party;
 					}
 				}
 			HeroParty party = new HeroParty(hplugin.getCharacterManager().getHero(this.getServer().getPlayer(player)), hplugin);
 			hplugin.getPartyManager().addParty(party);
			return party;	
 			}
 		
 		public HeroParty find(Player player) {
 			return find(player.getName());
 			}
 			
 		public void update(HeroParty hp) {
 			for (Hero player : hp.getMembers()) {
 				update(player.getPlayer());	
 				}	
 			}
 		  @EventHandler(priority=EventPriority.MONITOR)
 		  public void onPlayerRespawn(PlayerRespawnEvent event)
 		  {
 		    redrawAll(event.getPlayer());
 		  }
 		  @EventHandler(priority=EventPriority.MONITOR)
 		  public void onPlayerPortal(PlayerPortalEvent event) {
 		    redrawAll(event.getPlayer());
 		  }
 		  @EventHandler(priority=EventPriority.MONITOR)
 		  public void onPlayerTeleport(PlayerTeleportEvent event) {
 		    if (!event.getFrom().getWorld().equals(event.getTo().getWorld()))
 		      redrawAll(event.getPlayer());
 		  }
 		  
 		  public void redrawAll(Player player) {
 			    SpoutPlayer splayer = SpoutManager.getPlayer(player);
 			    if (splayer.isSpoutCraftEnabled())
 			      for (Widget widget : splayer.getMainScreen().getAttachedWidgets())
 			        if ((widget.getPlugin() instanceof Features))
 			          widget.setDirty(true);
 			  }
 		  

 		    @EventHandler
 		    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
 		    {
 		      Entity target = event.getRightClicked();
 		      perPlayer per = (perPlayer)data.get(event.getPlayer());
 		      if (((target instanceof LivingEntity)) && (per != null))
 		        per.setTarget((LivingEntity)target);
 		    }

 		    @EventHandler
 		    public void onEntityDamage(EntityDamageEvent event)
 		    {
 		      if (event.isCancelled()) {
 		        return;
 		      }
 		      LivingEntity attacker = null;
 		      Entity defender = event.getEntity();
 		      if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
 		        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
 		        if ((e.getDamager() instanceof LivingEntity))
 		          attacker = (LivingEntity)e.getDamager();
 		      }
 		      else if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
 		        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
 		        Projectile arrow = (Projectile)e.getDamager();
 		        if ((arrow.getShooter() instanceof LivingEntity)) {
 		          attacker = arrow.getShooter();
 		        }
 		      }
 		      if (((attacker instanceof Player)) && ((defender instanceof LivingEntity)) && (!attacker.equals(defender))) {
 		        perPlayer per = (perPlayer)data.get(attacker);
 		        if (per != null)
 		          per.setTarget((LivingEntity)defender);
 		      }
 		    }

 		    @EventHandler
 		    public void onPlayerQuit1(PlayerQuitEvent event) {
 		      data.remove(event.getPlayer());
 		    }
 		    
 		    @EventHandler
 		    public void onSpoutcraftEnable1(SpoutCraftEnableEvent event)
 		    {
 		      SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
 		      Container container = this.getContainer(player, "TOP_CENTER", 0, Features.config_ui_top);
 		      perPlayer bar = new perPlayer(player, config_ui_maxwidth);
 		      container.addChild(bar).setLayout(ContainerType.VERTICAL);
 		      container.setVisible(false);
 		      data.put(player, bar);
 		    }
 		    
 		    public List<String> getChannels(){
 		   	List<String> channels = new ArrayList<String>();
 		    	for (Channel c: Herochat.getChannelManager().getChannels()){
 		    		channels.add(c.getName());
 		    	}
				return channels;
 		    	
 		    }

		public final class perPlayer extends GenericLivingEntity
 		  {
 		    protected final SpoutPlayer player;
 		    protected LivingEntity target = null;
 		    protected LivingEntity target2 = null;

 		    public perPlayer(SpoutPlayer player) {
 		      this(player, 80);
 		    }

 		    public perPlayer(SpoutPlayer player, int width) {
 		      super();
 		      this.player = player;
 		      setEntity((LivingEntity)null);
 		      setVisible(false);
 		    }

 		    public void setTarget(LivingEntity target) {
 		      if (target != this.target) {
 		        this.target = target;
 		        setEntity(target);
 		        setVisible(target != null);
 		      }
 		    }

 		    public LivingEntity getTarget() {
 		      return this.target;
 		    }

 		    public void onTick()
 		    {
 		      if (this.target != null) {
 		        int health = getHealth(this.target);
 		        if ((!this.target.isDead()) && (health > 0) && (this.player.getWorld() == this.target.getWorld()) && (this.player.getLocation().distance(this.target.getLocation()) <= config_max_range))
 		        {
 		          if (((this.target instanceof Player)) && (data.containsKey((Player)this.target)))
 		            setTargets(new LivingEntity[] { ((perPlayer)data.get((Player)this.target)).target });
 		          else if (((this.target instanceof Creature)) && (((Creature)this.target).getTarget() != null) && (!((Creature)this.target).getTarget().isDead()))
 		            setTargets(new LivingEntity[] { ((Creature)this.target).getTarget() });
 		          else
 		            setTargets(new LivingEntity[0]);
 		        }
 		        else {
 		          setTargets(new LivingEntity[0]);
 		          setTarget(null);
 		        }
 		      }
 		      super.onTick();
 		    }
 		  }
 		}
