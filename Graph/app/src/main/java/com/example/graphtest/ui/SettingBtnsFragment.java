package com.example.graphtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graphtest.GraphData;
import com.example.graphtest.R;

public class SettingBtnsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting_btns, container, false);

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button updateGraphButton = view.findViewById(R.id.btnUpdateGraph);
        updateGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // グラフに値をセットする
                GraphData graphData = GraphData.getInstance();
                graphData.getGraphData().clearValues();
                graphData.setData(1000, 200 , -3, 2,false, GraphData.Logics.Fraction);
                graphData.getGraphData().notifyDataSetChanged();
            }
        });

        Button updateGraphButton2 = view.findViewById(R.id.btnUpdateGraph2);
        updateGraphButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // グラフに値をセットする
                GraphData graphData = GraphData.getInstance();
                graphData.getGraphData().clearValues();
                graphData.setData(1000, 100 , 2, 1, true, GraphData.Logics.Fraction);
                graphData.getGraphData().notifyDataSetChanged();
            }
        });

    }
}
