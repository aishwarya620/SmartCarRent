package com.example.cc14.smartcarrent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cc14.smartcarrent.Model.AddVehiclePojo;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Addvehicle extends AppCompatActivity {
    private static final String TAG = "Addvehicle";
    private ScrollView mScrollLayout;
    private RelativeLayout mBaseLayout;
    private TextView mPageHeadaddvehicle;
    private RelativeLayout mSubLayout;
    private EditText mEtVehiclenumber;
    private EditText mEtVehiclename;
    private EditText mEtVehiclecolor;
    private EditText mEtVehiclecompany;
    private RadioGroup mRadioType;
    private RadioButton mRadioAC;
    private RadioButton mRadioNonAC;
    private EditText mEtGSMnumber;
    private EditText mEtCurrentlocation;
    private EditText mEtVehiclephoto;
    private EditText mEtCharges;
    private EditText mEtNumberofseats;
    private EditText mEtSpeedlimit;
    private Button mBtnADD;
    private Button mBtnReset;
    private CircleImageView circleImageView;

    String vehicleType;
    private ProgressDialog progressDialog;
    Preferences preferences;

    String imagePath = "", postPath = "";


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvehicle);
        bindViews();

    }
    private void bindViews() {

        progressDialog = new ProgressDialog(Addvehicle.this);
        preferences = new Preferences(Addvehicle.this);

        mScrollLayout = (ScrollView) findViewById(R.id.scrollLayout);
        mBaseLayout = (RelativeLayout) findViewById(R.id.baseLayout);
        mPageHeadaddvehicle = (TextView) findViewById(R.id.pageHeadaddvehicle);
        mSubLayout = (RelativeLayout) findViewById(R.id.subLayout);
        mEtVehiclenumber = (EditText) findViewById(R.id.etVehiclenumber);
      //  mEtVehiclename = (EditText) findViewById(R.id.etVehiclename);
        mEtVehiclecolor = (EditText) findViewById(R.id.etVehiclecolor);
        mEtVehiclecompany = (EditText) findViewById(R.id.etVehiclecompany);
        mRadioType = (RadioGroup) findViewById(R.id.radioType);
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

                    vehicleType = String.valueOf(checkedRadioButton.getText());
                }
            }
        });

      //  mRadioAC = (RadioButton) findViewById(R.id.radioAC);
      //  mRadioNonAC = (RadioButton) findViewById(R.id.radioNonAC);
        mEtGSMnumber = (EditText) findViewById(R.id.etGSMnumber);
        mEtCurrentlocation = (EditText) findViewById(R.id.etCurrentlocation);
        mEtVehiclephoto = (EditText) findViewById(R.id.etVehiclephoto);
        mEtCharges = (EditText) findViewById(R.id.etCharges);
        mEtNumberofseats = (EditText) findViewById(R.id.etNumberofseats);
        mEtSpeedlimit = (EditText) findViewById(R.id.etSpeedlimit);
        mBtnADD = (Button) findViewById(R.id.btnADD);
        mBtnReset = (Button) findViewById(R.id.btnReset);
        circleImageView = (CircleImageView) findViewById(R.id.vehicle_image);


        mBtnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Flag = 0;
                if (TextUtils.isEmpty(mEtVehiclenumber.getText().toString())) {
                    mEtVehiclenumber.requestFocus();
                    mEtVehiclenumber.setError("Please enter vehicle number");
                    Flag = 1;
                } if (TextUtils.isEmpty(mEtVehiclecolor.getText().toString())) {
                    mEtVehiclecolor.requestFocus();
                    mEtVehiclecolor.setError("Please enter vehicle color");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtVehiclecompany.getText().toString())) {
                    mEtVehiclecompany.requestFocus();
                    mEtVehiclecompany.setError("Please enter vehicle company");
                    Flag = 1;
                }/*else if (password.getText().toString().trim().length() < 8) {
                    password.requestFocus();
                    password.setError("Please enter 8 digit password");
                    Flag = 1;
                }*/
                if (TextUtils.isEmpty(mEtGSMnumber.getText().toString())) {
                    mEtGSMnumber.requestFocus();
                    mEtGSMnumber.setError("Please enter gst number");
                    Flag = 1;
                }/*else if (conformpassword.getText().toString().trim().length() < 8) {
                    conformpassword.requestFocus();
                    conformpassword.setError("Please enter 8 conform password");
                    Flag = 1;
                }*/
                if (TextUtils.isEmpty(mEtCurrentlocation.getText().toString())) {
                    mEtCurrentlocation.requestFocus();
                    mEtCurrentlocation.setError("Please enter current location");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtCharges.getText().toString())) {
                    mEtCharges.requestFocus();
                    mEtCharges.setError("Please enter charges");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtNumberofseats.getText().toString())) {
                    mEtNumberofseats.requestFocus();
                    mEtNumberofseats.setError("Please enter number of seats");
                    Flag = 1;
                }
                if (TextUtils.isEmpty(mEtSpeedlimit.getText().toString())) {
                    mEtSpeedlimit.requestFocus();
                    mEtSpeedlimit.setError("Please enter speed limit");
                    Flag = 1;
                }


                if (Flag == 0) {
                    addVehicle();
                }
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(true)
                        .setAllowFlipping(false)
                        .setAllowRotation(true)
                        .start(Addvehicle.this);
            }
        });

    }

    public void addVehicle(){
        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String vehicleNumber = mEtVehiclenumber.getText().toString();
        String vehicleColor = mEtVehiclecolor.getText().toString();
        String vehicleCompany = mEtVehiclecompany.getText().toString();
        String gsmNumber = mEtGSMnumber.getText().toString();
        String currentLocation = mEtCurrentlocation.getText().toString();
        String charges = mEtCharges.getText().toString();
        String noOFSeats = mEtNumberofseats.getText().toString();
        String speedLimit = mEtSpeedlimit.getText().toString();

        Retrofit retrofit = null;

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MultipartBody.Part body = null, body1 = null;
        if(!postPath.equalsIgnoreCase("")) {
            File file = new File(postPath);

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("vehicleimage", file.getName(), requestBody);
        }



        RequestBody VehicleNumber = RequestBody.create(MediaType.parse("text/plain"), vehicleNumber);
        RequestBody VehicleColor = RequestBody.create(MediaType.parse("text/plain"), vehicleColor);
        RequestBody VehicleCompany = RequestBody.create(MediaType.parse("text/plain"), vehicleCompany);
        RequestBody VehicleType = RequestBody.create(MediaType.parse("text/plain"), vehicleType);
        RequestBody GsmNumber = RequestBody.create(MediaType.parse("text/plain"),gsmNumber);
        RequestBody CurrentLocation = RequestBody.create(MediaType.parse("text/plain"), currentLocation);
        RequestBody Charges = RequestBody.create(MediaType.parse("text/plain"),charges);
        RequestBody NoOFSeats = RequestBody.create(MediaType.parse("text/plain"),noOFSeats);
        RequestBody SpeedLimit = RequestBody.create(MediaType.parse("text/plain"),speedLimit);
        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"),preferences.getUserID());


        ApiInterface apiInterface;
        apiInterface = retrofit.create(ApiInterface.class);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("vehiclenumber",VehicleNumber);
        params.put("vehiclecolor",VehicleColor);
        params.put("vehiclecompany",VehicleCompany);
        params.put("vehicletype",VehicleType);
        params.put("gsmnumber",GsmNumber);
        params.put("currentlocation",CurrentLocation);
        params.put("charges",Charges);
        params.put("noofseats",NoOFSeats);
        params.put("speedlimit",SpeedLimit);
        params.put("ownerid",UserId);

        Call<AddVehiclePojo> call=apiInterface.addvehicle(params,body);
        call.enqueue(new Callback<AddVehiclePojo>() {
            @Override
            public void onResponse(Call<AddVehiclePojo> call, retrofit2.Response<AddVehiclePojo> response) {

                progressDialog.dismiss();
                if (response.body().getSuccess() == 1) {

                    Toast.makeText(Addvehicle.this, "Vehicle added Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Addvehicle.this,OwnerDashboard.class);
                    startActivity(intent);
                    finish();

                } else if (response.body().getSuccess() == 0) {

                    Toast.makeText(Addvehicle.this, "Unsucessfull....Try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AddVehiclePojo> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(Addvehicle.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imagePath = getRealPathFromURI(resultUri);
                    Glide.with(Addvehicle.this)
                            .load(imagePath)
                            .into(circleImageView);

                    postPath = imagePath;

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "sorry, there was an error", Toast.LENGTH_LONG).show();
        }


    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
