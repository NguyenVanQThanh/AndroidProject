package com.example.projecthk1_2023_2024.Util;

import android.app.Application;
import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.ImportBatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListExport extends Application {
    List<Pair<String, Export>> expList;
    public static ListExport instance;
    public static ListExport getInstance(){
        if (instance == null){
            instance = new ListExport();
        }
        return instance;
    }

    public ListExport(List<Pair<String, Export>> expList) {
        this.expList = expList;
    }

    public ListExport() {
    }

    public List<Pair<String, Export>> getProductList() {
        return expList;
    }

    public void setListExport(List<Pair<String, Export>> expList) {
        this.expList = expList;
    }
    public Pair<String, Export> find(String id){
        for (Pair<String, Export> exportPair : expList ){
            if (exportPair.first.equals(id)){
                return exportPair;
            }
        }
        return null;
    }
//    public List<Integer> getCountForMonth(){
//        List<Pair<String, Export>>exportList = ListExport.getInstance().expList;
//        List<Integer> total = new ArrayList<>();
//        for(int i = 1;i<=12;i++) {
//            int totalCount = 0;
//            for (Pair<String, Export> exp : exportList) {
//                Calendar date = Calendar.getInstance();
//                date.setTime(exp.second.getDate().toDate());
//                if (exp.second.isEnable() && date.get(Calendar.MONTH) + 1 == i) {
//                    totalCount += exp.second.getQuantity_import();
//                }
//            }
//            total.add(totalCount);
//        }
//        return total;
//    }
}
