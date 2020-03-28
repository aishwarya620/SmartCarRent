package com.example.cc14.smartcarrent;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.example.cc14.smartcarrent.Model.GetCarBookingPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Billing extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    ArrayList<GetCarBookingPojo.Carbookinglist> carbookinglists;
    BookingAdapter bookingAdapter;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        progressDialog = new ProgressDialog(Billing.this);
        preferences = new Preferences(Billing.this);
        carbookinglists = new ArrayList<GetCarBookingPojo.Carbookinglist>();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager vehicleLayoutManager = new LinearLayoutManager(Billing.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(vehicleLayoutManager);

        getBill();
    }

    public void getBill() {
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
        params.put("userid", preferences.getUserID());

        Call<GetCarBookingPojo> call = apiInterface.getBill(params);
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
                    bookingAdapter = new BookingAdapter(Billing.this, carbookinglists);

                    // Set the adapter for RecyclerView
                    recyclerView.setAdapter(bookingAdapter);

                    //   clear();
                    bookingAdapter.notifyDataSetChanged();


                } else if (response.body().getSuccess() == 0) {

                    carbookinglists.clear();
                    recyclerView.removeAllViewsInLayout();

                    Toast.makeText(Billing.this, "No Billing found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<GetCarBookingPojo> call, Throwable t) {

                progressDialog.dismiss();

                Log.e(VehicleList.class.getSimpleName(), t.toString());
                Toast.makeText(Billing.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
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

            String flag = carbookinglists.get(position).getFlag();

            if (flag.equals("1")) {

                holder.txtUserName.setText("Username : " + carbookinglists.get(position).getUsername());
                holder.txtKm.setText("Kilometer : " + carbookinglists.get(position).getKm());
                holder.txtSource.setText("Source : " + carbookinglists.get(position).getSource());
                holder.txtDestination.setText("Destination : " + carbookinglists.get(position).getDestination());
                holder.txtVehicleType.setText("Ac Type : " + carbookinglists.get(position).getActype());

                String cost = carbookinglists.get(position).getFinalamount();
                int co = Integer.parseInt(cost);
                int finalAmt = co + 300;
                String totalCost = String.valueOf(finalAmt);

                holder.txtFinalAmount.setText("Final Amount : " +totalCost);
                holder.txtAcTime.setText("Ac Time : 2 hours");

                holder.btnGenerateBill.setVisibility(View.GONE);

            }else {
                holder.cardView.setVisibility(View.GONE);
            }



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
                cardView = itemView.findViewById(R.id.cardView);
            }
        }
    }
}
