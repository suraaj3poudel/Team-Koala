package com.example.alphademo.views.triplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.alphademo.R;
import com.example.alphademo.databinding.FragmentSiteListBinding;

public class SiteListFragment extends Fragment {

    FragmentSiteListBinding binding;
    RecyclerViewSite adapter;

    public SiteListFragment(RecyclerViewSite siteAdapter) {
        adapter = siteAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_site_list,container,false);
        binding.siteList.setAdapter(adapter);
        return binding.getRoot();
    }
}
