package com.example.thanh.android_project_mob204.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thanh.android_project_mob204.R;

public class TrendingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewTrending = inflater.inflate(R.layout.fragment_statistics_trending, container, false);
        return viewTrending;
    }
}
