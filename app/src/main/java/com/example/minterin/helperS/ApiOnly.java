package com.example.minterin.helperS;

import com.example.minterin.modelS.Matkul;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiOnly {
    @GET("akses.php")
    Call<List<Matkul>> getMatkul();
}
