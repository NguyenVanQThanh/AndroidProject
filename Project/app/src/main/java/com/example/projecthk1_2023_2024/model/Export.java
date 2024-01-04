package com.example.projecthk1_2023_2024.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class Export {
    private Timestamp Create_Date;
    private String Status;
    private boolean Enable;
    private Timestamp Date_Success;
    private DocumentReference IDUser_confirm;
    private int Quantity;
    public Export(){}

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Export(Timestamp Create_Date, String Status, boolean Enable, Timestamp Date_Success, DocumentReference IDUser_confirm, int Quantity) {
        this.Create_Date = Create_Date;
        this.Status = Status;
        this.Enable = Enable;
        this.Date_Success = Date_Success;
        this.IDUser_confirm = IDUser_confirm;
        this.Quantity = Quantity;
    }

    public Timestamp getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(Timestamp create_Date) {
        Create_Date = create_Date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isEnable() {
        return Enable;
    }

    public void setEnable(boolean enable) {
        Enable = enable;
    }

    public Timestamp getDate_Success() {
        return Date_Success;
    }

    public void setDate_Success(Timestamp date_Success) {
        Date_Success = date_Success;
    }

    public DocumentReference getIDUser_confirm() {
        return IDUser_confirm;
    }

    public void setIDUser_confirm(DocumentReference IDUser_confirm) {
        this.IDUser_confirm = IDUser_confirm;
    }
    public String StampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        java.util.Date date = timestamp.toDate();
        return dateFormat.format(date);
    }
}
