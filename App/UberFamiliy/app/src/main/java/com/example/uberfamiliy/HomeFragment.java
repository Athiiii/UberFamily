package com.example.uberfamiliy;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeFragment extends Fragment {
    private FusedLocationProviderClient client;
    private View root;
    private MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        Button pickUpBtn = root.findViewById(R.id.buttonPickUP);

        requestPermission();
        //Create a new instance of FusedLocationProviderClient(The main entry point for interacting with the fused location provider) for use in an Activity
        client = LocationServices.getFusedLocationProviderClient(getContext());

        mMapView = root.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                // For showing a move to my location button and a blue point where the user is
                mMap.setMyLocationEnabled(true);
                posMyLocationButton();

                googleMap = mMap;
            }

            private void posMyLocationButton() {
                //Here the MyLocationButton gets positioned in the right bottom corner
                @SuppressLint("ResourceType") View locationButton = ((View) HomeFragment.this.getView().findViewById(1).getParent()).findViewById(2);
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                rlp.setMargins(0, 0, 30, 30);
            }
        });
        //Checks if user has permissions for GPS data
        if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //get current GPS data
            client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener() {
                @Override
                public void onSuccess(Object location) {
                    Location loc = (Location) location;
                    if (location != null) {
                        LatLng currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                        // zooming automatically to the current location of the user
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }

                }
            });
        }

        pickUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }


            }
        });

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void requestPermission() {
        //request the permissions for GPS data
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}