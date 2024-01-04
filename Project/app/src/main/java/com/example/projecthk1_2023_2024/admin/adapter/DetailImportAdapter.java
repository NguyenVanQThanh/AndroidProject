package com.example.projecthk1_2023_2024.Admin.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ProductBatch;

import java.util.List;

public class DetailImportAdapter extends RecyclerView.Adapter<DetailImportAdapter.MyViewHolder> {

    Context context;
    List<Pair<String, ProductBatch>> list;

    public DetailImportAdapter(Context context, List<Pair<String, ProductBatch>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_view_importsp,parent,false);
        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pair<String, ProductBatch> productBatchPair = list.get(position);
        holder.maSp.setText(productBatchPair.second.getIDProduct().getId());
        holder.gia.setText(String.valueOf(productBatchPair.second.getImportPrice()));
        holder.slt.setText(String.valueOf(productBatchPair.second.getQuantity_Valid()));
        holder.sln.setText(String.valueOf(productBatchPair.second.getQuantity()));
        if (productBatchPair.second.isEnable()){
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
        TextView maSp, gia, slt,sln, tt;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            maSp = itemView.findViewById(R.id.maSp);
            gia = itemView.findViewById(R.id.giaSPx);
            slt = itemView.findViewById(R.id.sltX);
            sln = itemView.findViewById(R.id.slnX);
            tt = itemView.findViewById(R.id.ttX);
            context = ctx;
        }
    }
}
