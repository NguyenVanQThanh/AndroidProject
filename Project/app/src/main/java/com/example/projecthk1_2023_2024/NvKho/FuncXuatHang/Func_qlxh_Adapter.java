package com.example.projecthk1_2023_2024.NvKho.FuncXuatHang;

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
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Func_qlxh_Adapter extends RecyclerView.Adapter<Func_qlxh_Adapter.MyViewHolder> {

    Context context;
    List<Pair<String, Export>> list;

    public Func_qlxh_Adapter(Context context, List<Pair<String, Export>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.func_qlxh_adapter_layout,parent,false);
        return new MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pair<String, Export> item = list.get(position);
        holder.maPx.setText(item.first);
        holder.date.setText(StampToString(item.second.getCreate_Date()));
        holder.status.setText(item.second.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String StampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = timestamp.toDate();
        return dateFormat.format(date);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView maPx, date, status;
        public MyViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;
            maPx = itemView.findViewById(R.id.mapx3);
            date = itemView.findViewById(R.id.ngayTao3);
            status = itemView.findViewById(R.id.status3);
        }
    }
}