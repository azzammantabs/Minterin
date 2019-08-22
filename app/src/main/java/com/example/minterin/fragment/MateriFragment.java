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

import com.example.minterin.R;

import retrofit2.Retrofit;

public class MateriFragment extends Fragment {
    RecyclerView rcView;
    Retrofit retrofit;
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
    }
}
