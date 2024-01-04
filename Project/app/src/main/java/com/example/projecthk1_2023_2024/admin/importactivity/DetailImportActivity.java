package com.example.projecthk1_2023_2024.Admin.importactivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.adapter.DetailImportAdapter;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.example.projecthk1_2023_2024.Util.ListProductBatch;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.ProductBatch;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetailImportActivity extends AppCompatActivity {
    TextView idImport, ncc, dateCreated, dateSuccess, status, total;
    ImageView back;
    RecyclerView recyclerView;
    ListImportBatch listImportBatch = ListImportBatch.getInstance();
    ListProductBatch listProductBatch = ListProductBatch.getInstance();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_detail);
        idImport = findViewById(R.id.ID_import);
        ncc = findViewById(R.id.ncc_name);
        dateCreated = findViewById(R.id.date);
        dateSuccess = findViewById(R.id.datesuccess);
        status = findViewById(R.id.tinhtrang_import_HT2);
        total = findViewById(R.id.total);
        recyclerView = findViewById(R.id.recycler_view);
        String idImp = getIntent().getStringExtra("IdImport");
        Log.d("Id",idImp);
        Log.d("Size","" +listImportBatch.getProductList().size());
        Pair<String, ImportBatch> importBatchPair = listImportBatch.find(idImp);
        Log.d("Null", "onCreate: "+ (importBatchPair.second.getDate_success() == null ? "False" : importBatchPair.first));
        List<Pair<String, ProductBatch>> list = listProductBatch.findByImportId(idImp);
        Log.d("Size","" +list.size());

        idImport.setText(importBatchPair.first);
        ncc.setText(importBatchPair.second.getSupplier());
        dateCreated.setText(importBatchPair.second.StampToString(importBatchPair.second.getDate()));
        dateSuccess.setText(importBatchPair.second.StampToString(importBatchPair.second.getDate_success()));
        if (importBatchPair.second.isEnable()){
            status.setText("Nhận");
            status.setTextColor(R.color.green);
        } else {
            status.setText("Không nhận");
            status.setTextColor(R.color.red);
        }
        total.setText(String.valueOf(importBatchPair.second.getQuantity_import()));
        sortProductList(list);
        DetailImportAdapter detailImportAdapter = new DetailImportAdapter(DetailImportActivity.this,list);
        Log.d("List", ""+detailImportAdapter.getItemCount());
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailImportActivity.this));
        recyclerView.setAdapter(detailImportAdapter);
//        recyclerView.getAdapter().notifyDataSetChanged();

    }
    private void sortProductList(List<Pair<String,ProductBatch>> productList) {
        // Sử dụng Comparator để so sánh theo thuộc tính Enable
        Comparator<Pair<String,ProductBatch>> comparator = new Comparator<Pair<String,ProductBatch>>() {
            @Override
            public int compare(Pair<String,ProductBatch> product1, Pair<String,ProductBatch> product2) {
                // So sánh theo thuộc tính Enable
                return Boolean.compare(product2.second.isEnable(), product1.second.isEnable());
            }
        };

        // Sắp xếp danh sách sử dụng Comparator
        Collections.sort(productList, comparator);
    }
}
