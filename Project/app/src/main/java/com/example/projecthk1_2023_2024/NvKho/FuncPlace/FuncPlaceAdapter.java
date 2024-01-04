package com.example.projecthk1_2023_2024.NvKho.FuncPlace;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.Admin.clickhandler.OnChildItemClickListener;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.ViewModel.Product_PB_VM;
import com.example.projecthk1_2023_2024.model.ViewModel.Zone_Shelf_VM;
import com.example.projecthk1_2023_2024.model.Zone;

import java.util.ArrayList;
import java.util.List;

public class FuncPlaceAdapter extends RecyclerView.Adapter<FuncPlaceAdapter.MyViewHolder> {
    List<Pair<String,Zone>> listZone;
    List<Pair<String,Shelf>> listShelf;
    Context context;
    OnChildItemClickListener childItemClickListener;

    public FuncPlaceAdapter(List<Pair<String, Zone>> listZone, List<Pair<String, Shelf>> listShelf, Context context) {
        this.listZone = listZone;
        this.listShelf = listShelf;
        this.context = context;
    }

    public void setOnChildItemClickListener(OnChildItemClickListener listener) {
        this.childItemClickListener = listener;
    }


    @NonNull
    @Override
    public FuncPlaceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemkhureal, parent, false);
        return new MyViewHolder(itemView,context);
    }



    @Override
    public void onBindViewHolder(@NonNull FuncPlaceAdapter.MyViewHolder holder, int position) {
        Pair<String,Zone> zonePair = listZone.get(position);
        holder.txtNameKhu.setText(zonePair.second.getName());

        List<Pair<String,Shelf>> results = new ArrayList<>();
        for(Pair<String, Shelf> shelfPair : listShelf){
            if (shelfPair.second.getID_Zone().getId().equals(zonePair.first)){
                results.add(shelfPair);
            }
        }
        FuncPlaceChildAdapter childAdapter = new FuncPlaceChildAdapter(results, position);
        childAdapter.setOnChildItemClickListener(childItemClickListener);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        holder.recyclerView.setAdapter(childAdapter);

    }


    @Override
    public int getItemCount() {
        return listZone.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameKhu;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView, Context ctx) {

                super(itemView);
                context = ctx;
                txtNameKhu = itemView.findViewById(R.id.nameKhu);
                recyclerView = itemView.findViewById(R.id.recycler_view_child);

        }
    }


}
