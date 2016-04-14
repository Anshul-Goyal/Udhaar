package com.udhaar.udhaar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.ContentResolver;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListFragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;
import android.provider.ContactsContract.PhoneLookup;
import android.widget.Toast;


public class userhome extends ListActivity implements AsyncResponse {

    String namearray[];
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    String txtid="";
    private static final int mode=0;

    private static final int PICK_CONTACT = 1; // request code integer


    ArrayList<HashMap<String, String>> ContactList;
    private static String url_all_products = "http://172.30.127.159:8088/udhaar-db/get_list.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MOB_NO = "mob_no";
    private static final String TAG_PERSON = "person";
    private static final String TAG_COUNT = "count";
    JSONArray persons = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

        preferenceSettings = getPreferences(mode);
        String id = preferenceSettings.getString("txtid",txtid);

        ContactList = new ArrayList<HashMap<String, String>>();
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
//        postData.put("txtid",id); ---------------->>>>>>>>>> Should be uncommented and next line should be commented
        postData.put("txtid","1");

        PostResponseAsyncTask listTask = new PostResponseAsyncTask(userhome.this, postData);
        listTask.execute(url_all_products);


    }


    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = phoneNumber;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }

    public void display() {
        System.out.println("inside display");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,namearray);
        //setlistAdapter(adapter);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
//        // Binding Array to ListAdapter
//        // refer the ArrayAdapter Document in developer.android.com
        ListView lv = getListView();
//
//        // listening to single list item on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String num = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), userprofile.class);
                // sending data to new activity
                i.putExtra("name", num);
                startActivity(i);
//
//
            }
        });

    }





    public void processFinish(String output)
    {
        System.out.println(output);
        JSONObject jObj= new JSONObject();
        try {
            jObj = new JSONObject(output.toString());
        }
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        try{
            if(jObj.getString("success").equals("1"))          // Fetching the Person List
            {
                try {
                    JSONArray person= jObj.getJSONArray("person");
                    int count = jObj.getInt(TAG_COUNT);
                    System.out.println(count);
                    namearray=new String[person.length()];
                    if(person != null) {
                        String[] mob_nos = new String[person.length()];
                        System.out.println(person.toString());
                        for(int i = 0 ; i < person.length() ; i++) {
//                    mob_nos[i] = person.getString("mob_no");
                            JSONObject object = person.getJSONObject(i);
                            System.out.println(object.toString());
                            System.out.println(object.getString("mob_no")); // contact number to be used to get name from contacts
                            String name=getContactName(this,object.getString("mob_no"));
                            System.out.println("Name is : " + name);
                            namearray[i]=name;
                        }
                    }

                    display();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else if(jObj.getString("success").equals("2"))         // Adding A NeW Person
            {
                Toast.makeText(this, "User Has Been Successfully Added",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, userhome.class);
                startActivity(intent);

            }
            else
            {
                Toast.makeText(this, "Operation Failed!!!",
                        Toast.LENGTH_LONG).show();
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

    }





    public void get_contacts (View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
        System.out.println("Now you will get ...");
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        String Contact_Number="";
        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        // TODO Whatever you want to do with the selected contact name.
                        System.out.println(name);
//                        add_to_list(name); ----->>>>> define and call this method to add the contact to the list :) :)
                        String name2 = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        // TODO Whatever you want to do with the selected contact name.
                        System.out.println(name);

                        ContentResolver cr = getContentResolver();
                        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                                "DISPLAY_NAME = '" + name + "'", null, null);
                        if (cursor.moveToFirst()) {
                            String contactId =
                                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            //
                            //  Get all phone numbers.
                            //
                            Cursor phones = cr.query(Phone.CONTENT_URI, null,
                                    Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (phones.moveToNext()) {
                                String number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
                                Contact_Number = "9760807747";
                                int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
                                switch (type) {
                                    case Phone.TYPE_HOME:
                                        // do something with the Home number here...
                                        System.out.println("TYPE_HOME " + number);
                                        break;
                                    case Phone.TYPE_MOBILE:
                                        // do something with the Mobile number here...
                                        System.out.println("TYPE_MOBILE " + number);
                                        break;
                                    case Phone.TYPE_WORK:
                                        // do something with the Work number here...
                                        System.out.println("TYPE_WORK " + number);
                                        break;
                                }
                            }
                            phones.close();
                        }
                        cursor.close();

                    }

                    preferenceSettings = getPreferences(mode);
                    String id="";
                    id = preferenceSettings.getString("txtid",null);
                    System.out.println("Adders id ::::::::::  " + id );
                    HashMap postData2 = new HashMap();
                    postData2.put("mobile", "android");
                    postData2.put("user_mob_no", Contact_Number);
                    postData2.put("txtid","1");

                    PostResponseAsyncTask AddTask =
                            new PostResponseAsyncTask(userhome.this, postData2);
                    System.out.println("Before Adding user...");
                    AddTask.execute("http://172.30.127.159:8088/udhaar-db/add_user.php");
                    System.out.println("After Sending Request...");
                }
                break;
        }
    }

    public String getContact (String num)
    {
        String n="";
        char a[] = num.toCharArray();
        int l = a.length;

        for(int i=l-1;i>0;i++)
        {
            if((a[i]>='0'&&a[i]<='9'))
            {
                n+=a[i];
            }
        }
        return n;
    }


}