package com.udhaar.udhaar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class userprofile extends AppCompatActivity {
    String cnum;
    String name;
    String finalmoney;
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
                name = extras.getString("name");
                finalmoney = extras.getString("name");  // CHECK HERE !!!!
            }
        } else {
            cnum = (String) savedInstanceState.getSerializable("cnum");
        }

        TextView t1 = (TextView)findViewById(R.id.textname);
        t1.setText(name);

        finalmoney="250";
        TextView t2 = (TextView)findViewById(R.id.textmoney);
        t2.setText(finalmoney);



        if(finalmoney.startsWith("-"))
        {
            t2.setTextColor(Color.RED);
        }

        else
        {
            t2.setTextColor(Color.GREEN);
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
