package com.example.verifly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class Request extends FragmentActivity implements OnMapReadyCallback {
    private Location currentLocation;
    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private Handler mHandler = new Handler();


    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Intent mIntent = getIntent();

        mUserId = mIntent.getStringExtra("User_ID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("requests");
        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(Request.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Request.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
            return;
        }
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;


                    //Toast.makeText(Request.this,currentLocation.getLatitude()+" "+currentLocation.getLongitude(),
                    // Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().
                            findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(Request.this);
                } else {
                    Toast.makeText(Request.this, "No Location recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are Here");

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        //Adding the created the marker on the map
        //googleMap.addMarker(markerOptions);
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        Loc mLoc = new Loc(user.getUid(), currentLocation.getLatitude(), currentLocation.getLongitude());

//        DatabaseReference newRef = mDatabase.child("requests").push();
//        newRef.setValue(mLoc);

        mDatabase.push().setValue(mLoc);
//
//        String id = mDatabase.child("requests").push().getKey();
//        mDatabase.child("requests").child(id).setValue(mLoc);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent mIntent = getIntent();
                String previousActivity= mIntent.getStringExtra("Injury");

                if (previousActivity.equals("Nosebleed")){
                    Intent intent = new Intent(Request.this, Guidelines.class);
                    startActivity(intent);
                }

                if (previousActivity.equals("Sprains")){
                    Intent intent = new Intent(Request.this, Guidelines2.class);
                    startActivity(intent);
                }
                if (previousActivity.equals("Cuts")){
                    Intent intent = new Intent(Request.this, Guidelines3.class);
                    startActivity(intent);
                }

            }
        }, 7000); // 4 seconds
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                } else {
                    Toast.makeText(Request.this,"Location permission missing",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @IgnoreExtraProperties
    public class Loc {


        public  String userID;
        public Double long1;
        public Double lat;


        public Loc() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Loc(String userID,Double lat, Double long1) {
            this.userID = userID;
            this.long1 = long1;
            this.lat = lat;
        }

    }


}
