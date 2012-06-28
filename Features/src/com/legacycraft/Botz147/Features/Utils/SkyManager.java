package com.legacycraft.Botz147.Features.Utils;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.player.RenderDistance;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.legacycraft.Botz147.Features.Features;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class SkyManager implements Runnable{
	RenderDistance rd;
	Plugin p;
	Player py;
	private HashMap<Player, Vector> playerLoc = new HashMap<Player, Vector>();
	 private HashMap<LocalPlayer, String> playerRegion = new HashMap<LocalPlayer, String>();
	public SkyManager(Plugin p, Player py)
	{
		this.p = p;
		this.py = py;
	}
	@Override
	public void run() {
		SpoutPlayer sp = SpoutManager.getPlayer(py);
			if (!(sp.isSpoutCraftEnabled()))
				return;
		      Vector position = new Vector(py.getLocation().getBlockX(), py.getLocation().getBlockY(), py.getLocation().getBlockZ());
		      if (!this.playerLoc.containsKey(py)) {
		        this.playerLoc.put(py, position);
		        return;
		      }
		      if (!((Vector)this.playerLoc.get(py)).equals(position)) {
		        this.playerLoc.put(py, position);
		        WorldGuardPlugin wgPlugin = Features.wg;
		        rd = sp.getRenderDistance();
		        LocalPlayer lp = wgPlugin.wrapPlayer(py);
		        Vector vec = lp.getPosition();
		        Location loc = py.getLocation();
		        World world = loc.getWorld();
		        RegionManager rm = wgPlugin.getRegionManager(world);

		        ApplicableRegionSet appregions = rm.getApplicableRegions(vec);
		        if (appregions.size() == 0) {
		          if (this.playerRegion.containsKey(lp)){
		            this.playerRegion.remove(lp);
		            SpoutManager.getSkyManager().setSkyColor(sp, Color.remove());
	                SpoutManager.getSkyManager().setSunVisible(sp, true);
	                SpoutManager.getSkyManager().setCloudsVisible(sp, true);
	                SpoutManager.getSkyManager().setMoonVisible(sp, true);
	                SpoutManager.getSkyManager().setFogColor(sp, Color.remove());
	                sp.resetMinimumRenderDistance();
		            return;
		        }
		   }
		        String regionName = "";

		        for (ProtectedRegion protectedRegion : appregions) {
		          regionName = protectedRegion.getId();
		        }

		        if ((this.playerRegion.containsKey(lp)) && (((String)this.playerRegion.get(lp)).equals(regionName))) {
		          return;
		        }

		        this.playerRegion.put(lp, regionName);
		        if (regionName == rm.getRegion("spawn").getId()){
	                SpoutManager.getSkyManager().setSkyColor(sp, new Color(255, 40, 40));
	                SpoutManager.getSkyManager().setSunVisible(sp, false);
	                SpoutManager.getSkyManager().setCloudsVisible(sp, false);
	                SpoutManager.getSkyManager().setMoonVisible(sp, false);
	                SpoutManager.getSkyManager().setFogColor(sp, new Color(0, 0, 0));
	                sp.setRenderDistance(RenderDistance.NORMAL);
		        	}
		        } else {
	                SpoutManager.getSkyManager().setSkyColor(sp, Color.remove());
	                SpoutManager.getSkyManager().setSunVisible(sp, true);
	                SpoutManager.getSkyManager().setCloudsVisible(sp, true);
	                SpoutManager.getSkyManager().setMoonVisible(sp, true);
	                SpoutManager.getSkyManager().setFogColor(sp, Color.remove());
	                sp.resetMinimumRenderDistance();
		        }
		        }
		      }