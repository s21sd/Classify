package com.example.keepnotes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {

    TabLayout tab;
    ViewPager viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        tab=view.findViewById(R.id.tab);
        viewPager=view.findViewById(R.id.viewPager);
        ViewPagerMsgAdapter viewPagerMsgAdapter=new ViewPagerMsgAdapter(getParentFragmentManager());
        viewPager.setAdapter(viewPagerMsgAdapter);
        tab.setupWithViewPager(viewPager);

       return view;
    }
}