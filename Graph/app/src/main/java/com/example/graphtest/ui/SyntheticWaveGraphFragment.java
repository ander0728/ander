package com.example.graphtest.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.graphtest.GraphData;
import com.example.graphtest.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SyntheticWaveGraphFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_synthetic_wave, container, false);

        return  view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//         グラフViewを初期化する
        initLineChart();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ここだとBarCharFragmentのonViewCreatedより先に呼ばれた
    }


    @Override
    public void onStart() {
        super.onStart();

        // グラフに値をセットする
        GraphData.getInstance().setData(1000, 100 ,1,1,false, GraphData.Logics.Default);

    }

    private void initLineChart(){
        // 線グラフView
        LineChart mLineChart ;
        mLineChart = getView().findViewById(R.id.chart_StaticLineGraph);

        // グラフ説明テキストを表示するか
        mLineChart.getDescription().setEnabled(true);
        // グラフ説明テキストのテキスト設定
        mLineChart.getDescription().setText("testtext");
        // グラフ説明テキストの文字色設定
        mLineChart.getDescription().setTextColor(Color.BLACK);
        // グラフ説明テキストの文字サイズ設定
        mLineChart.getDescription().setTextSize(10f);
        // グラフ説明テキストの表示位置設定
        mLineChart.getDescription().setPosition(0, 0);

        // グラフへのタッチジェスチャーを有効にするか
        mLineChart.setTouchEnabled(true);

        // グラフのスケーリングを有効にするか
        mLineChart.setScaleEnabled(true);
        //mLineChart.setScaleXEnabled(true);     // X軸のみに対しての設定
        //mLineChart.setScaleYEnabled(true);     // Y軸のみに対しての設定

        // グラフのドラッギングを有効にするか
        mLineChart.setDragEnabled(true);

        // グラフのピンチ/ズームを有効にするか
        mLineChart.setPinchZoom(true);

        // グラフの背景色設定
        mLineChart.setBackgroundColor(Color.WHITE);

        // Y軸(左)の設定
        // Y軸(左)の取得
        YAxis leftYAxis = mLineChart.getAxisLeft();
        // Y軸(左)の最大値設定
        leftYAxis.setAxisMaximum(1f);
        // Y軸(左)の最小値設定
        leftYAxis.setAxisMinimum(-1f);

        // Y軸(右)の設定
        // Y軸(右)は表示しない
        mLineChart.getAxisRight().setEnabled(false);

        // X軸の設定
        XAxis xAxis = mLineChart.getXAxis();
        // X軸の最大値設定
        xAxis.setAxisMaximum(1000f);
        // X軸の最小値設定
        xAxis.setAxisMinimum(0f);
        // X軸の値表示設定
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                if(value >= 10) {
//                    // (n * 2)π
//                    if (((int) value % 20) == 0) {
//                        return getResources().getString(R.string.pi_label, ((int) value / 20 *2 ));
//                    }
//                    // nπ
//                    else if (((int) value % 10) == 0) {
//                        return getResources().getString(R.string.pi_label, (int) value  / 10 );
//                    }
//                }
                // nullを返すと落ちるので、値を書かない場合は空文字を返す
                return "";
            }
        });
        GraphData.getInstance().setGraphData(mLineChart);
    }





}





