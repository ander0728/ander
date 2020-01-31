package com.example.graphtest.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graphtest.GraphData;
import com.example.graphtest.R;

public class SettingBtnsFragment extends Fragment {

    static private boolean oddFlag ;
    static private GraphData.Logics logic ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting_btns, container, false);

        oddFlag = false;
        logic = GraphData.Logics.Default;

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final EditText editX = view.findViewById(R.id.editX);
        final EditText editY = view.findViewById(R.id.editY);

        final Switch oddSwitch = view.findViewById(R.id.switchOdd);
        oddSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //nx不活性処理
                    editX.setEnabled(false);
                    oddFlag = true;
                    //flagかなんかTrueでもつ？
                }else{
                    editX.setEnabled(true);
                    oddFlag = false;
                    //nx活性処理
                    //flagかなんかFalseでもつ？
                }
            }
        });

        final TextView textViewFormula = view.findViewById(R.id.textViewFormula);
        final Spinner spinner = view.findViewById(R.id.spinnerLogic);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                switch (item){
                    case "Default":
                        textViewFormula.setText("Default Formula");
                        logic = GraphData.Logics.Default;
                        break;
                    case "Fraction":
                        textViewFormula.setText("Fraction Formula");
                        logic = GraphData.Logics.Fraction;
                        break;
                    case "Multiplier":
                        textViewFormula.setText("Multiplier Formula");
                        logic = GraphData.Logics.Multiplier;
                        break;
                    default:
                        logic = GraphData.Logics.Default;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                logic = GraphData.Logics.Default;
            }
        });


        Button updateGraphButton = view.findViewById(R.id.btnUpdateGraph);
        updateGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // グラフに値をセットする
                GraphData graphData = GraphData.getInstance();
                graphData.getGraphData().clearValues();
                graphData.getBarData().clearValues();
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
                graphData.getBarData().clearValues();
                graphData.setData(1000, 100 , 2, 1, true, GraphData.Logics.Fraction);
                graphData.getGraphData().notifyDataSetChanged();
            }
        });

        Button updateGraphButton3 = view.findViewById(R.id.btnUpdateGraph3);
        updateGraphButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // グラフに値をセットする
                GraphData graphData = GraphData.getInstance();
                graphData.getGraphData().clearValues();
                graphData.getBarData().clearValues();

                graphData.setData(1000, 100 ,
                        Integer.parseInt(editX.getText().toString()),
                        Integer.parseInt(editY.getText().toString()),
                        oddFlag, logic);
                graphData.getGraphData().notifyDataSetChanged();
            }
        });

    }
}
