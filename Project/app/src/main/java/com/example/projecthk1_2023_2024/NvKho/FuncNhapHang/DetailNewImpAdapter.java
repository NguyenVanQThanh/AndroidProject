package com.example.projecthk1_2023_2024.NvKho.FuncNhapHang;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ProductBatch;
import com.example.projecthk1_2023_2024.model.ViewModel.Product_PB_VM;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailNewImpAdapter extends RecyclerView.Adapter<DetailNewImpAdapter.MyViewHolder>  {
    Context context;
    //ItemClick itemClick;
    private List<Pair<Pair<String, Product>, Pair<String, ProductBatch>>> listsAdapter;

    public DetailNewImpAdapter(Context context, List<Pair<Pair<String, Product>, Pair<String, ProductBatch>>> listsAdapter) {
        this.context = context;
        this.listsAdapter = listsAdapter;
    }

    // Constructor để nhận dữ liệu từ Activity/Fragment


//    public void setClickListener(ItemClick itemClick){
//        this.itemClick = itemClick;
//    }

    @NonNull
    @Override
    public DetailNewImpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.nvkho_f3_1_detail_item,parent,false);
        return new DetailNewImpAdapter.MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailNewImpAdapter.MyViewHolder holder, int position) {
        Pair<Pair<String, Product>, Pair<String, ProductBatch>> mainData = listsAdapter.get(position);


        holder.txtTenSp.setText(mainData.first.second.getName());
//        holder.txtMaLo.setText(mainData.getIdBatch());
        holder.txtHsd.setText(StampToString((mainData.second.second.getExpiryDate())));
        holder.txtSlt.setText(String.valueOf(mainData.second.second.getQuantity()));
        holder.txtSln.setText(String.valueOf(mainData.second.second.getQuantity()));
        holder.txtGia.setText(String.valueOf(mainData.second.second.getImportPrice()));



    }

    @Override
    public int getItemCount() {
        return listsAdapter.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTenSp, txtMaLo, txtHsd, txtSlt, txtSln,txtGia;

        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            txtTenSp = itemView.findViewById(R.id.namePr31);
            txtSlt = itemView.findViewById(R.id.slTon31);
            txtSln = itemView.findViewById(R.id.slNhap31);
            txtMaLo= itemView.findViewById(R.id.maLo31);
            txtHsd = itemView.findViewById(R.id.hsd31);
            txtGia= itemView.findViewById(R.id.gia31);

        }


        @Override
        public void onClick(View v) {

        }
    }

    private String StampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = timestamp.toDate();
        return dateFormat.format(date);
    }
}
