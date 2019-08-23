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
import com.example.minterin.adapter.SubmateriAdapter;
import com.example.minterin.helperS.ApiOnlyK;
import com.example.minterin.helperS.ApiOnlyS;
import com.example.minterin.helperS.Const;
import com.example.minterin.modelS.Materi;
import com.example.minterin.modelS.Submateri;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubMateriFragment extends Fragment {
    RecyclerView rcView;
    Retrofit retrofit;
    private SubmateriAdapter adapterM;
    private List<Submateri> listSubMateri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ui_submateri, container, false);
        rcView = v.findViewById(R.id.rc_submateri1);
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

        ApiOnlyS apiOnly = retrofit.create(ApiOnlyS.class);
        Call<List<Submateri>> result = apiOnly.getSubmateri();
        result.enqueue(new Callback<List<Submateri>>() {
            @Override
            public void onResponse(Call<List<Submateri>> call, Response<List<Submateri>> response) {
                listSubMateri = response.body();
                adapterM = new SubmateriAdapter(getActivity(), listSubMateri);
                rcView.setAdapter(adapterM);
            }

            @Override
            public void onFailure(Call<List<Submateri>> call, Throwable t) {
                Toast.makeText(getActivity(), "tidak dapat mengambil data", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
