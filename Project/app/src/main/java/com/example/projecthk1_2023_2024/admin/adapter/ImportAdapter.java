package com.example.projecthk1_2023_2024.Admin.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.ViewModel.ImportADViewModel;

import java.util.List;

public class ImportAdapter extends RecyclerView.Adapter<ImportAdapter.MyViewHolder> {

    static Context context;
    static ItemClick itemClick;
    List<ImportADViewModel> listImport;

    public ImportAdapter(Context context, List<ImportADViewModel> listImport) {
        this.listImport = listImport;
        this.context = context;
    }
    public void setFilterList(Context context, List<ImportADViewModel> filtedList){
        this.context = context;
        this.listImport = filtedList;
        notifyDataSetChanged();
    }
    public void setClickListener(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_view_import_admin,parent,false);
        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImportADViewModel importADViewModel = listImport.get(position);
        holder.idImport.setText(importADViewModel.getImportBatchPair().first);
        holder.date.setText(importADViewModel.getImportBatchPair().second.StampToString(importADViewModel.getImportBatchPair().second.getDate()));
        holder.sup.setText(importADViewModel.getImportBatchPair().second.getSupplier());
        if (importADViewModel.getUserPair()!=null){
        holder.user.setText(importADViewModel.getUserPair().second.getUserName());}
        if (importADViewModel.getImportBatchPair().second.getStatus().equals("Success")){
            holder.ht.setVisibility(View.VISIBLE);
            holder.dxl.setVisibility(View.GONE);
            holder.dh.setVisibility(View.GONE);
        } else if (importADViewModel.getImportBatchPair().second.getStatus().equals("Waiting")){
            holder.ht.setVisibility(View.GONE);
            holder.dxl.setVisibility(View.VISIBLE);
            holder.dh.setVisibility(View.GONE);
        } else {
            holder.ht.setVisibility(View.GONE);
            holder.dxl.setVisibility(View.GONE);
            holder.dh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listImport.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idImport,date,sup, dxl,ht,dh, user;

        public MyViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;
            idImport = itemView.findViewById(R.id.MaPhieuNhap);
            date = itemView.findViewById(R.id.Date_importbatch);
            sup = itemView.findViewById(R.id.NCC);
            dxl = itemView.findViewById(R.id.tinhtrang_import_DXL);
            dh = itemView.findViewById(R.id.tinhtrang_import_DH);
            ht = itemView.findViewById(R.id.tinhtrang_import_HT);
            user = itemView.findViewById(R.id.name_user);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(v,getAdapterPosition());
        }
    }
}
