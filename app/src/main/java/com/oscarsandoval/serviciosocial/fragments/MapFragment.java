package com.oscarsandoval.serviciosocial.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oscarsandoval.serviciosocial.Place;
import com.oscarsandoval.serviciosocial.R;


public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, LocationListener {

    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;
    private FloatingActionButton fab;
    private LocationManager locationManager;
    private Location currentLocation;

    private Marker marker;
    private CameraPosition camera;
    private TextView textViewCoordDist;


    //Marcadores
    private MarkerOptions markerAnexo;
    private MarkerOptions markerAnexoConta;
    private MarkerOptions markerConta;
    private MarkerOptions markerInstitutoInge;
    private MarkerOptions markerCasa;

    //Latitud Longitud
    Place anexoIngenieria;

    //Anexo y Contaduria
    Place anexoContaduria;

    //Contaduria
    Place contaduria;

    //Anexo Instituto Ingenieria
    Place institutoIngenieria;

    //Prueba Casa
    Place casa;

    Place[] posMarkers;






    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        //Prueba
        textViewCoordDist = (TextView) rootView.findViewById(R.id.textViewCoordDist);







        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }



    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);


        //Permiso para utilizar el GPS
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(false);


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 3, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3, this);

        //Inilizacion Marcadores
        //Anexo Ingeniera
        anexoIngenieria = new Place( 19.327345 , -99.18302, 100, "Anexo Ingeniería");
        markerAnexo = new MarkerOptions().position(anexoIngenieria.getPosition()).title("Anexo Ingeniera").draggable(false);
        gMap.addMarker(markerAnexo);

        //Anexo y Contaduria
        anexoContaduria = new Place( 19.326244  , -99.184004, 100,"Entre Anexo Ingenieria y Contaduria");
        markerAnexoConta = new MarkerOptions().position(anexoContaduria.getPosition()).title("Anexo  Contaduria").draggable(false);
        gMap.addMarker(markerAnexoConta);

        //Contaduria
        contaduria = new Place( 19.325494  , -99.184683,100,"Facultad Contaduria");
        markerConta = new MarkerOptions().position(contaduria.getPosition()).title("Contaduria").draggable(false);
        gMap.addMarker(markerConta);

        //Anexo Instituto Ingenieria
        institutoIngenieria = new Place( 19.328855  , -99.181801, 30, "Instituto de Ingenieria");
        markerInstitutoInge = new MarkerOptions().position(institutoIngenieria.getPosition()).title("Instituto Ingenieria").draggable(false);
        gMap.addMarker(markerInstitutoInge);

        casa = new Place( 19.275867  , -99.161295, 100,"Casa");
        markerCasa = new MarkerOptions().position(casa.getPosition()).title("Casa").draggable(false);
        gMap.addMarker(markerCasa);

        Place[] markers = {anexoContaduria,casa,anexoIngenieria,institutoIngenieria,contaduria};
        this.posMarkers = markers;

    }



    private boolean isGPSEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("GPS no activado")
                .setMessage("¿Deseas activarlo?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public void onClick(View view) {
        if (!this.isGPSEnabled()) {
            showInfoAlert();
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //GPS
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null){
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }
            currentLocation = location;
            if (currentLocation != null) {
                createOrUpdateMarkerByLocation(location);
                zoomToLocation(location);
            }
        }

    }
    private void createOrUpdateMarkerByLocation(Location location) {
        /*if (marker == null) {
            marker = gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
        } else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }*/

    }

    private void zoomToLocation(Location location) {
        camera = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(18)           // limit -> 21
                .bearing(90)         // 0 - 365º
                .tilt(10)           // limit -> 90
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

    }

    private Place closestPlace(Location location, Place[] places){
        double minDistance = 0;
        int k =0;
        double actualDist;
        double prevDist;
        for(int i = 1; i< places.length; i++){
            actualDist = places[i].calcDistance(location);
            prevDist = places[k].calcDistance(location);
            if (prevDist>= actualDist){
                k = i;
            }
        }

        return places[k];
    }

    @Override
    public void onLocationChanged(Location location) {

        Place closestPlace = closestPlace(location,posMarkers);
        double dist = closestPlace.calcDistance(location);

        textViewCoordDist.setText(
                "\nLugar: " + closestPlace.getName() +
                "\nDistancia: " + closestPlace.calcDistance(location) +
                        "\nLatitud: " + closestPlace.getLatitude()+
                "\nLongitud: " + closestPlace.getLongitude()

        );



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
