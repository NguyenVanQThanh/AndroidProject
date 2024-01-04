package com.example.projecthk1_2023_2024.NvKho.FuncPlace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.adapter.ImportAdapter;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ViewModel.ItemShelfVM;

import java.util.List;

public class DetailPlaceAdapter extends RecyclerView.Adapter<DetailPlaceAdapter.MyViewHolder>{
    Context context;
    ItemClick itemClick;
    List<ItemShelfVM> list;

    public DetailPlaceAdapter(Context context, List<ItemShelfVM> list) {
        this.context = context;
        this.list = list;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.nvkho_f1_1_select_item,parent,false);
        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemShelfVM itemShelfVM = list.get(position);
        holder.tenSp.setText(itemShelfVM.getProductPair().second.getName());
        holder.maSp.setText(itemShelfVM.getProductPair().first);
        holder.maLo.setText(itemShelfVM.getProductBatchPair().second.getIDBatch().getId());
        holder.slInKe.setText(String.valueOf(itemShelfVM.getProductBatchPair().second.getQuantity_Valid()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView maSp,tenSp,maLo,slInKe;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            maSp = itemView.findViewById(R.id.maPX);
            tenSp = itemView.findViewById(R.id.namePr);
            maLo = itemView.findViewById(R.id.tenSP);
            slInKe = itemView.findViewById(R.id.slInKe);
            context = ctx;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(v,getAdapterPosition());
        }
    }
}
