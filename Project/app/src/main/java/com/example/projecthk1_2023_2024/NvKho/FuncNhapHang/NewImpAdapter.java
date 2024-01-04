package com.example.projecthk1_2023_2024.NvKho.FuncNhapHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewImpAdapter extends RecyclerView.Adapter<NewImpAdapter.MyViewHolder>  {
    Context context;
    ItemClick itemClick;
    private List<Pair<String, ImportBatch>> listImp;

    public NewImpAdapter(Context context, List<Pair<String, ImportBatch>> listNewExp) {
        this.context = context;
        this.listImp = listNewExp;
    }
    public void setFilterList(Context context, List<Pair<String, ImportBatch>> filtedList){
        this.context = context;
        this.listImp = filtedList;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public NewImpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.new_imp_adapter_layout,parent,false);
        return new NewImpAdapter.MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull NewImpAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pair<String, ImportBatch> NewImpPair = listImp.get(position);
        int totalSL = 0;
        holder.txtMapn.setText(NewImpPair.first);
        holder.txtNCC.setText(NewImpPair.second.getSupplier());
        holder.txtTTdon.setText(NewImpPair.second.getStatus().toString());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClick(v, position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listImp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtMapn, txtNCC, txtTTdon;

        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            txtMapn = itemView.findViewById(R.id.mapn3);
            txtNCC = itemView.findViewById(R.id.ncc3);
            txtTTdon = itemView.findViewById(R.id.statusDon3);


        }

        @Override
        public void onClick(View v) {
            if (itemClick!=null){
                itemClick.onClick(v,getAdapterPosition());
            }
        }

    }
    private String StampToString(Timestamp timestamp) {
        if (timestamp != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = timestamp.toDate();
            return dateFormat.format(date);
        } else {
            return "Null";
        }

    }

}