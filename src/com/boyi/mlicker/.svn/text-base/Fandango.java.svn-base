package com.boyi.mlicker;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.content.Intent;


public class Fandango extends Activity {
	
	String zip=null;

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fandango);
		
		EditText et = (EditText) findViewById(R.id.editLocation);
		WebView webview = (WebView) findViewById(R.id.webView);
     
		
		
		
		String s=getIntent().getExtras().getString("zip");
		et.setText(s);
		
		zip=getIntent().getExtras().getString("zip");
		
		String sUrl="http://www.fandango.com/"+zip+"_movietimes";	
		webview.getSettings().setJavaScriptEnabled(true);
	    webview.loadUrl(sUrl);
	    webview.setWebViewClient(new MyWebViewClient()); // forced open in webview instead of default browser

	}

	
	
	
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {			
			view.loadUrl(url);
            return true;	   
		}
	}
	
	
	

}
