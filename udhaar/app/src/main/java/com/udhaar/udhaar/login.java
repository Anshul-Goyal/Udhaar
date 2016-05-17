package com.udhaar.udhaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login extends AppCompatActivity implements AsyncResponse {
    public static String ip = "udhaar.site88.net";
//   public static String ip = "192.168.0.8:8088/udhaar-db";

    EditText etmobile;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etmobile = (EditText)findViewById(R.id.etmobile);
        btnLogin = (Button)findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap postData = new HashMap();
                postData.put("btnLogin", "Login");
                postData.put("mobile", "android");
                postData.put("oneid",(HomeActivity.oneid).toString());
                postData.put("txtmobile", etmobile.getText().toString());

                PostResponseAsyncTask loginTask =
                        new PostResponseAsyncTask(login.this, postData);
                System.out.println("Before Logging in");
                loginTask.execute("http://"+ip+"/registration.php");
                System.out.println("After Logging in....");
            }
        });


    }

    @Override
    public void processFinish(String output) {
        System.out.println("===================  "+output.toString()+"    =============");
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
                Toast.makeText(this, "Account created Successfully",
                        Toast.LENGTH_LONG).show();
                PreferenceManager.getDefaultSharedPreferences(login.this).edit().putString("txtid", jObj.getString("id")).commit();
                PreferenceManager.getDefaultSharedPreferences(login.this).edit().putString("oneid", HomeActivity.oneid).commit();
                PreferenceManager.getDefaultSharedPreferences(login.this).edit().putBoolean("firstlogin",false).commit();
                Intent intent = new Intent(this, userhome.class);
                startActivity(intent);
                this.finish();
            }
            else
            {
                Toast.makeText(this, "Login Failed!!!",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }


}



