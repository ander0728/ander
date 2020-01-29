package com.example.graphtest.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.graphtest.R;
import com.example.graphtest.ui.SettingBtnsFragment;
import com.example.graphtest.ui.SyntheticWaveGraphFragment;

public class StaticGraphActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph2_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerWaveGraph, new SyntheticWaveGraphFragment());
        fragmentTransaction.replace(R.id.containerSettingBtns, new SettingBtnsFragment());
        fragmentTransaction.commit();
    }


}
