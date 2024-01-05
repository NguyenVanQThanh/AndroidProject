package com.example.projecthk1_2023_2024.Admin.importactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.AdminHomeActivity;
import com.example.projecthk1_2023_2024.Admin.FragmentAdmin;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.example.projecthk1_2023_2024.Util.ListProductBatch;
import com.example.projecthk1_2023_2024.Util.ProductList;
import com.example.projecthk1_2023_2024.model.ExpBatch;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.Notification;
import com.example.projecthk1_2023_2024.model.ViewModel.AddImportVM;
import com.example.projecthk1_2023_2024.model.ViewModel.ItemAddImport;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddImportActivity extends AppCompatActivity implements ItemClick {

    AddImportVM addImportVM;
    TextView dateCreate, ncc;
    ImageButton imgAdd;
    Button btnSuccess;
    RecyclerView recyclerView;
    ImageView back;
    ListImportBatch listImportBatch = ListImportBatch.getInstance();
    ListProductBatch listProductBatch = ListProductBatch.getInstance();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_import);
        dateCreate = findViewById(R.id.date_import);
        ncc = findViewById(R.id.editText_supplier);
        recyclerView = findViewById(R.id.recyListSP);
        imgAdd = findViewById(R.id.imageButton_addin4);
        btnSuccess = findViewById(R.id.buttonAdd);
        back = findViewById(R.id.back_import);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddImportVM.instance = null;
                startActivity(new Intent(getApplicationContext(), FragmentAdmin.class));
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImportVM.setNcc(ncc.getText().toString());
                startActivity(new Intent(getApplicationContext(),AddItemImport.class));
            }
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateCurrent = simpleDateFormat.format(Timestamp.now().toDate());
        dateCreate.setText(dateCurrent);
        addImportVM = AddImportVM.getInstance();
//        List<ItemAddImport> imports = new ArrayList<>();
//        imports.add(new ItemAddImport(ProductList.instance.getProductList().get(0),"50000","50"));
//        addImportVM.setItemAddImportList(imports);
        Log.d("Existence VM",(addImportVM==null?"False":"True"));
        Log.d("Existence Total",""+addImportVM.getTotal());
//        Log.d("Existence VM",(addImportVM==null?"False":"True"));
        if (addImportVM != null){

            if(addImportVM.getNcc()!=null){
                ncc.setText(addImportVM.getNcc());
            }
            if (addImportVM.getItemAddImportList()!=null){
                List<ItemAddImport> listItems = addImportVM.getItemAddImportList();
                AddImportAdapter adapter = new AddImportAdapter(getApplicationContext(),listItems);
                adapter.setItemClick(AddImportActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                Log.d("Checkkk","Lot zo day roi : size " + adapter.getItemCount());
            }else {
                btnSuccess.setVisibility(View.GONE);
            }
        }else {
                        btnSuccess.setVisibility(View.GONE);

        }


        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ncc.getText().toString().trim()!=null) {
                    addImportVM.setNcc(ncc.getText().toString());
                    saveImport();
                    startActivity(new Intent(getApplicationContext(), FragmentAdmin.class));
                } else {
                    Toast.makeText(AddImportActivity.this, "Vui lòng điền nhà cung cấp", Toast.LENGTH_SHORT).show();
                }
            }

            private void saveImport() {
                Log.d("Check Status","Da vo");
//                ImportBatch importBatch = new ImportBatch(Timestamp.now(),true,Timestamp.now(),"Waiting",addImportVM.getTotal(), addImportVM.getNcc(), )
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                WriteBatch batch = db.batch();
                Map<String, Object> importData = new HashMap<>();
                importData.put("Date", Timestamp.now());
                importData.put("Enable", true);
                importData.put("Date_success", Timestamp.now());
                importData.put("Status", "Waiting");
                importData.put("Quantity_import",addImportVM.getTotal());
                importData.put("Supplier", addImportVM.getNcc());
//                importData.put("IDUser", userID);
                batch.set(db.collection("ImportBatch").document(String.valueOf(listImportBatch.getProductList().size()+1)),importData);



                int count = 1;
                for (ItemAddImport addImport : addImportVM.getItemAddImportList()) {
                    DocumentReference product = db.collection("Product").document(addImport.getProductPair().first);
                    DocumentReference batchRef = db.collection("ImportBatch").document(String.valueOf(listImportBatch.getProductList().size() + 1));

                    Calendar calendar = Calendar.getInstance();
                    Timestamp currentTimestamp = Timestamp.now();
                    calendar.setTimeInMillis(currentTimestamp.getSeconds() * 1000);
                    calendar.add(Calendar.YEAR, 1);
                    Timestamp newTimestamp = new Timestamp(calendar.getTime());

                    Map<String, Object> productBatchData = new HashMap<>();
                    productBatchData.put("Enable", false);
                    productBatchData.put("ExpiryDate", newTimestamp);
                    productBatchData.put("IDBatch", batchRef);
                    productBatchData.put("IDProduct", product);
                    productBatchData.put("ImportPrice", Integer.valueOf(addImport.getGia()));
                    productBatchData.put("Quantity", Integer.valueOf(addImport.getSLN()));
                    productBatchData.put("Quantity_Valid", Integer.valueOf(addImport.getSLN()));
                    productBatchData.put("Status", "Waiting");

                    batch.set(db.collection("ProductBatch").document(String.valueOf(listProductBatch.getProductList().size() + count)), productBatchData);
                    count++;
                }

                DocumentReference batchRefNot = db.collection("ImportBatch").document(String.valueOf(listImportBatch.getProductList().size() + 1));
                Map<String, Object> notificationData = new HashMap<>();
                notificationData.put("Date_Create", Timestamp.now());
                notificationData.put("Description", "Phiếu nhập "+ (listImportBatch.getProductList().size()+1) + " vừa được tạo");
                notificationData.put("Enable", true);
                notificationData.put("IDImport", batchRefNot);
                notificationData.put("Role", true);
                batch.set(db.collection("Notification").document(String.valueOf(listProductBatch.getProductList().size() + count)), notificationData);
                batch.commit()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Handle success
                                Toast.makeText(AddImportActivity.this, "Được rồi", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddImportActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }

    @Override
    public void onClick(View v, int pos) {
        List<ItemAddImport> itemAddImportList = addImportVM.getItemAddImportList();
        itemAddImportList.remove(pos);
        addImportVM.setItemAddImportList(itemAddImportList);
        recreate();
    }
}
