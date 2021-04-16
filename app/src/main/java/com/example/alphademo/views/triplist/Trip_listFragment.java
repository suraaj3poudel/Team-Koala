package com.example.alphademo.views.triplist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphademo.R;
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
    RecyclerView recyclerView1,recyclerView2;
    ArrayList<SourceObject> sourceList;
    ArrayList<SiteObject> siteList;
    TabLayout tabLayout;
    ViewPager viewPager;
    ProgressBar pbar;
    String JSON_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/GetTripListDetailByDriver/D1?apiKey=f20f8b25-b149-481c-9d2c-41aeb76246ef";
    RecyclerViewSource sourceAdapter;
    RecyclerViewSite siteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_list, container, false);

        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_trip_details,null);
//        recyclerView1 = view.findViewById(R.id.sourceList);
//        recyclerView2 = view.findViewById(R.id.siteList);
//        pbar = view.findViewById(R.id.progressBar2);
        sourceList = new ArrayList<SourceObject>();
        siteList = new ArrayList<SiteObject>();

        extractDriverNames();

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;

        tabLayout.addTab(tabLayout.newTab().setText("Source List"));
        tabLayout.addTab(tabLayout.newTab().setText("Site List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(requireContext(),getChildFragmentManager(),
                tabLayout.getTabCount(), sourceAdapter, siteAdapter);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
        return binding.getRoot();
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


//                    recyclerView1.setAdapter(sourceAdapter);
//                    recyclerView2.setAdapter(siteAdapter);

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
