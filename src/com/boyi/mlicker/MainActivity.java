package com.boyi.mlicker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	private LocationManager locationMangaer=null;  
	private LocationListener locationListener=null;   
	   
	private Button btnGetLocation = null; 
	private Button btnStart;
	private Button btnFandango;
	private Button btnFindtheaters;
	private TextView zipView = null; 
	private ProgressBar pb =null; 
	String cityName=null; 
    String zipCode=null;
	PopupWindow pw;

	   
	private static final String TAG = "main";  
	private Boolean flag = false;  
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnGetLocation = (Button) findViewById(R.id.btnLocation);  
		btnGetLocation.setOnClickListener(this);  
		btnFandango = (Button) findViewById(R.id.btnFandango);  
		btnFandango.setOnClickListener(this);    
		btnFindtheaters = (Button) findViewById(R.id.btnFindtheaters); 
		btnFindtheaters.setOnClickListener(this);
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);  
		pb.setVisibility(View.INVISIBLE);  
		    
		zipView = (TextView) findViewById(R.id.cityandzip);  
		
		
		 		 
		locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override  
	public void onClick(View v) { 
		 
		
	if (v==btnGetLocation){
		
		/*---------------------------------ask for zipcode----------------------------*/
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        View layout = inflater.inflate(R.layout.popwindowaskzip,(ViewGroup) findViewById(R.id.popup_element));
        
        final EditText et = (EditText) layout.findViewById(R.id.pwTextEdit);
        
        Button btnclosepw = (Button) layout.findViewById(R.id.btnclosepw);
        btnclosepw.setOnClickListener(new OnClickListener() {
        	public void  onClick(View v){
        		pw.dismiss();
        		zipCode = et.getText().toString();
        		zipView.setText(zipCode);
        	}
        });
		
        pw = new PopupWindow(layout,600,400,true);
		pw.showAtLocation(layout, Gravity.CENTER, 0, 0);	
		
		
	
//	   flag = displayGpsStatus();  
//	   if (flag) {  
//	     	     
//	   cityandzip.setText("Please Wait..");  
//	     
//	   pb.setVisibility(View.VISIBLE);  
//	   locationListener = new MyLocationListener();  
//	  
//	   locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);  
//   	   //Log.v(TAG,"Message here"); 
//  
//	   } 
//	  
//	  else {  
//	  Toast.makeText(getApplicationContext(),"Your GPS is: OFF", Toast.LENGTH_SHORT).show(); 
//	  }  
		
		
	}
	
	if (v==btnFandango){
  		Intent intent = new Intent(v.getContext(),Fandango.class);
  		intent.putExtra("city", cityName);
  		intent.putExtra("zip", zipCode);
  		startActivity(intent);
  		
	}
	
	
	
	if (v==btnFindtheaters){
	Intent i= new Intent(v.getContext(),Theaterpick.class);
	i.putExtra("city", cityName);
	i.putExtra("zip", zipCode);
    startActivity(i);
	}	
    
	//Toast...
		 
    }	
	 
//	private Boolean displayGpsStatus() {  
//		  ContentResolver contentResolver = getBaseContext().getContentResolver();  
//		  boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);  
//		  if (gpsStatus) {  
//		   return true;  
//		  
//		  } else {  
//		   return false;  
//		  }  
//    }  		 

//	 /*----------Listener class to get coordinates ------------- */  
//    private class MyLocationListener implements LocationListener {  
//	        
//	        @Override  
//	        public void onLocationChanged(Location loc) {  
//	            
//	        	cityandzip.setText("");  
//	            pb.setVisibility(View.INVISIBLE);  
//	            String longitude = "Longitude: " +loc.getLongitude();    
//	            String latitude = "Latitude: " +loc.getLatitude();  
//	            
//	    /*----------to get City-Name and zipcode from coordinates ------------- */  
//	      Geocoder gcd = new Geocoder(getBaseContext(),Locale.getDefault());               
//	      List<Address>  addresses;    
//	      try {    
//	      addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);    
//	      if (addresses.size() > 0)    
//	         System.out.println(addresses.get(0).getLocality());    
//	         cityName=addresses.get(0).getLocality(); 
//	         zipCode=addresses.get(0).getPostalCode();
//	    	 Log.v(TAG,"cityName: "+cityName);
//
//	        } catch (IOException e) {              
//	        e.printStackTrace();    
//	      }   
//	            
//	      //String s = longitude+"\n"+latitude +"\n\nMy Currrent City is: "+cityName;  
//	      //String s = cityName;  	    
//	     
//	      cityandzip.setText(cityName+"  "+zipCode);
//	      
//	      }  
//	  
//	        @Override  
//	        public void onProviderDisabled(String provider) {  
//	            // TODO Auto-generated method stub           
//	        }  
//	  
//	        @Override  
//	        public void onProviderEnabled(String provider) {  
//	            // TODO Auto-generated method stub           
//	        }  
//	  
//	        @Override  
//	        public void onStatusChanged(String provider,   
//	             int status, Bundle extras) {  
//	            // TODO Auto-generated method stub           
//	        }  
//    } 
	 
  
	 
	 
	 

}
