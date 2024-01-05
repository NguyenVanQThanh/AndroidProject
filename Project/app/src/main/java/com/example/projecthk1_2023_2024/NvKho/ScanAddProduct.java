package com.example.projecthk1_2023_2024.NvKho;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecthk1_2023_2024.Admin.productactivity.ProductAdminActivity;
import com.example.projecthk1_2023_2024.CaptureAct;
import com.example.projecthk1_2023_2024.NvKho.FuncQLSP.Func_QLSPActivity;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ProductList;
import com.example.projecthk1_2023_2024.model.Product;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;

public class ScanAddProduct extends AppCompatActivity {
    Button btnScan, btnAdd, btnImg;
    EditText codeEDT,nameEDT;
    ImageView imageView;
    Uri uri;
    ProductList productList = ProductList.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_scan_product);
        btnScan = findViewById(R.id.btn_scan_find);
        btnAdd = findViewById(R.id.btn_Add);
        codeEDT = findViewById(R.id.edtCode);
        nameEDT = findViewById(R.id.nameProduct);
        btnImg = findViewById(R.id.btn_add_img);
        imageView = findViewById(R.id.imgVLoadSP);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<String, Product> productPairResult = productList.findProduct(codeEDT.getText().toString());
                if (productPairResult != null) {
                    Toast.makeText(ScanAddProduct.this, "Đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    WriteBatch batch = db.batch();

                    if (uri != null) {
                        String imageName = "product/" + nameEDT.getText().toString().trim() + ".jpg";
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imageName);

                        UploadTask uploadTask = storageRef.putFile(uri);
                        uploadTask.addOnSuccessListener(taskSnapshot -> {
                            // Nếu tải lên ảnh thành công, lấy đường link của ảnh
                            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                // Cập nhật đường link vào đối tượng Product
//                                Product product = new Product(nameEDT.getText().toString(), imageUrl, 0, 0, 0);
                                Map<String,Object> productData = new HashMap<>();
                                productData.put("Name",nameEDT.getText().toString());
                                productData.put("Link_Photo",imageUrl);
                                productData.put("Quantity",0);
                                productData.put("Quantity_Valid",0);
                                productData.put("Quantity_Stock",0);
                                // Lưu đối tượng Product vào Firestore
                                db.collection("Product").document(codeEDT.getText().toString())
                                        .set(productData)
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d(TAG, "Success");
                                            startActivity(new Intent(ScanAddProduct.this, Func_QLSPActivity.class));
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.d(TAG, "Failure: " + e.getMessage());
                                        });
                            });
                        }).addOnFailureListener(e -> {
                            Log.d(TAG, "Image upload failed: " + e.getMessage());
                        });
                    } else {
                        // Xử lý khi không có ảnh
//                        Product product = new Product(nameEDT.getText().toString(), "", 0, 0, 0);
                        Map<String,Object> productData = new HashMap<>();
                        productData.put("Name",nameEDT.getText().toString());
                        productData.put("Link_Photo","");
                        productData.put("Quantity",0);
                        productData.put("Quantity_Valid",0);
                        productData.put("Quantity_Stock",0);
                        db.collection("Product").document(codeEDT.getText().toString())
                                .set(productData)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Success");
                                    startActivity(new Intent(ScanAddProduct.this, Func_QLSPActivity.class));
                                })
                                .addOnFailureListener(e -> {
                                    Log.d(TAG, "Failure: " + e.getMessage());
                                });
                    }

                }
            }
        });

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ScanAddProduct.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
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
        codeEDT.setText(result.getContents());
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }
}
