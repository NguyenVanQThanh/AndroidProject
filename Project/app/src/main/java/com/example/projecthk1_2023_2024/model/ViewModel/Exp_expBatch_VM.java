package com.example.projecthk1_2023_2024.model.ViewModel;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.ExpBatch;
import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.ProductBatch;
import com.example.projecthk1_2023_2024.model.User;

public class Exp_expBatch_VM {
    private Pair<String, ExpBatch> expBatchPair;
    private Pair<String, Export> expPair;
    private Pair<String, ProductBatch> productBatchPair;

    public Exp_expBatch_VM() {
    }

    public Exp_expBatch_VM(Pair<String, ExpBatch> expBatchPair, Pair<String, Export> expPair, Pair<String, ProductBatch> productBatchPair) {
        this.expBatchPair = expBatchPair;
        this.expPair = expPair;
        this.productBatchPair = productBatchPair;
    }

    public Pair<String, ExpBatch> getExpBatchPair() {
        return expBatchPair;
    }

    public void setExpBatchPair(Pair<String, ExpBatch> expBatchPair) {
        this.expBatchPair = expBatchPair;
    }

    public Pair<String, Export> getExpPair() {
        return expPair;
    }

    public void setExpPair(Pair<String, Export> expPair) {
        this.expPair = expPair;
    }

    public Pair<String, ProductBatch> getProductBatchPair() {
        return productBatchPair;
    }

    public void setProductBatchPair(Pair<String, ProductBatch> productBatchPair) {
        this.productBatchPair = productBatchPair;
    }
}
