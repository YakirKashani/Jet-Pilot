package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import Interfaces.MovementCallback;
import Logic.GameManager;
import Logic.HallOfFame;

import Utilities.MovementDetector;
import Utilities.SignalManager;
import Views.Game_Summary;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton main_FAB_right;
    private ExtendedFloatingActionButton main_FAB_left;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[][] main_IMG_GameMap;
    private MaterialTextView main_MTV_points;
    private GameManager gameManager;
    private static long DELAY;
    final Handler handler = new Handler();
    private long startTime;
    private boolean timerOn = false;
    public static final String SPEED = "SPEED";
    HallOfFame HOF;
    private Location location;
    public static final String KEY_OPTION = "KEY_OPTION";
    private MovementDetector movementDetector;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY);
            updateTimer();
            gameManager.MoveObstacles();
            gameManager.MoveGasCans();
            gameManager.updateMap();
            gameManager.CreateObcstacle();
            gameManager.CreateGasCan();
            gameManager.setPoints(gameManager.getPoints() + 100);
            refreshUI();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Intent previousScreen = getIntent();
        int speed = previousScreen.getIntExtra(SPEED, 0);
        this.location = getIntent().getParcelableExtra("location");
        gameManager = new GameManager(this,speed);
        refreshUI();
        int option = previousScreen.getIntExtra(KEY_OPTION,1);
        if(option == 2) { //sensors mode
            main_FAB_left.setVisibility(View.INVISIBLE);
            main_FAB_right.setVisibility(View.INVISIBLE);
            initMovementDetector();
        }
        else{
            main_FAB_left.setOnClickListener(view -> PlayerMove(-1)); // -1 move left
            main_FAB_right.setOnClickListener(view -> PlayerMove(1)); // 1 move right
        }
        startTimer();
        SignalManager.init(this);
        DELAY = gameManager.getGameSpeed();
        HOF = new HallOfFame();
    }
    private void refreshUI() {
        for (int row = 0; row < main_IMG_GameMap.length; row++) {
            for (int col = 0; col < main_IMG_GameMap[0].length; col++) {
                if (gameManager.getValue(row, col) == 3) {
                    main_IMG_GameMap[row][col].setImageResource(R.drawable.plain2);
                    main_IMG_GameMap[row][col].setVisibility(View.VISIBLE);
                } else if (gameManager.getValue(row, col) == 2) {
                    main_IMG_GameMap[row][col].setImageResource(R.drawable.gasoline);
                    main_IMG_GameMap[row][col].setVisibility(View.VISIBLE);
                } else if (gameManager.getValue(row, col) == 1) {
                    main_IMG_GameMap[row][col].setImageResource(R.drawable.missile);
                    main_IMG_GameMap[row][col].setVisibility(View.VISIBLE);
                } else if (gameManager.getValue(row, col) == 0) {
                    main_IMG_GameMap[row][col].setVisibility(View.INVISIBLE);
                }
            }
        }
        if (gameManager.getLife() < main_IMG_hearts.length)
            main_IMG_hearts[gameManager.getLife()].setVisibility(View.INVISIBLE);
        if(DELAY != gameManager.getGameSpeed())
            DELAY = gameManager.getGameSpeed();
        if (gameManager.isNewGame()) {
            stopTimer();
            if(movementDetector!=null)
                movementDetector.stop();
            changeActivityToSummary(gameManager.getPoints());
        }
        main_MTV_points.setText("" + gameManager.getPoints());
    }
    private void changeActivityToSummary(long score) {
        Intent scoreIntent = new Intent(this, Game_Summary.class);
        scoreIntent.putExtra(Game_Summary.KEY_SCORE, score);
        scoreIntent.putExtra("location",location);
        startActivity(scoreIntent);
        finish();
    }
    private void PlayerMove(int direction) {
        gameManager.MovePlayer(direction);
        refreshUI();
    }
    private void findViews() {
        main_FAB_right = findViewById(R.id.main_FAB_right);
        main_FAB_left = findViewById(R.id.main_FAB_left);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3),
        };
        main_IMG_GameMap = new ShapeableImageView[][]{
                {findViewById(R.id.R0_C0),
                        findViewById(R.id.R0_C1),
                        findViewById(R.id.R0_C2),
                        findViewById(R.id.R0_C3),
                        findViewById(R.id.R0_C4)
                },

                {findViewById(R.id.R1_C0),
                        findViewById(R.id.R1_C1),
                        findViewById(R.id.R1_C2),
                        findViewById(R.id.R1_C3),
                        findViewById(R.id.R1_C4)},


                {findViewById(R.id.R2_C0),
                        findViewById(R.id.R2_C1),
                        findViewById(R.id.R2_C2),
                        findViewById(R.id.R2_C3),
                        findViewById(R.id.R2_C4)},

                {findViewById(R.id.R3_C0),
                        findViewById(R.id.R3_C1),
                        findViewById(R.id.R3_C2),
                        findViewById(R.id.R3_C3),
                        findViewById(R.id.R3_C4)},

                {findViewById(R.id.R4_C0),
                        findViewById(R.id.R4_C1),
                        findViewById(R.id.R4_C2),
                        findViewById(R.id.R4_C3),
                        findViewById(R.id.R4_C4)},

                {findViewById(R.id.R5_C0),
                        findViewById(R.id.R5_C1),
                        findViewById(R.id.R5_C2),
                        findViewById(R.id.R5_C3),
                        findViewById(R.id.R5_C4)},

                {findViewById(R.id.R6_C0),
                        findViewById(R.id.R6_C1),
                        findViewById(R.id.R6_C2),
                        findViewById(R.id.R6_C3),
                        findViewById(R.id.R6_C4)},

                {findViewById(R.id.R7_C0),
                        findViewById(R.id.R7_C1),
                        findViewById(R.id.R7_C2),
                        findViewById(R.id.R7_C3),
                        findViewById(R.id.R7_C4)},

                {findViewById(R.id.R8_C0),
                        findViewById(R.id.R8_C1),
                        findViewById(R.id.R8_C2),
                        findViewById(R.id.R8_C3),
                        findViewById(R.id.R8_C4)},

                {findViewById(R.id.R9_C0),
                        findViewById(R.id.R9_C1),
                        findViewById(R.id.R9_C2),
                        findViewById(R.id.R9_C3),
                        findViewById(R.id.R9_C4)},


        };
        main_MTV_points = findViewById(R.id.main_MTV_points);
    }
    private void updateTimer(){
        long currentMillis = System.currentTimeMillis() - startTime;
        int seconds = (int)(currentMillis/1000);
    }
    public void startTimer(){
        if(!timerOn){
            timerOn = true;
            startTime = System.currentTimeMillis();
            handler.postDelayed(runnable,0);

        }
    }
    public void stopTimer(){
        timerOn=false;
        handler.removeCallbacks(runnable);
    }
    private void initMovementDetector() {
        movementDetector = new MovementDetector(this, new MovementCallback() {
            @Override
            public void moveZright() {
                PlayerMove(1);
            }

            @Override
            public void moveZleft() {
                PlayerMove(-1);
            }

            @Override
            public void moveXup() {
                gameManager.setGameSpeed(1);

            }
            @Override
            public void moveXdown() {
                gameManager.setGameSpeed(0);
            }
        });
        movementDetector.start();
    }
}