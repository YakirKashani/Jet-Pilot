package Views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.example.hw2.MainActivity;
import com.example.hw2.R;
import com.google.android.material.button.MaterialButton;

public class SpeedActivity extends AppCompatActivity {
    private MaterialButton Speed_BTN_NewGameSlow;
    private MaterialButton Speed_BTN_NewGameFast;
    private Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        findViews();
        Speed_BTN_NewGameSlow.setOnClickListener(view -> changeActivity(0));
        Speed_BTN_NewGameFast.setOnClickListener(view -> changeActivity(1));
        this.location = getIntent().getParcelableExtra("location");
    }
    private void changeActivity(int speed) {
        Intent NewGame = new Intent(this, MainActivity.class);
        NewGame.putExtra(MainActivity.SPEED,speed);
        NewGame.putExtra("location",location);
        startActivity(NewGame);
        finish();
    }
    public void findViews(){
        Speed_BTN_NewGameSlow = findViewById(R.id.Speed_BTN_NewGameSlow);
        Speed_BTN_NewGameFast = findViewById(R.id.Speed_BTN_NewGameFast);
    }
}