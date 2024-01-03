package com.example.projecthk1_2023_2024.Util;

import android.app.Application;
import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.ImportBatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListExportBatch extends Application {
    List<Pair<String, Export>> expList;
    public static ListExportBatch instance;
    public static ListExportBatch getInstance(){
        if (instance == null){
            instance = new ListExportBatch();
        }
        return instance;
    }

    public ListExportBatch(List<Pair<String, Export>> expList) {
        this.expList = expList;
    }

    public ListExportBatch() {
    }

    public List<Pair<String, Export>> getProductList() {
        return expList;
    }

    public void setListExport(List<Pair<String, Export>> expList) {
        this.expList = expList;
    }
    public List<Integer> getCountForMonth(){
        List<Pair<String, Export>> list = ListExportBatch.getInstance().expList;
        List<Integer> total = new ArrayList<>();
        for(int i = 1;i<=12;i++) {
            int totalCount = 0;
            for (Pair<String, Export> importBatch : list) {
                Calendar date = Calendar.getInstance();
                date.setTime(importBatch.second.getCreate_Date().toDate());
                if (importBatch.second.isEnable() && date.get(Calendar.MONTH) + 1 == i) {
                    totalCount += importBatch.second.getQuantity();
                }
            }
            total.add(totalCount);
        }
        return total;
    }
}

