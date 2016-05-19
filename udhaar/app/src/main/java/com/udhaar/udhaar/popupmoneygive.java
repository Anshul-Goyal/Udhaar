package com.udhaar.udhaar;

import android.content.Context;
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

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class popupmoneygive extends AppCompatActivity implements AsyncResponse{
    String cnum;
    Button btngive;
    Bundle extras;
    String money;
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
            extras = getIntent().getExtras();
            if(extras == null) {
                cnum = null;
            } else {
                cnum = extras.getString("cnum");
            }
        } else {
            cnum = (String) savedInstanceState.getSerializable("cnum");
        }

        btngive = (Button)findViewById(R.id.button22);

        btngive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = PreferenceManager.getDefaultSharedPreferences(popupmoneygive.this).getString("txtid", "NULL");
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtid1", id);
                postData.put("txtmob", cnum);
                EditText editText = (EditText) findViewById(R.id.editText4);
                money = editText.getText().toString();
                postData.put("txtmoney", money);
                PostResponseAsyncTask Task =
                        new PostResponseAsyncTask(popupmoneygive.this, postData);
                System.out.println("Before Logging in");
                Task.execute("http://"+login.ip+"/popupmoneygive.php");
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

                com.udhaar.udhaar.Contacts contact = new com.udhaar.udhaar.Contacts(extras.getInt("id"),extras.getString("name"),extras.getString("cnum"),Integer.parseInt(jObj.getString("money")),jObj.getString("tym"),extras.getString("oneid"));
               //PROBLEM HERE - EXTRAS.GETINT(MONEY) is not getting updated .. perhaps got to create a new intent
                DatabaseHandler ob = new DatabaseHandler(this);
                ob.updateContact(contact);

                DatabaseHandler db = new DatabaseHandler(this);
                Log.d("Reading: ", "Reading all contacts..");
                List<Contacts> contacts = db.getAllContacts();

                for (com.udhaar.udhaar.Contacts cn : contacts) {
                    String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber()+" ,Money: "+cn.getMoney()+" ,tym: "+cn.getTime()+" ,oneid: "+cn.getoneid();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }

//                try {
//                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'Test Message'}, 'include_player_ids': ['" + jObj.getString("oneid") + "']}"), null);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                try {
                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'@Name@ has taken Rs."+money+"from you.'Final Balance is: "+jObj.getString("money")+" }, 'include_player_ids': ['" + HomeActivity.oneid +extras.getString("oneid") + "']}"), null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Toast.makeText(this, "Money Added Successfully",
                        Toast.LENGTH_LONG).show();
                userhome.uhobj.finish();
                Intent intent = new Intent(this, userhome.class);
                userprofile.upobj.finish();

                userhome.var=123;
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
