package com.example.projecthk1_2023_2024.Admin.ExpActivity;

import static androidx.databinding.DataBindingUtil.setContentView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projecthk1_2023_2024.Admin.adapter.ImportAdapter;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ViewModel.ExpADV_VM;
import com.example.projecthk1_2023_2024.model.ViewModel.ImportADViewModel;

import java.util.List;

public class ListExportAdapter extends RecyclerView.Adapter<ListExportAdapter.MyViewHolder> {

    static Context context;
    static ItemClick itemClick;
    List<ExpADV_VM> listExp;

    public ListExportAdapter(Context context, List<ExpADV_VM> listExp) {
        this.listExp = listExp;
        this.context = context;
    }
    public void setFilterList(Context context, List<ExpADV_VM> filtedList){
        this.context = context;
        this.listExp = filtedList;
        notifyDataSetChanged();
    }
    public void setClickListener(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    @NonNull
    @Override
    public ListExportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_export_adapter_item,parent,false);
        return new ListExportAdapter.MyViewHolder(view,context);
    }
    @Override
    public void onBindViewHolder(@NonNull ListExportAdapter.MyViewHolder holder, int position) {
        ExpADV_VM expADViewModel = listExp.get(position);
        holder.idExp.setText(expADViewModel.getExpPair().first);
        holder.date.setText(expADViewModel.getExpPair().second.StampToString(expADViewModel.getExpPair().second.getDate_Success()));
        holder.user.setText(expADViewModel.getUserPair().second.getUserName());
        if (expADViewModel.getExpPair().second.getStatus().equals("Success")){
            holder.ht.setVisibility(View.VISIBLE);
            holder.dxl.setVisibility(View.GONE);
            holder.dh.setVisibility(View.GONE);
        } else if (expADViewModel.getExpPair().second.getStatus().equals("Waiting")){
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
        return listExp.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idExp,date, dxl,ht,dh, user;

        public MyViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;
            idExp = itemView.findViewById(R.id.MaPhieuX);
            date = itemView.findViewById(R.id.Date_importbatch);
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
