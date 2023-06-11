package com.example.placarbasquete.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placarbasquete.R;
import com.example.placarbasquete.models.HistoricoModel;

import java.util.ArrayList;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder> {
    private ArrayList<HistoricoModel> historicoList;

    public HistoricoAdapter( ArrayList<HistoricoModel> list) {
        this.historicoList = list;
    }


    @NonNull
    @Override
    public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_histotico, parent, false);
        return new HistoricoAdapter.HistoricoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoViewHolder holder, int position) {
        final HistoricoModel partida = historicoList.get(position);
        holder.tvItemDate.setText("Data: "+ partida.getData());
        holder.tvItemPlacar.setText(partida.getPlacar());
        holder.tvItemTime.setText(partida.getTimes());
        holder.tvItemDuration.setText("Duração: "+partida.getDuracao());
    }

    @Override
    public int getItemCount() {
        return historicoList.size();
    }


    class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemDate, tvItemDuration, tvItemPlacar, tvItemTime;

        public HistoricoViewHolder(View itemView) {
            super(itemView);
            tvItemDate = itemView.findViewById(R.id.tvItemDate);
            tvItemDuration = itemView.findViewById(R.id.tvItemDuration);
            tvItemPlacar = itemView.findViewById(R.id.tvItemPlacar);
            tvItemTime = itemView.findViewById(R.id.tvItemTime);

        }
    }
}
