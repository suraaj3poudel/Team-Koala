package com.example.alphademo.views.triplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.alphademo.R;
import com.example.alphademo.adapters.RecyclerViewSite;
import com.example.alphademo.databinding.FragmentSiteListBinding;

public class SiteListFragment extends Fragment {

    FragmentSiteListBinding binding;
    RecyclerViewSite adapter;
    boolean tr;

    public SiteListFragment(RecyclerViewSite siteAdapter,boolean t) {
        adapter = siteAdapter;
        tr=t;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_site_list,container,false);
        binding.siteList.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SwipeRefreshLayout pullToRefresh = binding.pullToRefresh2;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.siteList.setAdapter(adapter);
                binding.pullToRefresh2.setRefreshing(false);
            }
        });
        if(tr){
            binding.ic.setVisibility(View.VISIBLE);
        }
        else{
            binding.ic.setVisibility(View.GONE);
        }

        binding.spinKit2.setVisibility(View.GONE);
    }
}