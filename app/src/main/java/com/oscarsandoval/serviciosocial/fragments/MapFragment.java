package com.oscarsandoval.serviciosocial.fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oscarsandoval.serviciosocial.R;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    private MapView mapView;
    private  GoogleMap gMap;


    private Geocoder geocoder;
    private Address addresses;

    private MarkerOptions marker;

    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        this.checkIfGPSIsEnabled();



    }


    @Override
    public void onResume() {
        super.onResume();
        this.checkIfGPSIsEnabled();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);


        //Marcador 1
        LatLng place1 = new LatLng( 19.327028, -99.181376);
        marker = new MarkerOptions();
        marker.position(place1);
        marker.title("Marcador 1");
        marker.draggable(false);
        gMap.addMarker(marker);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(place1));
        gMap.animateCamera(zoom);

        gMap.animateCamera(zoom);

    }

    private void checkIfGPSIsEnabled(){
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal== 0){
                //El GPS no esta activado
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }
}
