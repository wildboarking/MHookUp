package com.boyi.mlicker;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ListView;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;




public class Moviepick extends Activity implements OnClickListener{
	
	String zip=null;
	String sUrl;
	TextView textView;
	TextView theaterView;
	ListView listView;
	Button btnGetmovies,btnLickMe;
	private static final String TAG = "moviepick"; 
	StringBuilder textSB;
	String text,textR="";
	Theater theater;
	Optimization optimized;
	ArrayList<Integer> checkedIDs = new ArrayList<Integer>(); 
	PopupWindow pw;


    
	protected void onCreate(Bundle savedInstanceState)  {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moviepick);
		//textView = (TextView) findViewById(R.id.textView1); 
		theaterView = (TextView) findViewById(R.id.theatername); 
		listView = (ListView) findViewById(R.id.listView1); 
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);

		btnGetmovies = (Button) findViewById(R.id.btnGetmovies);  
		btnGetmovies.setOnClickListener(this);
		
		btnLickMe = (Button) findViewById(R.id.btnLick);  
		btnLickMe.setOnClickListener(this);
		
		
		textSB= new StringBuilder();
		
		registerClickCallback();
		
		//String s=getIntent().getExtras().getString("city")+"  "+getIntent().getExtras().getString("zip");		
		//zip=getIntent().getExtras().getString("zip");
		
		sUrl=getIntent().getExtras().getString("weblink");
		String name= getIntent().getExtras().getString("theatername");
		theaterView.setText(name);
        
		
	}

	
	public void onClick(View v) {
	if (v==btnGetmovies) {	
	/*--------------------------------read stream---------------------------------*/  
		Thread thread1 = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        		 			        	
		    	
		        	try{  
		 	        textSB.setLength(0); // initialize StringBuilder
		        		
		        	URL page= new URL(sUrl);
		 	    	
		 	    	HttpURLConnection conn = (HttpURLConnection) page.openConnection();
		 	    	conn.connect();
		 	        
		 	    	InputStreamReader in = new InputStreamReader( (InputStream) conn.getContent() );
		 	        
		 	
		 	        BufferedReader buff = new BufferedReader(in);
		 	        
		 	        String line="";
		 	        
		 	        do {
		 	        	line= buff.readLine();
			 	    	//Log.v(TAG,"line is: "+line); 
		 	        	textSB.append(line);
		 	        	textSB.append("\n");	 	        	
		 	           }while (line != null);
		 	    	   //Log.v(TAG,"textSB lol: "+textSB);
		 	        		 	        	        
		        	}     		        
		            
		        	catch (Exception  e) {
		               e.printStackTrace();
		            }
		    
		    		    		    
		    }
		});

		thread1.start(); 
		
		try {
			thread1.join(); // we need the previous thread to be done.
		} catch (InterruptedException e) {
			e.printStackTrace();}
		
	   /*---------------------------------get theater from text---------------------------*/	
  		
	   text=textSB.toString();
       //textView.setText(text); 		
	//	Toast.makeText(this, text, Toast.LENGTH_LONG).show();

			
	   makemovielist();  // get theater: theater obtained based on text
				 			        	   
	   
	   /*---------------------------------display moive list---------------------------*/	

	   int i;
       int n =theater.movies.size();	
	   String[] movieitems= new String[n];      
       for (i=0;i<n;i++){
    	int l= theater.movies.get(i).showtimes.size();    	   
    	movieitems[i]=theater.movies.get(i).name+"\n";
    	int j;
    	for (j=0;j<l;j++){
    		movieitems[i] = movieitems[i]+theater.movies.get(i).showtimes.get(j)+" ";
    	}
    	int a=theater.movies.get(i).runtime;
    	if (a==-99){
    		movieitems[i]=movieitems[i]+"\n"+"RUNTIME not provided, don't check!";	
    	} else{
    		movieitems[i]=movieitems[i]+"\n"+"RUNTIME: "+theater.movies.get(i).runtime+" min";	

    	}
       }
       ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,R.layout.movieitem,movieitems);
       listView.setAdapter(adapter); 
	   } // end of Getmovies button
	
	
	if (v==btnLickMe) {
	    /*-------------------------------display movie optimization-------------------------*/	
		textR="";
	    String message;

		checkedmovies(); // get checkedIDs
		
		if (checkedIDs.size()<2) {
		//Toast.makeText(Moviepick.this, "Please check at least 2 movies ^_^", Toast.LENGTH_LONG).show();
		message="Please check at least 2 movies ^_^";
		}else{
		
		optimized = new Optimization(checkedIDs,theater);
		//Toast.makeText(Moviepick.this, "movies picked: "+checkedIDs.size(), Toast.LENGTH_LONG).show();
		
		
		if (optimized.picks.size()==0){
			//Toast.makeText(Moviepick.this, "...bad", Toast.LENGTH_LONG).show();
			message="bad bad...";
		}
		else {
			int i;    //read out
		    for (i=0;i<optimized.picks.size();i++){
		    textR=textR+optimized.picks.get(i)+"\n\n";
		    }
			//Toast.makeText(Moviepick.this, textR, Toast.LENGTH_LONG).show();
		    message=optimized.picks.size()+" choice(s):\n\n"+textR;
		}
	
		}
	    /*---------------------------------pop up window-----------------------------------*/	

		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        View layout = inflater.inflate(R.layout.popwindowresult,(ViewGroup) findViewById(R.id.popup_element));
        
        TextView tv = (TextView) layout.findViewById(R.id.pwText);
        tv.setText(message);
        
        Button btnclosepw = (Button) layout.findViewById(R.id.btnclosepw);
        btnclosepw.setOnClickListener(new OnClickListener() {
        	public void  onClick(View v){
        		pw.dismiss();
        	}
        });
		
        //pw = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pw = new PopupWindow(layout,960,1250,true);
		pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

		
	  }
	}
	
	public void registerClickCallback(){
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				
				TextView textView = (TextView) viewClicked;				
				String message = "You clicked # "+position+",which is: "+ textView.getText().toString();		    
				//Toast.makeText(Moviepick.this, message, Toast.LENGTH_LONG).show();
			}
		
		});
	}
	
	public void checkedmovies(){
		checkedIDs.clear();
		SparseBooleanArray sp=listView.getCheckedItemPositions();
		for (int i = 0; i < sp.size(); i++){
		    // And this tells us the item status at the above position
		    boolean isChecked = sp.valueAt(i);
		    if (isChecked){
		        // This tells us the item position we are looking at
		        int position = sp.keyAt(i);                                             
		        // Put the value of the id in our list
		        checkedIDs.add(position);                                                       
		    }
		}
	}
	
	
	public void makemovielist(){	
		// find theater 
		int theaterbegin=text.indexOf("<title>");
		int theaterend=text.indexOf("- Movie Times - Fandango Mobile</title>");
		String theatername = text.substring(theaterbegin+7,theaterend);
        //Toast.makeText(getApplicationContext(), theatername, Toast.LENGTH_LONG).show(); 
        theater= new Theater(theatername);
       
        Log.v(TAG,"theater: "+theater.name);
        // find movie
        ArrayList<Integer> moviebegin = new ArrayList<Integer>();  
        ArrayList<Integer> movieend = new ArrayList<Integer>();        

        int tempbegin,tempend;
        tempbegin=text.indexOf("class='movTitle'>");
        tempend=text.indexOf("</a><br/><span class='movRuntime'");
        //Log.v(TAG,"tempbegin: "+tempbegin);
        //Log.v(TAG,"tempend: "+tempend);

            
        while(tempbegin >= 0) {
        	moviebegin.add(tempbegin);
        	movieend.add(tempend);
            Log.v(TAG,"tempbegin: "+tempbegin);

        	String movietemp=text.substring(tempbegin+17,tempend);
        	Movie movie = new Movie(movietemp);
            Log.v(TAG,"name: "+movie.name);

        	String runtimetemp=text.substring(tempend+31,tempend+60);
        	movie.runtime=cleanruntime(runtimetemp);
            theater.movies.add(movie);
            tempbegin=text.indexOf("class='movTitle'>",tempbegin+1);	
            tempend=text.indexOf("</a><br/><span class='movRuntime'",tempend+1);	

        }
        Log.v(TAG,"movies: "+theater.movies.size());

        int n=theater.movies.size();

        moviebegin.add(moviebegin.get(n-1)+5000); //fake last movie for loop convenience
        
        // find showtime
        int i;
        for (i=0;i<n;i++){	
         
         String textmovie = text.substring(moviebegin.get(i),moviebegin.get(i+1));  
         ArrayList<Integer> showtimebegin = new ArrayList<Integer>(); 
        

         int tempshowtimebegin;
         tempshowtimebegin=textmovie.indexOf("class='showtimes'>");
         while(tempshowtimebegin >= 0 ) {
        	showtimebegin.add(tempshowtimebegin);
        	String s=textmovie.substring(tempshowtimebegin+17,tempshowtimebegin+24);
        	String cleaneds= cleanshowtime(s);
        	theater.movies.get(i).showtimes.add(cleaneds);
        	tempshowtimebegin=textmovie.indexOf("class='showtimes'>",tempshowtimebegin+1);		 
         }
        
         
        }
        
        // debug
//        for (i=0;i<theater.movies.size();i++){
//             n=theater.movies.get(i).showtimes.size();
//         	 Log.v(TAG,"movie: "+theater.movies.get(i).name);
//             int j;
//            for (j=0;j<n;j++) {
//            	Log.v(TAG,"showtimes: "+ theater.movies.get(i).showtimes.get(j));
//            }
//        }	
        
//          for (i=0;i<theater.movies.size();i++){
//          Log.v(TAG,"movie: "+theater.movies.get(i).runtime);
//          }

	 }
	
	
	
		   
	 public String cleanshowtime(String string) {  
		 
		 String a = null;
		 
		 if (string.indexOf(">") != -1) {
			 a=string.substring(1);}
		 
		 if (a.indexOf("<") != -1) {
			 a=a.substring(0, a.length()-1);} 
		 
		 a=a.trim();
		 return a;
	 }
			    
     
     public int cleanruntime(String string) {  
		 int a=1,h=1,m=1;
		 
		 if (string.indexOf("hr") == -1){  //in case there is no run time showing
			 a=-99;
		 }else{
			 h=Integer.valueOf(string.substring(string.indexOf("hr")-2,string.indexOf("hr")-1).trim());
		     m=Integer.valueOf(string.substring(string.indexOf("min")-3,string.indexOf("min")-1).trim());
		 
		     a=h*60+m; 
		 }	     
		 return a;
	 }
	 
}









