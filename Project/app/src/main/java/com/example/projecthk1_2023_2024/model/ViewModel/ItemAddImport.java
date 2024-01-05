package com.example.projecthk1_2023_2024.model.ViewModel;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Product;

public class ItemAddImport {
    private Pair<String, Product> productPair;
    private String Gia;
    private String SLN;


    public ItemAddImport(Pair<String, Product> productPair, String gia, String SLN) {
        this.productPair = productPair;
        Gia = gia;
        this.SLN = SLN;
    }

    public Pair<String, Product> getProductPair() {
        return productPair;
    }

    public void setProductPair(Pair<String, Product> productPair) {
        this.productPair = productPair;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getSLN() {
        return SLN;
    }

    public void setSLN(String SLN) {
        this.SLN = SLN;
    }
}
