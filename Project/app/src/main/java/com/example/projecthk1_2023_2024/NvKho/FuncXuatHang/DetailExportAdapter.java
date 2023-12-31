package com.example.projecthk1_2023_2024.NvKho.FuncXuatHang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ViewModel.VMDetailNewExp;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.google.firebase.Timestamp;

import java.util.List;

public class DetailExportAdapter extends RecyclerView.Adapter<DetailExportAdapter.MyViewHolder>  {
    Context context;
    ItemClick itemClick;
    private List<VMDetailNewExp> listDetail;

    public DetailExportAdapter(Context context, List<VMDetailNewExp> listDetail) {
        this.context = context;
        this.listDetail = listDetail;
    }

    public void setClickListener(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public DetailExportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.nvkho_f2_1_detail_item,parent,false);
        return new DetailExportAdapter.MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailExportAdapter.MyViewHolder holder, int position) {
        VMDetailNewExp DetailPair = listDetail.get(position);
        holder.txtTensp.setText(DetailPair.getProductPair2().second.getName());
        holder.txtSlt.setText(DetailPair.getProductPair2().second.getQuantity());

        String khu = DetailPair.getShelfPair2().second.getName();
        String ke = DetailPair.getZonePair2().second.getName();
        holder.txtVt.setText(khu + " " + ke);

        Timestamp exprided = DetailPair.getBatchPair2().second.getExpiryDate();
        // Định dạng ngày thành kiểu String
        String exDate = String.valueOf(exprided);
        // Đặt giá trị cho TextView
        holder.txtHsd.setText(exDate);
    }

    @Override
    public int getItemCount() {
        return listDetail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTensp;
        TextView txtSlt;
        TextView txtSlx;
        TextView txtVt;
        TextView txtHsd;

        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            txtTensp = itemView.findViewById(R.id.tensp21);
            txtSlt = itemView.findViewById(R.id.slt21);
            txtSlx = itemView.findViewById(R.id.slx21);
            txtVt= itemView.findViewById(R.id.vt21);
            txtHsd = itemView.findViewById(R.id.hsd21);
        }

        @Override
        public void onClick(View v) {
            if (itemClick!=null){
                itemClick.onClick(v,getAdapterPosition());
            }
        }
    }
}
