package com.example.mobileiotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class humidgraph extends AppCompatActivity {
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidgraph);
        Intent intent = getIntent();
        int[] received = intent.getIntArrayExtra(MainActivity.HUMID_TEXT);

        home = findViewById(R.id.backhumid);
        GraphView graph = (GraphView) findViewById(R.id.humidgraph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
//        int k=0;
//        if (received.length > 5) {
//            for (int i = received.length - 5; i <= received.length - 1; i--) {
//                DataPoint dataPoint = new DataPoint(k, received[i]);
//            }
//        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 9),
                new DataPoint(3, 2),
                new DataPoint(4, 9)
        });
        graph.addSeries(series);

        home.setOnClickListener(v -> finish());
    }
}