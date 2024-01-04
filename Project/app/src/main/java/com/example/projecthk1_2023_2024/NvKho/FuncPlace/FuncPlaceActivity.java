package com.example.projecthk1_2023_2024.NvKho.FuncPlace;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.Admin.clickhandler.OnChildItemClickListener;
import com.example.projecthk1_2023_2024.NvKho.FuncQLSP.DetailProductNVKActivity;
import com.example.projecthk1_2023_2024.NvKho.FuncQLSP.DetailProductNVKAdapter;
import com.example.projecthk1_2023_2024.NvKho.NvkFragHome;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListShelfUtil;
import com.example.projecthk1_2023_2024.Util.ListZoneUtil;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.ViewModel.Product_PB_VM;
import com.example.projecthk1_2023_2024.model.ViewModel.Zone_Shelf_VM;
import com.example.projecthk1_2023_2024.model.Zone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FuncPlaceActivity extends AppCompatActivity implements OnChildItemClickListener {
    ImageView back;
    TextView empty, have, full;
    RecyclerView recyclerView;
    ListShelfUtil listShelfUtil = ListShelfUtil.getInstance();
    ListZoneUtil listZoneUtil = ListZoneUtil.getInstance();
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nvkho_func1_qlvtri_layout);
        recyclerView = findViewById(R.id.recy_khu_f1);
        List<Pair<String, Shelf>> shelfList = listShelfUtil.getPairList();
        List<Pair<String, Zone>> zoneList = listZoneUtil.getPairList();
        Log.d("Existence",(zoneList==null?"False":"True"));
        Log.d(MotionEffect.TAG,"Size:" + listZoneUtil.getPairList().size());
        FuncPlaceAdapter funcPlaceAdapter = new FuncPlaceAdapter(zoneList,shelfList,getApplicationContext());
        funcPlaceAdapter.setOnChildItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(funcPlaceAdapter);
    }

    @Override
    public void onChildItemClick(View view, int parentPosition, String childId) {
        Intent i = new Intent(FuncPlaceActivity.this, DetailPlaceActivity.class);
        String name = listZoneUtil.getPairList().get(parentPosition).second.getName()+ " " + listShelfUtil.find(childId).second.getName();
        i.putExtra("Name",name);
        i.putExtra("idShelf",childId);
        i.putExtra("idZone",listZoneUtil.getPairList().get(parentPosition).first);
        startActivity(i);
    }
}
