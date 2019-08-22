package com.example.minterin.modelS;

import com.google.gson.annotations.SerializedName;

public class Submateri {
    @SerializedName("id")
    String id;

    @SerializedName("judul")
    String judul;

    @SerializedName("durasi")
    String durasi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }
}
