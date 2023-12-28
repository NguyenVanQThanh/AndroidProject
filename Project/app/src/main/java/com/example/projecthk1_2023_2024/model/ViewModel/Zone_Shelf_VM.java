package com.example.projecthk1_2023_2024.model.ViewModel;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Product;
import com.example.projecthk1_2023_2024.model.Shelf;
import com.example.projecthk1_2023_2024.model.Zone;

import java.util.List;

public class Zone_Shelf_VM {
    private Pair<String, Zone> zonePair;
    private List<Pair<String, Shelf>> listShelfPair;

    public Zone_Shelf_VM() {
    }

    public Zone_Shelf_VM(Pair<String, Zone> zonePair, List<Pair<String, Shelf>> listShelfPair) {
        this.zonePair = zonePair;
        this.listShelfPair = listShelfPair;
    }

    public Pair<String, Zone> getZonePair() {
        return zonePair;
    }

    public void setZonePair(Pair<String, Zone> zonePair) {
        this.zonePair = zonePair;
    }

    public List<Pair<String, Shelf>> getListShelfPair() {
        return listShelfPair;
    }

    public void setListShelfPair(List<Pair<String, Shelf>> shelfPair) {
        this.listShelfPair = shelfPair;
    }
}
