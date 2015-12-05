package com.jpmorgan.team14.addcontrol;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        int value = 0;
        if (extras != null) {
            value = extras.getInt("position");
        }

        String[] dpos = new String[8];
        dpos[0] = "Bangladesh: Dhaka";
        dpos[1] = "Bangladesh: Pabna";
        dpos[2] = "Bangladesh: Barkhada";
        dpos[3] = "Cambodia: Krasang";
        dpos[4] = "Cambodia: Krong";
        dpos[5] = "Cambodia: Rovieng";
        dpos[6] = "Sudan: Ruwaba";
        dpos[7] = "Uganda: Lira";

        String[] names = new String[8];
        names[0] = "Chandni Disha";
        names[1] = "Ojana Puja";
        names[2] = "Maruf Anik";
        names[3] = "Saiful Jahid";
        names[4] = "Kibria Apon";
        names[5] = "Hasan Rubel";
        names[6] = "Tazy Farzana";
        names[7] = "Maruf Anik";

        int[] reps = new int[8];
        reps[0] = 6;
        reps[1] = 9;
        reps[2] = 12;
        reps[3] = 3;
        reps[4] = 5;
        reps[5] = 7;
        reps[6] = 11;
        reps[7] = 2;

                ((TextView) findViewById(R.id.region)).setText(dpos[value]);
        ((TextView) findViewById(R.id.owner)).setText("Owner: " + names[value]);
        ((TextView) findViewById(R.id.reps)).setText("Representatives: " + Integer.toString(reps[value]));
        ((TextView) findViewById(R.id.members)).setText("Members: " + Integer.toString(100*reps[value]));
        ((TextView) findViewById(R.id.reports)).setText(Integer.toString(4*reps[value]));
        ((TextView) findViewById(R.id.resolved)).setText(Integer.toString(3*reps[value]));

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<LatLng> list;
        list = new ArrayList<>();
        list.add(new LatLng(23.804893, 90.415310));
        list.add(new LatLng(24.009064, 89.251513));
        list.add(new LatLng(23.937270, 89.093714));
        list.add(new LatLng(14.926261, 103.311102));
        list.add(new LatLng(13.381763, 103.855047));
        list.add(new LatLng(11.193096, 104.793777));
        list.add(new LatLng(12.909347, 31.219668));
        list.add(new LatLng(2.250663, 32.892573));

        Bundle extras = getIntent().getExtras();
        int value = 0;
        if (extras != null) {
            value = extras.getInt("position");
        }

        mMap.addMarker(new MarkerOptions().position(list.get(value)).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list.get(value), 12.0f));

    }
}
