package com.example.alphademo.views.triplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.alphademo.R;
import com.example.alphademo.adapters.RecyclerViewSource;
import com.example.alphademo.database.SiteObject;
import com.example.alphademo.database.SourceObject;
import com.example.alphademo.database.TripInfo;
import com.example.alphademo.databinding.FragmentSourceListBinding;

import java.util.ArrayList;


public class SourceListFragment extends Fragment {
    FragmentSourceListBinding binding;
    RecyclerViewSource adapter;
    boolean b;

    public SourceListFragment(RecyclerViewSource sourceAdapter,boolean t) {
        adapter = sourceAdapter;
        b=t;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_source_list,container,false);

        binding.sourceList.setAdapter(adapter);


        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SwipeRefreshLayout pullToRefresh = binding.pullToRefresh1;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.ic.setVisibility(View.VISIBLE);
                binding.sourceList.setAdapter(adapter);
                pullToRefresh.setRefreshing(false);
                binding.ic.setVisibility(View.GONE);
            }
        });
        if(b){
            binding.ic.setVisibility(View.VISIBLE);
        }
        else{
            binding.ic.setVisibility(View.GONE);
        }
        binding.spinKit1.setVisibility(View.GONE);

    }


}