package com.example.projecthk1_2023_2024.Admin.ExpActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.projecthk1_2023_2024.Admin.FragmentAdmin;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListExport;
import com.example.projecthk1_2023_2024.Util.ListUser;
import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.User;
import com.example.projecthk1_2023_2024.model.ViewModel.ExpADV_VM;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ListExpActivity extends AppCompatActivity implements ItemClick {
    RecyclerView recyclerView;
    CheckBox cbFalse, cbTrue;
    ImageButton btnAdd;
    ImageView back;
    SearchView search;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Export");
    private List<ExpADV_VM> listExpVM = new ArrayList<>();
    private List<Pair<String, Export>> expPairList = new ArrayList<>();
    ListExportAdapter expAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_admin);
        recyclerView = findViewById(R.id.recyclerView_export);
        cbFalse = findViewById(R.id.checkBox_exp);
        cbTrue = findViewById(R.id.checkBox_exp2);
        btnAdd = findViewById(R.id.btnAdd_exp);
        back = findViewById(R.id.back_import);
        search = findViewById(R.id.searchPX);
        ListUser userList = ListUser.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListExpActivity.this, FragmentAdmin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), NewImportActivity.class));
            }
        });

        Log.d("Size List User",""+userList.getListUser().size());
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String idDocument = documentSnapshot.getId();
                    Export exp = documentSnapshot.toObject(Export.class);
                    Pair<String, Export> expPair = new Pair<>(idDocument,exp);
                    expPairList.add(expPair);
                    Pair<String, User> userPair = userList.find(expPair.second.getIDUser_confirm().getId());
                    ExpADV_VM expVM = new ExpADV_VM(userPair,expPair);
                    listExpVM.add(expVM);
                }
                ListExport expList = ListExport.getInstance();
                expList.setListExport(expPairList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListExpActivity.this));
                expAdapter = new ListExportAdapter(ListExpActivity.this, listExpVM);
                Log.d("Tag mode", ""+expAdapter.getItemCount());
                expAdapter.setClickListener(ListExpActivity.this);
                recyclerView.setAdapter(expAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        //tìm kiếm

        search.clearFocus();
        recyclerView.setHasFixedSize(true);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText!=null){
                    filterList(newText);}
                return true;
            }

            private void filterList(String text) {
                if (text != null) {
                    List<ExpADV_VM> filtedList = new ArrayList<>();
                    String searchTextWithoutDiacritics = removeDiacritics(text.toLowerCase());
                    for (ExpADV_VM exp : listExpVM) {
                        if (removeDiacritics(exp.getExpPair().second.getStatus().toLowerCase()).contains(searchTextWithoutDiacritics)
                                ||
                                removeDiacritics(exp.getUserPair().second.getUserName().toLowerCase()).contains(searchTextWithoutDiacritics) ||
//
                                removeDiacritics(exp.getExpPair().first).contains(searchTextWithoutDiacritics)
                        ) {
                            filtedList.add(exp);
                        }

                    }
                    if (filtedList.isEmpty() == true && text.isEmpty() == false) {
                        expAdapter.setFilterList(ListExpActivity.this, new ArrayList<>());
                    } else {
                        expAdapter.setFilterList(ListExpActivity.this, filtedList);
                    }
                }
            }

            public String removeDiacritics(String input) {
                String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
                return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            }
        });

    }



    @Override
    public void onClick(View v, int pos) {
        Intent i = new Intent(ListExpActivity.this, DetailExpActivity.class);
        i.putExtra("IdExport",listExpVM.get(pos).getExpPair().first);
        startActivity(i);
    }
}