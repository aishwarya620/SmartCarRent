package com.example.cc14.smartcarrent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVehiclePojo {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("vehiclelist")
    @Expose
    private List<Vehiclelist> vehiclelist = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Vehiclelist> getVehiclelist() {
        return vehiclelist;
    }

    public void setVehiclelist(List<Vehiclelist> vehiclelist) {
        this.vehiclelist = vehiclelist;
    }

    public class Vehiclelist {

        @SerializedName("vehicleid")
        @Expose
        private String vehicleid;
        @SerializedName("vehiclenumber")
        @Expose
        private String vehiclenumber;
        @SerializedName("vehiclecolor")
        @Expose
        private String vehiclecolor;
        @SerializedName("vehiclecompany")
        @Expose
        private String vehiclecompany;
        @SerializedName("vehicletype")
        @Expose
        private String vehicletype;
        @SerializedName("gsmnumber")
        @Expose
        private String gsmnumber;
        @SerializedName("currentlocation")
        @Expose
        private String currentlocation;
        @SerializedName("charges")
        @Expose
        private String charges;
        @SerializedName("noofseats")
        @Expose
        private String noofseats;
        @SerializedName("speedlimit")
        @Expose
        private String speedlimit;
        @SerializedName("ownerid")
        @Expose
        private String ownerid;
        @SerializedName("vehicleimage")
        @Expose
        private String vehicleimage;

        public String getVehicleid() {
            return vehicleid;
        }

        public void setVehicleid(String vehicleid) {
            this.vehicleid = vehicleid;
        }

        public String getVehiclenumber() {
            return vehiclenumber;
        }

        public void setVehiclenumber(String vehiclenumber) {
            this.vehiclenumber = vehiclenumber;
        }

        public String getVehiclecolor() {
            return vehiclecolor;
        }

        public void setVehiclecolor(String vehiclecolor) {
            this.vehiclecolor = vehiclecolor;
        }

        public String getVehiclecompany() {
            return vehiclecompany;
        }

        public void setVehiclecompany(String vehiclecompany) {
            this.vehiclecompany = vehiclecompany;
        }

        public String getVehicletype() {
            return vehicletype;
        }

        public void setVehicletype(String vehicletype) {
            this.vehicletype = vehicletype;
        }

        public String getGsmnumber() {
            return gsmnumber;
        }

        public void setGsmnumber(String gsmnumber) {
            this.gsmnumber = gsmnumber;
        }

        public String getCurrentlocation() {
            return currentlocation;
        }

        public void setCurrentlocation(String currentlocation) {
            this.currentlocation = currentlocation;
        }

        public String getCharges() {
            return charges;
        }

        public void setCharges(String charges) {
            this.charges = charges;
        }

        public String getNoofseats() {
            return noofseats;
        }

        public void setNoofseats(String noofseats) {
            this.noofseats = noofseats;
        }

        public String getSpeedlimit() {
            return speedlimit;
        }

        public void setSpeedlimit(String speedlimit) {
            this.speedlimit = speedlimit;
        }

        public String getOwnerid() {
            return ownerid;
        }

        public void setOwnerid(String ownerid) {
            this.ownerid = ownerid;
        }

        public String getVehicleimage() {
            return vehicleimage;
        }

        public void setVehicleimage(String vehicleimage) {
            this.vehicleimage = vehicleimage;
        }

    }
}
