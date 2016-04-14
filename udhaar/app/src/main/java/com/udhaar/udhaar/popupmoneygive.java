package com.udhaar.udhaar;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class popupmoneygive extends AppCompatActivity {
    String cnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupmoneygive);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.35));

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

    public void save_give (View view) {
        String id = PreferenceManager.getDefaultSharedPreferences(popupmoneygive.this).getString("txtid", "NULL");
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtid1", id);
        postData.put("txtmob",cnum);
        EditText editText = (EditText) findViewById(R.id.editText4);
        String money = editText.getText().toString();
        postData.put("txtmoney",money);

//        PostResponseAsyncTask giveTask =
//                new PostResponseAsyncTask(popupmoneygive.this, postData);
//
//        System.out.println("Before Adding user...");
//        giveTask.execute("http://172.30.127.159:8088/udhaar-db/popupmoneygive.php");
//        System.out.println("After Sending Request...");

        Intent intent = new Intent(this, userhome.class);

        startActivity(intent);
    }

}
