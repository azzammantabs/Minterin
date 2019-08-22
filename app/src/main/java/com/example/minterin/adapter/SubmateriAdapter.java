package com.example.minterin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minterin.R;
import com.example.minterin.modelS.Submateri;

import java.util.List;

public class SubmateriAdapter extends RecyclerView.Adapter<SubmateriAdapter.SubmateriViewHolder> {

    Context ctx;
    List<Submateri> submateriList;
    public SubmateriAdapter(Context ctx, List<Submateri> submateriList) {
        this.ctx = ctx;
        this.submateriList = submateriList;
    }

    @NonNull
    @Override
    public SubmateriAdapter.SubmateriViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.list_video, viewGroup, false);
        SubmateriViewHolder sv = new SubmateriViewHolder(view);
        return sv;
    }

    @Override
    public void onBindViewHolder(@NonNull final SubmateriAdapter.SubmateriViewHolder submateriViewHolder, int i) {
        final Submateri submateri = submateriList.get(i);
        submateriViewHolder.tv_judul.setText(submateri.getJudul());
        submateriViewHolder.tv_durasi.setText(submateri.getDurasi());

        //memberikan onclick pada imageview
        submateriViewHolder.img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Sub Materi ke "+submateriViewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return submateriList.size();
    }

    public class SubmateriViewHolder extends RecyclerView.ViewHolder {
        TextView tv_judul, tv_durasi;
        ImageView img_play;
        public SubmateriViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_judul = itemView.findViewById(R.id.bab_matkul);
            tv_durasi = itemView.findViewById(R.id.durasivideo);
            img_play = itemView.findViewById(R.id.img_play);
        }
    }
}
