package com.example.projecthk1_2023_2024.NvKho.FuncPlace;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.OnChildItemClickListener;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.Shelf;

import java.util.List;

public class FuncPlaceChildAdapter extends RecyclerView.Adapter<FuncPlaceChildAdapter.MyViewHolder> {

    List<Pair<String, Shelf>> shelfList;
    private int positionParent;
    private OnChildItemClickListener childItemClickListener;



//    String idZone;

    public FuncPlaceChildAdapter(List<Pair<String, Shelf>> shelfList, int positionParent) {
        this.shelfList = shelfList;
        this.positionParent = positionParent;
    }


//    public FuncPlaceChildAdapter(List<Pair<String, Shelf>> shelfList, String idZone) {
//        this.shelfList = shelfList;
//        this.idZone = idZone;
//    }

    public void setOnChildItemClickListener(OnChildItemClickListener listener) {
        this.childItemClickListener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new FuncPlaceChildAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pair<String, Shelf> shelfPair = shelfList.get(position);
        holder.btn.setText(shelfPair.second.getName());
        if(!shelfPair.second.isEnable()){
            holder.btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.RED));
        }else if (shelfPair.second.getQuantity()>0){
            holder.btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.GREEN));
        }

    }

    @Override
    public int getItemCount() {
        return shelfList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.ke1_ne);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childItemClickListener != null) {
                        String id = shelfList.get(getAdapterPosition()).first;
//                        Log.d("FuncPlaceChildAdapter", "Child item clicked: " + id);
                        childItemClickListener.onChildItemClick(v, positionParent, id);
                    }
                }
            });
        }
    }
}
