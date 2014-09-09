package com.boyi.mlicker;
import java.util.ArrayList;


public class Movie extends Object{
  ArrayList<String> showtimes= new ArrayList<String>();
  String name;
  int runtime;
  
  public Movie(String string){
	  this.name=string;
  }
  
}
