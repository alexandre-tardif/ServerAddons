package com.legacycraft.Botz147.Features.Listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.legacycraft.Botz147.Features.Features;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class TestListener implements Listener{
	
	@EventHandler()
	public void onExpBurn(EntityCombustEvent e){
		if (e instanceof ExperienceOrb){
			e.setCancelled(true);
		}
	}
	@EventHandler()	
	public void onEntityDeath(EntityDeathEvent e){
		if (e.getEntity().getLastDamageCause().getCause() == DamageCause.FIRE_TICK){
			EntityType en = e.getEntityType();
			switch (en){
			case CREEPER: e.setDroppedExp(5);
			break;
			case GHAST: e.setDroppedExp(5);
			break;
			case CHICKEN: e.setDroppedExp(3);
			}
		}
	}
	@EventHandler()
	public void onPlayerMove(PlayerMoveEvent e){
		if (e.getPlayer().getLocation().getBlock().getType() == Material.STATIONARY_WATER)
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 2), true);
		LocalPlayer lp = Features.wg.wrapPlayer(e.getPlayer());
		ApplicableRegionSet as = Features.wg.getGlobalRegionManager().get(Bukkit.getServer().getWorld("World")).getApplicableRegions(e.getPlayer().getLocation()).canBuild(lp);
		}
	}

}
