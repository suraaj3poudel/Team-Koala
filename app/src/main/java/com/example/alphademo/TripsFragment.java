package com.example.alphademo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.alphademo.views.setting.DialogFragment;
import com.example.alphademo.views.triplist.Trip_listFragment;


public class TripsFragment extends Fragment {
    Button button;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);
        button= (Button)view.findViewById(R.id.tripBtn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openList();
            }
        });

        return view;
    }
    public void openList(){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Trip_listFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
