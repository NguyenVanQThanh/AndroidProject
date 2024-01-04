package com.example.projecthk1_2023_2024.Admin.ExpActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.ViewModel.Exp_expBatch_VM;

import java.util.List;

public class DetailExpAdapter extends RecyclerView.Adapter<DetailExpAdapter.MyViewHolder> {

    Context context;
    List<Exp_expBatch_VM> list;

    public DetailExpAdapter(Context context, List<Exp_expBatch_VM> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.detail_exp_adapter_item,parent,false);
        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exp_expBatch_VM exportBatchPair = list.get(position);
        holder.maSp.setText(exportBatchPair.getProductBatchPair().second.getIDProduct().getId());
        holder.sln.setText(String.valueOf(exportBatchPair.getExpBatchPair().second.getQuantity()));
        if (exportBatchPair.getExpPair().second.isEnable()){
            holder.tt.setText("Nhận");
            holder.tt.setTextColor(R.color.green);
        }else {
            holder.tt.setText("Không nhận");
            holder.tt.setTextColor(R.color.red);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView maSp,sln, tt;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            maSp = itemView.findViewById(R.id.maSpN);
            sln = itemView.findViewById(R.id.sln);
            tt = itemView.findViewById(R.id.ttN);
            context = ctx;
        }
    }
}