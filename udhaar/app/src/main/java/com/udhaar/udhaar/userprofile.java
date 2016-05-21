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
import java.util.HashMap;
import java.util.ArrayList;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import java.util.List;
import android.content.pm.ResolveInfo;
import java.util.Collections;
import android.content.pm.ActivityInfo;
import android.content.ComponentName;
import android.widget.Toast;
import android.content.Context;
import android.widget.ImageView;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.graphics.PorterDuff;

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
            t2.setTextColor(Color.rgb(34,177,76));
        }

        //get the image view
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        //set the ontouch listener
        imageView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        //overlay is black with transparency of 0x77 (119)
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        System.out.println("helloinsideopencalc");
                        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                        //PackageManager pm;
                        final PackageManager pm = getPackageManager();
                        List<PackageInfo> packs = pm.getInstalledPackages(0);
                        for (PackageInfo pi : packs) {
                            if (pi.packageName.toString().toLowerCase().contains("calcul")) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("appName", pi.applicationInfo.loadLabel(pm));
                                map.put("packageName", pi.packageName);
                                items.add(map);
                            }
                        }

                        if (items.size() >= 1) {

                            String packageName = (String) items.get(0).get("packageName");
                            Intent i = pm.getLaunchIntentForPackage(packageName);
                            if (i != null)
                                startActivity(i);
                            else {
                                Intent intent = new Intent();
                                intent.setPackage(packageName);

                                PackageManager p = getPackageManager();
                                List<ResolveInfo> resolveInfos = p.queryIntentActivities(intent, 0);
                                Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(p));

                                if (resolveInfos.size() > 0) {
                                    ResolveInfo launchable = resolveInfos.get(0);
                                    ActivityInfo activity = launchable.activityInfo;
                                    ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                                            activity.name);
                                    Intent it = new Intent(Intent.ACTION_MAIN);

                                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                    it.setComponent(name);

                                    startActivity(it);
                                } else {
                                    Context context = getApplicationContext();
                                    CharSequence text = "Calculator cannot be opened. ";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            }

                            //  System.out.println("found "+packageName);

                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "Calculator cannot be opened. ";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        //clear the overlay
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }

                return true;
            }
        });

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
