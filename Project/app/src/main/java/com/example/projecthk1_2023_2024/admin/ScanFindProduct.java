package com.example.projecthk1_2023_2024.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecthk1_2023_2024.Admin.productactivity.DetailProductActivity;
import com.example.projecthk1_2023_2024.CaptureAct;
import com.example.projecthk1_2023_2024.R;

import com.example.projecthk1_2023_2024.Util.ProductList;
import com.example.projecthk1_2023_2024.model.Product;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanFindProduct extends AppCompatActivity {
    Button btnScan, btnFind;
    EditText codeEDT;
    ProductList productList = ProductList.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_scan_product);
        btnScan = findViewById(R.id.btn_scan_find);
        btnFind = findViewById(R.id.btn_Find);
        codeEDT = findViewById(R.id.edtCode);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
        btnFind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Pair<String, Product> productPairResult = productList.findProduct(codeEDT.getText().toString());
                if (productPairResult==null){
                    Toast.makeText(ScanFindProduct.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(ScanFindProduct.this, DetailProductActivity.class);
                    i.putExtra("IdProduct",productPairResult.first);
                    i.putExtra("nameProduct",productPairResult.second.getName());
                    startActivity(i);
                }
            }
        });
        
    }
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan BarCode");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents()!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ScanFindProduct.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            codeEDT.setText(result.getContents());
        }
//        String idCode = result.getContents();
//
//        Pair<String, Product> productPair = productList.findProduct(idCode);
//        if (productPair!= null){
//            Intent i = new Intent(ScanFindProduct.this, DetailProductActivity.class);
//            i.putExtra("IdProduct",idCode);
//            i.putExtra("nameProduct",productPair.second.getName());
//            startActivity(i);
//        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
