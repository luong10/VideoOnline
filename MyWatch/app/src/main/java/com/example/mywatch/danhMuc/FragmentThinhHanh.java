package com.example.mywatch.danhMuc;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mywatch.R;
import com.example.mywatch.adapter.ViewPaperAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThinhHanh extends Fragment {
     ViewPager viewPager;
     TabLayout tabLayout;
     View v;
     ViewPaperAdapter viewPaperAdapter;
    public FragmentThinhHanh() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_thinh_hanh, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Thịnh hành");
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPaperAdapter = new ViewPaperAdapter(getChildFragmentManager());
        addTabs(viewPager);
        viewPager.setAdapter(viewPaperAdapter);

    }

    public void addTabs(ViewPager viewPager){
        viewPaperAdapter.add(new FragmentTrending(),"Mới nhất");
        viewPaperAdapter.add(new FragmentAmNhac(),"Âm nhạc");
        viewPaperAdapter.add(new FragmentTroChoi(),"Trò chơi");
        viewPaperAdapter.add(new FragmentTinTuc(),"Tin tức");
        viewPaperAdapter.add(new FragmentLive(),"Live");


    }
}
