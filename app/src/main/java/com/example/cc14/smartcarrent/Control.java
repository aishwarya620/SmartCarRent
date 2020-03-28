package com.example.cc14.smartcarrent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Control extends AppCompatActivity {

    private Switch acSwitch, ignitionSwitch;
    String number = "8975317384";
    String messageToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        acSwitch = findViewById(R.id.acSwitch);
        ignitionSwitch = findViewById(R.id.ignitionSwitch);

        acSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    number = "8975317384";
                      messageToSend = "*1.";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

                    /*messageToSend = "*5.";
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();*/

                }else {
                      messageToSend = "*0.";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

                   /* messageToSend = "*5.";
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();*/
                }
                //  Log.v("Switch State=", ""+isChecked);
            }

        });

        ignitionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                      messageToSend = "*2.";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

                   /* messageToSend = "*5.";
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();*/
                }else {
                      messageToSend = "*3.";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

/*                    messageToSend = "*5.";
                    smsManager.sendTextMessage(number, null, messageToSend, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();*/
                }
                //  Log.v("Switch State=", ""+isChecked);
            }

        });
    }
}
