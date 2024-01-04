package com.example.projecthk1_2023_2024.Util;


import android.app.Application;
import android.util.Pair;

import com.example.projecthk1_2023_2024.model.ExpBatch;

import java.util.ArrayList;
import java.util.List;

public class ListExpBatch extends Application {
    List<Pair<String, ExpBatch>> pbList;
    public static ListExpBatch instance;
    public static ListExpBatch getInstance(){
        if (instance == null){
            instance = new ListExpBatch();
        }
        return instance;
    }

    public ListExpBatch(List<Pair<String, ExpBatch>> pbList) {
        this.pbList = pbList;
    }

    public ListExpBatch() {
    }

    public List<Pair<String, ExpBatch>> getProductList() {
        return pbList;
    }

    public void setListExpBatch(List<Pair<String, ExpBatch>> pbList) {
        this.pbList = pbList;
    }
    public List<Pair<String,ExpBatch>> findByExportId(String id){
        List<Pair<String, ExpBatch>> results = new ArrayList<>();
        for(Pair<String, ExpBatch> ExpBatchPair : pbList){
            if (ExpBatchPair.second.getIDExport().getId().equals(id)){
                results.add(ExpBatchPair);
            }
        }
        return results;
    }
}

