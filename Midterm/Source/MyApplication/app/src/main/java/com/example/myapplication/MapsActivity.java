package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    Spinner sp_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addControls();
        addEvents();
    }

    private void addEvents() {
        sp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 1:
                        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    case 2:
                        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 3:
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addControls() {
        sp_spinner = findViewById(R.id.spinner);

        ArrayList<String> styleMapList = new ArrayList<>();
        styleMapList.add("Hybrid");
        styleMapList.add("Terrain");
        styleMapList.add("Satellite");
        styleMapList.add("Normal");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, styleMapList);
        sp_spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng currentLocation = new LatLng(10.76614687495322, 106.6816577860987);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
        // tao vi tri cua hang
        LatLng store1 = new LatLng(10.786082017417565, 106.70270859668636);
        LatLng store2 = new LatLng(10.763014773985528, 106.68250385435682);
        LatLng store3 = new LatLng(10.772259470328448, 106.72208861753627);
        LatLng store4 = new LatLng(10.761317247799518, 106.67274742874211);
        map.addMarker(new MarkerOptions()
                .position(store1)
                .title("Foods store (cơ sở 1)"));

        map.addMarker(new MarkerOptions()
                .position(store2)
                .title("Foods store (cơ sở 2)"));

        map.addMarker(new MarkerOptions()
                .position(store3)
                .title("Foods store (cơ sở 3)"));

        map.addMarker(new MarkerOptions()
                .position(store4)
                .title("Foods store (cơ sở 4)"));


        //bat nut zoom out/ zoom in
        map.getUiSettings().setZoomControlsEnabled(true);

        //lay vi tri hien tai
        map.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

    }
}