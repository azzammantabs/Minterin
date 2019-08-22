package com.example.minterin.helperS;

import com.example.minterin.modelS.Submateri;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiOnlyS {
    @GET("aksesS.php")
    Call<List<Submateri>> getSubmateri();
}
