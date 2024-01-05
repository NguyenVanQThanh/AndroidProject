package com.example.projecthk1_2023_2024.NvKho.FuncPlace;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.example.projecthk1_2023_2024.Util.ListProductBatch;
import com.example.projecthk1_2023_2024.Util.ListShelfUtil;
import com.example.projecthk1_2023_2024.Util.ListZoneUtil;
import com.example.projecthk1_2023_2024.Util.ProductList;
import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ProductBatch;
import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.ViewModel.ItemShelfVM;
import com.example.projecthk1_2023_2024.model.Zone;

import java.util.ArrayList;
import java.util.List;

public class DetailPlaceActivity extends AppCompatActivity implements ItemClick {
    TextView txtKho, avai;
    ImageView back;
    RecyclerView recyclerView;
    Button btnAdd, btnChange;
    ListShelfUtil listShelfUtil = ListShelfUtil.getInstance();
    ListZoneUtil listZoneUtil = ListZoneUtil.getInstance();
    List<ItemShelfVM> list = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nvkho_f1_1_select_item_layout);
        txtKho = findViewById(R.id.textKho);
        recyclerView = findViewById(R.id.listKhu);
        btnAdd = findViewById(R.id.btn_addPr);
        btnChange = findViewById(R.id.btn_full);
        avai = findViewById(R.id.textView16);
        back = findViewById(R.id.backHomeKho);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String idShelf = getIntent().getStringExtra("idShelf");
        String name = getIntent().getStringExtra("Name");
        String idZone = getIntent().getStringExtra("idZone");
        Pair<String, Zone> zonePair = listZoneUtil.find(idZone);
        Pair<String, Shelf> shelfPair = listShelfUtil.find(idShelf);
        ListProductBatch listProductBatch = ListProductBatch.getInstance();
        ProductList productList = ProductList.getInstance();
        for(Pair<String, ProductBatch> productBatchPair : listProductBatch.getProductList()){
            Log.d("Size ",""+((productBatchPair.second.getIDShelf()==null)?"False " + productBatchPair.first:"True"));

            if (productBatchPair.second.getIDShelf()!=null && productBatchPair.second.getIDShelf().getId().equals(idShelf)){
                for (Pair<String,Product> productPair : productList.getProductList()){
                    if (productPair.first.equals(productBatchPair.second.getIDProduct().getId())){
                        ItemShelfVM itemShelfVM = new ItemShelfVM(productPair,productBatchPair);
                        list.add(itemShelfVM);
                    }
                }

            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DetailPlaceAdapter detailPlaceAdapter = new DetailPlaceAdapter(getApplicationContext(),list);
        detailPlaceAdapter.setItemClick(this);
        recyclerView.setAdapter(detailPlaceAdapter);
        txtKho.setText(name);
        if (!shelfPair.second.isEnable()){
            avai.setText("Unavailable");
        }
    }

    @Override
    public void onClick(View v, int pos) {

    }
}
