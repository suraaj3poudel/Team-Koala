package com.example.alphademo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigator;

import com.example.alphademo.views.map.ForegroundService;
import com.example.alphademo.views.map.VoiceSkinsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.guidance.AudioPlayerDelegate;
import com.here.android.mpa.guidance.NavigationManager;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.customization.CustomizableScheme;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.Maneuver;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteTta;
import com.here.android.mpa.routing.RouteWaypoint;

import com.here.android.mpa.routing.Router;
import com.here.android.mpa.routing.RoutingError;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MapTemp extends Fragment {

    private static final int REQUEST_LOCATION = 1;
    private AndroidXMapFragment m_mapFragment;
    private Button m_naviControlButton;
    private Map m_map;
    //CoreRouter coreRouter;
    private NavigationManager m_navigationManager;
    private GeoBoundingBox m_geoBoundingBox;
    private Route m_route;
    private boolean m_foregroundServiceStarted;
    double d1;
    double d2;
    String modes;
    LocationManager locationManager;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MapPrefs" ;
    double latitude, longitude;
    View rootView ;
    FloatingActionButton center,highways,stop,mode;
    TextView eta,arrivalTime,speed,street,direction,whereto;
    ImageView img;
    private java.util.EnumSet<NavigationManager.NaturalGuidanceMode> enumSet;
    Maneuver maneuver;
    FrameLayout btMap;
    LinearLayout tpMap;
    MapMarker customMarker2;
    boolean highway = false;
    Bundle savedInstance;
    Route routeop;
    RouteOptions routeOptions;
    ActivityCompat a;
    private FusedLocationProviderClient fusedLocationClient;
    private static int c =0;
    CustomizableScheme scheme1 ;
    CustomizableScheme scheme2 ;
    String target,modesd;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.map_fragment_view, container,false);
        if(getArguments() != null) {
            d1 = getArguments().getDouble("d1");
            d2 = getArguments().getDouble("d2");
            target = getArguments().getString("info");
        }
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        modesd = sharedpreferences.getString("mode","day");
        whereto = rootView.findViewById(R.id.whereto);
        whereto.setText(target);
        stop = rootView.findViewById(R.id.stop);

        editor = sharedpreferences.edit();
        highway = sharedpreferences.getBoolean("highway", true);
        m_naviControlButton = (Button)rootView.findViewById(R.id.naviCtrlButton);
        center = rootView.findViewById(R.id.recenter);
        eta = rootView.findViewById(R.id.ETA);
        highways = rootView.findViewById(R.id.highway);



        return rootView;
    }

    public void changeMode(String modes){

        if(modes.equals("day")) {
            editor.putString("mode", "night").apply();
            mode.setImageResource(R.drawable.day);
        }
        else {
            editor.putString("mode", "day").apply();
            mode.setImageResource(R.drawable.ic_baseline_nights_stay_24);

        }
        modes = sharedpreferences.getString("mode","night");
        modesd=modes;



        editor.commit();
        m_map.setMapScheme(modes);



    }

    private void clickHighway(){
        highway = sharedpreferences.getBoolean("highway",false);
        editor.putBoolean("highway",!highway).apply();
        editor.commit();
        routeOptions.setHighwaysAllowed(!highway);
        createRoute(d1,d2);
    }

    // declare the listeners
// add application specific logic in each of the callbacks.

    private NavigationManager.NewInstructionEventListener instructListener
            = new NavigationManager.NewInstructionEventListener() {

        @Override
        public void onNewInstructionEvent() {
            // Interpret and present the Maneuver object as it contains
            // turn by turn navigation instructions for the user.
            m_navigationManager.getNextManeuver();
        }
    };

    private void recenter() {
        m_map.setCenter(new GeoCoordinate(latitude,longitude,0.0), Map.Animation.BOW);
        m_navigationManager.setMapUpdateMode(NavigationManager.MapUpdateMode.ROADVIEW);
        Log.i("NewCenter",latitude+" "+longitude);
        m_map.setZoomLevel(18);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        savedInstance= savedInstanceState;
        super.onActivityCreated(savedInstanceState);
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
        initMapFragment();

        arrivalTime = rootView.findViewById(R.id.arrival_time);
        speed = rootView.findViewById(R.id.speed);
        direction = rootView.findViewById(R.id.direction);
        street = rootView.findViewById(R.id.street);
        img = rootView.findViewById(R.id.direction_img);
        btMap = rootView.findViewById(R.id.bottomMap);
        tpMap = rootView.findViewById(R.id.topMap);
        mode = rootView.findViewById(R.id.mode);

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recenter();
            }
        });
        highways.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHighway();
            }
        });
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode(modesd);
            }
        });

        initNaviControlButton();
        initVoicePackagesButton();
        double sd1=Double.parseDouble(sharedpreferences.getString("d1","0"));
        double sd2=Double.parseDouble(sharedpreferences.getString("d2","0"));

        if(d1 !=0 & d2 !=0) {
            initNaviControlButton();
            //Visibility(View.VISIBLE);
        }

        else if(sd1!=0 && sd2 !=0){
            Log.i("PosTG","I am here now");
            //highways.setVisibility(View.VISIBLE);
          whereto.setText(sharedpreferences.getString("info","N/A"));
            d1=sd1;
            d2=sd2;
        }
        else{
            highways.setVisibility(View.INVISIBLE);
            m_naviControlButton.setVisibility(View.INVISIBLE);
            mode.setVisibility(View.INVISIBLE);
            showMessage("Start Trip", "Select a trip from trip Menu to start Navigation!");
        }
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private AndroidXMapFragment getMapFragment() {
        return (AndroidXMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapfragment);
    }


    private void initMapFragment() {
        /* Locate the mapFragment UI element */
        m_mapFragment = getMapFragment();
        //m_navigationManager = NavigationManager.getInstance();
        // This will use external storage to save map cache data, it is also possible to set
        // private app's path
        //String path = new File(getActivity().getExternalFilesDir(null), ".here-map-data")
        //.getAbsolutePath();
        // This method will throw IllegalArgumentException if provided path is not writable
        //com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(path);
        if (m_mapFragment != null) {
            /* Initialize the AndroidXMapFragment, results will be given via the called back. */
            m_mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {

                    if (error == Error.NONE) {


                        m_map = m_mapFragment.getMap();
                        String mapTheme = sharedpreferences.getString("mode","day");
                        Log.i("MAPTHEME",mapTheme);

                        try{
                        scheme1 = m_map.createCustomizableScheme("night", Map.Scheme.NORMAL_NIGHT_GREY);
                        scheme2 = m_map.createCustomizableScheme("day", Map.Scheme.NORMAL_DAY);}
                        catch (Exception e){
                            scheme1 = m_map.getCustomizableScheme("night");
                            scheme2 = m_map.getCustomizableScheme("day");

                        }
                        Log.i("MAPTHEME",mapTheme);
                        m_map.setMapScheme(mapTheme);
                        Image image = new Image();
                        try {
                            image.setImageResource(R.drawable.location);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //m_map.getPositionIndicator();
                        customMarker2 = new MapMarker(new GeoCoordinate(latitude,longitude,1), image);
                        m_map.addMapObject(customMarker2);
                        //setMarker(latitude,longitude,R.drawable.currentlocationmarker,true);
                        setMarker(d1,d2,R.drawable.map_label,true);
                        //if(latitude==0){
                           // getLocation();
                        //}
                        m_map.setCenter(new GeoCoordinate(latitude, longitude),
                                Map.Animation.NONE);
                        //Put this call in Map.onTransformListener if the animation(Linear/Bow)
                        //is used in setCenter()
                        m_map.setZoomLevel(18);
                        m_navigationManager = NavigationManager.getInstance();

                        /*
                         * Get the NavigationManager instance.It is responsible for providing voice
                         * and visual instructions while driving and walking
                         */
                        //coreRouter = new CoreRouter();

                        m_mapFragment.getMapGesture().addOnGestureListener(new MapGesture.OnGestureListener() {
                            @Override
                            public void onPanStart() {
                                m_navigationManager.setMapUpdateMode(NavigationManager.MapUpdateMode.POSITION_ANIMATION);
                            }

                            @Override
                            public void onPanEnd() {

                            }

                            @Override
                            public void onMultiFingerManipulationStart() {

                            }

                            @Override
                            public void onMultiFingerManipulationEnd() {

                            }

                            @Override
                            public boolean onMapObjectsSelected(@NonNull List<ViewObject> list) {
                                return false;
                            }

                            @Override
                            public boolean onTapEvent(@NonNull PointF pointF) {
                                return false;
                            }

                            @Override
                            public boolean onDoubleTapEvent(@NonNull PointF pointF) {
                                return false;
                            }

                            @Override
                            public void onPinchLocked() {

                            }

                            @Override
                            public boolean onPinchZoomEvent(float v, @NonNull PointF pointF) {
                                return false;
                            }

                            @Override
                            public void onRotateLocked() {

                            }

                            @Override
                            public boolean onRotateEvent(float v) {
                                return false;
                            }

                            @Override
                            public boolean onTiltEvent(float v) {
                                return false;
                            }

                            @Override
                            public boolean onLongPressEvent(@NonNull PointF pointF) {
                                return false;
                            }

                            @Override
                            public void onLongPressRelease() {

                            }

                            @Override
                            public boolean onTwoFingerTapEvent(@NonNull PointF pointF) {
                                return false;
                            }
                        },0,true);

                    } else {
                        new AlertDialog.Builder(getActivity()).setMessage(
                                "Error : " + error.name() + "\n\n" + error.getDetails())
                                .setTitle(R.string.engine_init_error)
                                .setNegativeButton(android.R.string.cancel,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                getActivity().finish();
                                            }
                                        }).create().show();
                    }
                }
            });
        }
    }



    private void setMarker(double d1, double d2,int i,boolean check){
        try {
            Image image = new Image();
            image.setImageResource(i);
            //m_map.getPositionIndicator();
            MapMarker customMarker = new MapMarker(new GeoCoordinate(d1,d2,1), image);
            m_map.addMapObject(customMarker);
        } catch (Exception e) {
            //m_map.removeMapObject(customMarker);
            Log.e("HERE", e.getMessage());
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }


    private void createRoute(double d1,double d2) {
        /* Initialize a CoreRouter */
        //initMapFragment();
        CoreRouter coreRouter = new CoreRouter();
        Toast.makeText(getContext(), "Working on it.", Toast.LENGTH_SHORT).show();


        /* Initialize a RoutePlan */
        RoutePlan routePlan = new RoutePlan();

        /*
         * Initialize a RouteOption. HERE Mobile SDK allow users to define their own parameters for the
         * route calculation,including transport modes,route types and route restrictions etc.Please
         * refer to API doc for full list of APIs
         */
        routeOptions = new RouteOptions();
        /* Other transport modes are also available e.g Pedestrian */
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        /* Disable highway in this route. */
        //routeOptions.setHighwaysAllowed(true);
        /* Calculate the shortest route available. */
        routeOptions.setRouteType(RouteOptions.Type.SHORTEST);
        /* Calculate 1 route. */
        routeOptions.setRouteCount(1);
        /* Finally set the route option */
        routePlan.setRouteOptions(routeOptions);

        /* Define waypoints for the route */
        /* START: 4350 Still Creek Dr */
        RouteWaypoint startPoint = new RouteWaypoint(new GeoCoordinate(latitude, longitude));
        /* END: Langley BC */
        RouteWaypoint destination = new RouteWaypoint(new GeoCoordinate(d1, d2));

        /* Add both waypoints to the route plan */
        routePlan.addWaypoint(startPoint);
        routePlan.addWaypoint(destination);

        /* Trigger the route calculation,results will be called back via the listener */
        coreRouter
                .calculateRoute(routePlan, new Router.Listener<List<RouteResult>, RoutingError>() {
                    @Override
                    public void onProgress(int i) {
                        /* The calculation progress can be retrieved in this callback. */
                    }

                    @Override
                    public void onCalculateRouteFinished(
                            List<RouteResult> routeResults,
                            RoutingError routingError) {
                        /* Calculation is done.Let's handle the result */
                        if (routingError == RoutingError.NONE) {
                            if (routeResults.get(0).getRoute() != null) {

                                m_route = routeResults.get(0).getRoute();
                                /* Create a MapRoute so that it can be placed on the map */
                                MapRoute mapRoute = new MapRoute(
                                        routeResults.get(0).getRoute());

                                /* Show the maneuver number on top of the route */
                                mapRoute.setManeuverNumberVisible(true);

                                /* Add the MapRoute to the map */
                                m_map.addMapObject(mapRoute);
                                m_map.setTrafficInfoVisible(true);
                                m_map.removeMapObject(customMarker2);

                                /*
                                 * We may also want to make sure the map view is orientated properly
                                 * so the entire route can be easily seen.
                                 */
                                m_geoBoundingBox = routeResults.get(0).getRoute()
                                        .getBoundingBox();
                                m_map.zoomTo(m_geoBoundingBox, Map.Animation.NONE,
                                        Map.MOVE_PRESERVE_ORIENTATION);
                                //arrivalTime.setText((CharSequence) m_navigationManager.getEta(true,null));
                                startNavigation();
                            } else {
                                Toast.makeText(getActivity(),
                                        "Error:route results returned is not valid",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),
                                    "Error:route calculation returned error code: "
                                            + routingError,
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void initNaviControlButton() {
        m_naviControlButton = rootView.findViewById(R.id.naviCtrlButton);
        m_naviControlButton.setText(R.string.start_navi);
        m_naviControlButton.setBackgroundColor(getResources().getColor(R.color.green));

        m_naviControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpMap.setVisibility(View.GONE);
                btMap.setVisibility(View.GONE);
                /*
                 * To start a turn-by-turn navigation, a concrete route object is required.We use
                 * the same steps from Routing sample app to create a route from 4350 Still Creek Dr
                 * to Langley BC without going on HWY.
                 *
                 * The route calculation requires local map data.Unless there is pre-downloaded map
                 * data on device by utilizing MapLoader APIs,it's not recommended to trigger the
                 * route calculation immediately after the MapEngine is initialized.The
                 * INSUFFICIENT_MAP_DATA error code may be returned by CoreRouter in this case.
                 *
                 */
                Toast.makeText(getContext(),"Working on it", Toast.LENGTH_SHORT);
               // if (m_route == null) {
                    stop.setVisibility(View.VISIBLE);
                    m_naviControlButton.setVisibility(View.GONE);
                    highways.setVisibility(View.VISIBLE);
                    //m_navigationManager = NavigationManager.getInstance();
                    createRoute(d1,d2);
                    //startNavigation();
                //} else {
//                    m_navigationManager.stop();
////                    latitude=Double.parseDouble(sharedpreferences.getString("lat","0"));
////                    longitude=Double.parseDouble(sharedpreferences.getString("lon","0"));
//                    d1 = Double.parseDouble(sharedpreferences.getString("d1","0"));
//                    d2 = Double.parseDouble(sharedpreferences.getString("d2","0"));
//                    /*
//                     * Restore the map orientation to show entire route on screen
//                     */
//                    m_map.setZoomLevel(13);
//                    m_map.zoomTo(m_geoBoundingBox, Map.Animation.NONE, 0f);
//                    m_naviControlButton.setText(R.string.start_navi);
//                    m_naviControlButton.setBackgroundColor(getResources().getColor(R.color.green));
//                    m_route = null;
//                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpMap.setVisibility(View.GONE);
                btMap.setVisibility(View.GONE);
                stop.setVisibility(View.INVISIBLE);
                highways.setVisibility(View.INVISIBLE);
                m_naviControlButton.setVisibility(View.VISIBLE);
                m_navigationManager.stop();
//                    latitude=Double.parseDouble(sharedpreferences.getString("lat","0"));
//                    longitude=Double.parseDouble(sharedpreferences.getString("lon","0"));
                d1 = Double.parseDouble(sharedpreferences.getString("d1","0"));
                d2 = Double.parseDouble(sharedpreferences.getString("d2","0"));
                /*
                 * Restore the map orientation to show entire route on screen
                 */
                m_map.setZoomLevel(13);
                m_map.zoomTo(m_geoBoundingBox, Map.Animation.NONE, 0f);
                m_naviControlButton.setText(R.string.start_navi);
                m_naviControlButton.setBackgroundColor(getResources().getColor(R.color.green));
                m_route = null;
            }
        });
    }
    FloatingActionButton m_voicePackagesButton;
    private void initVoicePackagesButton() {
        m_voicePackagesButton = rootView.findViewById(R.id.voiceCtrlButton);
        m_voicePackagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VoiceSkinsActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * Android 8.0 (API level 26) limits how frequently background apps can retrieve the user's
     * current location. Apps can receive location updates only a few times each hour.
     * See href="https://developer.android.com/about/versions/oreo/background-location-limits.html
     * In order to retrieve location updates more frequently start a foreground service.
     * See https://developer.android.com/guide/components/services.html#Foreground
     */
    private void startForegroundService() {
        if (!m_foregroundServiceStarted) {
            m_foregroundServiceStarted = true;
            Intent startIntent = new Intent(getActivity(), ForegroundService.class);
            startIntent.setAction(ForegroundService.START_ACTION);
            getActivity().getApplicationContext().startService(startIntent);
        }
    }

    private void stopForegroundService() {
        if (m_foregroundServiceStarted) {
            m_foregroundServiceStarted = false;
            Intent stopIntent = new Intent(getActivity(), ForegroundService.class);
            stopIntent.setAction(ForegroundService.STOP_ACTION);
            getActivity().getApplicationContext().startService(stopIntent);
        }
    }

    double dis=0;


    private void startNavigation() {
        m_map.removeMapObject(customMarker2);
        m_naviControlButton.setText(R.string.stop_navi);
        m_naviControlButton.setBackgroundColor(getResources().getColor(R.color.teal_200));
        /* Configure Navigation manager to launch navigation on current map */
        m_navigationManager.setMap(m_map);
        tpMap.setVisibility(View.VISIBLE);
        btMap.setVisibility(View.VISIBLE);
        Image image = new Image();

        try {
            image = new Image();
            image.setImageResource(R.drawable.label);
            //m_map.getPositionIndicator();
            //MapMarker customMarker = new MapMarker(new GeoCoordinate(d1,d2,1), image);
            //m_map.addMapObject(customMarker);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        m_mapFragment.getPositionIndicator().setMarker(image);

        m_map.getPositionIndicator().setVisible(true);
        /*
         * Start the turn-by-turn navigation.Please note if the transport mode of the passed-in
         * route is pedestrian, the NavigationManager automatically triggers the guidance which is
         * suitable for walking. Simulation and tracking modes can also be launched at this moment
         * by calling either simulate() or startTracking()
         */
        //m_navigationManager.setRealisticViewMode(NavigationManager.RealisticViewMode.NIGHT);
        //m_navigationManager.startNavigation(m_route);
        m_navigationManager.getTta(Route.TrafficPenaltyMode.OPTIMAL, true);

        //m_navigationManager.setNaturalGuidanceMode(enumSet);
        getManuevers();


        //String name = m_navigationManager.getNextManeuver().getRoadNames().get(0);
        //street.setText(name);

        //img.setImageURI(ma.get(0).getNextRoadImage());

        //m_map.setTilt(60);
        //startForegroundService();

        //Choose navigation modes between real time navigation and simulation
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Navigation");
        alertDialogBuilder.setMessage("Choose Mode");
        alertDialogBuilder.setNegativeButton("Navigation", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                m_navigationManager.startNavigation(m_route);
                m_map.setZoomLevel(18);
                m_map.setTilt(60);
                startForegroundService();

            }
        });
        alertDialogBuilder.setPositiveButton("Simulation", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                m_navigationManager.simulate(m_route, 50);//Simualtion speed is set to 60 m/s
                m_map.setTilt(60);
                m_map.setZoomLevel(18);
                startForegroundService();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        /*
         * Set the map update mode to ROADVIEW.This will enable the automatic map movement based on
         * the current location.If user gestures are expected during the navigation, it's
         * recommended to set the map update mode to NONE first. Other supported update mode can be
         * found in HERE Mobile SDK for Android (Premium) API doc
         */
        m_navigationManager.setMapUpdateMode(NavigationManager.MapUpdateMode.ROADVIEW);

        /*
         * Sets the measuring unit system that is used by voice guidance.
         * Check VoicePackage.getCustomAttributes() to see whether selected package has needed
         * unit system.
         */
        m_navigationManager.setDistanceUnit(NavigationManager.UnitSystem.IMPERIAL);

        /*
         * NavigationManager contains a number of listeners which we can use to monitor the
         * navigation status and getting relevant instructions.In this example, we will add 2
         * listeners for demo purpose,please refer to HERE Android SDK API documentation for details
         */
        addNavigationListeners();
    }

    private void addNavigationListeners() {

        /*
         * Register a NavigationManagerEventListener to monitor the status change on
         * NavigationManager
         */
        m_navigationManager.addNavigationManagerEventListener(
                new WeakReference<>(m_navigationManagerEventListener));

        /* Register a PositionListener to monitor the position updates */
        m_navigationManager.addPositionListener(new WeakReference<>(m_positionListener));

        /* Register a AudioPlayerDelegate to monitor TTS text */
        m_navigationManager.getAudioPlayer().setDelegate(m_audioPlayerDelegate);

        m_navigationManager.addRerouteListener(new WeakReference<NavigationManager.RerouteListener >(newRoute){
        });

        m_navigationManager.addNewInstructionEventListener(new WeakReference<NavigationManager.NewInstructionEventListener>(m_newInstructionEventListener));


    }

    public void getManuevers(){




        //maneuver = m_route.getManeuvers();
        //Log.i("ManeM:", maneuver.get(0).get);

        //String s = maneuver.get(1).getNextRoadName();
        //street.setText(s);

    }

    private NavigationManager.ManeuverEventListener maneuverEventListener = new NavigationManager.ManeuverEventListener() {
        @Override
        public void onManeuverEvent() {

        }
    };


    private NavigationManager.RerouteListener newRoute = new NavigationManager.RerouteListener() {
        @Override
        public void onRerouteBegin() {
            super.onRerouteBegin();
            getLocation();
            //m_route=null;
            recenter();
            //routePlan = new RoutePlan();
            m_map.removeAllMapObjects();
            createRoute(d1,d2);
            getManuevers();
        }

        @Override
        public void onRerouteEnd(@NonNull RouteResult routeResult, RoutingError routingError) {
            super.onRerouteEnd(routeResult, routingError);
        }
    };



    private NavigationManager.PositionListener m_positionListener =
            new NavigationManager.PositionListener() {
                @Override
                public void onPositionUpdated(GeoPosition geoPosition) {
                    getLocation();
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    int speedNav = (int) m_navigationManager.getAverageSpeed();
                    speed = rootView.findViewById(R.id.speed);
                    speed.setText(speedNav + "");
                    if(speedNav==0){
                        speedNav=30;
                    }

                    //int seconds = m_route.getTtaIncludingTraffic(0).getDuration();

                    //int hour = currentTime.getHours()+m_navigationManager.getEta(false, Route.TrafficPenaltyMode.AVOID_LONG_TERM_CLOSURES).getHours();
                    //int minutes = currentTime.getMinutes()+m_navigationManager.getEta(false, Route.TrafficPenaltyMode.AVOID_LONG_TERM_CLOSURES).getMinutes();

                    //eta.setText(m_navigationManager.getRemainingDistance(1));

                    dis = m_navigationManager.getDestinationDistance()/(1609.0);
                    String distance = String.format("%.1f",dis);


                    eta.setText(distance+"");
                    double calc = Double.parseDouble(distance)/speedNav;
                    String calcs = String.format("%.2f",calc);

                    int seconds = (int)(Double.parseDouble(calcs)*3600);
                    Log.i("ETAS1", seconds+"");

                    Log.i("ETAS2", seconds+"");

                    int p1 = seconds % 60;
                    int p2 = seconds / 60;
                    int p3 = p2 % 60;
                    p2 = p2 / 60;
                    //Log.i("TIME",p2+":"+p3+"");
                    int h = currentTime.getHours()+p2;
                    int m = currentTime.getMinutes()+p3;
                    //arrivalTime.setText(m_navigationManager.getEta(true, Route.TrafficPenaltyMode.OPTIMAL).getTime()/60+"");
                    if(m>60){
                        m = m%60;
                        h++;
                    }
                    String s ="";
                    if(m<10){
                        s="0";
                    }
                    if(h>12){
                        h=h%12;
                    }
                    //arrivalTime.setText(hour+":"+minute);

                    arrivalTime.setText(h+":"+s+m);
                    /* Current position information can be retrieved in this callback */
                }
            };



    private NavigationManager.NewInstructionEventListener m_newInstructionEventListener =
            new NavigationManager.NewInstructionEventListener() {
                @Override
                public void onNewInstructionEvent() {
                    maneuver = m_navigationManager.getNextManeuver();
                    if(maneuver !=null){

                        if(maneuver.getTurn().name().equals("QUITE_RIGHT") || maneuver.getTurn().name().equals("HEAVY_RIGHT") ){
                            img.setBackgroundResource(R.drawable.rightturn);
                            direction.setText("Turn Right");
                        }
                        else if(maneuver.getTurn().name().equals("QUITE_LEFT") || maneuver.getTurn().name().equals("HEAVY_LEFT") ){
                            img.setBackgroundResource(R.drawable.leftturn);
                            direction.setText("Turn Left");
                        }

                        else if(maneuver.getTurn().name().equals("KEEP_MIDDLE") || maneuver.getTurn().name().equals("NO_TURN")){
                            img.setBackgroundResource(R.drawable.straight);
                            direction.setText("Keep Straight");
                        }

                        else if(maneuver.getTurn().name().equals("LIGHT_LEFT")){
                            img.setBackgroundResource(R.drawable.lightleft);
                            direction.setText("Slight Left");
                        }

                        else if(maneuver.getTurn().name().equals("LIGHT_RIGHT")){
                            img.setBackgroundResource(R.drawable.lightright);
                            direction.setText("Slight Left");
                        }
                        street.setText(maneuver.getNextRoadName());
                        int hour = 0;


                    }
                }
            };

    private NavigationManager.NavigationManagerEventListener m_navigationManagerEventListener =
            new NavigationManager.NavigationManagerEventListener() {

                @Override
                public void onRouteUpdated(Route route) {
                    Toast.makeText(getContext(), "Route updated", Toast.LENGTH_SHORT).show();
                    dis = m_route.getLength();
                    eta.setText(dis+"");

                    //arrivalTime.setText((CharSequence) m_navigationManager.getEta(true,null));
                }

                @Override
                public void onCountryInfo(String s, String s1) {
                    Toast.makeText(getContext(), "Country info updated from " + s + " to " + s1,
                            Toast.LENGTH_SHORT).show();
                }

            };

    private AudioPlayerDelegate m_audioPlayerDelegate = new AudioPlayerDelegate() {
        @Override public boolean playText(final String s) {
            getActivity().runOnUiThread(new Runnable() {
                @Override public void run() {
                    //Toast.makeText(getContext(), "TTS output: " + s, Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }

        @Override public boolean playFiles(String[] strings) {
            return false;
        }
    };

    public void onDestroy() {
        /* Stop the navigation when app is destroyed */
        super.onDestroy();
        if (m_navigationManager != null) {
            stopForegroundService();
            m_navigationManager.stop();
        }
    }

    Location locationGPS ;
    private void getLocation() {

        try {
            if (ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                 locationGPS=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS != null) {
                    double lat = locationGPS.getLatitude();
                    double longi = locationGPS.getLongitude();
                    int speeds = (int) Math.floor(locationGPS.getSpeed());

                    latitude = Double.parseDouble(String.valueOf(lat));
                    longitude = Double.parseDouble(String.valueOf(longi));
                } else {
                    Toast.makeText(getContext(), "Unable to find location.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e){
            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double lat = locationGPS.getLatitude();
            double longi = locationGPS.getLongitude();
            int speeds = (int) Math.floor(locationGPS.getSpeed());
            speed.setText(speeds + "");
            latitude = Double.parseDouble(String.valueOf(lat));
            longitude = Double.parseDouble(String.valueOf(longi));
        }
    }


}