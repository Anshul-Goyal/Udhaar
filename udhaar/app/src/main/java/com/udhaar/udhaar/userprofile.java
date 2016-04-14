package com.udhaar.udhaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class userprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void popupmoneygive (View view){

        Intent intent = new Intent(this, popupmoneygive.class);

        startActivity(intent);
    }

    public void popupmoneytake (View view){

        Intent intent = new Intent(this, popupmoneytake.class);

        startActivity(intent);
    }

    public void popupitemgive (View view){

        Intent intent = new Intent(this, popupitemgive.class);

        startActivity(intent);
    }

    public void popupitemreturn (View view){

        Intent intent = new Intent(this, popupitemreturn.class);

        startActivity(intent);
    }

}
