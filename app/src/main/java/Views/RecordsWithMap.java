package Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import Fragments.ListFragment;
import com.example.hw2.R;
import Fragments.MapFragment;
import com.google.android.material.button.MaterialButton;

import Interfaces.Callback_highScoreClicked;

public class RecordsWithMap extends AppCompatActivity {
    private FrameLayout main_FRAME_list;
    private FrameLayout main_FRAME_map;
    private MapFragment mapFragment;
    private ListFragment listFragment;
    private MaterialButton Fragments_MB_Return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_with_map);
        findViews();
        listFragment = new ListFragment(this);
        mapFragment = new MapFragment();
        listFragment.setCallbackHighScoreClicked(new Callback_highScoreClicked() {
            @Override
            public void highScoreClicked(double let, double lon) {
                mapFragment.zoom(let, lon);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.main_FRAME_map, mapFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        Fragments_MB_Return.setOnClickListener(v -> changeActivityToMainMenu());
    }

    public void findViews(){
        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
        Fragments_MB_Return = findViewById(R.id.Fragments_MB_Return);
    }

    private void changeActivityToMainMenu() {
        Intent MenuIntent = new Intent(this, MenuActivity.class);
        startActivity(MenuIntent);
        finish();
    }
}