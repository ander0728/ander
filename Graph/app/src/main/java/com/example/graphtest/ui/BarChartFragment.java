package com.example.graphtest.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graphtest.GraphData;
import com.example.graphtest.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class BarChartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//         グラフViewを初期化する
        initBarChart();
    }





    private void initBarChart() {
        //棒グラフ
        BarChart mBarChart;
        mBarChart = getView().findViewById(R.id.chart_BarChart);

        // グラフ説明テキストを表示するか
        mBarChart.getDescription().setEnabled(true);
        // グラフ説明テキストのテキスト設定
        mBarChart.getDescription().setText("testtext");
        // グラフ説明テキストの文字色設定
        mBarChart.getDescription().setTextColor(Color.BLACK);
        // グラフ説明テキストの文字サイズ設定
        mBarChart.getDescription().setTextSize(10f);
        // グラフ説明テキストの表示位置設定
        mBarChart.getDescription().setPosition(0, 0);

        // グラフへのタッチジェスチャーを有効にするか
        mBarChart.setTouchEnabled(true);

        // グラフのスケーリングを有効にするか
        mBarChart.setScaleEnabled(true);
        //mLineChart.setScaleXEnabled(true);     // X軸のみに対しての設定
        //mLineChart.setScaleYEnabled(true);     // Y軸のみに対しての設定

        // グラフのドラッギングを有効にするか
        mBarChart.setDragEnabled(true);

        // グラフのピンチ/ズームを有効にするか
        mBarChart.setPinchZoom(true);

        // グラフの背景色設定
        mBarChart.setBackgroundColor(Color.WHITE);

        //Yは指定しない
//        // Y軸(左)の設定
//        // Y軸(左)の取得
//        YAxis leftYAxis = mBarChart.getAxisLeft();
//        // Y軸(左)の最大値設定
//        leftYAxis.setAxisMaximum(1);
//        // Y軸(左)の最小値設定
//        leftYAxis.setAxisMinimum(0);

        // Y軸(右)の設定
        // Y軸(右)は表示しない
        mBarChart.getAxisRight().setEnabled(false);

        // X軸の設定
        XAxis xAxis = mBarChart.getXAxis();
        // X軸の最大値設定
        xAxis.setAxisMaximum(100);
        // X軸の最小値設定
        xAxis.setAxisMinimum(0);
        // X軸の値表示設定
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                // nullを返すと落ちるので、値を書かない場合は空文字を返す
                return "";
            }
        });


        GraphData.getInstance().setBarData(mBarChart);

    }

}
