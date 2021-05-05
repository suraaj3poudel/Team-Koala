package com.example.alphademo.views.triplist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.example.alphademo.database.DatabaseProfile;
import com.example.alphademo.database.SiteObject;
import com.example.alphademo.database.SourceObject;
import com.example.alphademo.database.TripInfo;
import com.example.alphademo.adapters.RecyclerViewTrip;
import com.github.ybq.android.spinkit.SpinKitView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAllTripsFragment extends Fragment {


    RecyclerView recyclerViewTrip;
    SpinKitView pbar;
    ArrayList<SourceObject> sourceList;
    ArrayList<SiteObject> siteList;
    ArrayList<TripInfo> trips;
    RecyclerViewTrip tripAdapter;
    TextView ic;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    String JSON_URL;
    DatabaseJson obj;
    DatabaseProfile extractedName;


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trips, container, false);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String user = sharedPreferences.getString("username", "TeamKoala");
        JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/GetTripListDetailByDriver/"+user+"?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";

        recyclerViewTrip = view.findViewById(R.id.trips);
        pbar = view.findViewById(R.id.spin_kit);
        sourceList = new ArrayList<SourceObject>();
        siteList = new ArrayList<SiteObject>();
        trips = new ArrayList<TripInfo>();
        ic = view.findViewById(R.id.ic);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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

        final String[] abc = {""};
        Log.i("Message: ", "I am fetching data from JSON",null);
        pbar.setVisibility(View.VISIBLE);
        ic.setVisibility(View.GONE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject driverObject = response.getJSONObject("data".toString());
                    JSONArray tripinfo = driverObject.getJSONArray("resultSet1");
                    JSONObject jsonObject = tripinfo.getJSONObject(0);
                    abc[0] = jsonObject.getString("DriverName").toString();
                    Log.i("NAME23",abc[0]);

                    extractedName = new DatabaseProfile(getContext());
                    if(extractedName.getData(1,"NAME") == null){
                        extractedName.addData(1, abc[0], "", "", "","");
                    }
                   else {
                        extractedName.updateInfo(1, abc[0], "", "", "", "");
                        Log.i("UPDATED23",extractedName.getData(1,"NAME"));
                    }

                    for(int j = 0; j < 1; j++) {
                        driverObject = response.getJSONObject("data".toString());
                        tripinfo = driverObject.getJSONArray("resultSet1".toString());
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

                    pbar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

                    pbar.setVisibility(View.GONE);

                }
                catch (JSONException e) {
                    e.printStackTrace();

                }
                ic.setVisibility(View.VISIBLE);
                Log.d("TAG", "onErrorResponse: ");
            }


        });

        queue.add(jsonObjectRequest);
    }



}