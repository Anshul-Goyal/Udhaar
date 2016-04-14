package com.udhaar.udhaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class userprofile extends AppCompatActivity {
    String cnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                cnum = null;
            } else {
                cnum = extras.getString("cnum");
            }
        } else {
            cnum = (String) savedInstanceState.getSerializable("cnum");
        }

    }

    public void popupmoneygive (View view){

        Intent i = new Intent(this, popupmoneygive.class);
        i.putExtra("cnum",cnum);
        startActivity(i);
    }

    public void popupmoneytake (View view){

        Intent i = new Intent(this, popupmoneytake.class);
        i.putExtra("cnum",cnum);
        startActivity(i);
    }


}
