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
import android.widget.TextView;
import android.widget.Toast;

public class UserDashboard extends AppCompatActivity {

    private RelativeLayout mBaseLayout;
    private ImageView mImgHead;
    private TextView mTxtHead;
    private Button mBtnBookCar;
    private Button mBtnWallet,mBtnBilling;

    private Preferences preferences;

    // End Of Content View Elements
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        bindViews();
    }
    private void bindViews() {

        preferences = new Preferences(this);

        mBaseLayout = (RelativeLayout) findViewById(R.id.baseLayout);
        mImgHead = (ImageView) findViewById(R.id.imgHead);
        mTxtHead = (TextView) findViewById(R.id.txtHead);
        mBtnBookCar = (Button) findViewById(R.id.btnBookCar);
        mBtnWallet = (Button) findViewById(R.id.btnWallet);
        mBtnBilling = findViewById(R.id.btnBilling);

        mBtnBookCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserDashboard.this, "Book Car", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserDashboard.this,VehicleList.class));
            }
        });

        mBtnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this,Wallet.class));
            }
        });

        mBtnBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this,Billing.class));
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
            startActivity(new Intent(UserDashboard.this, LoginActivity.class));
            finish();
        }

        return true;
    }

}
