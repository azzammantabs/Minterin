package com.example.minterin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minterin.HomeActivity;
import com.example.minterin.R;
import com.example.minterin.fragment.AkunFragment;
import com.example.minterin.fragment.MateriFragment;
import com.example.minterin.fragment.SubMateriFragment;
import com.example.minterin.modelS.Materi;
import com.example.minterin.modelS.Matkul;

import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.MateriViewHolder> {

    private Context ctx;
    private List<Materi> listMateri;

    public MateriAdapter(Context ctx, List<Materi> listMateri) {
        this.ctx = ctx;
        this.listMateri = listMateri;
    }

    @NonNull
    @Override
    public MateriAdapter.MateriViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.list_materi, viewGroup, false);
        MateriAdapter.MateriViewHolder mv = new MateriAdapter.MateriViewHolder(v);
        return mv;
    }

    @Override
    public void onBindViewHolder(@NonNull final MateriAdapter.MateriViewHolder materiViewHolder, int i) {
        final Materi materi = listMateri.get(i);
        materiViewHolder.tv_materi.setText(materi.getNama_materi());
        materiViewHolder.tv_jmlvideo.setText(materi.getJml_video());

        materiViewHolder.cv_materi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materiViewHolder.getAdapterPosition() == 0) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new SubMateriFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                }
                else {
                    Toast.makeText(ctx, "Belum dapat mengakses item ini", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMateri.size();
    }

    public class MateriViewHolder extends RecyclerView.ViewHolder {
        TextView tv_materi, tv_jmlvideo;
        CardView cv_materi;

        public MateriViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_materi = itemView.findViewById(R.id.tv_materi);
            tv_jmlvideo = itemView.findViewById(R.id.tv_jmlvideo);
            cv_materi = itemView.findViewById(R.id.cv_materi);
        }
    }
}
