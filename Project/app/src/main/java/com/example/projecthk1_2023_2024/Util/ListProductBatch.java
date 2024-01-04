package com.example.projecthk1_2023_2024.Util;


import android.app.Application;
import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ProductBatch;

import java.util.ArrayList;
import java.util.List;

public class ListProductBatch extends Application {
    List<Pair<String, ProductBatch>> pbList;
    public static ListProductBatch instance;
    public static ListProductBatch getInstance(){
        if (instance == null){
            instance = new ListProductBatch();
        }
        return instance;
    }

    public ListProductBatch(List<Pair<String, ProductBatch>> pbList) {
        this.pbList = pbList;
    }

    public ListProductBatch() {
    }

    public List<Pair<String, ProductBatch>> getProductList() {
        return pbList;
    }

    public void setListProductBatch(List<Pair<String, ProductBatch>> pbList) {
        this.pbList = pbList;
    }
    public Pair<String,ProductBatch> find(String id){
        for(Pair<String, ProductBatch> productBatchPair : pbList){
            if (productBatchPair.first.equals(id)){
                return productBatchPair;
            }
        }
        return null;
    }
    public List<Pair<String,ProductBatch>> findByImportId(String id){
        List<Pair<String, ProductBatch>> results = new ArrayList<>();
        for(Pair<String, ProductBatch> productBatchPair : pbList){
            if (productBatchPair.second.getIDBatch().getId().equals(id)){
                results.add(productBatchPair);
            }
        }
        return results;
    }
}

