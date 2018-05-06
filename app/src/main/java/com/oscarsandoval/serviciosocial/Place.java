package com.oscarsandoval.serviciosocial;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    //Atributos
    public String name;
    private LatLng position;
    private double distUserPlace;
    private double radio;
    private double latitude;
    private double longitude;
    private boolean isOnPlace;


    public Place(){

    }

    public Place(double latitude, double longitude , double radio, String name){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.position = new LatLng(latitude, longitude);
        this.radio = radio;
        this.isOnPlace = false;

    }

    public double calcDistance(Location location){
        double earthRadius = 6378;
        double dLat = Math.toRadians(this.latitude - location.getLatitude()) ;
        double dLon = Math.toRadians(this.longitude - location.getLongitude());
        double a = Math.pow(Math.sin(dLat/2),2) + Math.cos(this.latitude)*Math.cos(location.getLatitude())*Math.pow(Math.sin(dLon/2),2);
        double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        this.distUserPlace = earthRadius*c*1000;

        if (this.distUserPlace <= this.radio)
        {
            this.isOnPlace = true;
        }

        return this.distUserPlace;
    }

    public Place closestPlace(Location location, Place[] places){
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

    public String getName(){return this.name;}
    public LatLng getPosition(){return this.position;}
    public double getLatitude(){return this.latitude;}
    public double getLongitude(){return this.longitude;}
    public boolean getIsOnPlace(){return this.isOnPlace;}







}
