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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Trip_listFragment extends Fragment {


    RecyclerView recyclerView1,recyclerView2;
    ArrayList<SourceObject> sourceList;
    ArrayList<SiteObject> siteList;
    ProgressBar pbar;
    String JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/GetTripListDetailByDriver/D1?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";
    RecyclerViewSource sourceAdapter;
    RecyclerViewSite siteAdapter;
    DatabaseJson obj;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_trip_details,container,false);

        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_trip_details,null);
        recyclerView1 = view.findViewById(R.id.sourceList);
        recyclerView2 = view.findViewById(R.id.siteList);
        pbar = view.findViewById(R.id.progressBar2);
        sourceList = new ArrayList<SourceObject>();
        siteList = new ArrayList<SiteObject>();

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
                    JSONArray tripinfo = driverObject.getJSONArray("resultSet1".toString());


                    JSONObject jsonObjects = new JSONObject();
                    obj = new DatabaseJson(getContext());
                    obj.addData(1, driverObject.toString());
                    jsonObjects = obj.getObject(1);
                    Toast.makeText(getContext(),  jsonObjects.toString(), Toast.LENGTH_SHORT).show();




                    for(int i = 0; i < tripinfo.length(); i++) {
                        JSONObject object = (JSONObject) tripinfo.get(i);
                        if(object.getString("WaypointTypeDescription".toString().trim()).equals("Source")) {
                            SourceObject source = new SourceObject(object);
                            sourceList.add(source);
                        }

                        else{
                            SiteObject site = new SiteObject(object);
                            siteList.add(site);
                        }
                    }
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

                    sourceAdapter = new RecyclerViewSource(getContext(), sourceList);
                    siteAdapter = new RecyclerViewSite(getContext(), siteList);


                    recyclerView1.setAdapter(sourceAdapter);
                    recyclerView2.setAdapter(siteAdapter);
                    Toast.makeText(getContext(),"Loaded from Internet", Toast.LENGTH_LONG).show();

                    //pbar.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();

                }

                //}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


               try{
                obj = new DatabaseJson(getContext());
                JSONObject jsonObjects = obj.getObject(1);
                JSONArray tripInformation = jsonObjects.getJSONArray("resultSet1".toString());



                for(int i = 0; i < tripInformation.length(); i++) {
                    JSONObject object = (JSONObject) tripInformation.get(i);
                    if(object.getString("WaypointTypeDescription".toString().trim()).equals("Source")) {
                        SourceObject source = new SourceObject(object);
                        sourceList.add(source);
                    }

                    else{
                        SiteObject site = new SiteObject(object);
                        siteList.add(site);
                    }
                }
                recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

                sourceAdapter = new RecyclerViewSource(getContext(), sourceList);
                siteAdapter = new RecyclerViewSite(getContext(), siteList);


                recyclerView1.setAdapter(sourceAdapter);
                recyclerView2.setAdapter(siteAdapter);
                }


                catch (JSONException e) {
                    e.printStackTrace();

                }
               Toast.makeText(getContext(),"no Internet", Toast.LENGTH_LONG).show();
                Log.d("TAG", "onErrorResponse: ");

                //pbar.setVisibility(View.VISIBLE);

            }
        });

        //pbar.setVisibility(View.VISIBLE);
        queue.add(jsonObjectRequest);
    }

}
