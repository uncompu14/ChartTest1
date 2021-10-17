package com.hanium.charttest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    LineChart chart_1;
    LineChart chart_2;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    LineDataSet lineDataSet_2 = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    ArrayList<ILineDataSet> iLineDataSets_2 = new ArrayList<>();
    LineData lineData;
    LineData lineData_2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    float a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        chart_1 = findViewById(R.id.chart_1);
        chart_2 = findViewById(R.id.chart_2);
        chart_1.setDrawGridBackground(true);
        chart_1.setBackgroundColor(Color.WHITE);
        chart_1.setGridBackgroundColor(Color.WHITE);
        chart_2.setDrawGridBackground(true);
        chart_2.setBackgroundColor(Color.WHITE);
        chart_2.setGridBackgroundColor(Color.WHITE);
        chart_1.getDescription().setEnabled(true);
        Description des_1 = chart_1.getDescription();
        des_1.setEnabled(true);
        des_1.setText("시간(초)");
        des_1.setTextSize(13f);
        des_1.setTextColor(Color.BLACK);
        chart_2.getDescription().setEnabled(true);
        Description des_2 = chart_2.getDescription();
        des_2.setEnabled(true);
        des_2.setText("시간(초)");
        des_2.setTextSize(13f);
        des_2.setTextColor(Color.BLACK);
        chart_1.setTouchEnabled(true);
        chart_2.setTouchEnabled(true);
        chart_1.setDragEnabled(true);
        chart_2.setDragEnabled(true);
        chart_1.setAutoScaleMinMaxEnabled(true);
        chart_2.setAutoScaleMinMaxEnabled(true);
        chart_1.getXAxis().setDrawAxisLine(true);
        chart_1.getXAxis().setEnabled(true);
        chart_1.getXAxis().setDrawGridLines(true);
        chart_1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_1.getXAxis().setTextSize(15);
        chart_2.getXAxis().setDrawAxisLine(true);
        chart_2.getXAxis().setEnabled(true);
        chart_2.getXAxis().setDrawGridLines(true);
        chart_2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_2.getXAxis().setTextSize(15);
        YAxis leftAxis_1 = chart_1.getAxisLeft();
        leftAxis_1.setEnabled(true);
        leftAxis_1.setDrawGridLines(true);
        leftAxis_1.setTextSize(15);
        YAxis leftAxis_2 = chart_2.getAxisLeft();
        leftAxis_2.setEnabled(true);
        leftAxis_2.setDrawGridLines(true);
        leftAxis_2.setTextSize(15);
        YAxis rightAxis_1 = chart_1.getAxisRight();
        rightAxis_1.setEnabled(false);
        YAxis rightAxis_2 = chart_2.getAxisRight();
        rightAxis_2.setEnabled(false);
        Legend l = chart_1.getLegend();
        l.setEnabled(true);
        l.setFormSize(10f);
        l.setTextSize(18f);
        l.setTextColor(Color.BLACK);
        Legend l2 = chart_2.getLegend();
        l2.setEnabled(true);
        l2.setFormSize(10f);
        l2.setTextSize(18f);
        l2.setTextColor(Color.BLACK);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("ChartValues");
        retrieveData();



    }


    private void retrieveData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataVals = new ArrayList<Entry>();
                ArrayList<Entry> dataVals_2 = new ArrayList<Entry>();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DataPoint dataPoint = snapshot.getValue(DataPoint.class);
                        dataVals.add(new Entry(a, dataPoint.getxValue()));
                        dataVals_2.add(new Entry(a, dataPoint.getyValue()));
                        a++;
                    }
                    showChart(dataVals);
                    showChart_2((dataVals_2));
                } else {
                    chart_1.clear();
                    chart_1.invalidate();
                    chart_2.clear();
                    chart_2.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
            }
        });
    }

    private void showChart(ArrayList<Entry> dataVals) {
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("전압");
        iLineDataSets.clear();
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColor(Color.parseColor("#000000"));
        lineDataSet.setCircleHoleColor(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(true);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(10);
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        chart_1.clear();
        chart_1.setData(lineData);
        chart_1.invalidate();
    }

    private void showChart_2(ArrayList<Entry> dataVals_2) {
        lineDataSet_2.setValues(dataVals_2);
        lineDataSet_2.setLabel("전류");
        iLineDataSets_2.clear();
        lineDataSet_2.setLineWidth(2);
        lineDataSet_2.setCircleRadius(4);
        lineDataSet_2.setCircleColor(Color.parseColor("#000000"));
        lineDataSet_2.setCircleHoleColor(Color.BLUE);
        lineDataSet_2.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet_2.setDrawCircleHole(true);
        lineDataSet_2.setDrawCircles(true);
        lineDataSet_2.setDrawHorizontalHighlightIndicator(true);
        lineDataSet_2.setDrawHighlightIndicators(false);
        lineDataSet_2.setDrawValues(true);
        lineDataSet_2.setValueTextSize(10);
        iLineDataSets_2.add(lineDataSet_2);
        lineData_2 = new LineData(iLineDataSets_2);
        chart_2.clear();
        chart_2.setData(lineData_2);
        chart_2.invalidate();
    }
}