package com.example.cc14.smartcarrent;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    SharedPreferences sharePref;
    SharedPreferences.Editor editor;

    public Preferences(Context context) {
        sharePref= PreferenceManager.getDefaultSharedPreferences(context);
        editor=sharePref.edit();
    }

    public void clearPreferences(){
        editor.clear();
        editor.commit();
    }

    public void  setUserID(String userID) {
        editor.putString("USERID",userID);
        editor.commit();
    }
    public String getUserID()
    {
        return sharePref.getString("USERID","");
    }

    public void  setUserName(String userName) {
        editor.putString("USERNAME",userName);
        editor.commit();
    }
    public String getUserName()
    {
        return sharePref.getString("USERNAME","");
    }

    public void  set_is_loggedin(String b) {
        editor.putString("LOGGEDIN",b);
        editor.commit();
    }
    public String get_is_loggedin()
    {
        return sharePref.getString("LOGGEDIN","");
    }

    public void  setBillAmount(String billAmount) {
        editor.putString("BILLAMOUNT",billAmount);
        editor.commit();
    }
    public String getBillAmount()
    {
        return sharePref.getString("BILLAMOUNT","");
    }

    public void  setUserType(String userType) {
        editor.putString("USERTYPE",userType);
        editor.commit();
    }
    public String getUserType()
    {
        return sharePref.getString("USERTYPE","");
    }

    public void  setWalletAmt(String walletAmt) {
        editor.putString("WALLETAMT",walletAmt);
        editor.commit();
    }
    public String getWalletAmt()
    {
        return sharePref.getString("WALLETAMT","");
    }
}
