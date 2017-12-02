package com.example.helio.gotochurchmobileproject.Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.helio.gotochurchmobileproject.ChurchFragment;
import com.example.helio.gotochurchmobileproject.R;
import com.example.helio.gotochurchmobileproject.SectorFragment;

/**
 * Created by JORGE on 02/12/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    FragmentPagerAdapter adapterViewPager;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages.
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for a particular page.
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChurchFragment.newInstance(R.layout.fragment_church);
            case 1:
                return SectorFragment.newInstance(R.layout.fragment_sector);
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence titulo = "Lista" + position;
        if(position == 0){
            titulo =  "Congregações ";
        }

        if(position == 1){
            titulo = "Setores ";
        }
        
        return titulo;
    }



}