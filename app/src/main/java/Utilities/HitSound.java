package Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HitSound {
    private Context context;
    private Executor executor;
    private Handler handler;
    private MediaPlayer mediaPlayer;
    private int resID;

    public HitSound(Context context,int resID){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.resID = resID;
    }

    public void playSound(){
        executor.execute(() -> {
            mediaPlayer = MediaPlayer.create(context,this.resID);
            mediaPlayer.setVolume(1.0f,1.0f);
            mediaPlayer.start();
        });
    }
}
