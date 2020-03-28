package com.example.cc14.smartcarrent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class IndexActivity extends Activity implements View.OnClickListener {
    private ImageView mImgHead;
    private Button mBtnSignUp;
    private Button mBtnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        bindViews();
    }
    private void bindViews() {

        mImgHead = (ImageView) findViewById(R.id.imgHead);
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);
        mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        mBtnSignUp.setOnClickListener(this);
        mBtnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSignUp)
        {
            startActivity(new Intent(IndexActivity.this,RegisterActivity.class));

        }
        if(v.getId() == R.id.btnSignIn)
        {
            startActivity(new Intent(IndexActivity.this,LoginActivity.class));
            finish();
        }

    }
}
