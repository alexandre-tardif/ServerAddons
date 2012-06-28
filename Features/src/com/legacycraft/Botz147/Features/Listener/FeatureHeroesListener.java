package com.legacycraft.Botz147.Features.Listener;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.herocraftonline.heroes.api.events.ClassChangeEvent;
import com.herocraftonline.heroes.api.events.ExperienceChangeEvent;
import com.herocraftonline.heroes.api.events.HeroChangeLevelEvent;
import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;
import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.CharacterTemplate;
import com.herocraftonline.heroes.characters.Hero;
import com.legacycraft.Botz147.Features.Features;
import com.legacycraft.Botz147.Features.Utils.DamageManager;
import com.legacycraft.Botz147.Features.Utils.TextSpawn;

public class FeatureHeroesListener implements Listener {
	private Plugin plugin;
	public FeatureHeroesListener (Plugin p){
		this.plugin = p;
	}
    private static final DecimalFormat decFormat = new DecimalFormat("#0.##");
    private Player p;
    private Hero h;
    private Vector v;
    private float scale = 0.5F;
    private Location loc;
    private int duration;
    private String msg;
    private SpoutPlayer sp;
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
	public void onExpChange(ExperienceChangeEvent e){
		loc = e.getLocation();
		p = e.getHero().getPlayer();
        sp = SpoutManager.getPlayer(p);
		scale = 0.5F;
		duration = 60;
		v = new Vector(0.0F,0.025F,0.0F);
		if (e.getExpChange() > 0)
		    msg = (ChatColor.YELLOW + "+" + decFormat.format(e.getExpChange()) + " " + "XP");
		else
			msg = (ChatColor.RED + "-" + decFormat.format(e.getExpChange()) + " " + "XP");
    	if((Features.hplugin.getCharacterManager().getHero(p).isVerbose()))
	    new TextSpawn(sp, v, scale, duration, msg, loc);
	}
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onWeaponDamage(WeaponDamageEvent e){
		CharacterTemplate en = e.getDamager();
		LivingEntity le = null;
		int health = -1;
		Player p = null;
		    if (en instanceof Hero){
		    	h = (Hero) en;
		        p = h.getPlayer();
		    }
	    Entity mob = e.getEntity();
	        if (mob instanceof ComplexEntityPart){
	        	le = ((ComplexEntityPart) e.getEntity()).getParent();
	        }
	        if (mob instanceof LivingEntity){
	        	le = (LivingEntity) mob;
	        }
	        
	        if (le != null)
	        	health = Features.hplugin.getCharacterManager().getHealth(le);
	        if(p != null && le != null && health != -1){
	        	plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new DamageManager(SpoutManager.getPlayer(p), e.getDamage(), new Vector(0.0F, 0.025F, 0.0F), 0.5F, 60, e.getEntity().getLocation().add(0, 1, 0), le, health), 2L);
	}
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onSkillDamage(SkillDamageEvent e){
    	CharacterTemplate chara = e.getDamager();
	    if (!(chara instanceof Hero) || (e.getDamage() == 0)){
	    	return;
	    }
		Hero h = (Hero) chara;
		p = h.getPlayer();
		v = new Vector(0.0F,0.025F,0.0F);
		duration = 60;
		scale = 0.5F;
		sp = SpoutManager.getPlayer(p);
		loc = e.getEntity().getLocation().add(0, 1, 0);
		msg = (ChatColor.RED + "-" + decFormat.format(e.getDamage()) + " HP");
    	if((Features.hplugin.getCharacterManager().getHero(p).isVerbose()))
        new TextSpawn(sp, v, scale, duration, msg, loc);   
    }
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onHealthRegain(HeroRegainHealthEvent e){
    	loc = e.getHero().getPlayer().getLocation().add(0, 1, 0);
    	p = e.getHealer().getPlayer();
        sp = SpoutManager.getPlayer(p);
		scale = 0.5F;
		duration = 60;
		v = new Vector(0.0F,0.025F,0.0F);
		msg = (ChatColor.GREEN + "+" + decFormat.format(e.getAmount()) + " " + "HP");
    	if((Features.hplugin.getCharacterManager().getHero(p).isVerbose()))
        new TextSpawn(sp, v, scale, duration, msg, loc);
    }
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onLevelUp(HeroChangeLevelEvent e) {
    	int previousLevel = e.getFrom();
    	int newLevel = e.getTo();
    	loc = e.getHero().getPlayer().getLocation().add(0.0D, 1.0D, 0.0D);
    	scale = 0.7F;
    	duration = 150;
    	v = new Vector(0.0F, 0.025F, 0.0F);
    	List<Entity> list = e.getHero().getPlayer().getNearbyEntities(20, 20, 20);
    	Iterator<Entity> i = list.iterator();
    	
    	    if (previousLevel < newLevel){
    	        msg = (ChatColor.GREEN + "Level Up");
    	    }else{
    	        msg = (ChatColor.RED + "Lost Level");
    	    }
    	    
    	    while(i.hasNext()){
    	    	Entity entity = i.next();
    	    	    if (!(entity instanceof Player)) 
    	    	    	continue;
    	    	p = (Player)entity;
    	    	sp = SpoutManager.getPlayer(p);
    	    	if((Features.hplugin.getCharacterManager().getHero(p).isVerbose()))
    	    	new TextSpawn(sp, v, scale, duration, msg, loc);
    	    	
    	    }
	}
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onLevelUp(ClassChangeEvent e) {
    	loc = e.getHero().getPlayer().getLocation().add(0.0D, 1.0D, 0.0D);
    	scale = 0.4F;
    	duration = 100;
    	v = new Vector(0.0F, 0.020F, 0.0F);
    	String cl = e.getTo().getName();
    	List<Entity> list = e.getHero().getPlayer().getNearbyEntities(20, 20, 20);
    	Iterator<Entity> i = list.iterator();
        msg = (ChatColor.LIGHT_PURPLE + "Changed to " + cl);
    	    
    	    
    	    while(i.hasNext()){
    	    	Entity entity = i.next();
    	    	    if (!(entity instanceof Player)) 
    	    	    	continue;
    	    	p = (Player)entity;
    	    	sp = SpoutManager.getPlayer(p);
    	    	if((Features.hplugin.getCharacterManager().getHero(p).isVerbose()))
    	    	new TextSpawn(sp, v, scale, duration, msg, loc);
    	    	
    	    }
    }
}