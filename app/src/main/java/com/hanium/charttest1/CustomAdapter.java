package com.hanium.charttest1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<User> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CustomAdapter.CustomViewHolder holder, int position) {
        holder.r_count.setText(String.valueOf(arrayList.get(position).getCount()));
        holder.r_xValue.setText(String.valueOf(arrayList.get(position).getxValue()));
        holder.r_yValue.setText(String.valueOf(arrayList.get(position).getyValue()));
        holder.r_resistor.setText(String.valueOf(arrayList.get(position).getResistor()));
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView r_xValue;
        TextView r_yValue;
        TextView r_resistor;
        TextView r_count;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.r_count = itemView.findViewById(R.id.r_count);
            this.r_xValue = itemView.findViewById(R.id.r_xValue);
            this.r_yValue = itemView.findViewById(R.id.r_yValue);
            this.r_resistor = itemView.findViewById(R.id.r_resistor);
        }
    }
}


