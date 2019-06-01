package com.example.user.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<Double>  latitude_array = new ArrayList<>();
    private ArrayList<Double> longitude_array = new ArrayList<>();
    private ArrayList<Integer> db = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        randLocation();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void randLocation(){
        for (int i=0; i<5 ; i++ ){
            Random r_lat = new Random();
            Random r_lng = new Random();
            Random r_db = new Random();

            latitude_array.add(13.82 + (r_lat.nextFloat()/40));
            longitude_array.add(100.51 + (r_lng.nextFloat()/40));
            db.add(r_db.nextInt(50)+40);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        plotMarker();

        double currentLat = Main.lat;
        double currentLng = Main.lng;

        Log.d("xxxx", "lat: " + String.valueOf(currentLat));
        Log.d("xxxx", "lng: " + String.valueOf(currentLng));

        LatLng current = new LatLng(currentLat, currentLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void plotMarker(){
        int size = 50;

        for (int i=0; i < latitude_array.size(); i++){
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(size, size, conf);
            Canvas canvas = new Canvas(bmp);

            Paint color = new Paint();

            if (db.get(i)>70){
                color.setColor(Color.parseColor("#55FF0000"));
            }else if (db.get(i)>60){
                color.setColor(Color.parseColor("#55FFFF00"));
            }else{
                color.setColor(Color.parseColor("#5500FF00"));
            }
            canvas.drawCircle(size/2, size/2, size/2, color);

            LatLng bangsue = new LatLng(latitude_array.get(i), longitude_array.get(i));
            mMap.addMarker(new MarkerOptions()
                    .position(bangsue))
                    .setIcon(BitmapDescriptorFactory.fromBitmap(bmp));
        }
    }
}
