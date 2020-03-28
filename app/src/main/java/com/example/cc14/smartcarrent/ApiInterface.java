package com.example.cc14.smartcarrent;

import com.example.cc14.smartcarrent.Model.AddVehiclePojo;
import com.example.cc14.smartcarrent.Model.GetCarBookingPojo;
import com.example.cc14.smartcarrent.Model.GetVehiclePojo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @Multipart
    @POST("addvehicle.php")
    Call<AddVehiclePojo> addvehicle(@PartMap() Map<String, RequestBody> partMap,
                                    @Part MultipartBody.Part file);

    @POST("getvehicle.php")
    Call<GetVehiclePojo> getVehicle(@QueryMap Map<String, String> params);

    @POST("getcarbooking.php")
    Call<GetCarBookingPojo> getCar(@QueryMap Map<String, String> params);

    @POST("getbilling.php")
    Call<GetCarBookingPojo> getBill(@QueryMap Map<String, String> params);

}
