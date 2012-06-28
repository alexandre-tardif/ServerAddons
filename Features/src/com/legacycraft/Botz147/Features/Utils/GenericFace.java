package com.legacycraft.Botz147.Features.Utils;

import org.getspout.spoutapi.gui.GenericTexture;

public final class GenericFace extends GenericTexture
{
  private static String facePath = "http://face.mmo.me.uk/";
  private static int defaultSize = 8;
  private String name;

  public GenericFace()
  {
    this("", defaultSize);
  }

  public GenericFace(String name) {
    this(name, defaultSize);
  }

  public GenericFace(String name, int size) {
    setWidth(size).setHeight(size).setFixed(true);
    setName(name);
  }

  public String getName() {
    return this.name;
  }

  public GenericFace setName(String name) {
    this.name = (name == null ? "" : name);
    super.setUrl(facePath + this.name + ".png");
    super.setDirty(true);
    return this;
  }

  public GenericFace setSize(int size) {
    super.setWidth(size).setHeight(size);
    return this;
  }
}