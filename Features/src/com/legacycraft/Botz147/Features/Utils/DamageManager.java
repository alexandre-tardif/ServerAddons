package com.legacycraft.Botz147.Features.Utils;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.particle.Particle;
import org.getspout.spoutapi.particle.Particle.ParticleType;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.legacycraft.Botz147.Features.Features;

public class DamageManager implements Runnable{

	private SpoutPlayer sp;
	private int damage;
	private Vector movement;
	private float scale;
	private int duration;
	private Location loc;
	private LivingEntity le;
	private int hp;
    private static final DecimalFormat decFormat = new DecimalFormat("#0.##");

	public DamageManager(SpoutPlayer sp, int damage, Vector movement, float scale, int duration, Location loc, LivingEntity e, int hp){
            this.sp = sp;
            this.damage = damage;
            this.movement = movement;
            this.scale = scale;
            this.duration = duration;
            this.loc = loc;
            this.le = e;
            this.hp = hp;
	}	

	@Override
	public void run() {
	    Boolean checkForZeroDamageHide = Boolean.valueOf(true);    
		int mobsHealth;
		String msg = null;
		
		
		if (le.isDead()){
	          mobsHealth = hp - damage;
	        }
	        else {
	          mobsHealth = Features.hplugin.getDamageManager().getHealth(le);
	        }
		int damageTaken = hp - mobsHealth;    	    

	    if (damageTaken > 0) {
	        checkForZeroDamageHide = Boolean.valueOf(false);
	    }else{
	        checkForZeroDamageHide = Boolean.valueOf(true);
	    }
	        
        if (!checkForZeroDamageHide.booleanValue()){
           msg = (ChatColor.RED + "-" + decFormat.format(damage) + "HP");
           new TextSpawn(sp, movement, scale, duration, msg, loc);
	        Particle part = new Particle(ParticleType.SNOWBALLPOOF, loc, new Vector(0, -0.4f, 0)).setParticleRed(1).setParticleBlue(0).setParticleGreen(0).setMaxAge(500).setGravity(1F).setScale(0.7f).setAmount(damage);
	        part.spawn();
       	
           List<Entity> list = sp.getNearbyEntities(20, 20, 20);
       	   Iterator<Entity> i = list.iterator();
   	       while(i.hasNext()){
	    	   Entity entity = i.next();
	    	       if (!(entity instanceof Player)) 
	    	       	   continue;
	    	   Player p = (Player)entity;
	    	   sp = SpoutManager.getPlayer(p);
	    	   String m = (ChatColor.YELLOW + "-" +decFormat.format(damage)+ "HP");
	    	   if((Features.hplugin.getCharacterManager().getHero(p).isVerbose()))
	    	   new TextSpawn(sp, movement, scale, duration, m, loc);
	    	       
	    	
	    }
	   }
	   else return;
		}	

		trololol
	}
