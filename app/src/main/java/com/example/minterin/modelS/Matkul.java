package com.example.minterin.modelS;

import com.google.gson.annotations.SerializedName;

public class Matkul {
    @SerializedName("id")
    String id;

    @SerializedName("nama_matkul")
    String matkul;

    @SerializedName("jml_matkul")
    String jumlah;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatkul() {
        return matkul;
    }

    public void setMatkul(String matkul) {
        this.matkul = matkul;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
