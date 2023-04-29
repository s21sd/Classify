package com.example.keepnotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Timetableshow extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetableshow);

        tab=findViewById(R.id.tab);
        viewPager=findViewById(R.id.viewPager);
        ViewPagerMsgAdapter viewPagerMsgAdapter=new ViewPagerMsgAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerMsgAdapter);
        tab.setupWithViewPager(viewPager);

    }
}