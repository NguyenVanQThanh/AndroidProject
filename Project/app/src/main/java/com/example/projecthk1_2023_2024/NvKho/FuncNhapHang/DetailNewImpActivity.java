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

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClickStatus;
import com.example.projecthk1_2023_2024.NvKho.NvkActivity;
import com.example.projecthk1_2023_2024.Util.ListProductBatch;
import com.example.projecthk1_2023_2024.Util.ProductList;
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

public class DetailNewImpActivity extends AppCompatActivity implements ItemClickStatus {
    Context context;
    RecyclerView recyclerView;
    ImageView back;

    List<Pair<Pair<String,Product>, Pair<String, ProductBatch>>> list = new ArrayList<>();
    ListProductBatch listProductBatch = ListProductBatch.getInstance();
    ProductList productList = ProductList.getInstance();
//    List<Pair<String, Product>> listProduct = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productRef = db.collection("Product");
    CollectionReference productBatchRef = db.collection("ProductBatch");

//    private List<Map<String, Object>> listDataProduct = new ArrayList();
//    private List<Map<String, Object>> listDataProBatch = new ArrayList();
    private List<Product_PB_VM> listData = new ArrayList<Product_PB_VM>();

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
//        productBatchRef
//                .whereEqualTo("IDBatch", documentReference)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, "Success");
//                                // Lấy dữ liệu từ tài liệu chính
//                                String documentId = document.getId();
//                                ProductBatch productBatch = document.toObject(ProductBatch.class);
//                                Log.d(TAG, "ProductBatchId: " + documentId);
//                                Pair<String, ProductBatch> productBatchPair = new Pair<>(documentId, productBatch);
//                                productBatch.getIDProduct().addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                                        String productId = value.getId();
//                                        Product product = value.toObject(Product.class);
//                                        Pair<String,Product> productPair = new Pair<>(productId,product);
//                                        Product_PB_VM productPbVm = new Product_PB_VM();
//                                        productPbVm.setProductPair(productPair);
//                                        productPbVm.setIdBatch(finalIdBatch);
//                                        productPbVm.setProductBatchPair(productBatchPair);
//                                        listData.add(productPbVm);
//                                        recyclerView.setLayoutManager(new LinearLayoutManager(DetailNewImpActivity.this));
//                                        DetailNewImpAdapter detailNewImpAdapter = new DetailNewImpAdapter(DetailNewImpActivity.this, listData);
//                                        recyclerView.setAdapter(detailNewImpAdapter);
//                                        recyclerView.getAdapter().notifyDataSetChanged();
//                                    }
//                                });
//                            }
//
//                        }else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
        for (Pair<String,ProductBatch> productBatch : listProductBatch.getProductList()){
            if (productBatch.second.getIDBatch().getId().equals(idBatch)){
                for (Pair<String, Product> productPair : productList.getProductList()){
                    if (productBatch.second.getIDProduct().getId().equals(productPair.first)){
                        Pair<Pair<String,Product>, Pair<String,ProductBatch>> pair = new Pair<>(productPair,productBatch);
                        list.add(pair);
                    }
                }
            }
        }
        Product_PB_VM productPbVm = Product_PB_VM.getInstance();
        productPbVm.setList(list);
        DetailNewImpAdapter detailNewImpAdapter = new DetailNewImpAdapter(getApplicationContext(),list);
        detailNewImpAdapter.setItemClickStatus(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailNewImpActivity.this));
        recyclerView.setAdapter(detailNewImpAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        }


    @Override
    public void onClick(View v, int pos, boolean status) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Pair<String,ProductBatch> productBatchPair =  list.get(pos).second;
        Map<String, Object> updates = new HashMap<>();
        if (status){
            updates.put("Status", "Success");
            updates.put("Enable", true);
        }else {
            updates.put("Status", "Cancelled");
            updates.put("Enable", false);
        }
        db.collection("ProductBatch").document(productBatchPair.first)
                .update(updates).addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Cập nhật thành công");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Cập nhật thất bại", e);
                });
        if(list.size()==1){
            Map<String, Object> udt = new HashMap<>();
            if (status){
                updates.put("Status", "Success");
                updates.put("Enable", true);
            } else {
                updates.put("Status", "Cancelled");
                updates.put("Enable", false);
            }
        productBatchPair.second.getIDBatch().update(udt).addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {

                });
        }
        startActivity(new Intent(getApplicationContext(), NvkActivity.class));
    }
}
