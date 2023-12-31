package com.example.projecthk1_2023_2024.NvKho;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projecthk1_2023_2024.NvKho.FuncNhapHang.Func_qlNhapHang1Activity;
import com.example.projecthk1_2023_2024.NvKho.FuncPlace.FuncPlaceActivity;
import com.example.projecthk1_2023_2024.NvKho.FuncQLSP.Func_QLSPActivity;
import com.example.projecthk1_2023_2024.NvKho.FuncXuatHang.Func_qlXuatHangActivity;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Admin.adapter.NotificationAdapter;
import com.example.projecthk1_2023_2024.Util.ListExpBatch;
import com.example.projecthk1_2023_2024.Util.ListExportBatch;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.example.projecthk1_2023_2024.Util.ListProductBatch;
import com.example.projecthk1_2023_2024.Util.ListShelfUtil;
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

import java.util.ArrayList;
import java.util.List;

public class NvkFragHome extends Fragment {
    static boolean isImgBack = false;
    TextView nameAccount;
    ImageView avtAcount;
    FrameLayout vtri, qlXuat, qlNhap, qlSP;
    RecyclerView recyNote;
    private User user;
    private List<Pair<String, Notification>> notificationList = new ArrayList<>();
    private List<Pair<String, ProductBatch>> productBatchList = new ArrayList<>();
    private List<Pair<String, Product>> productList = new ArrayList<>();
    private List<Pair<String, Export>> exportList = new ArrayList<>();
    private List<Pair<String, ImportBatch>> importList = new ArrayList<>();
    private List<Pair<String, ExpBatch>> expBatchList = new ArrayList<>();
    List<Pair<String, Shelf>> listShelf = new ArrayList<>();
    List<Pair<String, Zone>> listZone = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceNotification = db.collection("Notification");
    private CollectionReference collectionReferenceUser = db.collection("User");
    private CollectionReference collectionReferenceProductBatch = db.collection("ProductBatch");
    private CollectionReference collectionReferenceExport = db.collection("Export");
    private CollectionReference collectionReferenceImport = db.collection("ImportBatch");

    private CollectionReference collectionReferenceProduct = db.collection("Product");
    CollectionReference collectionReferenceZone = db.collection("Zone");
    CollectionReference collectionReferenceShelf = db.collection("Shelf");
    private CollectionReference collectionReferenceExportBatch = db.collection("ExpBatch");

    private FirebaseUser currentUser;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.nvkho_frag_home_layout, container, false);
        recyNote = view.findViewById(R.id.newNote);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        nameAccount = view.findViewById(R.id.nameAccountNV);
        avtAcount = view.findViewById(R.id.imgAvt);
        String loginId = currentUser.getUid();

// Xử lý hiển thị thông báo mới
        collectionReferenceNotification.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String IdDocument = document.getId();
                        Notification notification = document.toObject(Notification.class);
                        Pair<String, Notification> notificationPair = new Pair<>(IdDocument,notification);
                        if (notification.getEnable() == false) {//?? xử lý hiển thị đúng thông báo cho nhân viên cần thêm điều kiện
                            notificationList.add(notificationPair);
                        }
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyNote.setLayoutManager(layoutManager);

                    NotificationAdapter notificationAdapter = new NotificationAdapter(getActivity(), notificationList);
                    recyNote.setAdapter(notificationAdapter);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

// Xử lý hiển thị tên người dùng
        collectionReferenceUser.whereEqualTo("LoginID",loginId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(getActivity(), error.getMessage() + "Line 87", Toast.LENGTH_SHORT).show();
                        }
                        assert value != null;
                        if (!value.isEmpty()){
                            for(QueryDocumentSnapshot snapshot : value){
                                user = snapshot.toObject(User.class);
                                String name = snapshot.getString("Role");
                                nameAccount.setText(user.getUserName());
//                                Glide.with(getContext())
//                                        .load(user.getImage())
//                                        //.placeholder()
//                                        .fitCenter()
//                                        .into(avtAcount);

                            }
                        }
                    }
                });
        collectionReferenceProductBatch.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    String idDocument = queryDocumentSnapshot.getId();
                    ProductBatch productBatch = queryDocumentSnapshot.toObject(ProductBatch.class);
                    Pair<String, ProductBatch> productBatchPair = new Pair<>(idDocument,productBatch);
                    productBatchList.add(productBatchPair);
                }
                    ListProductBatch listProductBatch = ListProductBatch.getInstance();
                listProductBatch.setListProductBatch(productBatchList);
            }
        });
        collectionReferenceProduct.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    String idDocument = queryDocumentSnapshot.getId();
                    Product product = queryDocumentSnapshot.toObject(Product.class);
                    Pair<String, Product> productPair = new Pair<>(idDocument,product);
                    productList.add(productPair);
                }
                ProductList productListInstance = ProductList.getInstance();
                productListInstance.setProductList(productList);
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
        collectionReferenceShelf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    String idDocument = queryDocumentSnapshot.getId();
                    Shelf shelf = queryDocumentSnapshot.toObject(Shelf.class);
                    Pair<String,Shelf> shelfPair = new Pair<>(idDocument,shelf);
                    listShelf.add(shelfPair);
                }
                ListShelfUtil listShelfUtil = ListShelfUtil.getInstance();
                listShelfUtil.setPairList(listShelf);

            }
        });
        collectionReferenceZone.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    String idDoc = queryDocumentSnapshot.getId();
                    Log.d("IDZone",idDoc);
                    Zone zone = queryDocumentSnapshot.toObject(Zone.class);
                    Pair<String, Zone> zonePair = new Pair<>(idDoc,zone);
                    listZone.add(zonePair);
                }
                ListZoneUtil listZoneUtil = ListZoneUtil.getInstance();
                listZoneUtil.setPairList(listZone);
            }
        });
// 2. xử lý xem và sửa thông tin tài khoản cá nhân
        avtAcount = view.findViewById(R.id.imgAvt);
        avtAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AccountActivity.class);
                startActivity(i);
            }
        });
        // 3. Xử lý vị trí sản phẩm
        vtri = view.findViewById(R.id.func_viTriSP);
        vtri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FuncPlaceActivity.class);
                startActivity(i);
            }
        });


        //4. Xu ly xuat kho
        qlXuat = view.findViewById(R.id.func_qlXuat);
        qlXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Func_qlXuatHangActivity.class);
                startActivity(i);
                // bắt các sự kiện để xem tt detail
            }
        });


        // 5.xu ly nhap
        qlNhap = view.findViewById(R.id.func_qlNhap);
        qlNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Func_qlNhapHang1Activity.class);
                startActivity(i);
                // bắt các sự kiện để xem tt detail
            }
        });


        // 6. Quan ly san phaam
        qlSP = view.findViewById(R.id.func_qlsp);
        qlSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getContext(), Func_QLSPActivity.class);
                startActivity(i);
            }
        });
        return view;


    }

}
