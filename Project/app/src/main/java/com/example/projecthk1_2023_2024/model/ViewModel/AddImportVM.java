package com.example.projecthk1_2023_2024.model.ViewModel;

import com.example.projecthk1_2023_2024.Util.ListProductBatch;

import java.util.List;

public class AddImportVM {
    String ncc;
    List<ItemAddImport> itemAddImportList;
    int total;
    public static AddImportVM instance;
    public static AddImportVM getInstance(){
        if (instance==null){
            instance = new AddImportVM();
            instance.setTotal(0);
        }
        return instance;
    }

    public String getNcc() {
        return ncc;
    }

    public void setNcc(String ncc) {
        this.ncc = ncc;
    }

    public List<ItemAddImport> getItemAddImportList() {
        return itemAddImportList;
    }

    public void setItemAddImportList(List<ItemAddImport> itemAddImportList) {
        this.itemAddImportList = itemAddImportList;
    }

    public static void setInstance(AddImportVM instance) {
        AddImportVM.instance = instance;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
