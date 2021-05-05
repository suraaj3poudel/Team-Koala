package com.example.alphademo.views.triplist;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphademo.R;
import com.example.alphademo.adapters.MyAdapter;
import com.example.alphademo.adapters.RecyclerViewSite;
import com.example.alphademo.adapters.RecyclerViewSource;
import com.example.alphademo.database.DatabaseJson;
import com.example.alphademo.database.DatabaseProfile;
import com.example.alphademo.database.SiteObject;
import com.example.alphademo.database.SourceObject;
import com.example.alphademo.databinding.FragmentTripListBinding;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Trip_listFragment extends Fragment {


    FragmentTripListBinding binding;
    //ViewDataBinding binding;
    RecyclerView recyclerView1,recyclerView2;
    ArrayList<SourceObject> sourceList;
    ArrayList<SiteObject> siteList;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView ic,ic2;
    SwipeRefreshLayout swipeRefreshLayout;
    String JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/GetTripListDetailByDriver/TeamKoala?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";
    RecyclerViewSource sourceAdapter;
    RecyclerViewSite siteAdapter;
    DatabaseJson obj;
    DatabaseProfile extractedName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_list, container, false);

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        tabLayout.addTab(tabLayout.newTab().setText("SOURCE").setIcon(R.drawable.ic_baseline_local_gas_station_24));
        tabLayout.addTab(tabLayout.newTab().setText("SITE").setIcon(R.drawable.site));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#b53a09"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#b53a09"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        extractDriverNames();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void extractDriverNames() {
        RequestQueue queue = Volley.newRequestQueue(getContext());


        final String[] abc = {""};


        sourceList = new ArrayList<SourceObject>();
        siteList = new ArrayList<SiteObject>();
        final boolean[] tr = {false};
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                String abc= "";
                tr[0] =false;
                try {

                    JSONObject driverObject = response.getJSONObject("data".toString());
                    JSONArray tripinfo = driverObject.getJSONArray("resultSet1");

                    JSONObject jsonObject = tripinfo.getJSONObject(0);
                    abc = jsonObject.getString("DriverName");

                    extractedName = new DatabaseProfile(getContext());
                    extractedName.addData(1, abc, "", "", "","");


                    JSONObject jsonObjects = new JSONObject();
                    obj = new DatabaseJson(getContext());
                    obj.addData(1, driverObject.toString());



                    jsonObjects = obj.getObject(1);

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

                    sourceAdapter = new RecyclerViewSource(getContext(), sourceList);
                    siteAdapter = new RecyclerViewSite(getContext(), siteList);


                    final MyAdapter adapter = new MyAdapter(requireContext(),getChildFragmentManager(), tabLayout.getTabCount(), sourceAdapter, siteAdapter, tr[0]);

                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


                    viewPager.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tr[0] = true;
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
                    sourceAdapter = new RecyclerViewSource(getContext(), sourceList);
                    siteAdapter = new RecyclerViewSite(getContext(), siteList);

                    final MyAdapter adapter = new MyAdapter(requireContext(),getChildFragmentManager(),
                            tabLayout.getTabCount(), sourceAdapter, siteAdapter, tr[0]);
                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                }


                catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });


        queue.add(jsonObjectRequest);
    }

}