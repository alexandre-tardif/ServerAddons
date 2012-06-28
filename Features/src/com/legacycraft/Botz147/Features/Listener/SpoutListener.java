package com.legacycraft.Botz147.Features.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.legacycraft.Botz147.Features.Utils.SkyManager;

public class SpoutListener implements Listener{
	Plugin pl;
	public SpoutListener(Plugin p){
		this.pl = p;
	}
	    @EventHandler()
		public void onSpoutCraftEnable(SpoutCraftEnableEvent e){
			Player p = e.getPlayer();
			SpoutPlayer sp = SpoutManager.getPlayer(p);
        	if (p.hasPermission("group.citizen")){
	            SpoutManager.getSkyManager().setSkyColor(sp, Color.remove());
                SpoutManager.getSkyManager().setSunVisible(sp, true);
                SpoutManager.getSkyManager().setCloudsVisible(sp, true);
                SpoutManager.getSkyManager().setMoonVisible(sp, true);
                SpoutManager.getSkyManager().setFogColor(sp, Color.remove());
                sp.resetMinimumRenderDistance();
        		return;
        	}else{
			p.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new SkyManager(pl, p), 2L, 100L);
        	}
	    }
}
