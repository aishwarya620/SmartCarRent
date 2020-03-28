package com.example.cc14.smartcarrent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cc14.smartcarrent.Model.GetVehiclePojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VehicleList extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    ArrayList<GetVehiclePojo.Vehiclelist> vehiclelists;
    VehicleAdapter vehicleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        progressDialog = new ProgressDialog(VehicleList.this);
        vehiclelists = new ArrayList<GetVehiclePojo.Vehiclelist>();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager vehicleLayoutManager = new LinearLayoutManager(VehicleList.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(vehicleLayoutManager);

        getVehicle();
    }

    public void getVehicle() {
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
        // params.put("subjectid",subjectId);

        Call<GetVehiclePojo> call = apiInterface.getVehicle(params);
        call.enqueue(new Callback<GetVehiclePojo>() {
            @Override
            public void onResponse(Call<GetVehiclePojo> call, retrofit2.Response<GetVehiclePojo> response) {

                progressDialog.dismiss();


                if (response.body().getSuccess() == 1) {

                    if (vehiclelists.size() > 0) {
                        vehiclelists.clear();
                        recyclerView.removeAllViewsInLayout();
                    }

                    /*ArrayList<GetCoursePojo.Courselist>*/
                    vehiclelists = new ArrayList<>();


                    vehiclelists.addAll(response.body().getVehiclelist());

                    // Initialize a new instance of RecyclerView Adapter instance
                    /*RecyclerView.Adapter*/
                    vehicleAdapter = new VehicleAdapter(VehicleList.this, vehiclelists);

                    // Set the adapter for RecyclerView
                    recyclerView.setAdapter(vehicleAdapter);

                    //   clear();
                    vehicleAdapter.notifyDataSetChanged();


                } else if (response.body().getSuccess() == 0) {

                    vehiclelists.clear();
                    recyclerView.removeAllViewsInLayout();

                    Toast.makeText(VehicleList.this, "No vehicle found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<GetVehiclePojo> call, Throwable t) {

                progressDialog.dismiss();

                Log.e(VehicleList.class.getSimpleName(), t.toString());
                Toast.makeText(VehicleList.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {
        private ArrayList<GetVehiclePojo.Vehiclelist> vehiclelists;
        private Context context;
        ProgressDialog progressDialog;
        String str = "";

        public VehicleAdapter(Context context, ArrayList<GetVehiclePojo.Vehiclelist> vehiclelists) {
            this.vehiclelists = vehiclelists;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_vehicle, null);
            ViewHolder vh = new ViewHolder(view);

            progressDialog = new ProgressDialog(context);

            return vh;

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            holder.txtVehicleNumber.setText(vehiclelists.get(position).getVehiclenumber());
            holder.txtNoOfSeats.setText("No Of Seats : "+vehiclelists.get(position).getNoofseats());
            holder.txtCurrentLocation.setText("Current Location : "+vehiclelists.get(position).getCurrentlocation());
            holder.txtVehicleType.setText("Vehicle Type : "+vehiclelists.get(position).getVehicletype());
            holder.txtCharges.setText("Charges : "+vehiclelists.get(position).getCharges());

            str = vehiclelists.get(position).getVehicleid();

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VehicleList.this, BookCar.class);

                    intent.putExtra("vehicleid", vehiclelists.get(position).getVehicleid());
                    intent.putExtra("currentlocation", vehiclelists.get(position).getCurrentlocation());
                    intent.putExtra("charges", vehiclelists.get(position).getCharges());
                    intent.putExtra("ownerid", vehiclelists.get(position).getOwnerid());
                    /*intent.putExtra("coursename", courseName);
                    intent.putExtra("classid", classId);
                    intent.putExtra("classname", className);
                    intent.putExtra("subjectid", subjectId);
                    intent.putExtra("subjectname", subjectName);
                    intent.putExtra("chapterid", str);*/

                    startActivity(intent);
                  //  finish();
                }
            });


            //   preferenceLecturer.setClassId(str);


        }

        @Override
        public int getItemCount() {
            return vehiclelists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtVehicleNumber, txtNoOfSeats, txtCurrentLocation, txtVehicleType, txtCharges;
            public CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                txtVehicleNumber = itemView.findViewById(R.id.txtVehicleNumber);

                txtNoOfSeats = itemView.findViewById(R.id.txtNoOfSeats);
                txtCurrentLocation = itemView.findViewById(R.id.currentLocation);
                txtVehicleType = itemView.findViewById(R.id.txtVehicleType);
                txtCharges = itemView.findViewById(R.id.txtCharges);
                cardView = itemView.findViewById(R.id.cardView);
            }
        }
    }
}
