package com.example.alphademo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.alphademo.R;
import com.example.alphademo.views.map.ForegroundService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.guidance.NavigationManager;
import com.here.android.mpa.guidance.VoiceCatalog;
import com.here.android.mpa.guidance.VoiceGuidanceOptions;
import com.here.android.mpa.guidance.VoicePackage;
import com.here.android.mpa.guidance.VoiceSkin;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.Router;
import com.here.android.mpa.routing.RoutingError;
import com.here.services.location.internal.PositionListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import static java.sql.Types.NULL;


public class MapFragmentTemp extends Fragment {

    private static final int REQUEST_LOCATION = 1;
    private AndroidXMapFragment m_mapFragment;
    private Button m_naviControlButton;
    private Map m_map;
    private NavigationManager m_navigationManager= NavigationManager.getInstance();;
    private GeoBoundingBox m_geoBoundingBox;
    private Route m_route;
    private boolean m_foregroundServiceStarted;
    double d1;
    double d2;
    LocationManager locationManager;
    double latitude, longitude;
    View rootView ;
    FloatingActionButton center;
    VoiceCatalog voiceCatalog;
    VoiceGuidanceOptions voiceGuidanceOptions;
    //boolean start =false;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container,         false);
        if(getArguments() != null) {
            d1 = getArguments().getDouble("d1");
            d2 = getArguments().getDouble("d2");
            initMapFragment();
            //getLocation();

        }

        m_naviControlButton = (Button) rootView.findViewById(R.id.naviCtrlButton);
        center = rootView.findViewById(R.id.recenter);
        return rootView;
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recenter();
            }
        });

        initMapFragment();
        checkVoicePackage();


        if(d1 !=0 & d2 !=0) {
            initNaviControlButton();
            //createRoute(d1,d2);
        }
        else{
            m_naviControlButton.setVisibility(View.INVISIBLE);
            showMessage("Start Trip", "Select a trip from trip Menu to start Navigation!");
        }



    }

    private void placeTrack(){

    }

    private void checkVoicePackage() {
        voiceCatalog = VoiceCatalog.getInstance();

        //Log.i("LocalVoice", localInstalledSkins.toString());
        voiceCatalog.downloadCatalog(new VoiceCatalog.OnDownloadDoneListener(){

            @Override
            public void onDownloadDone(VoiceCatalog.Error error) {
                List packages = voiceCatalog.getCatalogList();
            }
        });

        voiceCatalog.downloadCatalog(new VoiceCatalog.OnDownloadDoneListener(){
        //Log.i("Downloaded", "English");
            @Override
            public void onDownloadDone(VoiceCatalog.Error error) {

                return;
            }
        });

    }

    private void recenter() {
        m_map.setCenter(new GeoCoordinate(latitude,longitude,0.0), Map.Animation.BOW);
        m_map.setZoomLevel(15);
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

    private void initMapFragment() {
        /* Locate the mapFragment UI element */

        m_mapFragment = getMapFragment();
        m_navigationManager = NavigationManager.getInstance();


        if (m_mapFragment != null) {
            /* Initialize the AndroidXMapFragment, results will be given via the called back. */
            m_mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(Error error) {
                    if (error == Error.NONE) {
                        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            OnGPS();
                        } else {
                            getLocation();
                        }
                        m_map = m_mapFragment.getMap();
                        //m_mapFragment.getPositionIndicator().setVisible(false);
                        setMarker(latitude,longitude,R.drawable.label);
                        setMarker(d1,d2,R.drawable.map_label);
                        //m_mapFragment.getPositionIndicator().setVisible(false);
                        //setMarker(d1,d2,j);
                        m_map.setCenter(new GeoCoordinate(latitude,longitude,0.0), Map.Animation.NONE);
                        m_map.setZoomLevel(15);
                        if (m_route == null) {
                            createRoute(d1,d2);
                        }

                    }

                    else {
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

    private AndroidXMapFragment getMapFragment() {
        return (AndroidXMapFragment) getChildFragmentManager().findFragmentById(R.id.mapfragment);
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, com.example.alphademo.MapFragmentTemp.REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = Double.parseDouble(String.valueOf(lat));
                longitude = Double.parseDouble(String.valueOf(longi));
            } else {
                Toast.makeText(getContext(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    ;


    private void createRoute(double d1, double d2) {

        Toast.makeText(getContext(), "Working on it.", Toast.LENGTH_SHORT).show();
        /* Initialize a CoreRouter */
        CoreRouter coreRouter= new CoreRouter();

        /* Initialize a RoutePlan */
        RoutePlan routePlan = new RoutePlan();

        /*
         * Initialize a RouteOption. HERE Mobile SDK allow users to define their own parameters for the
         * route calculation,including transport modes,route types and route restrictions etc.Please
         * refer to API doc for full list of APIs
         */
        RouteOptions routeOptions = new RouteOptions();
        /* Other transport modes are also available e.g Pedestrian */
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        /* Disable highway in this route. */
        routeOptions.setHighwaysAllowed(true);
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

        coreRouter.calculateRoute(routePlan,
                new Router.Listener<List<RouteResult>, RoutingError>() {

                    @Override
                    public void onProgress(int i) {

                        /* The calculation progress can be retrieved in this callback. */
                    }

                    @Override
                    public void onCalculateRouteFinished(List<RouteResult> routeResults,
                                                         RoutingError routingError) {
                        //Toast.makeText(getContext(),"Updating Route",Toast.LENGTH_SHORT);
                        /* Calculation is done.Let's handle the result */
                        if (routingError == RoutingError.NONE) {
                            if (routeResults.get(0).getRoute() != null) {

                                m_route = routeResults.get(0).getRoute();                                /* Create a MapRoute so that it can be placed on the map */
                                MapRoute mapRoute = new MapRoute(routeResults.get(0).getRoute());

                                /* Show the maneuver number on top of the route */
                                //mapRoute.setManeuverNumberVisible(true);

                                /* Add the MapRoute to the map */

                                m_map.addMapObject(mapRoute);

                                /*
                                 * We may also want to make sure the map view is orientated properly
                                 * so the entire route can be easily seen.
                                 */

                                //m_geoBoundingBox = routeResults.get(0).getRoute().getBoundingBox();
                                //m_map.zoomTo(m_geoBoundingBox, Map.Animation.NONE,
                                        //Map.MOVE_PRESERVE_TILT);


                            } else {
                                Toast.makeText(getContext(),
                                        "Error:route results not valid",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(),
                                    "Error:route calculation returned error code: " + routingError,
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void initNaviControlButton() {

        if (!voiceCatalog.isLocalVoiceSkin(1101))
        {
            voiceCatalog.downloadVoice(1101, new VoiceCatalog.OnDownloadDoneListener() {
                @Override
                public void onDownloadDone(VoiceCatalog.Error error) {

                }

            });
        }
        m_naviControlButton.setText(R.string.start_navi);
        m_naviControlButton.setBackgroundColor(getResources().getColor(R.color.peach));

        m_naviControlButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (m_naviControlButton.getText().equals("Start Navigation")) {
                    //createRoute(d1,d2);

                    m_navigationManager = NavigationManager.getInstance();
                    try {
                        startNavigation();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //start=false;

                    m_navigationManager.stop();

                    m_map.setZoomLevel(13);

                    m_naviControlButton.setText(R.string.start_navi);
                    m_naviControlButton.setBackgroundColor(getResources().getColor(R.color.peach));

                }
            }
        });
    }

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

    private void setMarker(double d1, double d2,int i){

        try {
            Image image = new Image();
            image.setImageResource(i);
            MapMarker customMarker = new MapMarker(new GeoCoordinate(d1,d2, 0.0), image);
            m_map.addMapObject(customMarker);
        } catch (Exception e) {
            Log.e("HERE", e.getMessage());
        }
    }

    private void startNavigation() throws IOException {
        Toast.makeText(getContext(),"Updating Route",Toast.LENGTH_SHORT);
        m_naviControlButton.setText(R.string.stop_navi);
        m_naviControlButton.setBackgroundColor(getResources().getColor(R.color.teal_200));
        /* Configure Navigation manager to launch navigation on current map */
        m_navigationManager.setMap(m_map);


// set the voice skin for use by navigation manager
        List localInstalledSkins = voiceCatalog.getLocalVoiceSkins();
        VoiceSkin vs = (VoiceSkin) localInstalledSkins.get(2);
        voiceGuidanceOptions = m_navigationManager.getVoiceGuidanceOptions();
        voiceGuidanceOptions.setVoiceSkin(vs);
        // show position indicator
        // note, it is also possible to change icon for the position indicator
        //setMarker(latitude,longitude,R.drawable.label);
        //m_mapFragment.getPositionIndicator().setVisible(false);
            //m_navigationManager = NavigationManager.getInstance();


           //createRoute(d1,d2);


            m_navigationManager.startNavigation(m_route);

            m_map.setTilt(60);
            startForegroundService();


        /*
         * Start the turn-by-turn navigation.Please note if the transport mode of the passed-in
         * route is pedestrian, the NavigationManager automatically triggers the guidance which is
         * suitable for walking. Simulation and tracking modes can also be launched at this moment
         * by calling either simulate() or startTracking()
         */

//        /* Choose navigation modes between real time navigation and simulation */
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        alertDialogBuilder.setTitle("Navigation");
//        alertDialogBuilder.setMessage("Choose Mode");
//        alertDialogBuilder.setNegativeButton("Navigation",new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialoginterface, int i) {
//                m_navigationManager.startNavigation(m_route);
//                m_map.setTilt(60);
//                startForegroundService();
//            };
//        });
//        alertDialogBuilder.setPositiveButton("Simulation",new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialoginterface, int i) {
//                m_navigationManager.simulate(m_route,60);//Simualtion speed is set to 60 m/s
//                m_map.setTilt(60);
//                startForegroundService();
//            };
//        });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//        /*
//         * Set the map update mode to ROADVIEW.This will enable the automatic map movement based on
//         * the current location.If user gestures are expected during the navigation, it's
//         * recommended to set the map update mode to NONE first. Other supported update mode can be
//         * found in HERE Mobile SDK for Android (Premium) API doc
//         */
        m_navigationManager.setMapUpdateMode(NavigationManager.MapUpdateMode.ROADVIEW);



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

        /* Register a PositionListener to monitor the position updates */

        m_navigationManager.addRerouteListener(new WeakReference<NavigationManager.RerouteListener >(newRoute){
            });

        m_navigationManager.addPositionListener(
                new WeakReference<NavigationManager.PositionListener>(m_positionListener));

    }

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
        }

        @Override
        public void onRerouteEnd(@NonNull RouteResult routeResult, RoutingError routingError) {
            super.onRerouteEnd(routeResult, routingError);
        }
    };

    private NavigationManager.PositionListener m_positionListener = new NavigationManager.PositionListener() {
        @Override
        public void onPositionUpdated(GeoPosition geoPosition) {
            /* Current position information can be retrieved in this callback */
            getLocation();
            m_navigationManager.getTta(Route.TrafficPenaltyMode.DISABLED, true);
            m_navigationManager.getDestinationDistance();

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

}