package com.example.projecthk1_2023_2024.NvKho.FuncXuatHang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListExportBatch;
import com.example.projecthk1_2023_2024.model.Export;

import java.util.List;

public class Func_qlXuatHangActivity extends AppCompatActivity {
    ListExportBatch listExportBatch = ListExportBatch.getInstance();
    RecyclerView recyclerView;
    ImageView back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nvkho_func2_qlxkho_layout);
        back = findViewById(R.id.back_home);
        recyclerView = findViewById(R.id.dspxMoi);
        List<Pair<String, Export>> list = listExportBatch.getProductList();
        Func_qlxh_Adapter adapter = new Func_qlxh_Adapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

}