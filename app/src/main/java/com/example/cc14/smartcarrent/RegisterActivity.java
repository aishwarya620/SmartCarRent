package com.example.cc14.smartcarrent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class RegisterActivity extends Activity {
    private static final String TAG = "RegisterActivity";
    private ScrollView mScrollLayout;
    private RelativeLayout mBaseLayout;
    private ImageView mImgHead;
    private TextView mTxtHead;
    private TextView mPageHead;
    private RelativeLayout mSubLayout;
    private EditText mEtName;
    private EditText mEtEmail_id;
    private EditText mEtPhone_no;
    private EditText mEt_Password;
    private EditText mEtCpassword;
    private EditText mEtCity;
    private EditText mEtPincode, mEtWalletAmt;
    private RadioGroup mRadioSex;
    private RadioButton mRadioMale;
    private RadioButton mRadioFemale;
    private EditText mEtLicenceNo;
    private RadioGroup mRadioTypeUser;
    private RadioButton mRadioUser;
    private RadioButton mRadioOwner;
    private Button mBtnSignUp;
    private TextView mTxtBacktosignup;
    private Button mBtnSubmit;
    private Button mBtnReset;

    String name, email, mobile, password, city, pincode, licenceno, conformpassword,walletAmt;

    String sex, usertype;

    private ProgressDialog progressDialog;
    private Preferences preferences;

    private static final String REGISTER_URL = ApiClient.BASE_URL+"registration.php";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();
    }
    private void bindViews() {

        mScrollLayout = (ScrollView) findViewById(R.id.scrollLayout);
        mBaseLayout = (RelativeLayout) findViewById(R.id.baseLayout);
        mImgHead = (ImageView) findViewById(R.id.imgHead);
        mTxtHead = (TextView) findViewById(R.id.txtHead);
        mPageHead = (TextView) findViewById(R.id.pageHead);
        mSubLayout = (RelativeLayout) findViewById(R.id.subLayout);
        mEtName = (EditText) findViewById(R.id.etName);
        mEtEmail_id = (EditText) findViewById(R.id.etEmail_id);
        mEtPhone_no = (EditText) findViewById(R.id.etPhone_no);
        mEt_Password = (EditText) findViewById(R.id.et_Password);
        mEtCpassword = (EditText) findViewById(R.id.etCpassword);
        mEtCity = (EditText) findViewById(R.id.etCity);
        mEtPincode = (EditText) findViewById(R.id.etPincode);
        mEtWalletAmt = (EditText) findViewById(R.id.etWallet);
        mRadioSex = (RadioGroup) findViewById(R.id.radioSex);
       /* mRadioMale = (RadioButton) findViewById(R.id.radioMale);
        mRadioFemale = (RadioButton) findViewById(R.id.radioFemale);*/

        RadioButton checkedRadioButton = (RadioButton)mRadioSex.findViewById(mRadioSex.getCheckedRadioButtonId());


        mEtLicenceNo = (EditText) findViewById(R.id.etLicenceNo);
        mRadioTypeUser = (RadioGroup) findViewById(R.id.radioTypeUser);

        RadioButton radioButton = (RadioButton)mRadioTypeUser.findViewById(mRadioTypeUser.getCheckedRadioButtonId());

        mRadioTypeUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    //  tv.setText("Checked:" + checkedRadioButton.getText());

                    usertype = String.valueOf(checkedRadioButton.getText());
                }
            }
        });

        /*mRadioUser = (RadioButton) findViewById(R.id.radioUser);
        mRadioOwner = (RadioButton) findViewById(R.id.radioOwner);*/
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);
        mTxtBacktosignup = (TextView) findViewById(R.id.txtBacktosignup);
        mBtnSubmit = (Button) findViewById(R.id.btnSubmit);
        mBtnReset = (Button) findViewById(R.id.btnReset);

        mTxtBacktosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mRadioSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
                  //  tv.setText("Checked:" + checkedRadioButton.getText());

                    sex = String.valueOf(checkedRadioButton.getText());
                }
            }
        });

        progressDialog = new ProgressDialog(RegisterActivity.this);
        preferences = new Preferences(RegisterActivity.this);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                int Flag = 0;
                if (TextUtils.isEmpty(mEtName.getText().toString())) {
                    mEtName.requestFocus();
                    mEtName.setError("Please enter name");
                    Flag = 1;
                } if (TextUtils.isEmpty(mEtEmail_id.getText().toString())) {
                    mEtEmail_id.requestFocus();
                    mEtEmail_id.setError("Please enter valid email");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtPhone_no.getText().toString())) {
                    mEtPhone_no.requestFocus();
                    mEtPhone_no.setError("Please enter mobile");
                    Flag = 1;
                }/*else if (password.getText().toString().trim().length() < 8) {
                    password.requestFocus();
                    password.setError("Please enter 8 digit password");
                    Flag = 1;
                }*/
                if (TextUtils.isEmpty(mEt_Password.getText().toString())) {
                    mEt_Password.requestFocus();
                    mEt_Password.setError("Please enter password");
                    Flag = 1;
                }/*else if (conformpassword.getText().toString().trim().length() < 8) {
                    conformpassword.requestFocus();
                    conformpassword.setError("Please enter 8 conform password");
                    Flag = 1;
                }*/
                if (TextUtils.isEmpty(mEtCpassword.getText().toString())) {
                    mEtCpassword.requestFocus();
                    mEtCpassword.setError("Please enter conform password");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtCity.getText().toString())) {
                    mEtCity.requestFocus();
                    mEtCity.setError("Please enter city");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtPincode.getText().toString())) {
                    mEtPincode.requestFocus();
                    mEtPincode.setError("Please enter pincode");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtWalletAmt.getText().toString())) {
                    mEtWalletAmt.requestFocus();
                    mEtWalletAmt.setError("Please enter Wallet Amount");
                    Flag = 1;
                }


                if (Flag == 0) {

                    signup();

                    /*Thread timerThread = new Thread(){
                        public void run(){
                            try{
                                sleep(3000);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }finally{
                                signup();
                            }
                        }
                    };
                    timerThread.start();*/

                }

            }

        });
    }

    public void signup(){

        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        name = mEtName.getText().toString().trim();
        email = mEtEmail_id.getText().toString().trim();
        mobile = mEtPhone_no.getText().toString().trim();
        password = mEt_Password.getText().toString().trim();
        conformpassword = mEtCpassword.getText().toString().trim();
        city = mEtCity.getText().toString().trim();
        pincode = mEtPincode.getText().toString().trim();
        licenceno = mEtLicenceNo.getText().toString().trim();
        walletAmt = mEtWalletAmt.getText().toString().trim();

        if (isConnectingToInternet(getApplicationContext())) {
            if (password.equals(conformpassword)) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                try {

                                    JSONObject jsonResponse = new JSONObject(response);
                                    int success = jsonResponse.getInt("success");
                                    progressDialog.dismiss();
                                    if (success == 0) {
                                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                                    } else if (success == 1) {
                                        Toast.makeText(RegisterActivity.this, "You have successfully registered with SmartCarRent", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        //   pref.setFirstTimeLoggedIn("true");

                                    } else if (success == 2) {
                                        Toast.makeText(RegisterActivity.this, "This user is already registered with SmartCarRent", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", name);
                        params.put("email",email);
                        params.put("mobile", mobile);
                        params.put("password",password);
                        params.put("city", city);
                        params.put("pincode", pincode);
                        params.put("walletamt",walletAmt);
                        params.put("licenseno", licenceno);
                        params.put("usertype", usertype);
                        params.put("sex", sex);


                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
            }else {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Passwords are not matching", Toast.LENGTH_LONG).show();
            }
        }else {
            progressDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "Internet Connection is not available ", Toast.LENGTH_LONG).show();
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
