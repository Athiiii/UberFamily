package com.example.uberfamiliy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.SQLLight;
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

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FusedLocationProviderClient client;
    private View root;
    private MapView mMapView;
    private GoogleMap googleMap;
    private String adress = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        Button pickUpBtn = root.findViewById(R.id.buttonPickUP);
        pickUpBtn.setOnClickListener(this);


        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMapView = root.findViewById(R.id.mapView);

            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    mMap.setMyLocationEnabled(true);
                    posMyLocationButton();

                    googleMap = mMap;
                }

                private void posMyLocationButton() {
                    @SuppressLint("ResourceType") View locationButton = ((View) HomeFragment.this.getView().findViewById(1).getParent()).findViewById(2);
                    RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                    rlp.setMargins(0, 0, 30, 30);
                }
            });


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


        return root;
    }


    @Override
    public void onClick(View view) {
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);

        new AlertDialog.Builder(getActivity())
                .setTitle("Pick up request")
                .setMessage("Please enter your current address")
                .setView(input)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adress = input.getText().toString();
                        ConnectToDB.getInstance().createRequest(SQLLight.getInstance().getFirstUser(getActivity()).getUserId(), adress, null);
                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null)
            mMapView.onLowMemory();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}