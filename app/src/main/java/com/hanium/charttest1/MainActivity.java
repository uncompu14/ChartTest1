package com.hanium.charttest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText xValue;
    EditText yValue;
    Button btn_insert;

    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xValue = findViewById(R.id.x_value);
        yValue = findViewById(R.id.y_value);
        btn_insert = findViewById(R.id.btn_insert);
        lineChart = findViewById(R.id.graphview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("ChartValues");

    }

    private void inserData() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = myRef.push().getKey();
                int x = Integer.parseInt(xValue.getText().toString());
                int y = Integer.parseInt(yValue.getText().toString());

                DataPoint dataPoint = new DataPoint(x, y);
                myRef.child(id).setValue(dataPoint);
                retrieveData();
            }
        });

    }

    private void retrieveData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataVals = new ArrayList<Entry>();

                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPoint dataPoint = snapshot.getValue(DataPoint.class);
                        dataVals.add(new Entry(dataPoint.getxValue(),dataPoint.getyValue()));
                    }

                    showChart(dataVals);
                }else {
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });

    }

    private void showChart(ArrayList<Entry> dataVals) {
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("DataSet 1");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
}