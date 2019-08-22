package com.example.minterin.helperS;

import com.example.minterin.modelS.Materi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiOnlyK {
    @GET("aksesK.php")
    Call<List<Materi>> getMateri();
}
