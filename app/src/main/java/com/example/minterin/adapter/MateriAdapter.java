package com.example.minterin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minterin.R;
import com.example.minterin.modelS.Materi;

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
    public void onBindViewHolder(@NonNull MateriAdapter.MateriViewHolder materiViewHolder, int i) {
        final Materi materi = listMateri.get(i);
        materiViewHolder.tv_materi.setText(materi.getNama_materi());
        materiViewHolder.tv_jmlvideo.setText(materi.getJml_video());

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
