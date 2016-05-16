package com.udhaar.udhaar;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.content.SharedPreferences;

import com.onesignal.OneSignal;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    static String oneid;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    boolean firstlogin=true;
    private static final int mode=0;
    com.udhaar.udhaar.udhaarapp ob = new com.udhaar.udhaar.udhaarapp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new notificationhandler())
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                oneid = userId;
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });

        preferenceSettings = getPreferences(mode);
        boolean isfirstlogin = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getBoolean("firstlogin",true);
        if(isfirstlogin)
        {

            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            this.finish();
        }
        else
        {
            Intent intent = new Intent(this, userhome.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void one_time_login (View view){


        preferenceSettings = getPreferences(mode);
        preferenceEditor = preferenceSettings.edit();
        boolean isfirstlogin=preferenceSettings.getBoolean("firstlogin", firstlogin);
        if(isfirstlogin)
        {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void gotohomepage (View view) {

        Intent intent = new Intent(this, userhome.class);
        startActivity(intent);
        this.finish();
    }


    public void dbconnect (View view) {

        Intent intent = new Intent(this, db.class);
        startActivity(intent);
        this.finish();
    }

    public void gotoprofilepage (View view) {
        Intent intent = new Intent(this, userprofile.class);
        startActivity(intent);
        this.finish();
    }

    public void DB_DEMO (View view) {
        Intent intent = new Intent(this, DatabaseDemo.class);
        startActivity(intent);
        this.finish();
    }


    class udhaarapp extends Application{

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        }

        @Override
        public void onCreate() {
            super.onCreate();
            OneSignal.startInit(this)
                    .setNotificationOpenedHandler(new notificationhandler())
                    .init();
        }
//
//        @Override
//        public void notificationOpened(String s,JSONObject ob,boolean b)
//        {
//
//        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
        }

        @Override
        public void onTerminate() {
            super.onTerminate();
        }

    }


}
