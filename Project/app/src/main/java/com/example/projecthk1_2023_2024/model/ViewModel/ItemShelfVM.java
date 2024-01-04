package com.example.projecthk1_2023_2024.model.ViewModel;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ProductBatch;

import java.util.List;

public class ItemShelfVM {
    private Pair<String, Product> productPair;
    private Pair<String, ProductBatch> productBatchPair;

    public ItemShelfVM() {
    }

    public ItemShelfVM(Pair<String, Product> productPair, Pair<String, ProductBatch> productBatchPair) {
        this.productPair = productPair;
        this.productBatchPair = productBatchPair;
    }

    public Pair<String, Product> getProductPair() {
        return productPair;
    }

    public void setProductPair(Pair<String, Product> productPair) {
        this.productPair = productPair;
    }

    public Pair<String, ProductBatch> getProductBatchPair() {
        return productBatchPair;
    }

    public void setProductBatchPair(Pair<String, ProductBatch> productBatchPair) {
        this.productBatchPair = productBatchPair;
    }
}
