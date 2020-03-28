package com.example.cc14.smartcarrent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookCar extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private ScrollView mScrollLayout;
    private RelativeLayout mBaseLayout;
    private TextView mPageBookCar;
    private RelativeLayout mSubLayout;
    private EditText mEtSource;
    private EditText mEtDestination;
    private TextView mRadiohead;
    private RadioGroup mRadioType;
    private RadioButton mRadioAC;
    private RadioButton mRadioNonAC;
    private TextView mSpinnerHead;
    private Spinner mSpinner;
    private EditText mEtNumberofseats;
    private LinearLayout mSubLayout1;
    private Button mBtnBookcar;
    private Button mBtnReset;
    private EditText mEtKm;
    private TextView txtFinalAmt;

    private ProgressDialog progressDialog;
    private Preferences preferences;

    private int PLACE_PICKER_SOURCE = 1;
    private int PLACE_PICKER_DESTINATION = 2;
    private GoogleApiClient mGoogleApiClient;



    String userid, vehicleid, source, destination,km,currentlocation,charges,actype,noofseats,finalamount,ownerid;
    Integer tempKm;

    private static final String BOOKING_URL = ApiClient.BASE_URL+"booking.php";

    Button btnSource, btnDestination;

    String latitudeSource,longitudeSource,latitudeDestination,longitudeDestination,addressSource,addressDestination;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_car);

        progressDialog = new ProgressDialog(BookCar.this);
        preferences = new Preferences(BookCar.this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        Intent intent = getIntent();
        vehicleid = intent.getStringExtra("vehicleid");
        currentlocation = intent.getStringExtra("currentlocation");
        charges = intent.getStringExtra("charges");
        ownerid = intent.getStringExtra("ownerid");


        mEtKm = findViewById(R.id.etKm);
        txtFinalAmt = findViewById(R.id.txtFinalAmount);
        mEtSource = (EditText) findViewById(R.id.etSource);
        mEtDestination = (EditText) findViewById(R.id.etDestination);
        mRadioType = (RadioGroup) findViewById(R.id.radioType);
        /*mRadioAC = (RadioButton) findViewById(R.id.radioAC);
        mRadioNonAC = (RadioButton) findViewById(R.id.radioNonAC);*/
        mEtNumberofseats = (EditText) findViewById(R.id.etNumberofseats);
        mBtnBookcar = (Button) findViewById(R.id.btnBookcar);
        mBtnReset = (Button) findViewById(R.id.btnReset);
        btnSource = findViewById(R.id.btnSource);
        btnDestination = findViewById(R.id.btnDestination);

        btnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(BookCar.this), PLACE_PICKER_SOURCE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(BookCar.this), PLACE_PICKER_DESTINATION);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });



        RadioButton radioButton = (RadioButton)mRadioType.findViewById(mRadioType.getCheckedRadioButtonId());

        mRadioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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

                    actype = String.valueOf(checkedRadioButton.getText());
                }
            }
        });

        mEtKm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtKm.getText() != null && !mEtKm.getText().toString().isEmpty()) {
                    try {
                        tempKm = Integer.parseInt(mEtKm.getText().toString());
                    } catch (NumberFormatException ex) {
                        tempKm = 0; // or some other default value
                    }
                }
                int chargess = Integer.parseInt(charges);

                int finalAmt = chargess*tempKm;

                finalamount = String.valueOf(finalAmt);

                txtFinalAmt.setText("Final Amount : "+finalamount);

                preferences.setBillAmount(finalamount);

            }
        });


        mBtnBookcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int Flag = 0;
                if (TextUtils.isEmpty(mEtSource.getText().toString())) {
                    mEtSource.requestFocus();
                    mEtSource.setError("Please enter source");
                    Flag = 1;
                } if (TextUtils.isEmpty(mEtDestination.getText().toString())) {
                    mEtDestination.requestFocus();
                    mEtDestination.setError("Please enter destination");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtKm.getText().toString())) {
                    mEtKm.requestFocus();
                    mEtKm.setError("Please enter km");
                    Flag = 1;
                }/*else if (password.getText().toString().trim().length() < 8) {
                    password.requestFocus();
                    password.setError("Please enter 8 digit password");
                    Flag = 1;
                }*/
                if (TextUtils.isEmpty(mEtNumberofseats.getText().toString())) {
                    mEtNumberofseats.requestFocus();
                    mEtNumberofseats.setError("Please enter noofseats");
                    Flag = 1;
                }

                if (Flag == 0){
                    booking();
                }


            }

        });

      //  bindViews();



       /* String[] countries = getResources().getStringArray(R.array.array_countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_book_car, R.id.text, countries);


        spinner.setAdapter(adapter);*/

    }

    public void booking(){

        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        source = mEtSource.getText().toString().trim();
        destination = mEtDestination.getText().toString().trim();
        km = mEtKm.getText().toString().trim();
        noofseats = mEtNumberofseats.getText().toString().trim();



        if (isConnectingToInternet(getApplicationContext())) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, BOOKING_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                try {

                                    JSONObject jsonResponse = new JSONObject(response);
                                    int success = jsonResponse.getInt("success");
                                    progressDialog.dismiss();
                                    if (success == 0) {
                                        Toast.makeText(BookCar.this, "booking failed. Please try again.", Toast.LENGTH_LONG).show();
                                    } else if (success == 1) {
                                        Toast.makeText(BookCar.this, "You have successfully booked car", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(BookCar.this, UserDashboard.class);
                                        startActivity(intent);
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
                                Toast.makeText(BookCar.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("userid", preferences.getUserID());
                        params.put("username", preferences.getUserName());
                        params.put("ownerid", ownerid);
                        params.put("vehicleid",vehicleid);
                        params.put("source", source);
                        params.put("destination",destination);
                        params.put("km", km);
                        params.put("currentlocation", currentlocation);
                        params.put("actype", actype);
                        params.put("finalamount", finalamount);


                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(BookCar.this);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

        }else {
            progressDialog.dismiss();
            Toast.makeText(BookCar.this, "Internet Connection is not available ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_SOURCE) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
              //  City = String.format("%s", place.getName());
                latitudeSource = String.valueOf(place.getLatLng().latitude);
                longitudeSource = String.valueOf(place.getLatLng().longitude);
                addressSource = String.format("%s", place.getAddress());

               /* stBuilder.append("Name: ");
                stBuilder.append(City);
                stBuilder.append("\n");*/
                stBuilder.append("Latitude: ");
                stBuilder.append(latitudeSource);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitudeSource);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(addressSource);
                //  mTvPlaceDetails.setText(stBuilder.toString());

               // city.setText(City);
                mEtSource.setText(addressSource);
                Toast.makeText(this, stBuilder.toString(), Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode == PLACE_PICKER_DESTINATION) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
              //  City = String.format("%s", place.getName());
                latitudeDestination = String.valueOf(place.getLatLng().latitude);
                longitudeDestination = String.valueOf(place.getLatLng().longitude);
                addressDestination = String.format("%s", place.getAddress());

               /* stBuilder.append("Name: ");
                stBuilder.append(City);
                stBuilder.append("\n");*/
                stBuilder.append("Latitude: ");
                stBuilder.append(latitudeDestination);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitudeDestination);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(addressDestination);
                //  mTvPlaceDetails.setText(stBuilder.toString());

               // city.setText(City);
                mEtDestination.setText(addressDestination);
                Toast.makeText(this, stBuilder.toString(), Toast.LENGTH_LONG).show();

                double slat = Double.parseDouble(latitudeSource);
                double slog = Double.parseDouble(longitudeSource);
                double dlat = Double.parseDouble(latitudeDestination);
                double dlog = Double.parseDouble(longitudeDestination);

                double dist = CalculationByDistance(slat,slog,dlat,dlog);

                mEtKm.setText(String.format("%.0f", dist));

            }
        }
    }

    public double CalculationByDistance(double initialLat, double initialLong,
                                        double finalLat, double finalLong){

        int R = 6371; // km
        double dLat = toRadians(finalLat-initialLat);
        double dLon = toRadians(finalLong-initialLong);
        double lat1 = toRadians(dLat);
        double lat2 = toRadians(dLon);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private double toRadians(double d){
        return d*(Math.PI/180);
    }

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

    private void bindViews() {

        mEtSource = (EditText) findViewById(R.id.etSource);
        mEtDestination = (EditText) findViewById(R.id.etDestination);
        mRadioType = (RadioGroup) findViewById(R.id.radioType);
        mRadioAC = (RadioButton) findViewById(R.id.radioAC);
        mRadioNonAC = (RadioButton) findViewById(R.id.radioNonAC);
        mEtNumberofseats = (EditText) findViewById(R.id.etNumberofseats);
        mBtnBookcar = (Button) findViewById(R.id.btnBookcar);
        mBtnReset = (Button) findViewById(R.id.btnReset);
        mBtnBookcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Toast.makeText(BookCar.this, "Book Car", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BookCar.this, BookCar.class));

            }
        });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
