package com.example.alphademo.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.alphademo.views.triplist.SiteListFragment;
import com.example.alphademo.views.triplist.SourceListFragment;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    RecyclerViewSource sourceAdapter;
    RecyclerViewSite siteAdapter;
    boolean tr;

    public MyAdapter(Context c, FragmentManager fm, int tabCount, RecyclerViewSource sourceAdapter, RecyclerViewSite siteAdapter,boolean t) {
        super(fm);
        context = c;
        this.totalTabs = tabCount;
        this.sourceAdapter = sourceAdapter;
        this.siteAdapter = siteAdapter;
        tr=t;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SourceListFragment sourceListFragment = new SourceListFragment(sourceAdapter,tr);
                return sourceListFragment;
            case 1:
                SiteListFragment siteListFragment = new SiteListFragment(siteAdapter,tr);
                return siteListFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
