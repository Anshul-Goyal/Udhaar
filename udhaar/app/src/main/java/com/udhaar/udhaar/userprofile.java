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
    public static userprofile upobj;
    //    int id,mon;
    com.udhaar.udhaar.Contacts contact = new com.udhaar.udhaar.Contacts();
//    String finalmoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userhome.var=0;
        super.onCreate(savedInstanceState);
        upobj=this;
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
//              finalmoney = extras.getString("name");  // CHECK HERE !!!!
            }
        } else {
            cnum = (String) savedInstanceState.getSerializable("cnum");
        }

        TextView t1 = (TextView)findViewById(R.id.textname);
        t1.setText(name);


        DatabaseHandler ob = new DatabaseHandler(this);
        contact = ob.getContact(cnum);
//        contact.setMoney(45);
//        contact.setName("Aaarchit");
//        contact.setPhoneNumber("9908341446");
//        contact.setID(2);
        int finalm=contact.getMoney();
        TextView t2 = (TextView)findViewById(R.id.textmoney);
        String finalmoney = String.valueOf(finalm);
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
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("cnum", cnum);
        i.putExtra("name" , name);
        i.putExtra("id",contact.getID());
        i.putExtra("tym",contact.getTime());
        i.putExtra("money",contact.getMoney());
        i.putExtra("oneid",contact.getoneid());
        startActivity(i);
    }

    public void popupmoneytake (View view){

        Intent i = new Intent(this, popupmoneytake.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("cnum",cnum);
        i.putExtra("name" , name);
        i.putExtra("id",contact.getID());
        i.putExtra("tym",contact.getTime());
        i.putExtra("money",contact.getMoney());
        i.putExtra("oneid",contact.getoneid());
        startActivity(i);
    }


}
