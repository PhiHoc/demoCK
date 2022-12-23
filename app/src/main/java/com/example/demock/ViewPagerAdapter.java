package com.example.demock;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.demock.fragment.DatLichFragment;
import com.example.demock.fragment.HoSoFragment;
import com.example.demock.fragment.TrangChuFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrangChuFragment();
            case 1:
                return new DatLichFragment();
            case 2:
                return new HoSoFragment();
            default:
                return new TrangChuFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
