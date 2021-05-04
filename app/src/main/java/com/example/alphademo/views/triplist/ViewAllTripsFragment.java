package com.example.alphademo.views.triplist;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseJson;
import com.example.alphademo.database.SiteObject;
import com.example.alphademo.database.SourceObject;
import com.example.alphademo.database.TripInfo;
import com.example.alphademo.adapters.RecyclerViewTrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAllTripsFragment extends Fragment {


    RecyclerView recyclerViewTrip;
    ProgressBar pbar;
    ArrayList<SourceObject> sourceList;
    ArrayList<SiteObject> siteList;
    ArrayList<TripInfo> trips;
    RecyclerViewTrip tripAdapter;
    String JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/GetTripListDetailByDriver/TeamKoala?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";

    DatabaseJson obj;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_trip_details,null);
        recyclerViewTrip = view.findViewById(R.id.trips);
        //pbar = view.findViewById(R.id.progressBar2);
        sourceList = new ArrayList<SourceObject>();
        siteList = new ArrayList<SiteObject>();
        trips = new ArrayList<TripInfo>();

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Ref1: ", "I am refershing");
                sourceList = new ArrayList<SourceObject>();
                siteList = new ArrayList<SiteObject>();
                trips = new ArrayList<TripInfo>();
                extractDriverNames();// your code
                pullToRefresh.setRefreshing(false);
            }
        });

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



                    for(int j = 0; j < 1; j++) {
                        JSONObject driverObject = response.getJSONObject("data".toString());
                        JSONArray tripinfo = driverObject.getJSONArray("resultSet1".toString());
                        for (int i = 0; i < tripinfo.length(); i++) {
                            JSONObject object = (JSONObject) tripinfo.get(i);
                            if (object.getString("WaypointTypeDescription".toString().trim()).equals("Source")) {
                                SourceObject source = new SourceObject(object);
                                sourceList.add(source);
                            } else {
                                SiteObject site = new SiteObject(object);
                                siteList.add(site);
                            }
                        }
                        TripInfo tripFound = new TripInfo(((JSONObject)tripinfo.get(j)).getString("TripName"), sourceList.size(),siteList.size());
                        trips.add(tripFound);
                    }

                    recyclerViewTrip.setLayoutManager(new LinearLayoutManager(getContext()));

                    tripAdapter = new RecyclerViewTrip(getContext(),trips);

                    recyclerViewTrip.setAdapter(tripAdapter);

                    //pbar.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    obj = new DatabaseJson(getContext());
                    for(int j = 0; j < 1; j++) {
                        JSONObject jsonObjects = obj.getObject(1);
                        JSONArray tripinform = jsonObjects.getJSONArray("resultSet1".toString());


                        for (int i = 0; i < tripinform.length(); i++) {
                            JSONObject object = (JSONObject) tripinform.get(i);
                            if (object.getString("WaypointTypeDescription".toString().trim()).equals("Source")) {
                                SourceObject source = new SourceObject(object);
                                sourceList.add(source);
                            } else {
                                SiteObject site = new SiteObject(object);
                                siteList.add(site);
                            }
                        }
                        TripInfo tripFound = new TripInfo(((JSONObject)tripinform.get(j)).getString("TripName"), sourceList.size(),siteList.size());
                        trips.add(tripFound);
                    }

                    recyclerViewTrip.setLayoutManager(new LinearLayoutManager(getContext()));

                    tripAdapter = new RecyclerViewTrip(getContext(),trips);

                    recyclerViewTrip.setAdapter(tripAdapter);

                    //pbar.setVisibility(View.VISIBLE);

                }
                catch (JSONException e) {
                    e.printStackTrace();

                }
                Toast.makeText(getContext(),"no Internet", Toast.LENGTH_LONG).show();
                Log.d("TAG", "onErrorResponse: ");
            }


        });

        //pbar.setVisibility(View.VISIBLE);
        queue.add(jsonObjectRequest);
    }



}