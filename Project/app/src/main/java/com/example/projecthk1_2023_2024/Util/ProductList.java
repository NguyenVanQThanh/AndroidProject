package com.example.projecthk1_2023_2024.Util;

import android.app.Application;
import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Product;

import java.util.List;

public class ProductList extends Application {
    List<Pair<String, Product>> productList;
    public static ProductList instance;
    public static ProductList getInstance(){
        if (instance == null){
            instance = new ProductList();
        }
        return instance;
    }

    public ProductList(List<Pair<String, Product>> productList) {
        this.productList = productList;
    }

    public ProductList() {
    }

    public List<Pair<String, Product>> getProductList() {
        return productList;
    }

    public void setProductList(List<Pair<String, Product>> productList) {
        this.productList = productList;
    }
    public Pair<String, Product> findProduct(String idProduct){
        for(Pair<String,Product> productPair : productList){
            if (productPair.first.equals(idProduct)){
                return productPair;
            }
        }
        return null;
    }

}
