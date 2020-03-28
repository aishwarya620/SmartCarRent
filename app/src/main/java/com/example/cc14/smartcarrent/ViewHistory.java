package com.example.cc14.smartcarrent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cc14.smartcarrent.Model.GetCarBookingPojo;
import com.example.cc14.smartcarrent.Model.GetVehiclePojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewHistory extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    ArrayList<GetCarBookingPojo.Carbookinglist> carbookinglists;
    BookingAdapter bookingAdapter;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        progressDialog = new ProgressDialog(ViewHistory.this);
        preferences = new Preferences(ViewHistory.this);
        carbookinglists = new ArrayList<GetCarBookingPojo.Carbookinglist>();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager vehicleLayoutManager = new LinearLayoutManager(ViewHistory.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(vehicleLayoutManager);

        getCarBooking();
    }

    public void getCarBooking() {
        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = null;

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiInterface apiInterface;
        apiInterface = retrofit.create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
         params.put("ownerid", preferences.getUserID());

        Call<GetCarBookingPojo> call = apiInterface.getCar(params);
        call.enqueue(new Callback<GetCarBookingPojo>() {
            @Override
            public void onResponse(Call<GetCarBookingPojo> call, retrofit2.Response<GetCarBookingPojo> response) {

                progressDialog.dismiss();


                if (response.body().getSuccess() == 1) {

                    if (carbookinglists.size() > 0) {
                        carbookinglists.clear();
                        recyclerView.removeAllViewsInLayout();
                    }

                    /*ArrayList<GetCoursePojo.Courselist>*/
                    carbookinglists = new ArrayList<>();


                    carbookinglists.addAll(response.body().getCarbookinglist());

                    // Initialize a new instance of RecyclerView Adapter instance
                    /*RecyclerView.Adapter*/
                    bookingAdapter = new BookingAdapter(ViewHistory.this, carbookinglists);

                    // Set the adapter for RecyclerView
                    recyclerView.setAdapter(bookingAdapter);

                    //   clear();
                    bookingAdapter.notifyDataSetChanged();


                } else if (response.body().getSuccess() == 0) {

                    carbookinglists.clear();
                    recyclerView.removeAllViewsInLayout();

                    Toast.makeText(ViewHistory.this, "No History found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<GetCarBookingPojo> call, Throwable t) {

                progressDialog.dismiss();

                Log.e(VehicleList.class.getSimpleName(), t.toString());
                Toast.makeText(ViewHistory.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
        private static final String FLAG_URL = ApiClient.BASE_URL+"updateflag.php";
        private ArrayList<GetCarBookingPojo.Carbookinglist> carbookinglists;
        private Context context;
        ProgressDialog progressDialog;
        String str = "";

        public BookingAdapter(Context context, ArrayList<GetCarBookingPojo.Carbookinglist> carbookinglists) {
            this.carbookinglists = carbookinglists;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_car_booking, null);
            ViewHolder vh = new ViewHolder(view);

            progressDialog = new ProgressDialog(context);

            return vh;

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            holder.txtUserName.setText("Username : "+carbookinglists.get(position).getUsername());
            holder.txtKm.setText("Kilometer : "+carbookinglists.get(position).getKm());
            holder.txtSource.setText("Source : "+carbookinglists.get(position).getSource().trim());
            holder.txtDestination.setText("Destination : "+carbookinglists.get(position).getDestination().trim());
            holder.txtVehicleType.setText("Ac Type : "+carbookinglists.get(position).getActype());
            holder.txtFinalAmount.setText("Final Amount : "+carbookinglists.get(position).getFinalamount());

            holder.txtAcTime.setVisibility(View.GONE);

            holder.btnGenerateBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateFlag(position);
                }
            });


           /* holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VehicleList.this, BookCar.class);

                    intent.putExtra("vehicleid", vehiclelists.get(position).getVehicleid());
                    intent.putExtra("currentlocation", vehiclelists.get(position).getCurrentlocation());
                    intent.putExtra("charges", vehiclelists.get(position).getCharges());
                    intent.putExtra("ownerid", vehiclelists.get(position).getOwnerid());
                    *//*intent.putExtra("coursename", courseName);
                    intent.putExtra("classid", classId);
                    intent.putExtra("classname", className);
                    intent.putExtra("subjectid", subjectId);
                    intent.putExtra("subjectname", subjectName);
                    intent.putExtra("chapterid", str);*//*

                    startActivity(intent);
                    //  finish();
                }
            });*/


            //   preferenceLecturer.setClassId(str);


        }

        @Override
        public int getItemCount() {
            return carbookinglists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtUserName, txtKm, txtSource, txtDestination, txtVehicleType, txtFinalAmount,txtAcTime;
            public CardView cardView;
            public Button btnGenerateBill;

            public ViewHolder(View itemView) {
                super(itemView);
                txtUserName = itemView.findViewById(R.id.txtUserName);
                txtKm = itemView.findViewById(R.id.txtKm);
                txtSource = itemView.findViewById(R.id.txtSource);
                txtDestination = itemView.findViewById(R.id.txtDestination);
                txtVehicleType = itemView.findViewById(R.id.txtVehicleType);
                txtFinalAmount = itemView.findViewById(R.id.txtFinalAmount);
                txtAcTime = itemView.findViewById(R.id.txtAcTime);
                btnGenerateBill = itemView.findViewById(R.id.btnGenerateBill);
            }
        }

        public void updateFlag(final int position){
            progressDialog.setMessage("Please wait....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(100);
            progressDialog.show();

            if (isConnectingToInternet(getApplicationContext())) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, FLAG_URL,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    int success = jsonResponse.getInt("success");
                                    progressDialog.dismiss();
                                    if (success == 1) {

                                        Toast.makeText(context,"Bill Generated Sucessfully", Toast.LENGTH_LONG).show();

                                    } else if (success == 0) {
                                        Toast.makeText(context,"Bill not generated.", Toast.LENGTH_LONG).show();

                                    }
                                }catch (JSONException e) {
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", carbookinglists.get(position).getId());
                        params.put("userid", carbookinglists.get(position).getUserid());
                        params.put("flag", "1");


                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

            }else {
                progressDialog.dismiss();
                Toast.makeText(context, "Internet Connection is not available ", Toast.LENGTH_LONG).show();
            }

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
