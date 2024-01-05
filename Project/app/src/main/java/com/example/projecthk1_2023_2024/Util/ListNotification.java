package com.example.projecthk1_2023_2024.Util;

import android.app.Application;
import android.util.Pair;

import com.example.projecthk1_2023_2024.model.Notification;
import com.example.projecthk1_2023_2024.model.Product;

import java.util.List;

public class ListNotification extends Application {
    List<Pair<String, Notification>> NotificationList;
    public static ListNotification instance;
    public static ListNotification getInstance(){
        if (instance == null){
            instance = new ListNotification();
        }
        return instance;
    }

    public ListNotification(List<Pair<String, Notification>> NotificationList) {
        this.NotificationList = NotificationList;
    }

    public ListNotification() {
    }

    public List<Pair<String, Notification>> getNotificationList() {
        return NotificationList;
    }

    public void setNotificationList(List<Pair<String, Notification>> NotificationList) {
        this.NotificationList = NotificationList;
    }
    public Pair<String, Notification> findNotification(String idNotification){
        for(Pair<String,Notification> NotificationPair : NotificationList){
            if (NotificationPair.first.equals(idNotification)){
                return NotificationPair;
            }
        }
        return null;
    }

}
