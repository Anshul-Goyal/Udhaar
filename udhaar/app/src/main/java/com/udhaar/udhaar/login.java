package com.udhaar.udhaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class login extends AppCompatActivity implements AsyncResponse {

    EditText etmobile;
    Button btnLogin;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    String txtid="";
    private static final int mode=0;

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
                postData.put("txtmobile", etmobile.getText().toString());

                PostResponseAsyncTask loginTask =
                        new PostResponseAsyncTask(login.this, postData);
                System.out.println("Before Logging in");
                loginTask.execute("http://172.30.127.159:8088/udhaar-db/registration.php");
                System.out.println("After Logging in....");
            }
        });


    }

    @Override
    public void processFinish(String output) {
        System.out.println("===================  "+output.toString()+"    =============");
        if(output.equals("-1")){
            Toast.makeText(this, "Login Failed!!!",
                    Toast.LENGTH_LONG).show();
        }
        else{
                Toast.makeText(this, "Account created Successfully",
                        Toast.LENGTH_LONG).show();

            preferenceSettings = getPreferences(mode);
            preferenceEditor = preferenceSettings.edit();
            String isid = preferenceSettings.getString("txtid",txtid);

                preferenceEditor = preferenceSettings.edit();
                preferenceEditor.putString("txtid", output);
                preferenceEditor.commit();

            Intent intent = new Intent(this, userhome.class);
            startActivity(intent);

        }
    }

}

