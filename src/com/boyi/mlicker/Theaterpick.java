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

public class Theaterpick extends Activity implements OnClickListener{
	
	private static final String TAG = "theaterpick"; 
	String zipCode=null;
	String sUrl;
	StringBuilder textSB;
	String text;
	ListView lv;
	Button btnGettheater,btnGo;
	TextView textView;
    ArrayList<Theater> theaters;
	ArrayList<Integer> checkedIDs = new ArrayList<Integer>(); 


    protected void onCreate(Bundle savedInstanceState)  {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_theaterpick);
		//textView = (TextView) findViewById(R.id.textView1); 
		lv = (ListView) findViewById(R.id.listView1); 
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setItemsCanFocus(false);

        btnGettheater = (Button) findViewById(R.id.btnGettheater);  
        btnGettheater.setOnClickListener(this);
		
		btnGo = (Button) findViewById(R.id.btnGo);  
		btnGo.setOnClickListener(this);
		
		textSB= new StringBuilder(); //!!!!!!!!!!!!!!! so important!!!!!!!!!!!!!!!!!!!!!!!

		
		//String s=getIntent().getExtras().getString("city")+"  "+getIntent().getExtras().getString("zip");		
		//zipCode=getIntent().getExtras().getString("zip");
		
		theaters = new ArrayList<Theater>();
		Theater theater0 = new Theater("Regal Stonefield Statium 14 and IMAX");
		theater0.weblink = "http://www.fandango.com/regalstonefieldstadium14andimax_aawuc/theaterpage";
		
		Theater theater1 = new Theater("Regal Downtown Mall 6"); 
		theater1.weblink = "http://www.fandango.com/regaldowntownmall6_aaeqb/theaterpage";

		Theater theater2 = new Theater("Carmike 6"); 
		theater2.weblink = "http://www.fandango.com/carmike6_aaiwe/theaterpage";
		
		Theater theater3 = new Theater("AMC Tysons Corner 16"); 
		theater3.weblink = "http://www.fandango.com/amctysonscorner16_aatso/theaterpage";
		
		
		theaters.add(theater0);
		theaters.add(theater1);
		theaters.add(theater2);
		theaters.add(theater3);
		


	}

	
	public void onClick(View v){
	
	if (v==btnGettheater) {
	   
	   /*---------------------------------display theater list---------------------------*/	

	   int i;
       int n =theaters.size();	
	   String[] theateritems= new String[n];      
       for (i=0;i<n;i++){
    	theateritems[i]=theaters.get(i).name;    	
       }
       ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,R.layout.theateritem,theateritems);
       lv.setAdapter(adapter); 
	   
	   
	}
		
    if (v==btnGo) {
    	checkedtheater();
    	if (checkedIDs.size()>1 || checkedIDs.size()==0) {
    		Toast.makeText(this,"Please check ONE theater ^_^", Toast.LENGTH_LONG).show();
    		}
    	else{
    		Intent i= new Intent(v.getContext(),Moviepick.class);
    		i.putExtra("weblink", theaters.get(checkedIDs.get(0)).weblink);
    		i.putExtra("theatername", theaters.get(checkedIDs.get(0)).name);

    	    startActivity(i);
    	}
    }
		
	}
	
	


	public void checkedtheater(){
		checkedIDs.clear();
		SparseBooleanArray sp=lv.getCheckedItemPositions();
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


}
