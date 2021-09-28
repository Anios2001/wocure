package com.example.wocure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyLocationService extends BroadcastReceiver {
    SavedPref savedPreferences = SavedPref.getSavedPreferenceInstance();
    String uid;

    public static final String ACTION_PROCESS_UPDATE ="com.example.wocure";
    @Override
    public void onReceive(Context context, Intent intent) {
        uid = savedPreferences.getData("uid");
    if(intent!=null){
        final String action=intent.getAction();
        if(ACTION_PROCESS_UPDATE.equals(action)){
            LocationResult result= LocationResult.extractResult(intent);
            if(result!=null){
                Location location=result.getLastLocation();
                String location_string= "" + location.getLatitude() +
                        "/" +
                        location.getLongitude();
                try{
                   updateFirebase(location.getLatitude(),location.getLongitude());
                   Toast.makeText(context,location_string,Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){
                    Toast.makeText(context,location_string,Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    }
    public  void updateFirebase(double lat , double log){
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference();
        mDatabaseRef.child("Location").child(uid).child("Latitude").setValue(lat);
        mDatabaseRef.child("Location").child(uid).child("Londitude").setValue(log);

    }


}
