package com.example.thanh.android_project_mob204.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.thanh.android_project_mob204.fragment.EarningFragment;
import com.example.thanh.android_project_mob204.fragment.TrendingFragment;

public class StatisticsPagerAdapter extends FragmentPagerAdapter {

    public StatisticsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new EarningFragment();
            case 1:
                return new TrendingFragment();
            default:
                return new EarningFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Earning";
            case 1:
                return "Trending";
            default:
                return "Earning";
        }
    }
}
