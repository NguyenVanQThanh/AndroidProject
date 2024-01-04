package com.example.projecthk1_2023_2024.Admin.ExpActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListExpBatch;
import com.example.projecthk1_2023_2024.Util.ListExport;
import com.example.projecthk1_2023_2024.Util.ListExportBatch;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.example.projecthk1_2023_2024.Util.ListProductBatch;
import com.example.projecthk1_2023_2024.model.ExpBatch;
import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.ProductBatch;
import com.example.projecthk1_2023_2024.model.ViewModel.Exp_expBatch_VM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetailExpActivity extends AppCompatActivity {
    TextView idExport, dateCreated, dateSuccess, status, total;
    ImageView back;
    RecyclerView recyclerView;
    ListExport listExport = ListExport.getInstance();
    ListExpBatch listExpBatch = ListExpBatch.getInstance();
    ListProductBatch listProductBatch = ListProductBatch.getInstance();
    List<Exp_expBatch_VM> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_detail);
        idExport = findViewById(R.id.ID_ex);
        dateCreated = findViewById(R.id.ngaytaophieu);
        dateSuccess = findViewById(R.id.date_export);
        status = findViewById(R.id.tinhtrang_export_HT);
        total = findViewById(R.id.total_export);
        recyclerView = findViewById(R.id.recycler_view);
        back = findViewById(R.id.back_export);
        String idExp = getIntent().getStringExtra("IdExport");
        Pair<String, Export> exportPair = listExport.find(idExp);
        Log.d("Size",""+(listExpBatch.getProductList()==null?"False":"True"));
        List<Pair<String,ExpBatch>> expBatchList = listExpBatch.findByExportId(idExp);
        sortProductList(expBatchList);
        for (Pair<String,ExpBatch> expBatchPair : expBatchList){
            Log.d("Check",expBatchPair.second.getQuantity()+ "");
            Pair<String, ProductBatch> productBatchPair = listProductBatch.find(expBatchPair.second.getIDProductBatch().getId());
            Exp_expBatch_VM expExpBatchVm = new Exp_expBatch_VM(expBatchPair,exportPair,productBatchPair);
            list.add(expExpBatchVm);
        }
        idExport.setText(exportPair.first);
        dateCreated.setText(exportPair.second.StampToString(exportPair.second.getCreate_Date()));
        dateSuccess.setText(exportPair.second.StampToString(exportPair.second.getDate_Success()));
        if (exportPair.second.isEnable()){
            status.setText("Nhận");
            status.setTextColor(Color.parseColor("#50d189"));
        } else {
            status.setText("Không nhận");
            status.setTextColor(Color.parseColor("#a63c39"));
        }
        total.setText(String.valueOf(exportPair.second.getQuantity()));
        DetailExpAdapter detailImportAdapter = new DetailExpAdapter(DetailExpActivity.this,list);
        Log.d("List", ""+detailImportAdapter.getItemCount());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(detailImportAdapter);
//        recyclerView.getAdapter().notifyDataSetChanged();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void sortProductList(List<Pair<String,ExpBatch>> productList) {
        // Sử dụng Comparator để so sánh theo thuộc tính Enable
        Comparator<Pair<String,ExpBatch>> comparator = new Comparator<Pair<String,ExpBatch>>() {
            @Override
            public int compare(Pair<String,ExpBatch> product1, Pair<String,ExpBatch> product2) {
                // So sánh theo thuộc tính Enable
                return Boolean.compare(product2.second.isEnable(), product1.second.isEnable());
            }
        };

        // Sắp xếp danh sách sử dụng Comparator
        Collections.sort(productList, comparator);
    }
}
