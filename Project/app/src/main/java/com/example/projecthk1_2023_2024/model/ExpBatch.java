package com.example.projecthk1_2023_2024.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class ExpBatch {
    private boolean Enable;

    private DocumentReference IDProductBatch;
    private DocumentReference IDExport;
    private int Quantity;

    public ExpBatch(DocumentReference IDProductBatch, DocumentReference IDExport, int Quantity, boolean Enable) {
        this.IDProductBatch = IDProductBatch;
        this.IDExport = IDExport;
        this.Quantity = Quantity;
        this.Enable = Enable;
    }

    public ExpBatch() {
    }

    public boolean isEnable() {
        return Enable;
    }

    public void setEnable(boolean enable) {
        Enable = enable;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public DocumentReference getIDProductBatch() {
        return IDProductBatch;
    }

    public void setIDProductBatch(DocumentReference IDProductBatch) {
        this.IDProductBatch = IDProductBatch;
    }

    public DocumentReference getIDExport() {
        return IDExport;
    }

    public void setIDExport(DocumentReference IDExport){
        this.IDExport = IDExport;
    }
    public Product getProduct(DocumentReference IDProductBatch){
        final Product[] product = {new Product()};
        IDProductBatch.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    product[0] = documentSnapshot.toObject(Product.class);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return product[0];
    }
}
