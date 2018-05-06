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
    private MarkerOptions markerPrincipalIngenieria;
    private MarkerOptions markerArquitectura;
    private MarkerOptions markerLasIslas;
    private MarkerOptions markerCasa;
    private MarkerOptions markerOdontologia;
    private MarkerOptions markerMedicina;
    private MarkerOptions markerQuimica;


    Place anexoIngenieria;
    Place principalIngenieria;
    Place arquitectura;
    Place lasIslas;
    Place casa;
    Place odontologia;
    Place medicina;
    Place quimica;
    Place[] posMarkers;






    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

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
        anexoIngenieria = new Place( 19.32649  , -99.182388, 122, "Anexo Ingeniería");
        markerAnexo = new MarkerOptions().position(anexoIngenieria.getPosition()).title("Anexo Ingeniera").draggable(false);
        gMap.addMarker(markerAnexo);

        //Principal de Ingenieria
        principalIngenieria = new Place( 19.33139   , -99.184455, 115,"Principal de Ingenieria");
        markerPrincipalIngenieria = new MarkerOptions().position(principalIngenieria.getPosition()).title("Principal de Ingenieria").draggable(false);
        gMap.addMarker(markerPrincipalIngenieria);

        //Facultad de Arquitectura
        arquitectura = new Place( 19.331307   , -99.186625,100,"Facultad Arquitectura");
        markerArquitectura = new MarkerOptions().position(arquitectura.getPosition()).title("Facultad Arquitectura").draggable(false);
        gMap.addMarker(markerArquitectura);

        //Las Islas
        lasIslas = new Place( 19.332878   , -99.18515, 100, "Las Islas");
        markerLasIslas = new MarkerOptions().position(lasIslas.getPosition()).title("Las Islas").draggable(false);
        gMap.addMarker(markerLasIslas);

        //Facultad de Odontologia
        odontologia = new Place( 19.334285    , -99.181514, 75, "Facultad de Odontologia");
        markerOdontologia = new MarkerOptions().position(odontologia.getPosition()).title("Facultad de Odontologia").draggable(false);
        gMap.addMarker(markerOdontologia);

        //Facultad de Medicina
        medicina = new Place(19.333186 ,-99.180454,90,"Facultad de Medicina");
        markerMedicina = new MarkerOptions().position(medicina.getPosition()).title("Facultad de Medicina").draggable(false);
        gMap.addMarker(markerMedicina);

        //Facultad de Quimica
        quimica = new Place(19.332061,-99.18157,90,"Facultad de Quimica" );
        markerQuimica = new MarkerOptions().position(quimica.getPosition()).title("Facultad de Quimica").draggable(false);
        gMap.addMarker(markerQuimica);

        //Home
        casa = new Place( 19.275867  , -99.161295, 50,"Casa");
        markerCasa = new MarkerOptions().position(casa.getPosition()).title("Casa").draggable(false);
        gMap.addMarker(markerCasa);

        Place[] markers = {principalIngenieria,casa,anexoIngenieria, lasIslas, arquitectura,odontologia,medicina,quimica};
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
                zoomToLocation(location);
            }
        }

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



    @Override
    public void onLocationChanged(Location location) {

        Place closestPlace = casa.closestPlace(location,posMarkers);
        if (closestPlace.getIsOnPlace())
        {
            textViewCoordDist.setText(
                    "\nLugar: " + closestPlace.getName() +
                            "\nDistancia: " + closestPlace.calcDistance(location) +
                            "\nLatitud: " + closestPlace.getLatitude()+
                            "\nLongitud: " + closestPlace.getLongitude()

            );

        }else{
            textViewCoordDist.setText(
                    "No estas dentro de un sitio de interes" +
                            "\nLugar mas proximo " + closestPlace.getName() +
                            "\nDistancia: " + closestPlace.calcDistance(location) +
                            "\nLatitud: " + closestPlace.getLatitude()+
                            "\nLongitud: " + closestPlace.getLongitude()

            );

        }





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
