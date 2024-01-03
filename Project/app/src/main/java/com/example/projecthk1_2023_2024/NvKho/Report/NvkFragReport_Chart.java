package com.example.projecthk1_2023_2024.NvKho.Report;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.model.ImportBatch;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NvkFragReport_Chart extends Fragment {

    ArrayList impDataArrayList = new ArrayList();
    ArrayList expDataArrayList = new ArrayList();
//    ArrayList<BarDataSet> dataSets = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReferenceImp = db.collection("ImportBatch");
    CollectionReference collectionReferenceExp = db.collection("ExpBatch");



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.chart_layout, container, false);
        BarChart barChart = view.findViewById(R.id.chart);
        getData();

//        BarDataSet barDataSet1 = new BarDataSet(impDataArrayList, "Nhap");
//        BarDataSet barDataSet2 = new BarDataSet(expDataArrayList, "Xuat");
//        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);

        String[] labels = new String[]{"Tháng 1", "Tháng 1", "Tháng 2", "Tháng 2", "Tháng 3", "Tháng 3", "Tháng 4", "Tháng 4", "Tháng 5", "Tháng 5", "Tháng 6", "Tháng 6", "Tháng 7", "Tháng 7", "Tháng 8", "Tháng 8", "Tháng 9", "Tháng 9", "Tháng 10","Tháng 10", "Tháng 11","Tháng 11", "Tháng 12","Tháng 12"};


        // Create BarDataSet for import data
        BarDataSet importDataSet = new BarDataSet(impDataArrayList, "Số lượng nhập");
        //importDataSet.setColor(Color.BLUE); // Set color for import bars
        importDataSet.setColor(Color.parseColor("#3e4d7e"));// xanh đậm
        importDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        // Create BarDataSet for export data
        BarDataSet exportDataSet = new BarDataSet(expDataArrayList, "Số lượng xuất");
        exportDataSet.setStackLabels(labels);
        exportDataSet.setColor(Color.parseColor("#7e9bff")); // Set color for export bars
        exportDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 1f;
        float barSpace = 0f; // x2 dataset
        float barWidth = 0.5f;
        importDataSet.setValueTextSize(15f);
        exportDataSet.setValueTextSize(15f);

        // Combine BarDataSets into a single BarData object
        BarData barData = new BarData(importDataSet, exportDataSet);
        barData.setBarWidth(barWidth);

        // make this BarData object grouped
        barData.groupBars(0, groupSpace, barSpace); // start at x = 0
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(7f);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt vị trí của trục x ở dưới
        xAxis.setGranularity(2f); // Đặt giá trị tối thiểu giữa các nhóm cột
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // Sử dụng IndexAxisValueFormatter để đặt giá trị trục x
        xAxis.setLabelCount(labels.length); // Đặt số lượng nhãn trên trục x
        xAxis.setCenterAxisLabels(true); // Căn giữa nhãn trục x dưới cột
        xAxis.setDrawGridLines(true);

        return view;
    }
    ArrayList<Integer> impQuantityArrayList = new ArrayList();
    ArrayList<Integer> expQuantityArrayList = new ArrayList();

    private void getData() { //thêm các giá trị muốn hiển thị vao ipmDataArrayList và exp
        // Lấy tổng nhập của tất cả các tháng
        collectionReferenceImp.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int totalQuantity = 0;
                        int i= 1;

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            ImportBatch impBatch = document.toObject(ImportBatch.class);

                            Timestamp timeDate = impBatch.getDate_success();
                            Date date = new Date(String.valueOf(timeDate));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
                            int month = Integer.parseInt(dateFormat.format(date));
                            totalQuantity += (impBatch.getQuantity_import());
                            impQuantityArrayList.add(month-1, totalQuantity);

                            if(month != i){
                                i +=1;
                                totalQuantity = 0;
                            }
                        }

                        // In ra tổng số lượng
                        Log.d("TotalQuantity", "Total quantity for successful batches: " + totalQuantity);
                    }
                });


//        impDataArrayList = new ArrayList();
//        expDataArrayList = new ArrayList();
//        expDataArrayList.add(new BarEntry(1f, 10));
        impDataArrayList.add(new BarEntry(1.5f, impQuantityArrayList.get(0)));
//        expDataArrayList.add(new BarEntry(3f, 30));
        impDataArrayList.add(new BarEntry(3.5f, impQuantityArrayList.get(1)));
//        expDataArrayList.add(new BarEntry(5f, 70));
        impDataArrayList.add(new BarEntry(5.5f, impQuantityArrayList.get(2)));
//        expDataArrayList.add(new BarEntry(7f, 30));
        impDataArrayList.add(new BarEntry(7.4f, impQuantityArrayList.get(3)));
//        expDataArrayList.add(new BarEntry(9f, 435));
        impDataArrayList.add(new BarEntry(9.5f, impQuantityArrayList.get(4)));
//        expDataArrayList.add(new BarEntry(11f, 80));
        impDataArrayList.add(new BarEntry(11.5f, impQuantityArrayList.get(5)));
//
//        expDataArrayList.add(new BarEntry(13f, 10));
        impDataArrayList.add(new BarEntry(13.5f, impQuantityArrayList.get(6)));
//        expDataArrayList.add(new BarEntry(15f, 30));
        impDataArrayList.add(new BarEntry(15.5f, impQuantityArrayList.get(7)));
//        expDataArrayList.add(new BarEntry(17f, 70));
        impDataArrayList.add(new BarEntry(17.5f, impQuantityArrayList.get(8)));
//        expDataArrayList.add(new BarEntry(19f, 300));
        impDataArrayList.add(new BarEntry(19.5f, impQuantityArrayList.get(9)));
//        expDataArrayList.add(new BarEntry(21f, 40));
        impDataArrayList.add(new BarEntry(21.5f, impQuantityArrayList.get(10)));
//        expDataArrayList.add(new BarEntry(23f, 80));
        impDataArrayList.add(new BarEntry(23.5f, impQuantityArrayList.get(11)));
    }

    //Chuyển đổi ngày
    private String StampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = timestamp.toDate();
        return dateFormat.format(date);
    }


}