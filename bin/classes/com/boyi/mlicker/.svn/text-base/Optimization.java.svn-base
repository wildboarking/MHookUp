package com.boyi.mlicker;

import java.util.ArrayList;

public class Optimization {
    
 ArrayList<String>  picks= new ArrayList<String>();
 int wait=15,missed=-5;
	
	public Optimization(ArrayList<Integer> checkedIDs, Theater theater){
		
		int n=checkedIDs.size();
		int i,j;
		for (i=0;i<n-1;i++){
		   for (j=i+1;j<=n-1;j++){
			  
			 i=checkedIDs.get(i);
			 j=checkedIDs.get(j);
			 int A=theater.movies.get(i).showtimes.size();
			 int B=theater.movies.get(j).showtimes.size();
			 
			     int k,l;
			     for (k=0;k<A;k++){
					   for (l=0;l<B;l++){
						   int movieA = timetovalue(theater.movies.get(i).showtimes.get(k));
						   int movieArun = theater.movies.get(i).runtime;
						   int movieB = timetovalue(theater.movies.get(j).showtimes.get(l));
						   int movieBrun = theater.movies.get(j).runtime;

						if  ( ( movieB-(movieA+movieArun)<=wait && movieB-(movieA+movieArun)>=missed ) || ( movieA-(movieB+movieBrun)<=wait && movieA-(movieB+movieBrun)>=missed) ){
							String pick=theater.movies.get(i).name +"   "+ theater.movies.get(i).showtimes.get(k) +"   <--->   " +"\n"+theater.movies.get(j).name +"   "+theater.movies.get(j).showtimes.get(l);													
							picks.add(pick);		
						}
						   
					   }
			     }
		   }
		}
		
	}
	
	
	
	
	public int timetovalue(String string) {
		 int num=1,tempA=1,tempB=1;	 
		
			if (string.indexOf("a") != -1) {
				 tempA = Integer.valueOf(string.substring(0, string.indexOf(":")));
				 if (tempA==12){ //fix the bug 12:10a
					   tempA=0;
				   }

			     tempB = Integer.valueOf(string.substring(string.indexOf(":")+1, string.length()-1));
			     num = tempA*60+tempB;
			     }
			  
			if (string.indexOf("p") != -1) {
				   
				   tempA = Integer.valueOf(string.substring(0, string.indexOf(":")));
				   if (tempA==12){ //fix the bug 12:10p
					   tempA=0;
				   }

			       tempB = Integer.valueOf(string.substring(string.indexOf(":")+1, string.length()-1));

				  num = tempA*60+tempB+720;
				  
			}      
			
			return num;
			
	}
	
	
	
}
