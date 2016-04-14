package com.udhaar.udhaar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.content.SharedPreferences;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    boolean firstlogin=true;
    private static final int mode=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        preferenceSettings = getPreferences(mode);
        preferenceEditor = preferenceSettings.edit();
        boolean isfirstlogin=preferenceSettings.getBoolean("firstlogin",firstlogin);
        if(isfirstlogin)
        {
            preferenceEditor.putBoolean("firstlogin",false);
            preferenceEditor.commit();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, userhome.class);
            startActivity(intent);
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
        boolean isfirstlogin=preferenceSettings.getBoolean("firstlogin",firstlogin);
        if(isfirstlogin)
        {
            preferenceEditor.putBoolean("firstlogin",false);
            preferenceEditor.commit();
            Intent intent = new Intent(this, login.class);

            startActivity(intent);
        }
    }

    public void gotohomepage (View view) {

        Intent intent = new Intent(this, userhome.class);

        startActivity(intent);
    }


    public void dbconnect (View view) {

        Intent intent = new Intent(this, db.class);
        startActivity(intent);
    }

    public void gotoprofilepage (View view) {
        Intent intent = new Intent(this, userprofile.class);
        startActivity(intent);
    }

}
