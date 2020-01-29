package com.example.graphtest;
import android.graphics.Color;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
//import android.support.v7.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;


public class DynamicLineGraphActivity extends AppCompatActivity {

    private static final String TAG = "DynamicLineGraphActivity";

    // LineChartView
    private LineChart mLineChart;

    // 値をプロットするx座標
    private float mX = 0;

    // loopフラグ
    private boolean mIsLoop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);


        // グラフViewを初期化する
        initChart();

        // 一定間隔でグラフをアップデートする
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsLoop) {
                    updateGraph();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        Log.e(TAG, "");
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        mIsLoop = false;
        super.onDestroy();
    }

    /**
     * グラフViewの初期化
     */
    private void initChart() {
        // 線グラフView
        mLineChart = (LineChart) findViewById(R.id.chart_DynamicLineGraph);

        // グラフ説明テキストを表示するか
        mLineChart.getDescription().setEnabled(true);
        // グラフ説明テキストのテキスト設定
        mLineChart.getDescription().setText("test text");
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

        // 空のデータをセットする
        mLineChart.setData(new LineData());

        // Y軸(左)の設定
        // Y軸(左)の取得
        YAxis leftYAxis = mLineChart.getAxisLeft();
        // Y軸(左)の最大値設定
        leftYAxis.setAxisMaximum(2f);
        // Y軸(左)の最小値設定
        leftYAxis.setAxisMinimum(-2f);

        // Y軸(右)の設定
        // Y軸(右)は表示しない
        mLineChart.getAxisRight().setEnabled(false);

        // X軸の設定
        XAxis xAxis = mLineChart.getXAxis();
        // X軸の値表示設定
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value >= 10) {
                    // (n * 2)π
                    if (((int) value % 20) == 0) {
                        return getResources().getString(R.string.pi_label, ((int) value / 20) * 2);
                    }
                    // nπ
                    else if (((int) value % 10) == 0) {
                        return getResources().getString(R.string.pi_label, (int) value / 10);
                    }
                }
                // nullを返すと落ちるので、値を書かない場合は空文字を返す
                return "";
            }
        });
    }

    /**
     * グラフの更新
     */
    private void updateGraph() {
        // 線の情報を取得
        LineData lineData = mLineChart.getData();
        if(lineData == null) {
            return;
        }

        // 0番目の線を取得
        LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(0);
        // 0番目の線が初期化されていない場合は初期化する
        if(lineDataSet == null) {
            // LineDataSetオブジェクト生成
            lineDataSet = new LineDataSet(null, getResources().getString(R.string.sine_wave));
            // 線の色設定
            lineDataSet.setColor(Color.rgb(0xb9, 0x40, 0x47));
            // 線にプロット値の点を描画しない
            lineDataSet.setDrawCircles(false);
            // 線にプロット値の値テキストを描画しない
            lineDataSet.setDrawValues(false);
            // 線を追加
            lineData.addDataSet(lineDataSet);
        }
        // y値を作成
        float val = (float) Math.sin((Math.PI / 10) * mX);
        // 0番目の線に値を追加
        lineData.addEntry(new Entry(mX, val), 0);

        // 値更新通知
        mLineChart.notifyDataSetChanged();

        // X軸に表示する最大のEntryの数を指定
        mLineChart.setVisibleXRangeMaximum(100);

        // オシロスコープのように古いデータを左に寄せていくように表示位置をずらす
        mLineChart.moveViewTo(mX, getVisibleYCenterValue(mLineChart), YAxis.AxisDependency.LEFT);

        mX++;
    }

    /**
     * 表示しているY座標の中心値を返す<br>
     *     基準のY座標は左
     * @param lineChart 対象のLineChart
     * @return 表示しているY座標の中心値
     */
    private float getVisibleYCenterValue(LineChart lineChart) {
        Transformer transformer = lineChart.getTransformer(YAxis.AxisDependency.LEFT);
        ViewPortHandler viewPortHandler = lineChart.getViewPortHandler();

        float highestVisibleY = (float) transformer.getValuesByTouchPoint(viewPortHandler.contentLeft(),
                viewPortHandler.contentTop()).y;
        float highestY = Math.min(lineChart.getAxisLeft().mAxisMaximum, highestVisibleY);

        float lowestVisibleY = (float) transformer.getValuesByTouchPoint(viewPortHandler.contentLeft(),
                viewPortHandler.contentBottom()).y;
        float lowestY = Math.max(lineChart.getAxisLeft().mAxisMinimum, lowestVisibleY);

        return highestY - (Math.abs(highestY - lowestY) / 2);
    }
}

