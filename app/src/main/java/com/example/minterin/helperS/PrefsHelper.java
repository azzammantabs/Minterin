package com.example.minterin.helperS;


import android.content.Context;
import android.content.SharedPreferences;

//untuk menyimpan value yang dibutuhkan oleh aplikasi
public class PrefsHelper {
    SharedPreferences prefs;
    Context ctx;
    private final String PREF_STATUS = "status_login";
    private final String PREF_FRAGMENT = "status_fragment";
    private final String PREF_NAMA = "status_nama";

    private static PrefsHelper instance;

    public static PrefsHelper sharedInstance(Context ctx) {
        if (instance == null) {
            instance = new PrefsHelper(ctx);
        }
        return instance;
    }

    private PrefsHelper(Context ctx) {
        this.ctx = ctx;
        this.prefs = ctx.getSharedPreferences("LATIHANSHARED", Context.MODE_PRIVATE);
    }

    //fungsi untuk menyimpan login status pada perangkat kita
    public void setStatusLogin(boolean status) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_STATUS, status);
        editor.apply();
    }

    //fungsi untuk mengambil status login
    public boolean getStatusLogin() {
        return prefs.getBoolean(PREF_STATUS, false);
    }

    //fungsi untuk menyimpan nama
    public void setNamaUser(String nama) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_NAMA, nama);
        editor.apply();
    }

    //fungsi untuk mengambil nama
    public String getNamaUser(){
        return prefs.getString(PREF_NAMA, "tanpanama");
    }

    //fungsi untuk menyimpan status fragment
    public void setStatusFragment(boolean status) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_FRAGMENT, status);
        editor.apply();
    }

    //fungsi untuk mengambil status fragment
    public boolean getStatusFragment() {
        return prefs.getBoolean(PREF_FRAGMENT, false);
    }
}
