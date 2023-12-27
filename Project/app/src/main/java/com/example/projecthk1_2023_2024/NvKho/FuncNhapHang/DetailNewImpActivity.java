package com.example.projecthk1_2023_2024.NvKho.FuncNhapHang;

import static android.content.ContentValues.TAG;

import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ProductBatch;
import com.example.projecthk1_2023_2024.model.ViewModel.Product_PB_VM;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailNewImpActivity extends AppCompatActivity{
    Context context;
    RecyclerView recyclerView;
    ImageView back;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productRef = db.collection("Product");
    CollectionReference productBatchRef = db.collection("ProductBatch");

//    private List<Map<String, Object>> listDataProduct = new ArrayList();
//    private List<Map<String, Object>> listDataProBatch = new ArrayList();
    private List<Product_PB_VM> listData = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nvk_f3_detail_imp_layout);
        recyclerView = findViewById(R.id.ctpn);
        back = findViewById(R.id.back32);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        String idBatch = null;
        if (intent != null) {
            idBatch = intent.getStringExtra("IdBatch");
            Log.d("DetailNewImpActivity", "IdBatch: " + idBatch);
        }
        else
            Log.d("Lỗi: ", "Không lấy đc idBatch");
        DocumentReference documentReference = db.collection("ImportBatch").document(idBatch);

        String finalIdBatch = idBatch;
        productBatchRef
                .whereEqualTo("IDBatch", documentReference)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Success");
                                // Lấy dữ liệu từ tài liệu chính
                                String documentId = document.getId();
                                ProductBatch productBatch = document.toObject(ProductBatch.class);
                                Log.d(TAG, "ProductBatchId: " + documentId);
                                Pair<String, ProductBatch> productBatchPair = new Pair<>(documentId, productBatch);
                                productBatch.getIDProduct().addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        String productId = value.getId();
                                        Product product = value.toObject(Product.class);
                                        Pair<String,Product> productPair = new Pair<>(productId,product);
                                        Product_PB_VM productPbVm = new Product_PB_VM();
                                        productPbVm.setProductPair(productPair);
                                        productPbVm.setIdBatch(finalIdBatch);
                                        productPbVm.setProductBatchPair(productBatchPair);
                                        listData.add(productPbVm);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(DetailNewImpActivity.this));
                                        DetailNewImpAdapter detailNewImpAdapter = new DetailNewImpAdapter(DetailNewImpActivity.this, listData);
                                        recyclerView.setAdapter(detailNewImpAdapter);
                                        recyclerView.getAdapter().notifyDataSetChanged();
                                    }
                                });
                            }

                        }else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        }




}
