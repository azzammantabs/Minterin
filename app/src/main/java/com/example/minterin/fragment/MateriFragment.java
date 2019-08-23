package com.example.minterin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.minterin.R;
import com.example.minterin.adapter.MateriAdapter;
import com.example.minterin.helperS.ApiOnlyK;
import com.example.minterin.helperS.Const;
import com.example.minterin.modelS.Materi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MateriFragment extends Fragment {
    RecyclerView rcView;
    Retrofit retrofit;
    private MateriAdapter adapterM;
    private List<Materi> listMateri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ui_materi, container, false);
        rcView = v.findViewById(R.id.rc_materi);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcView.setHasFixedSize(true);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiOnlyK apiOnly = retrofit.create(ApiOnlyK.class);
        Call<List<Materi>> result = apiOnly.getMateri();
        result.enqueue(new Callback<List<Materi>>() {
            @Override
            public void onResponse(Call<List<Materi>> call, Response<List<Materi>> response) {
                listMateri = response.body();
                adapterM = new MateriAdapter(getActivity(), listMateri);
                rcView.setAdapter(adapterM);

            }

            @Override
            public void onFailure(Call<List<Materi>> call, Throwable t) {
                Toast.makeText(getActivity(), "data tidak dapat diambil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}