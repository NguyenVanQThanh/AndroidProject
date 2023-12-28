package com.example.projecthk1_2023_2024.NvKho.FuncPlace;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.ViewModel.Product_PB_VM;
import com.example.projecthk1_2023_2024.model.ViewModel.Zone_Shelf_VM;
import com.example.projecthk1_2023_2024.model.Zone;

import java.util.ArrayList;
import java.util.List;

public class FuncPlaceAdapter extends RecyclerView.Adapter<FuncPlaceAdapter.MyViewHolder> {
    private ArrayList<Zone_Shelf_VM> listKhuKe;
    Context context;
    ItemClick itemClick;


    public FuncPlaceAdapter(ArrayList<Zone_Shelf_VM> dsKhuKe, Context context, ItemClick itemClick) {
        this.listKhuKe = dsKhuKe;
        this.context = context;
        this.itemClick = itemClick;
    }
    public void setClickListener(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public FuncPlaceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nvkho_func1_item_khu, parent, false);
        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull FuncPlaceAdapter.MyViewHolder holder, int position) {
        Zone_Shelf_VM list = listKhuKe.get(position);
        holder.txtNameKhu.setText(list.getZonePair().second.getName().toString());
        List<Pair<String, Shelf>> listShelfPair = list.getListShelfPair();
        for (int i = 0; i < listShelfPair.size(); i++) {
            int y = i + 6;
            switch (i % y - 6) {
                case 0:
                    Pair<String, Shelf> pair = listShelfPair.get(i);
//            String key = pair.first;
                    String nameShelf = pair.second.getName();
                    holder.btn1.setText(nameShelf.toString());
                    y = i + 6;
                    listShelfPair.remove(i);
                    break;

                case 1:
                    pair = listShelfPair.get(i);
//            String key = pair.first;
                    nameShelf = pair.second.getName();
                    holder.btn2.setText(nameShelf.toString());
                    y = i + 6;
                    listShelfPair.remove(i);
                    break;
                case 2:
                    pair = listShelfPair.get(i);
//            String key = pair.first;
                    nameShelf = pair.second.getName();
                    holder.btn3.setText(nameShelf.toString());
                    y = i + 6;
                    listShelfPair.remove(i);
                    break;
                case 3:
                    pair = listShelfPair.get(i);
//            String key = pair.first;
                    nameShelf = pair.second.getName();
                    holder.btn4.setText(nameShelf.toString());
                    y = i + 6;
                    listShelfPair.remove(i);
                    break;
                case 4:
                    pair = listShelfPair.get(i);
//            String key = pair.first;
                    nameShelf = pair.second.getName();
                    holder.btn5.setText(nameShelf.toString());
                    y = i + 6;
                    listShelfPair.remove(i);
                    break;
                case 5:
                    pair = listShelfPair.get(i);
//            String key = pair.first;
                    nameShelf = pair.second.getName();
                    holder.btn6.setText(nameShelf.toString());
                    y = i + 6;
                    listShelfPair.remove(i);
                    break;
            }
        }
        }

    @Override
    public int getItemCount() {
//        return listKhuKe.size();
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameKhu;
        TextView btn1, btn2, btn3, btn4, btn5, btn6;

        public MyViewHolder(@NonNull View itemView) {

                super(itemView);
                txtNameKhu = itemView.findViewById(R.id.nameKhu);
                btn1 = itemView.findViewById(R.id.Ke1);
                btn2 = itemView.findViewById(R.id.Ke2);
                btn3 = itemView.findViewById(R.id.Ke3);
                btn4 = itemView.findViewById(R.id.Ke4);
                btn5 = itemView.findViewById(R.id.Ke5);
                btn6 = itemView.findViewById(R.id.Ke6);

        }
    }


}
