package com.legacycraft.Botz147.Features.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class MMOHUDEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player player;
  private Plugin plugin;
  private WidgetAnchor anchor;
  private int offsetX;
  private int offsetY;

  public MMOHUDEvent(Player player, Plugin plugin, WidgetAnchor anchor, int offsetX, int offsetY)
  {
    this.player = player;
    this.plugin = plugin;
    this.anchor = anchor;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  public Player getPlayer()
  {
    return this.player;
  }

  public Plugin getPlugin()
  {
    return this.plugin;
  }

  public WidgetAnchor getAnchor()
  {
    return this.anchor;
  }

  public int getOffsetX()
  {
    return this.offsetX;
  }

  public void setOffsetX(int offsetX)
  {
    this.offsetX = offsetX;
  }

  public int getOffsetY()
  {
    return this.offsetY;
  }

  public void setOffsetY(int offsetY)
  {
    this.offsetY = offsetY;
  }

  public HandlerList getHandlers()
  {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}