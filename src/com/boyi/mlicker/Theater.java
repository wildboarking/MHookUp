package com.boyi.mlicker;

import java.util.ArrayList;

public class Theater extends Object{
  public String location;
  public String name;
  public ArrayList<Movie> movies = new ArrayList<Movie>();
  public String weblink;
  
  
  public Theater(String string) {
	  this.name=string;
  }
  
  
}
