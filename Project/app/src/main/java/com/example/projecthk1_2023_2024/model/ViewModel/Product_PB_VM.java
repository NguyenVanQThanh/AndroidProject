package com.example.projecthk1_2023_2024.model.ViewModel;

import android.util.Pair;

import com.example.projecthk1_2023_2024.Util.ListUser;
import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.ProductBatch;
import com.example.projecthk1_2023_2024.model.User;

import java.util.List;

public class Product_PB_VM {
    private List<Pair<Pair<String, Product>,Pair<String, ProductBatch>>> list;
    public static Product_PB_VM instance;
    public static Product_PB_VM getInstance(){
        if (instance == null){
            instance = new Product_PB_VM();
        }
        return instance;
    }

    public List<Pair<Pair<String, Product>, Pair<String, ProductBatch>>> getList() {
        return list;
    }

    public void setList(List<Pair<Pair<String, Product>, Pair<String, ProductBatch>>> list) {
        this.list = list;
    }


}
