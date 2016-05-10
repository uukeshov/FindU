package com.uukeshov.findu;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class ShowCurrentLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gps;
    private Button sendBtn;
    private Double longitude; //долгота
    private Double latitude;
    private String adress = "тут. ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_current_location);
        sendBtn = (Button) findViewById(R.id.send_btn);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GPSTracker gps = new GPSTracker(ShowCurrentLocationActivity.this);

        if (gps.canGetLocation) {
            Location l = gps.getLocation();
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();

            LatLng loc = new LatLng(latitude, longitude);

            try {

                Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);

                if (addresses.isEmpty()) {
                    mMap.addMarker(new MarkerOptions().position(loc).title("Вы здесь!"));
                }
                if (addresses.size() > 0) {
                    mMap.addMarker(new MarkerOptions().position(loc).title(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName()));
                    adress = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
                }

            } catch (Exception e) {
                e.printStackTrace(); // getFromLocation() may sometimes fail
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14.0f));


        } else {
            gps.showSettingsAlert();
        }

    }

    public void OnclickSend(View view) {

        switch (view.getId()) {
            case R.id.send_btn:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                //sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Привет, я сейачс нахожусь в " + adress);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Привет, я сейчас нахожусь в " + adress + " Координаты" + latitude + " " + longitude);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            default:

                break;
        }
    }
}
