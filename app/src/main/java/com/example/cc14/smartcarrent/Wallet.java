package com.example.cc14.smartcarrent;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Wallet extends AppCompatActivity {
    private TextView txtTotalAmt;
    private Button btnUpdateBalance;

    String totalAmount;
    int b = 0;
    Preferences preferences;
    ProgressDialog progressDialog;

    private static final String BALANCE_URL = ApiClient.BASE_URL+"/updatebalancenew.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        preferences = new Preferences(Wallet.this);
        progressDialog = new ProgressDialog(Wallet.this);

        txtTotalAmt = findViewById(R.id.txtTotalAmt);
        btnUpdateBalance = findViewById(R.id.btnUpdateBalance);

        if (preferences.getBillAmount().equals("")) {
            Toast.makeText(Wallet.this,"No Car Booked", Toast.LENGTH_LONG).show();
            txtTotalAmt.setText("Remaining Amount : "+preferences.getWalletAmt());

        }else {
            int a = Integer.parseInt(preferences.getBillAmount());
            b = Integer.parseInt(preferences.getWalletAmt());

            int ab = b - a;

            totalAmount = String.valueOf(ab);

            txtTotalAmt.setText("Remaining Amount : "+totalAmount);
        }


        btnUpdateBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 updateBalance();
            }
        });



    }

    public void updateBalance(){

        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();


        if (isConnectingToInternet(getApplicationContext())) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, BALANCE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                int success = jsonResponse.getInt("success");
                                progressDialog.dismiss();
                                if (success == 0) {
                                    Toast.makeText(Wallet.this, "balance Update failed. Please try again.", Toast.LENGTH_LONG).show();
                                } else if (success == 1) {

                                    Toast.makeText(Wallet.this, "You have successfully updated balance", Toast.LENGTH_LONG).show();

                                        /*btnSoldOut.setEnabled(false);
                                        btnSoldOut.setBackgroundColor(getResources().getColor(R.color.colorgray));
                                        spinner.setVisibility(View.GONE);
                                        btnSave.setVisibility(View.GONE);*/



                                    // Intent intent = new Intent(FarmarDetail.this, Login.class);
                                    //  startActivity(intent);
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
                            Toast.makeText(Wallet.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("userid", preferences.getUserID());
                    params.put("balance", totalAmount);


                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(Wallet.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        }else {
            progressDialog.dismiss();
            Toast.makeText(Wallet.this, "Internet Connection is not available ", Toast.LENGTH_LONG).show();
        }

    }

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
}
