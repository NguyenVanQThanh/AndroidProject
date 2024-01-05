package com.example.projecthk1_2023_2024.Admin.importactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.NvKho.FuncPlace.FuncPlaceChildAdapter;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ViewModel.ItemAddImport;

import java.util.List;

public class AddImportAdapter extends RecyclerView.Adapter<AddImportAdapter.MyViewHolder>{
    Context context;
    ItemClick itemClick;
    List<ItemAddImport> list;

    public AddImportAdapter(Context context, List<ItemAddImport> list) {
        this.context = context;
        this.list = list;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_imp, parent, false);
        return new MyViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemAddImport itemAddImport = list.get(position);
        holder.giaSP.setText(itemAddImport.getGia());
        holder.tenSp.setText(itemAddImport.getProductPair().second.getName());
        holder.maSp.setText(itemAddImport.getProductPair().first);
        holder.sln.setText(itemAddImport.getSLN());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Button btnDelete;
        TextView maSp,tenSp,giaSP, sln;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            maSp = itemView.findViewById(R.id.editText_masanpham);
            tenSp = itemView.findViewById(R.id.editText_tensanpham);
            giaSP = itemView.findViewById(R.id.editText_soluongsanpham);
            sln = itemView.findViewById(R.id.editText_soluongnhap);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClick!=null){
                        itemClick.onClick(v,getAdapterPosition());
                    }
                }
            });
        }
    }
}
