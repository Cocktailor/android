package com.cs408.cocktailor_Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
 
public class SplashScreen extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private SharedPreferences prefs;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	prefs=getSharedPreferences("from",Activity.MODE_PRIVATE);
            	Intent i;
            	if(prefs.getBoolean("NFC", false)){
            		Editor edit = prefs.edit();
            		edit.putBoolean("NFC", false);
            		i =  new Intent(SplashScreen.this, MenuActivity.class);
            	}
            	else{
            		i = new Intent(SplashScreen.this, NFCWriter.class);
            	}

            	startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
}