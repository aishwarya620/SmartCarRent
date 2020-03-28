package com.example.cc14.smartcarrent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ScrollView mScrollLayout;
    private RelativeLayout mBaseLayout;
    private ImageView mImgHead;
    private TextView mTxtHead;
    private TextView mPageHead;
    private RelativeLayout mSubLayout;
    private EditText edtMobile;
    private EditText mEtPassword;
    private Button mBtnSignIn;
    private TextView mTxtforgotPass;
    private TextView mTxtBacktosignup;

    private ProgressDialog progressDialog;
    Preferences preferences;

    String mobile, password;

    private static final String LOGIN_URL = ApiClient.BASE_URL+"login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
    }
    private void bindViews() {

        mScrollLayout = (ScrollView) findViewById(R.id.scrollLayout);
        mBaseLayout = (RelativeLayout) findViewById(R.id.baseLayout);
        mImgHead = (ImageView) findViewById(R.id.imgHead);
        mTxtHead = (TextView) findViewById(R.id.txtHead);
        mPageHead = (TextView) findViewById(R.id.pageHead);
        mSubLayout = (RelativeLayout) findViewById(R.id.subLayout);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        mEtPassword = (EditText) findViewById(R.id.etPassword);

        mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        mTxtforgotPass = (TextView) findViewById(R.id.txtforgotPass);
        mTxtBacktosignup = (TextView) findViewById(R.id.txtBacktosignup);

        progressDialog = new ProgressDialog(LoginActivity.this);
        preferences = new Preferences(LoginActivity.this);

        mTxtBacktosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Please wait for a moments. Your report get generated soon...", Toast.LENGTH_LONG).show();
                        progressDialog.setMessage("Please wait....");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMax(100);
                        progressDialog.show();
                    }
                });

                 Thread timerThread = new Thread(){
                        public void run(){
                            try{
                                sleep(5000);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }finally{
                                login();
                            }
                        }
                    };
                    timerThread.start();*/

                int Flag = 0;
                if (TextUtils.isEmpty(edtMobile.getText().toString())) {
                    edtMobile.requestFocus();
                    edtMobile.setError("Please enter mobile number");
                    Flag = 1;
                } if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
                    mEtPassword.requestFocus();
                    mEtPassword.setError("Please enter password");
                    Flag = 1;
                }

                if (Flag == 0) {

                    login();




                   /* Thread timerThread = new Thread(){
                        public void run(){
                            try{
                                sleep(3000);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }finally{
                                login();
                            }
                        }
                    };
                    timerThread.start();*/

                }
            }
        });
    }

    public void login(){

        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();


        mobile = edtMobile.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();


        if (isConnectingToInternet(getApplicationContext())) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                try {

                                    JSONObject jsonResponse = new JSONObject(response);
                                    int success = jsonResponse.getInt("success");
                                    progressDialog.dismiss();
                                    if (success == 0) {
                                        Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_LONG).show();
                                    } else if (success == 1) {
                                        Toast.makeText(LoginActivity.this, "You have successfully login SmartCarRent", Toast.LENGTH_LONG).show();

                                        String id = jsonResponse.getString("id");
                                        String usertype = jsonResponse.getString("usertype");
                                        String username = jsonResponse.getString("name");
                                        String walletamt = jsonResponse.getString("walletamt");
                                        preferences.setWalletAmt(walletamt);
                                        preferences.setUserType(usertype);
                                        preferences.setUserName(username);
                                        preferences.setUserID(id);
                                        preferences.set_is_loggedin("1");

                                        if (usertype.equals("User")) {
                                            Intent intent = new Intent(LoginActivity.this, UserDashboard.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Intent intent = new Intent(LoginActivity.this, OwnerDashboard.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        //   pref.setFirstTimeLoggedIn("true");

                                    }
                                }catch (JSONException e) {
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("mobile", mobile);
                        params.put("password",password);



                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
               stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(stringRequest);

        }else {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Internet Connection is not available ", Toast.LENGTH_LONG).show();
        }

    }

    //email validations
    private boolean isValidEmail(String email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }
   /* // mobile validation
    private boolean isValidPhoneNumber(String phoneNumber) {
        String mobile_pattern = "\\+?\\d[\\d -]{8,12}\\d";
        Pattern pattern = Pattern.compile(mobile_pattern);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();

    }*/

    //internet connectivity
    public boolean isConnectingToInternet(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info[] = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++)

                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

            }
        }
        return false;
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // overridePendingTransition(R.xml.animation, R.xml.animation2);
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

        return true;
    }


}
