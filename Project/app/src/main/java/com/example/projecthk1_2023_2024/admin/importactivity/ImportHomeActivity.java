package com.example.projecthk1_2023_2024.Admin.importactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.AdminHomeActivity;
import com.example.projecthk1_2023_2024.Admin.FragmentAdmin;
import com.example.projecthk1_2023_2024.Admin.adapter.ImportAdapter;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.Admin.productactivity.ProductAdminActivity;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.example.projecthk1_2023_2024.Util.ListUser;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.User;
import com.example.projecthk1_2023_2024.model.ViewModel.ImportADViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ImportHomeActivity extends AppCompatActivity implements ItemClick {
    RecyclerView recyclerView;
    CheckBox cbFalse, cbTrue;
    ImageButton btnAdd;
    ImageView back;
    SearchView search;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("ImportBatch");
    private List<ImportADViewModel> listImport = new ArrayList<>();
    private List<Pair<String, ImportBatch>> importList = new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_admin);
        recyclerView = findViewById(R.id.recyclerView_import);
        cbFalse = findViewById(R.id.checkBox_import);
        cbTrue = findViewById(R.id.checkBox_import2);
        btnAdd = findViewById(R.id.btnAdd_import);
        back = findViewById(R.id.back_import);
        ListUser userList = ListUser.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportHomeActivity.this, FragmentAdmin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewImportActivity.class));
            }
        });

        Log.d("Size List User",""+userList.getListUser().size());
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String idDocument = documentSnapshot.getId();
                    ImportBatch importBatch = documentSnapshot.toObject(ImportBatch.class);
                    Pair<String, ImportBatch> importBatchPair = new Pair<>(idDocument,importBatch);
                    importList.add(importBatchPair);
                    Pair<String, User> userPair = userList.find(importBatchPair.second.getIDUser().getId());
                    ImportADViewModel importADViewModel = new ImportADViewModel(userPair,importBatchPair);
                    listImport.add(importADViewModel);
                }
                ListImportBatch importBatchList = ListImportBatch.getInstance();
                importBatchList.setListImportBatch(importList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ImportHomeActivity.this));
                ImportAdapter importAdapter = new ImportAdapter(ImportHomeActivity.this, listImport);
                Log.d("Tag mode", ""+importAdapter.getItemCount());
                importAdapter.setClickListener(ImportHomeActivity.this);
                recyclerView.setAdapter(importAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v, int pos) {
        Intent i = new Intent(ImportHomeActivity.this, DetailImportActivity.class);
        i.putExtra("IdImport",listImport.get(pos).getImportBatchPair().first);
        startActivity(i);
    }
}
