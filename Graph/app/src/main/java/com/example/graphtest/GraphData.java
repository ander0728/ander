package com.example.graphtest;

import android.graphics.Color;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GraphData {

    private static LineChart mLineChart;
    private static int root = 440;
    private static int maxOvertone = 100;

    public enum Logics {
        //R.values.stringsのリストデータと合致させること

        /** 1/n 減衰 */
        Fraction,
        /** x^(-n) 減衰 */
        Multiplier,

        /** 標準*/
        Default
    }


    private static GraphData graphData = null;

    private GraphData(){
    }

    public static synchronized  GraphData getInstance(){
        if(graphData == null){
            graphData = new GraphData();
        }
        return graphData;
    }


    /**
     *
     * /resolutionとitemCountは初期化時の図の横軸プロット数にも
     * 影響を受けて表示が変わるため、基本的にユーザー側で数値を変更はしないほうがいいかも
     * /余剰分itemCountはマップ上表示されない　∵初期化時横軸プロット数＝itemCountにすること
     * @param itemCount 離散データの個数
     * @param resolution 離散の度合い（分解能）
     * @param isOdd 奇数倍（
     * @param nx    n倍音
     * @param nPower    n^(nPower)
     * @param logic 計算のロジック
     */
    public void setData(int itemCount, int resolution, int nx, int nPower, boolean isOdd, Logics logic) {

        ArrayList<Entry> datas = new ArrayList<>(itemCount);

        if(logic == Logics.Default) {
            for (int i = 0; i < itemCount; i++) {
                // 数値を生成
                float val = (float) Math.sin((Math.PI / resolution) * i);
                // (x, y) = (i, val)として座標データをセット
                datas.add(new Entry(i, val));
            }
        } else {
            ArrayList<Float> list = new ArrayList<>();
            float max = 0 ;

            //ロジック部
            for(int i = 0; i < itemCount; i++) {
                float val = 0;
                for(int j = 1; j <= maxOvertone; j++){
                    float jnx;
                    if (isOdd){
                        jnx = 2 * j - 1;
                    } else {
                        jnx = nx * j;
                    }
                    switch (logic){
                        case Fraction:
                            val += (float) Math.sin((Math.PI / resolution) * i * jnx) / Math.pow(jnx, nPower) ;
                            break;
                        case Multiplier:
                            val += (float) Math.pow(Math.sin((Math.PI / resolution) * i * jnx), -Math.pow(jnx, nPower));
                            break;
                        default:
                            break;
                    }

                }
                list.add(i, val);

                // 最大値を取得する
                if(i == 0){
                    max = val;
                } else if ( max < val ){
                    max = val;
                }
            }
            //最大値が1になるように全体にamplifyを掛ける
            float amplify = 1f/max;
            for (int i = 0; i < list.size(); i++){
                datas.add(new Entry(i, list.get(i) * amplify));
            }
        }

        // 第一引数にデータ、第二引数にラベル名を設定する
        LineDataSet lineDataSet = new LineDataSet(datas, "label");

        // 値のプロット点を描かない
        lineDataSet.setDrawCircles(false);
        // 線の色設定
        lineDataSet.setColor(Color.rgb(0xb9, 0x40, 0x47));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData lineData = new LineData(dataSets);
        mLineChart.setData(lineData);
    }


    public  LineChart getGraphData(){
        return mLineChart;
    }
    public  void setGraphData(LineChart mlineChart){
        this.mLineChart = mlineChart;
    }

    public static int getRoot() {
        return root;
    }

    public static void setRoot(int root) {
        GraphData.root = root;
    }



}

