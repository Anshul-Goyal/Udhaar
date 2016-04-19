package com.udhaar.udhaar;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class popupmoneytake extends AppCompatActivity implements AsyncResponse {
    String cnum;
    Button btngive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupmoneytake);

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

        btngive = (Button)findViewById(R.id.button24);

        btngive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = PreferenceManager.getDefaultSharedPreferences(popupmoneytake.this).getString("txtid", "NULL");
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtid1", id);
                postData.put("txtmob", cnum);
                EditText editText = (EditText) findViewById(R.id.editText5);
                String money = editText.getText().toString();
                postData.put("txtmoney", money);
                PostResponseAsyncTask Task =
                        new PostResponseAsyncTask(popupmoneytake.this, postData);
                System.out.println("Before Logging in");
                Task.execute("http://172.30.127.159:8088/udhaar-db/popupmoneytake.php");
                System.out.println("After Logging in....");
            }
        });

    }

    @Override
    public void processFinish(String output) {
        System.out.println("===================  " + output.toString() + "    =============");
        JSONObject jObj= new JSONObject();
        try {
            jObj = new JSONObject(output.toString());
        }
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        try {
            if (jObj.getString("success").equals("1"))
            {
                Toast.makeText(this, "Money Taken Successfully",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, userhome.class);
                startActivity(intent);
                this.finish();
            }
            else
            {
                Toast.makeText(this, "Operation Failed!!!",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }


}
