package com.legacycraft.Botz147.Features.Utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TextSpawn {	


	public TextSpawn(SpoutPlayer sp, Vector movement, float scale, int duration, String msg, Location loc){
		TextEntities(sp, msg, loc,scale,duration,movement);
	}
	
	public void TextEntities(SpoutPlayer sp, String msg, Location loc, float scale, int duration, Vector movement){
		sp.spawnTextEntity(msg, loc, scale, duration, movement);
	}
}
