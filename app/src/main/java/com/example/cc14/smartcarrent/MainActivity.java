package com.example.cc14.smartcarrent;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private static MainActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;

    final int REQUEST_CODE_ASK_PERMISSIONS = 123;


    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsListView = (ListView) findViewById(R.id.SMSList);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);

        smsListView.setOnItemClickListener(this);

     //   if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            refreshSmsInbox();

     //   }
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), new String[]{"_id", "address", "date", "body"}, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        String smsDate = String.valueOf(smsInboxCursor.getColumnIndexOrThrow("date"));


        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";


            if( smsInboxCursor.getString(indexAddress).equals("+918975317384")) {
                arrayAdapter.add(str);
            }
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;


          //  String msgdata[]=smsMessage.split("Latitude:|Longitude:");

            String msgdata[]=smsMessage.split(" |:");

            String Product=msgdata[1];
            String Latitude=msgdata[3];
            String Logitude=msgdata[5];

            Toast.makeText(this, Product+" "+" "+Latitude+" "+Logitude , Toast.LENGTH_SHORT).show();

            Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
            intent.putExtra("Product",Product);
            intent.putExtra("Latitude",Latitude);
            intent.putExtra("Logitude",Logitude);

            startActivity(intent);

            //startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}