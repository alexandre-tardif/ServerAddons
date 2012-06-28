package com.legacycraft.Botz147.Features.Utils;

import java.util.ArrayList;

public class ArrayListString extends ArrayList<String>
{
  private static final long serialVersionUID = 6793276940676543571L;

  public boolean contains(String o)
  {
    for (String e : this) {
      if (o == null ? e == null : o.equalsIgnoreCase(e)) {
        return true;
      }
    }
    return false;
  }

  public int indexOf(String o)
  {
    for (int i = 0; i < size(); i++) {
      if (o == null ? get(i) == null : o.equalsIgnoreCase((String)get(i))) {
        return i;
      }
    }
    return -1;
  }

  public int lastIndexOf(String o)
  {
    for (int i = size() - 1; i >= 0; i--) {
      if (o == null ? get(i) == null : o.equalsIgnoreCase((String)get(i))) {
        return i;
      }
    }
    return -1;
  }

  public boolean remove(String o)
  {
    int i = indexOf(o);
    if (i != -1) {
      remove(i);
      return true;
    }
    return false;
  }

  public String get(String index)
  {
    int i = indexOf(index);
    if (i != -1) {
      return (String)get(i);
    }
    return null;
  }

  public ArrayListString meFirst(String name) {
    ArrayListString copy = new ArrayListString();
    if (contains(name)) {
      copy.add(name);
    }
    for (String next : this) {
      if (!next.equalsIgnoreCase(name)) {
        copy.add(next);
      }
    }
    return copy;
  }
}