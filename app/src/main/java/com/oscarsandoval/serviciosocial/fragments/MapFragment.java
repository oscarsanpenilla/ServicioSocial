package com.oscarsandoval.serviciosocial.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        gMap.setMinZoomPreference(20);
        gMap.setMaxZoomPreference(24);

        LatLng facultad_ingenieria = new LatLng( 19.327028, -99.181376);
        gMap.addMarker(new MarkerOptions().position(facultad_ingenieria).title("Anexo Ingenieria"));

        CameraPosition camera = new CameraPosition.Builder()
                .target(facultad_ingenieria)
                .zoom(20)
                .tilt(20)
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

    }
}
