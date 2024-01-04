package com.example.projecthk1_2023_2024.model.ViewModel;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Export;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.User;

public class ExpADV_VM {
    private Pair<String, User> userPair;
    private Pair<String, Export> expPair;

    public ExpADV_VM(Pair<String, User> userPair, Pair<String, Export> expPair) {
        this.userPair = userPair;
        this.expPair = expPair;
    }

    public Pair<String, User> getUserPair() {
        return userPair;
    }

    public void setUserPair(Pair<String, User> userPair) {
        this.userPair = userPair;
    }

    public Pair<String, Export> getExpPair() {
        return expPair;
    }

    public void setImportBatchPair(Pair<String, Export> importBatchPair) {
        this.expPair = expPair;
    }

}
