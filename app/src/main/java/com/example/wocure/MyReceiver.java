package com.example.wocure;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.core.app.ActivityCompat;

public class MyReceiver extends BroadcastReceiver {


    static int countPowerOff = 0;
    private Activity activity = null;

    public MyReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("onReceive", "Power button is pressed.");
        Toast.makeText(context, "power button clicked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, HomeScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            countPowerOff++;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (countPowerOff == 5) {
                Toast.makeText(context, "power button clicked", Toast.LENGTH_SHORT).show();
                i = new Intent(context, HomeScreen.class).putExtra("call update",true);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        }

    }

}