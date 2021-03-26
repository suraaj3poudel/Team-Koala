package com.example.alphademo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<datamodel> data;

    public TripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripFragment newInstance(String param1, String param2) {
        TripFragment fragment = new TripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        recyclerView =view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        data = new ArrayList<>();

        datamodel object1 = new datamodel(R.drawable.driveridbox, "Trip A");
        data.add(object1);

        datamodel object2 = new datamodel(R.drawable.driveridbox, "Trip B");
        data.add(object2);

        datamodel object3 = new datamodel(R.drawable.driveridbox, "Trip C");
        data.add(object3);

        datamodel object4 = new datamodel(R.drawable.driveridbox, "Trip D");
        data.add(object4);

        datamodel object5 = new datamodel(R.drawable.driveridbox, "Trip E");
        data.add(object5);

        datamodel object6 = new datamodel(R.drawable.driveridbox, "Trip F");
        data.add(object6);

        datamodel object7 = new datamodel(R.drawable.driveridbox, "Trip A");
        data.add(object7);

        datamodel object8 = new datamodel(R.drawable.driveridbox, "Trip A");
        data.add(object8);
        return view;
    }
}