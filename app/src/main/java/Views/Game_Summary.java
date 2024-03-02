package Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import com.example.hw2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import Logic.HallOfFame;
import Logic.Record;
import Utilities.PermissionsManager;


public class Game_Summary extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";
    private MaterialTextView summary_MTV_points;
    private MaterialButton summary_BTN_Restart;
    HallOfFame HOF;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_summary);
        findViews();
        Intent previousScreen = getIntent();
        long score = previousScreen.getLongExtra(KEY_SCORE,0);
        HOF = new HallOfFame();
        summary_BTN_Restart.setOnClickListener(view -> changeActivityToMainMenu());
        this.location = getIntent().getParcelableExtra("location");
        HOF.addRecord(new Record(score, location.getLatitude(), location.getLongitude()));
        refreshUI(score);
    }
    public void refreshUI(long score) {
        summary_MTV_points.setText("" + score);
    }
    public void findViews(){
        summary_BTN_Restart = findViewById(R.id.summary_BTN_Restart);
        summary_MTV_points = findViewById(R.id.summary_MTV_points);
    }
    private void changeActivityToMainMenu() {
        Intent MenuIntent = new Intent(this, MenuActivity.class);
        startActivity(MenuIntent);
        finish();
    }
}