package com.example.projecthk1_2023_2024.Util;

import android.app.Application;
import android.util.Pair;

import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListImportBatch extends Application {
    List<Pair<String, ImportBatch>> impList;
    public static ListImportBatch instance;
    public static ListImportBatch getInstance(){
        if (instance == null){
            instance = new ListImportBatch();
        }
        return instance;
    }

    public ListImportBatch(List<Pair<String, ImportBatch>> impList) {
        this.impList = impList;
    }

    public ListImportBatch() {
    }

    public List<Pair<String, ImportBatch>> getProductList() {
        return impList;
    }

    public void setListImportBatch(List<Pair<String, ImportBatch>> impList) {
        this.impList = impList;
    }
    public Pair<String, ImportBatch> find(String id){
        for (Pair<String, ImportBatch> importBatchPair : impList ){
            if (importBatchPair.first.equals(id)){
                return importBatchPair;
            }
        }
        return null;
    }
    public List<Integer> getCountForMonth(){
        List<Pair<String, ImportBatch>>importBatchList = ListImportBatch.getInstance().impList;
        List<Integer> total = new ArrayList<>();
        for(int i = 1;i<=12;i++) {
            int totalCount = 0;
            for (Pair<String, ImportBatch> importBatch : importBatchList) {
                Calendar date = Calendar.getInstance();
                date.setTime(importBatch.second.getDate().toDate());
                if (importBatch.second.isEnable() && date.get(Calendar.MONTH) + 1 == i) {
                    totalCount += importBatch.second.getQuantity_import();
                }
            }
            total.add(totalCount);
        }
        return total;
    }
}

