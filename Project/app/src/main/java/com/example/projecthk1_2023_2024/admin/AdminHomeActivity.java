package com.example.projecthk1_2023_2024.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthk1_2023_2024.Admin.ExpActivity.ListExpActivity;
import com.example.projecthk1_2023_2024.Admin.activityuser.UserAdminActivity;
import com.example.projecthk1_2023_2024.Admin.adapter.NotificationAdapter;
import com.example.projecthk1_2023_2024.Admin.clickhandler.ItemClick;
import com.example.projecthk1_2023_2024.Admin.importactivity.ImportHomeActivity;
import com.example.projecthk1_2023_2024.Admin.productactivity.ProductAdminActivity;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListExpBatch;
import com.example.projecthk1_2023_2024.Util.ListExportBatch;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.example.projecthk1_2023_2024.Util.ListNotification;
import com.example.projecthk1_2023_2024.Util.ListProductBatch;
import com.example.projecthk1_2023_2024.Util.ListShelfUtil;
import com.example.projecthk1_2023_2024.Util.ListUser;
import com.example.projecthk1_2023_2024.Util.ListZoneUtil;
import com.example.projecthk1_2023_2024.Util.ProductList;
import com.example.projecthk1_2023_2024.model.ExpBatch;
import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.Notification;
import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ProductBatch;
import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.User;
import com.example.projecthk1_2023_2024.model.Zone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends Fragment implements ItemClick {
    ImageView imgUser;
    TextView textView;
    ViewGroup nhanVien, nhapHang, xuatHang, product;
    RecyclerView recyclerView;
    private User user;
    private List<Pair<String, ExpBatch>> expBatchList = new ArrayList<>();

    private List<Pair<String, Product>> productList = new ArrayList<>();
    private List<Pair<String, Notification>> notificationList = new ArrayList<>();
    private List<Pair<String, Export>> exportList = new ArrayList<>();
    private List<Pair<String, ImportBatch>> importList = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceNotification = db.collection("Notification");
    private CollectionReference collectionReferenceUser = db.collection("User");
    private CollectionReference collectionReferenceProduct = db.collection("Product");
    private CollectionReference collectionReferenceProductIB = db.collection("ProductBatch");
    private CollectionReference collectionReferenceExport = db.collection("Export");
    private CollectionReference collectionReferenceImport = db.collection("ImportBatch");

    private CollectionReference collectionReferenceExportBatch = db.collection("ExpBatch");

    private FirebaseUser currentUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_home,container,false);
        nhanVien = view.findViewById(R.id.frameLayout1);
        nhapHang = view.findViewById(R.id.frameLayout3);
        xuatHang = view.findViewById(R.id.frameLayout4);
        product = view.findViewById(R.id.frameLayout2);
        recyclerView = view.findViewById(R.id.notification);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        textView = view.findViewById(R.id.nameAccount);
        imgUser = view.findViewById(R.id.imageView2);
        String loginId = currentUser.getUid();
        collectionReferenceNotification.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String IdDocument = document.getId();
                        Notification notification = document.toObject(Notification.class);
                        Pair<String, Notification> notificationPair = new Pair<>(IdDocument,notification);
                        if (notification.getEnable() == false) {
                            notificationList.add(notificationPair);
                        }
                    }
                    ListNotification listNotification = ListNotification.getInstance();
                    listNotification.setNotificationList(notificationList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new NotificationAdapter(getActivity(), notificationList));
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        collectionReferenceExport.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    String idDocument = queryDocumentSnapshot.getId();
                    Export export = queryDocumentSnapshot.toObject(Export.class);
                    Pair<String, Export> exportPair = new Pair<>(idDocument,export);
                    exportList.add(exportPair);
                }
                ListExportBatch list = ListExportBatch.getInstance();
                list.setListExport(exportList);
            }
        });

        collectionReferenceImport.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    String idDocument = queryDocumentSnapshot.getId();
                    ImportBatch importBatch = queryDocumentSnapshot.toObject(ImportBatch.class);
                    Pair<String, ImportBatch> importBatchPair = new Pair<>(idDocument,importBatch);
                    importList.add(importBatchPair);
                }
                ListImportBatch list = ListImportBatch.getInstance();
                list.setListImportBatch(importList);
            }
        });
        collectionReferenceUser.whereEqualTo("LoginID",loginId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(getActivity(), error.getMessage() + "Line 97", Toast.LENGTH_SHORT).show();
                        }
                        assert value != null;
                        if (!value.isEmpty()){
                            for(QueryDocumentSnapshot snapshot : value){
                                user = snapshot.toObject(User.class);
                                String name = snapshot.getString("Role");
                                textView.setText(user.getUserName());

                            }
                        }
                    }
                });
        List<Pair<String, User>> listUser = new ArrayList<>();
        collectionReferenceUser.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String IdDocument = document.getId();
                        User user = document.toObject(User.class);
                        Pair<String, User> userPair = new Pair<>(IdDocument,user);
                        listUser.add(userPair);
                    }
//                    Log.d(TAG, String.valueOf(listUser.size()),task.getException());
                    ListUser listUserInstance = ListUser.getInstance();
                    listUserInstance.setListUser(listUser);
                    Log.d("User Count",""+listUserInstance.getListUser().size());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        collectionReferenceProduct.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String idDocument = documentSnapshot.getId();
                    Product product = documentSnapshot.toObject(Product.class);
                    Pair<String, Product> productPair = new Pair<>(idDocument,product);
                    productList.add(productPair);
                }
                ProductList productListInstance = ProductList.getInstance();
                productListInstance.setProductList(productList);
            }
        });
        List<Pair<String, ProductBatch>> productBatchList = new ArrayList<>();
        collectionReferenceProductIB.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String idDocument = documentSnapshot.getId();
                    ProductBatch product = documentSnapshot.toObject(ProductBatch.class);
                    Pair<String, ProductBatch> productPair = new Pair<>(idDocument,product);
                    productBatchList.add(productPair);
                }
                ListProductBatch productListInstance = ListProductBatch.getInstance();
                productListInstance.setListProductBatch(productBatchList);
            }
        });
        collectionReferenceExportBatch.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    String idDocument = queryDocumentSnapshot.getId();
                    ExpBatch export = queryDocumentSnapshot.toObject(ExpBatch.class);
                    Pair<String, ExpBatch> exportPair = new Pair<>(idDocument,export);
                    expBatchList.add(exportPair);
                }
                ListExpBatch list = ListExpBatch.getInstance();
                list.setListExpBatch(expBatchList);
            }
        });

        nhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserAdminActivity.class));
            }
        });
        nhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ImportHomeActivity.class));

            }
        });
        xuatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ListExpActivity.class));

            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProductAdminActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onClick(View v, int pos) {

    }
}
