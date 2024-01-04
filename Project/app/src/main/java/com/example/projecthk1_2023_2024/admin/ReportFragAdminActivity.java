package com.example.projecthk1_2023_2024.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListExportBatch;
import com.example.projecthk1_2023_2024.Util.ListImportBatch;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ReportFragAdminActivity extends Fragment {

    ArrayList impDataArrayList;
    ArrayList expDataArrayList;
    List<Float> impTotal = new ArrayList<>();
    List<Float> expTotal = new ArrayList<>();

    ListExportBatch exportBatchList = ListExportBatch.getInstance();
    ListImportBatch importBatchList = ListImportBatch.getInstance();

//    ArrayList<BarDataSet> dataSets = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.chart_layout, container, false);
        BarChart barChart = view.findViewById(R.id.chart);

//        List<Integer> totalExport =exportBatchList.getCountForMonth();
//        List<Integer> totalImport = importBatchList.getCountForMonth();
//        Log.d("Fade Mode",""+ totalImport.size());
//        Log.d("Fade Mode",""+ totalExport.size());
//        for (int i=0;i<=11;i++){
////            float pos1 = 1+2*i;
////            float pos2 = (float) (1.5+2*i);
//            Log.d("Fade Mode",""+totalExport.get(i));
//            Log.d("Fade Mode",""+totalImport.get(i));
////            expDataArrayList.add(new BarEntry(pos1,(float) totalExport.get(i)));
////            impDataArrayList.add(new BarEntry(pos2,(float) totalImport.get(i)));
//        }
        getData();

        BarDataSet barDataSet1 = new BarDataSet(impDataArrayList, "Nhap");
        BarDataSet barDataSet2 = new BarDataSet(expDataArrayList, "Xuat");
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

    private void getData() { //thêm các giá trị muốn hiển thị vao ipmDataArrayList và exp
        impDataArrayList = new ArrayList();
        expDataArrayList = new ArrayList();
        List<Integer> totalExport =exportBatchList.getCountForMonth();
        List<Integer> totalImport = importBatchList.getCountForMonth();
        for (int i=0;i<=11;i++){
            float pos1 = 1+2*i;
            float pos2 = (float) (1.5+2*i);

            expDataArrayList.add(new BarEntry(pos1,(float) totalExport.get(i)));
            impDataArrayList.add(new BarEntry(pos2,(float) totalImport.get(i)));
        }
//        expDataArrayList.add(new BarEntry(1f, 10));
//        impDataArrayList.add(new BarEntry(1.5f, 20));
//        expDataArrayList.add(new BarEntry(3f, 30));
//        impDataArrayList.add(new BarEntry(3.5f, 50));
//        expDataArrayList.add(new BarEntry(5f, 70));
//        impDataArrayList.add(new BarEntry(5.5f, 500));
//        expDataArrayList.add(new BarEntry(7f, 30));
//        impDataArrayList.add(new BarEntry(7.5f, 90));
//        expDataArrayList.add(new BarEntry(9f, 435));
//        impDataArrayList.add(new BarEntry(9.5f, 50));
//        expDataArrayList.add(new BarEntry(11f, 80));
//        impDataArrayList.add(new BarEntry(11.5f, 300));
//
//        expDataArrayList.add(new BarEntry(13f, 10));
//        impDataArrayList.add(new BarEntry(13.5f, 20));
//        expDataArrayList.add(new BarEntry(15f, 30));
//        impDataArrayList.add(new BarEntry(15.5f, 500));
//        expDataArrayList.add(new BarEntry(17f, 70));
//        impDataArrayList.add(new BarEntry(17.5f, 50));
//        expDataArrayList.add(new BarEntry(19f, 300));
//        impDataArrayList.add(new BarEntry(19.5f, 70));
//        expDataArrayList.add(new BarEntry(21f, 40));
//        impDataArrayList.add(new BarEntry(21.5f, 50));
//        expDataArrayList.add(new BarEntry(23f, 80));
//        impDataArrayList.add(new BarEntry(23.5f, 30));
    }

}