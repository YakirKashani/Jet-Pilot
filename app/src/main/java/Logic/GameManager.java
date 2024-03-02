package Logic;


import android.content.Context;

import com.example.hw2.R;

import java.util.ArrayList;
import java.util.Random;

import Utilities.HitSound;
import Utilities.SignalManager;

public class GameManager {
    private long points;
    private int life;
    private int [][] Game_Map;
    private final int ROWS = 10;
    private  final int COLS = 5;
    private int playerCell;
    private  double ObstacleCreateSpeed;
    private double ObstacleMovementSpeed;
    private Random rand = new Random();
    private int NextCol_RANDOM;
    private ArrayList<Index> LiveObstaclesArrayList;
    private ArrayList<Index> LiveGasCansArrayList;
    private int GameSpeed;
    private boolean newGame;
    private HitSound hitSound;


    public GameManager(Context context){
        points = 0;
        this.life = 3;
        Game_Map = new int[ROWS][COLS];
        for(int i=0;i<ROWS;i++)
        {
            for(int j=0;j<COLS;j++)
            {
                Game_Map[i][j] = 0;
            }
        }
        playerCell = COLS/2; //Center of the Row
        Game_Map[ROWS-1][playerCell] = 3;
        ObstacleCreateSpeed = 2;
        ObstacleMovementSpeed = 1;
        LiveObstaclesArrayList = new ArrayList<Index>();
        LiveGasCansArrayList = new ArrayList<Index>();
        newGame = false;
        GameSpeed = 0;
        hitSound = new HitSound(context, R.raw.fail);
    }

    public GameManager(Context context, int speed){
        points = 0;
        this.life = 3;
        Game_Map = new int[ROWS][COLS];
        for(int i=0;i<ROWS;i++)
        {
            for(int j=0;j<COLS;j++)
            {
                Game_Map[i][j] = 0;
            }
        }
        playerCell = COLS/2; //Center of the Row
        Game_Map[ROWS-1][playerCell] = 3;
        ObstacleCreateSpeed = 2;
        ObstacleMovementSpeed = 1;
        LiveObstaclesArrayList = new ArrayList<Index>();
        LiveGasCansArrayList = new ArrayList<Index>();
        newGame = false;
        GameSpeed = speed;
        hitSound = new HitSound(context, R.raw.fail);
    }

    public void MovePlayer(int direction) {
        boolean MoveAble = true;
        if (((this.playerCell == 0) && (direction == -1)) || (this.playerCell == COLS-1 && direction == 1))
            MoveAble = false;
        if (MoveAble) {
            this.Game_Map[ROWS-1][playerCell] = 0;
            this.playerCell += direction;
            this.Game_Map[ROWS-1][playerCell] = 3;
        }
    }

    public void CreateObcstacle(){
        NextCol_RANDOM = rand.nextInt(COLS);
        if(this.Game_Map[0][NextCol_RANDOM] == 0) {
            this.Game_Map[0][NextCol_RANDOM] = 1;
            LiveObstaclesArrayList.add(new Index().setROW(0).setCOL(NextCol_RANDOM));
        }
    }

    public void CreateGasCan(){
        NextCol_RANDOM = rand.nextInt(COLS);
        if(this.Game_Map[0][NextCol_RANDOM] == 0) {
            this.Game_Map[0][NextCol_RANDOM] = 2;
            LiveGasCansArrayList.add(new Index().setROW(0).setCOL(NextCol_RANDOM));
        }
    }


    public void MoveObstacles() {
        for (int i = 0; i < LiveObstaclesArrayList.size(); i++) {
            Game_Map[LiveObstaclesArrayList.get(i).getROW()][LiveObstaclesArrayList.get(i).getCOL()] = 0;
            if (LiveObstaclesArrayList.get(i).getROW() == ROWS - 2) {
                if (LiveObstaclesArrayList.get(i).getCOL() == playerCell)
                    HIT();
                LiveObstaclesArrayList.remove(i);
                i--;
            } else {
                LiveObstaclesArrayList.get(i).setROW(LiveObstaclesArrayList.get(i).getROW() + 1);
            }
        }
    }

    public void MoveGasCans() {
        for (int i = 0; i < LiveGasCansArrayList.size(); i++) {
            Game_Map[LiveGasCansArrayList.get(i).getROW()][LiveGasCansArrayList.get(i).getCOL()] = 0;
            if (LiveGasCansArrayList.get(i).getROW() == ROWS - 2) {
                if (LiveGasCansArrayList.get(i).getCOL() == playerCell)
                    HitGasCan();
                LiveGasCansArrayList.remove(i);
                i--;
            } else {
                LiveGasCansArrayList.get(i).setROW(LiveGasCansArrayList.get(i).getROW() + 1);
            }
        }
    }

    public void HIT() {
        SignalManager.getInstance().vibrate(500);
        SignalManager.getInstance().toast("HIT!");
        hitSound.playSound();
        if (life == 1) {
            life = 3;
            newGame = true;
        } else {
            life--;
        }
    }
    public void HitGasCan(){
        points+=1000;
    }
    public void setLife(int life) {

        this.life = life;
    }
    public int getLife() {

        return life;
    }

    public int[][] getGame_Map() {
        return Game_Map;
    }

    public int getValue(int row,int col){
        return this.Game_Map[row][col];
    }

    public double getObstacleCreateSpeed() {
        return ObstacleCreateSpeed;
    }

    public double getObstacleMovementSpeed() {
        return ObstacleMovementSpeed;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public long getGameSpeed() {
        if(GameSpeed == 0) //slow-mode
            return 1000; //in mili-seconds
        else if(GameSpeed == 1) //fast-mode
            return 500; //in mili-second
        return 1000; // default value

    }

    public void setGameSpeed(int gameSpeed) {
        GameSpeed = gameSpeed;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void updateMap() {
        for (int i = 0; i < LiveObstaclesArrayList.size(); i++)
            Game_Map[LiveObstaclesArrayList.get(i).getROW()][LiveObstaclesArrayList.get(i).getCOL()] = 1;
        for (int i = 0; i < LiveGasCansArrayList.size(); i++)
            Game_Map[LiveGasCansArrayList.get(i).getROW()][LiveGasCansArrayList.get(i).getCOL()] = 2;
    }
}
