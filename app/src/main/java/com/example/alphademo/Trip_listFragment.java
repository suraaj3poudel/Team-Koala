package com.example.alphademo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Trip_listFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<TripInfoClass> driverNames;
    ProgressBar pbar;
    String JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/GetTripListDetailByDriver/D1?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";
    RecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_trip_details,container,false);

        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_trip_details,null);
        recyclerView = view.findViewById(R.id.driverList);
        pbar = view.findViewById(R.id.progressBar2);
        driverNames = new ArrayList<TripInfoClass>();

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        extractDriverNames();

    }

    private void extractDriverNames() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Log.i("Message: ", "I am fetching data from JSON",null);
        //pbar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getApplicationContext(),"fetching all data",Toast.LENGTH_SHORT).show();
                //for (int i = 0; i < response.length(); i++) {
                try {

                    JSONObject driverObject = response.getJSONObject("data".toString());
                    JSONArray driverInfo = driverObject.getJSONArray("resultSet1".toString());

                    TripInfoClass trip = new TripInfoClass(driverInfo);
                    Log.i("Data ", driverInfo.toString(),null);
                    driverNames.add(trip) ;

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    adapter = new RecyclerViewAdapter(getContext(), driverNames);


                    recyclerView.setAdapter(adapter);

                    //pbar.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: ");
            }
        });

        //pbar.setVisibility(View.VISIBLE);
        queue.add(jsonObjectRequest);
    }

}
