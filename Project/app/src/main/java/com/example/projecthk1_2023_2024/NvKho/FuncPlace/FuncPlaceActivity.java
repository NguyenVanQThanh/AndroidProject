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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.NvKho.FuncQLSP.DetailProductNVKActivity;
import com.example.projecthk1_2023_2024.NvKho.FuncQLSP.DetailProductNVKAdapter;
import com.example.projecthk1_2023_2024.NvKho.NvkFragHome;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.ViewModel.Product_PB_VM;
import com.example.projecthk1_2023_2024.model.ViewModel.Zone_Shelf_VM;
import com.example.projecthk1_2023_2024.model.Zone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FuncPlaceActivity extends AppCompatActivity {
    Context context;
    ImageView back;
    TextView empty, have, full;

    FuncPlaceAdapter adapterKhu;
    RecyclerView recyList;
    ArrayList<Zone_Shelf_VM> list = new ArrayList();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReferenceZone = db.collection("Zone");
    CollectionReference collectionReferenceShelf = db.collection("Shelf");


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nvkho_func1_qlvtri_layout);
        recyList = findViewById(R.id.recy_khu_f1);

        context = this;
        // 1. xu ly back
        back = findViewById(R.id.backHomeKho);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Zone_Shelf_VM zoneShelfVM = new Zone_Shelf_VM();
        collectionReferenceZone.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Duyệt qua tất cả các documents trong collection
                            for (DocumentSnapshot document : task.getResult()) {
                                String IdDocument = document.getId();
                                Zone zone = document.toObject(Zone.class);
                                Pair<String, Zone> zonePair = new Pair<>(IdDocument, zone);

                                zoneShelfVM.setZonePair(zonePair);
//                                Log.d(TAG, ": " + documentId);

                                List<Pair<String, Shelf>> shelfPairList = new ArrayList<>();
                                collectionReferenceShelf.get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    // Duyệt qua tất cả các documents trong collection
                                                    for (DocumentSnapshot document : task.getResult()) {
                                                        String IdDocument = document.getId();
                                                        Shelf shelf = document.toObject(Shelf.class);
                                                        Pair<String, Shelf> shelfPair = new Pair<>(IdDocument, shelf);
                                                        shelfPairList.add(shelfPair);
                                                        zoneShelfVM.setListShelfPair(shelfPairList);
                                                        list.add(zoneShelfVM);
                                                    }
                                                }
                                            }
                                        });

                            }


                        } else {
                            // Xử lý khi truy vấn không thành công
                            Exception exception = task.getException();
                            // TODO: Xử lý exception
                        }
                    }
                });
        ItemClick itemClick = new ItemClick() {
            @Override
            public void onClick(View v, int pos) {
                // Handle item click
            }
        };

        FuncPlaceAdapter funcPlaceAdapter = new FuncPlaceAdapter(list, context, itemClick );
        recyList.setLayoutManager(new LinearLayoutManager(FuncPlaceActivity.this));
        recyList.setAdapter(funcPlaceAdapter);



    }
}
