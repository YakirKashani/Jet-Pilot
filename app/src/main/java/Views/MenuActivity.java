package Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.location.Location;
import com.example.hw2.MainActivity;
import com.example.hw2.R;
import com.google.android.material.button.MaterialButton;

import Utilities.PermissionsManager;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton Menu_BTN_TwoButtonsMode;
    private MaterialButton Menu_BTN_SensorsMode;
    private MaterialButton Menu_BTN_Records;
    private PermissionsManager permissionsManager;
    private Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        Menu_BTN_TwoButtonsMode.setOnClickListener(view -> changeActivity(1));
        Menu_BTN_SensorsMode.setOnClickListener(view -> changeActivity(2));
        Menu_BTN_Records.setOnClickListener(view -> changeActivity(3));
        permissionsManager = new PermissionsManager(this);
        permissionsManager.setLocationRequest();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            permissionsManager.setLastLocation();
            permissionsManager.checkSettingsAndStartLocationUpdates();

        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
                }else{
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
                }
            }
        }
    }
    private void changeActivity(int option){
        location = permissionsManager.getLast_location();
        if(location == null){
            location = new Location("");
            location.setLatitude(-34);
            location.setLongitude(151);
        }
        if(option == 1) { //Two-Buttons Mode
            Intent nextActivity = new Intent(this, SpeedActivity.class);
            nextActivity.putExtra("location",location);
            startActivity(nextActivity);
            finish();
        }
        else if(option == 2) { //Sensors mode
            Intent nextActivity = new Intent(this, MainActivity.class);
            nextActivity.putExtra("location",location);
            nextActivity.putExtra(MainActivity.KEY_OPTION, option);
            startActivity(nextActivity);
            finish();
        }
        else if(option == 3) { // records board
            Intent nextActivity = new Intent(this, RecordsWithMap.class);
            startActivity(nextActivity);
            finish();
        }
    }
    public void findViews(){
        Menu_BTN_TwoButtonsMode = findViewById(R.id.Menu_BTN_TwoButtonsMode);
        Menu_BTN_SensorsMode = findViewById(R.id.Menu_BTN_SensorsMode);
        Menu_BTN_Records = findViewById(R.id.Menu_BTN_Records);
    }
}