package com.example.mohamed.myapplication.ViewHolders;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mohamed.myapplication.Tab1;
import com.example.mohamed.myapplication.Tab2;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int aNOFTabs;

    public PagerAdapter(FragmentManager fm, int aNumberOfTabs) {
        super(fm);
        this.aNOFTabs=aNumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                Tab1 tab1=new Tab1();
                return tab1;
            case 1:
                Tab2 tab2=new Tab2();
                return tab2;

            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return aNOFTabs;
    }
}
