package com.example.uberfamiliy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.Service.SQLLight;
import com.example.uberfamiliy.model.User;
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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FusedLocationProviderClient client;
    private View root;
    private MapView mMapView;
    private GoogleMap googleMap;
    private String address = "";
    private List<User> friends;
    private SQLLight sqlLight;
    private ServerSocket serverSocket;
    private User user;
    private Activity activity;
    private String message;
    private LatLng currentLocation;
    private Context context;
    private Bundle savedInstanceState;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        activity = getActivity();
        context = getContext();
        user = sqlLight.getFirstUser(getActivity());
        this.savedInstanceState = savedInstanceState;


        Button pickUpBtn = root.findViewById(R.id.buttonPickUP);


        if (user.isDriver()) {
            Thread socketServerThread = new Thread(new SocketServerThread());
            socketServerThread.start();
        }

        pickUpBtn.setOnClickListener(this);

        ConnectToDB.getInstance().getApprovedFriends(sqlLight.getId(this.getContext()), new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                friends = ConvertJSON.getInstance().toFriends(output);

            }
        });

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
                        currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                        // zooming automatically to the current location of the user
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }

                }
            });
        }


        return root;
    }

    private void init() {
        if (sqlLight == null) {
            sqlLight = SQLLight.getInstance();
        }
    }


    @Override
    public void onClick(View view) {
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        //shows an input box
        new AlertDialog.Builder(getActivity())
                .setTitle("Pick up request")
                .setMessage("Please enter your destination")
                .setView(input)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        address = input.getText().toString();
                        ConnectToDB.getInstance().createRequest(user.getUserId(), address, null);
                    }
                })
                .show();
        if (friends != null && friends.size() > 0) {
            for (User friend : friends) {
                MyClientTask myClientTask = new MyClientTask(
                        friend.getIp(),
                        friend.getPort());
                myClientTask.execute();
            }
        }

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
        //request Permissions
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        //Call the other Phone if you want to be picked up
        String dstAddress;
        int dstPort;
        String response = "";

        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                dOut.writeByte(1);
                //sends the current location
                dOut.writeUTF(currentLocation.longitude + ";" + currentLocation.latitude);
                dOut.flush(); // Send off the data
                // Send the exit message
                dOut.writeByte(-1);
                dOut.flush();

                dOut.close();


                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

                //saves the result
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.println(response);
            super.onPostExecute(result);
        }
    }

    private class SocketServerThread extends Thread {
        //Comes here if your a driver and your friend needs help
        static final int SocketServerPORT = 8080;
        int count = 0;


        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        CharSequence text = "You are a Driver";

                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getContext(), text, duration);
                        toast.show();
                    }
                });

                while (true) {
                    Socket socket = serverSocket.accept();
                    count++;
                    message += "#" + count + " from " + socket.getInetAddress()
                            + ":" + socket.getPort() + "\n";

                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("YEH" + message);
                        }
                    });

                    HomeFragment.SocketServerReplyThread socketServerReplyThread = new HomeFragment.SocketServerReplyThread(
                            socket, count);
                    socketServerReplyThread.run();

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;
        int cnt;

        SocketServerReplyThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }

        @Override
        public void run() {
            try {
                DataInputStream dIn = new DataInputStream(hostThreadSocket.getInputStream());

                boolean done = false;
                while (!done) {
                    byte messageType = dIn.readByte();

                    switch (messageType) {
                        case 1:
                            //receives the data send though the socket
                            Intent main = new Intent(context, ShowAddressActivity.class);
                            String coordinates = dIn.readUTF();
                            String[] splitCoordinates = coordinates.split(";");
                            if (splitCoordinates != null && splitCoordinates.length == 2) {
                                main.putExtra("longitude", splitCoordinates[0]);
                                main.putExtra("latitude", splitCoordinates[1]);
                            }
                            startActivity(main);
                            return;

                        default:
                            done = true;
                    }
                }

                dIn.close();
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message += "Something wrong! " + e.toString() + "\n";
            }

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    CharSequence text = message;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
            });
        }

    }
}