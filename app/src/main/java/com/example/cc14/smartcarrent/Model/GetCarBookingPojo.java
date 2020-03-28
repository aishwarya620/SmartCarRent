package com.example.cc14.smartcarrent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCarBookingPojo {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("carbookinglist")
    @Expose
    private List<Carbookinglist> carbookinglist = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Carbookinglist> getCarbookinglist() {
        return carbookinglist;
    }

    public void setCarbookinglist(List<Carbookinglist> carbookinglist) {
        this.carbookinglist = carbookinglist;
    }

    public class Carbookinglist {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("ownerid")
        @Expose
        private String ownerid;
        @SerializedName("vehicleid")
        @Expose
        private String vehicleid;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("destination")
        @Expose
        private String destination;
        @SerializedName("km")
        @Expose
        private String km;
        @SerializedName("currentlocation")
        @Expose
        private String currentlocation;
        @SerializedName("actype")
        @Expose
        private String actype;
        @SerializedName("finalamount")
        @Expose
        private String finalamount;
        @SerializedName("flag")
        @Expose
        private String flag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getOwnerid() {
            return ownerid;
        }

        public void setOwnerid(String ownerid) {
            this.ownerid = ownerid;
        }

        public String getVehicleid() {
            return vehicleid;
        }

        public void setVehicleid(String vehicleid) {
            this.vehicleid = vehicleid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public String getCurrentlocation() {
            return currentlocation;
        }

        public void setCurrentlocation(String currentlocation) {
            this.currentlocation = currentlocation;
        }

        public String getActype() {
            return actype;
        }

        public void setActype(String actype) {
            this.actype = actype;
        }

        public String getFinalamount() {
            return finalamount;
        }

        public void setFinalamount(String finalamount) {
            this.finalamount = finalamount;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

    }
}
