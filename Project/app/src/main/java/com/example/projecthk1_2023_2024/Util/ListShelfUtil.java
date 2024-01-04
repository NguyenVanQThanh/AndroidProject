package com.example.projecthk1_2023_2024.Util;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.User;

import java.util.List;

public class ListShelfUtil {
    List<Pair<String, Shelf>> pairList;
    public static ListShelfUtil instance;
    public static ListShelfUtil getInstance(){
        if (instance == null){
            instance = new ListShelfUtil();
        }
        return instance;
    }

    public ListShelfUtil() {
    }

    public List<Pair<String, Shelf>> getPairList() {
        return pairList;
    }

    public void setPairList(List<Pair<String, Shelf>> pairList) {
        this.pairList = pairList;
    }

    public Pair<String, Shelf> find(String id){
        for (Pair<String, Shelf> user : pairList){
            if (user.first.equals(id)){
                return user;
            }
        }
        return null;
    }
}
