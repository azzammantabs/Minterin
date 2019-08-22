package com.example.minterin.modelS;

import com.google.gson.annotations.SerializedName;

public class Materi {
    @SerializedName("id")
    String id;

    @SerializedName("nama_materi")
    String nama_materi;

    @SerializedName("jml_video")
    String jml_video;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_materi() {
        return nama_materi;
    }

    public void setNama_materi(String nama_materi) {
        this.nama_materi = nama_materi;
    }

    public String getJml_video() {
        return jml_video;
    }

    public void setJml_video(String jml_video) {
        this.jml_video = jml_video;
    }
}
