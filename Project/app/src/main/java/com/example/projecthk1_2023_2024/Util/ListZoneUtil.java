package com.example.projecthk1_2023_2024.Util;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Zone;

import java.util.List;

public class ListZoneUtil {
    List<Pair<String, Zone>> pairList;
    public static ListZoneUtil instance;
    public static ListZoneUtil getInstance(){
        if (instance == null){
            instance = new ListZoneUtil();
        }
        return instance;
    }

    public ListZoneUtil() {
    }

    public List<Pair<String, Zone>> getPairList() {
        return pairList;
    }

    public void setPairList(List<Pair<String, Zone>> pairList) {
        this.pairList = pairList;
    }

    public Pair<String, Zone> find(String id){
        for (Pair<String, Zone> user : pairList){
            if (user.first.equals(id)){
                return user;
            }
        }
        return null;
    }
}
