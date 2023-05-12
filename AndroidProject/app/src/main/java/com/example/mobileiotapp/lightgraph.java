package com.example.mobileiotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class lightgraph extends AppCompatActivity {
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightgraph);

        home = findViewById(R.id.backlight);
        GraphView graph = (GraphView) findViewById(R.id.lightgraph);
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