package com.example.cc14.smartcarrent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class OwnerDashboard extends AppCompatActivity {

    private ScrollView mScrollLayout;
    private RelativeLayout mBaseLayout;
    private ImageView mImgHead;
    private TextView mTxtHead;
    private Button mBtnAddVehicle;
    private Button mBtnViewEditInfButton;
    private Button mBtnTrackCar;
    private Button mBtnControl;
    private Button mBtnBillCharges;
    private Button mBtnViewHistory;

    private Preferences preferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);
        bindViews();
    }
    private void bindViews() {

        preferences = new Preferences(this);

        mScrollLayout = (ScrollView) findViewById(R.id.scrollLayout);
        mBaseLayout = (RelativeLayout) findViewById(R.id.baseLayout);
        mImgHead = (ImageView) findViewById(R.id.imgHead);
        mTxtHead = (TextView) findViewById(R.id.txtHead);
        mBtnAddVehicle = (Button) findViewById(R.id.btnAddVehicle);
      //  mBtnViewEditInfButton = (Button) findViewById(R.id.btnViewEditInfo);
        mBtnTrackCar = (Button) findViewById(R.id.btnTrackCar);
        mBtnControl = (Button) findViewById(R.id.btnControl);
     //   mBtnBillCharges = (Button) findViewById(R.id.btnBillCharges);
        mBtnViewHistory = (Button) findViewById(R.id.btnViewHistory);
        mBtnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OwnerDashboard.this, "Add vehicle", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OwnerDashboard.this,Addvehicle.class));
            }
        });

        mBtnTrackCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerDashboard.this, MainActivity.class);
                startActivity(intent);

            }
        });

        mBtnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerDashboard.this, Control.class);
                startActivity(intent);
            }
        });

        mBtnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerDashboard.this, ViewHistory.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  overridePendingTransition(R.xml.animation, R.xml.animation2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if(id == R.id.idlogout )
        {
            preferences.clearPreferences();
            startActivity(new Intent(OwnerDashboard.this, LoginActivity.class));
            finish();
        }

        return true;
    }
}
