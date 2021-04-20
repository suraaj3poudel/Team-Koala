package com.example.alphademo.views.triplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.alphademo.R;
import com.example.alphademo.databinding.FragmentSourceListBinding;


public class SourceListFragment extends Fragment {
    FragmentSourceListBinding binding;
    RecyclerViewSource adapter;
    ImageButton img;


    public SourceListFragment(RecyclerViewSource sourceAdapter) {
        adapter = sourceAdapter;

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_source_list,container,false);
        binding.sourceList.setAdapter(adapter);

        return binding.getRoot();
    }
}
