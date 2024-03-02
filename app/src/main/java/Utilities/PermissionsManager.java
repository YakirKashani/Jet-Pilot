package Utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;

public class PermissionsManager extends  AppCompatActivity{
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult( LocationResult locationResult) {
            if(locationResult == null){
                return;
            }
            //super.onLocationResult(locationResult);
        }
    };
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location last_location;
    private LocationRequest locationRequest;
    private final int LOCATION_REQUEST_CODE = 10001;
    private Context context;

    public PermissionsManager(Context cont) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(cont);
        this.context = cont;
    }
    public void setLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void checkSettingsAndStartLocationUpdates(){
        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this.context);

        Task<LocationSettingsResponse> locationSettingsResponseTask = settingsClient.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(locationSettingsResponse -> {
            startLocationUpdates(this.context);
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try{
                        SignalManager.getInstance().toast("locationSettingsResponseTask failed");
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void startLocationUpdates(Context context) {
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public Location getLast_location() {
        return last_location;
    }

    public void setLastLocation(){
        if(ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        Task<Location>locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(location -> {
            if(location != null){
                last_location = location;
            }
        });
    }

}

