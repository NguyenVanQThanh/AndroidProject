package com.example.projecthk1_2023_2024.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecthk1_2023_2024.Admin.activityuser.AddUserActivity;
import com.example.projecthk1_2023_2024.Admin.activityuser.UserAdminActivity;
import com.example.projecthk1_2023_2024.Admin.productactivity.DetailProductActivity;
import com.example.projecthk1_2023_2024.Admin.productactivity.ProductAdminActivity;
import com.example.projecthk1_2023_2024.CaptureAct;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ProductList;
import com.example.projecthk1_2023_2024.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanAddProduct extends AppCompatActivity {
    Button btnScan, btnAdd;
    EditText codeEDT,nameEDT;
    ProductList productList = ProductList.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_scan_product);
        btnScan = findViewById(R.id.btn_scan_find);
        btnAdd = findViewById(R.id.btn_Add);
        codeEDT = findViewById(R.id.edtCode);
        nameEDT = findViewById(R.id.nameProduct);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Pair<String, Product> productPairResult = productList.findProduct(codeEDT.getText().toString());
                if (productPairResult!=null){
                    Toast.makeText(ScanAddProduct.this, "Đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    Product product = new Product(nameEDT.toString(),"",0,0,0);
                    FirebaseFirestore.getInstance().collection("Product").document(codeEDT.getText().toString())
                            .set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"Success");
                                    startActivity(new Intent(ScanAddProduct.this, ProductAdminActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Failure");
                                }
                            });
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
//        if (result.getContents()!=null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(ScanProduct.this);
//            builder.setTitle("Result");
//            builder.setMessage(result.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).show();
//        }
        nameEDT.setText(result.getContents());
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
