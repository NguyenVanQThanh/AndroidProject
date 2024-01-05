package com.example.projecthk1_2023_2024.Admin.importactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ProductList;
import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ViewModel.AddImportVM;
import com.example.projecthk1_2023_2024.model.ViewModel.ItemAddImport;

import java.util.ArrayList;
import java.util.List;

public class AddItemImport extends AppCompatActivity {


    TextView txtCode;
    Spinner spinner;
    EditText edtGia, edtSln;
    Button btnAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_product_add_imp);
        txtCode = findViewById(R.id.editText_masanpham);
        spinner = findViewById(R.id.spinner);
        edtGia = findViewById(R.id.edtGia);
        edtSln = findViewById(R.id.editText_soluongnhap);
        btnAdd= findViewById(R.id.btnAdd);

        AddImportVM addImportVM = AddImportVM.getInstance();
        ProductList productList = ProductList.getInstance();
        ArrayList<String> nameProduct = new ArrayList<>();
        for(Pair<String, Product> productPair : productList.getProductList()){
            nameProduct.add(productPair.second.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,nameProduct);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                for (Pair<String,Product> productPair : productList.getProductList()){
                    if (productPair.second.getName().equals(item)){
                        txtCode.setText(productPair.first);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gia = edtGia.getText().toString();
                String sln = edtSln.getText().toString();
                String code = txtCode.getText().toString();
                Pair<String,Product> productPair = productList.findProduct(code);
                ItemAddImport itemAddImport = new ItemAddImport(productPair,gia,sln);
                List<ItemAddImport> imports = addImportVM.getItemAddImportList();
                if (imports == null){
                    imports = new ArrayList<>();
                }
                boolean check = true;
                for(ItemAddImport addImport : imports){
                    if (addImport.getProductPair().first.equals(code)){
                        check = false;
                    }
                }
                if (check) {
                    imports.add(itemAddImport);
                    int count = addImportVM.getTotal() + Integer.valueOf(sln);
                    addImportVM.setTotal(count);
                    addImportVM.setItemAddImportList(imports);
                    startActivity(new Intent(getApplicationContext(), AddImportActivity.class));
                }else {
                    Toast.makeText(AddItemImport.this, "Đã tồn tại sản phẩm này!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
