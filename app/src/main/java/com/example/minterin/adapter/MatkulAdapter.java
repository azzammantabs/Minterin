package com.example.minterin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minterin.R;
import com.example.minterin.fragment.AkunFragment;
import com.example.minterin.fragment.KuisFragment;
import com.example.minterin.fragment.MateriFragment;
import com.example.minterin.modelS.Matkul;

import java.util.List;

public class MatkulAdapter extends RecyclerView.Adapter<MatkulAdapter.MatkulViewHolder> {

    private Context ctx;
    private List<Matkul> listMatkul;
    Fragment mFragment;
    Bundle mBundle;

    public MatkulAdapter(Context ctx, List<Matkul> listMatkul) {
        this.ctx = ctx;
        this.listMatkul = listMatkul;
    }

    @NonNull
    @Override
    public MatkulAdapter.MatkulViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.list_matakuliah, viewGroup, false);
        MatkulViewHolder mv = new MatkulViewHolder(v);
        return mv;
    }

    @Override
    public void onBindViewHolder(@NonNull final MatkulViewHolder matkulViewHolder, int i) {
        final Matkul matkul = listMatkul.get(i);
        matkulViewHolder.tv_matkul.setText(matkul.getMatkul());
        matkulViewHolder.tv_jmlmateri.setText(matkul.getJumlah());

        //memberikan onclick pada cardview
        matkulViewHolder.cv_matkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (matkulViewHolder.getAdapterPosition() == 0) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new MateriFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                }
                else{
                    Toast.makeText(ctx, "Belum dapat mengakses item ini", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMatkul.size();
    }

    public class MatkulViewHolder extends RecyclerView.ViewHolder {
        TextView tv_matkul, tv_jmlmateri;
        CardView cv_matkul;

        public MatkulViewHolder(View itemView) {
            super(itemView);
            tv_matkul = itemView.findViewById(R.id.tv_matkul);
            tv_jmlmateri = itemView.findViewById(R.id.tv_jmlmateri);
            cv_matkul = itemView.findViewById(R.id.cv_matkul);
        }
    }

}
