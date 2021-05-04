package com.example.alphademo.views.triplist;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.alphademo.adapters.RecyclerViewSource;
import com.example.alphademo.adapters.RecyclerViewSite;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    RecyclerViewSource sourceAdapter;
    RecyclerViewSite siteAdapter;

    public MyAdapter(Context c, FragmentManager fm, int tabCount, RecyclerViewSource sourceAdapter, RecyclerViewSite siteAdapter) {
        super(fm);
        context = c;
        this.totalTabs = tabCount;
        this.sourceAdapter = sourceAdapter;
        this.siteAdapter = siteAdapter;
    }

    @NonNull
    @Override

    /**
     *Returns the fragment to display for that tab
     */
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SourceListFragment sourceListFragment = new SourceListFragment(sourceAdapter);
                return sourceListFragment;
            case 1:
                SiteListFragment siteListFragment = new SiteListFragment(siteAdapter);
                return siteListFragment;
            default:
                return null;
        }
    }

    @Override
    /**
     * returns total number of tabs
     */
    public int getCount() {
        return totalTabs;
    }
}
