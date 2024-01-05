package com.example.projecthk1_2023_2024.model.ViewModel;

import android.util.Pair;

import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.example.projecthk1_2023_2024.model.User;

public class ImportADViewModel {
    private Pair<String, User> userPair;


    private Pair<String, ImportBatch> importBatchPair;
    public ImportADViewModel() {
    }

    public ImportADViewModel(Pair<String, ImportBatch> importBatchPair) {
        this.importBatchPair = importBatchPair;
    }

    public ImportADViewModel(Pair<String, User> userPair, Pair<String, ImportBatch> importBatchPair) {
        this.userPair = userPair;
        this.importBatchPair = importBatchPair;
    }

    public Pair<String, User> getUserPair() {
        return userPair;
    }

    public void setUserPair(Pair<String, User> userPair) {
        this.userPair = userPair;
    }

    public Pair<String, ImportBatch> getImportBatchPair() {
        return importBatchPair;
    }

    public void setImportBatchPair(Pair<String, ImportBatch> importBatchPair) {
        this.importBatchPair = importBatchPair;
    }
}
