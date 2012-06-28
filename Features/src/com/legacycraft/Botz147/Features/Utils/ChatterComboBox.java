package com.legacycraft.Botz147.Features.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.GenericComboBox;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.Herochat;

public class ChatterComboBox extends GenericComboBox{
	  String info;
	  String text;
	  Map<String, String> items;
	  Player pl;
	  
	  public ChatterComboBox(String in, String tex, Player p){
		  this.info = in;
		  this.text = tex;
	      this.pl = p;
	      
	      if (this.info != null)
	    	  setTooltip(info);
	      
	      if (this.text != null)
	    	    setText(this.text);
	      
	      
	      if (Herochat.getChatterManager().getChatter(pl) == null)
	    	  return;
	      Herochat.getChatterManager().getChatter(pl);
	      List<String> temp = new ArrayList<String>();
	      this.items = new HashMap<String, String>();
	      for (Channel ch : Herochat.getChannelManager().getChannels()){
	    	  if((ch.getPassword() != "")){
	    	  String s = ch.getName();
	          temp.add(s);
	          this.items.put(s, s);
	    	  }
	      }
	        Collections.sort(temp);
	        temp.add("No item");
	        this.items.put("No item", "");
	        setItems(temp);
	  }
	
	
	@SuppressWarnings("deprecation")
	public void onSelectionChanged(int i, String text) {
		    if (i > -1)
		    {
		      if (text != "No item")
		      Herochat.getChatterManager().getChatter(pl).setActiveChannel(Herochat.getChannelManager().getChannel(text), true, true);
		      
		      SpoutPlayer sp = SpoutManager.getPlayer(pl);
		      sp.getMainScreen().closePopup();
		      sp.getMainScreen().removeWidget(this);
		      sp.closeActiveWindow();
		    }
		  }

}
