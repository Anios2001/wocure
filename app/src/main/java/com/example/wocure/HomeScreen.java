package com.example.wocure;

import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;


public class HomeScreen extends AppCompatActivity {
    static HomeScreen instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView txt;
    Button b1;

    SavedPref savedPreferences = SavedPref.getSavedPreferenceInstance();
    String uid;
    public static HomeScreen getInstance(){
        return instance;
    }
    public void onCreate(Bundle savedInstanceState) {
        final Bundle bundle = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_page);
        txt=(TextView)findViewById(R.id.location);
        b1= (Button) findViewById(R.id.update);
        instance=this;
        uid = savedPreferences.getData("uid");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        MyReceiver mReceiver = new MyReceiver (this);
        registerReceiver(mReceiver, filter);


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        if(bundle!=null){

                        }
                        else {
                            updateLocation();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(HomeScreen.this,"You must accept this to continue",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                    }

                }).check();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public void updateLocation() {
        BuildLocationRequest();

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,getPendingIntent());


    }
    private PendingIntent getPendingIntent() {
        Intent intent=new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private void BuildLocationRequest() {
        locationRequest =new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setSmallestDisplacement(10f);
    }

    public void updateTextview(final String value){
        HomeScreen.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HomeScreen.this,value,Toast.LENGTH_SHORT).show();
            }
        });
    }


   /* public void updateFirebase(double lat , double log){
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference();
        mDatabaseRef.child("Location").child(uid).child("Latitude").setValue(lat);
        mDatabaseRef.child("Location").child(uid).child("Londitude").setValue(log);
        Toast.makeText(HomeScreen.this, "Updated",Toast.LENGTH_SHORT).show();
    }*/


}



