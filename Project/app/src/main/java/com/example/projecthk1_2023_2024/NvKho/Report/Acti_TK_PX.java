package com.example.projecthk1_2023_2024.NvKho.Report;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecthk1_2023_2024.R;

public class Acti_TK_PX extends AppCompatActivity {
    ImageView backR;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nvkho_report2_xuat_layout);

        backR = findViewById(R.id.back_report);
        backR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Acti_TK_PX.this, NvkFragReport.class);
                startActivity(i);
            }
        });
    }
}