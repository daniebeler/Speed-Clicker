package com.daniebeler.speed_clicker;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;

public class SoundService extends Service {

    static MediaPlayer player00, player01, player02;
    static SharedPreferences spVolumeMusic;
    static SharedPreferences.Editor speVolumeMusic;

    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        spVolumeMusic = getApplicationContext().getSharedPreferences("volumemusic", 0);
        speVolumeMusic = spVolumeMusic.edit();
        player00 = MediaPlayer.create(this, R.raw.audio_1);
        player00.setLooping(true);
        player00.setVolume(0,0);
        player00.start();
        player01 = MediaPlayer.create(this, R.raw.audio_2);
        player01.setLooping(true);
        player01.setVolume(0,0);
        player02 = MediaPlayer.create(this, R.raw.audio_click1);
        player02.setVolume(0,0);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY ;
    }

    @Override
    public void onDestroy() {
        try{
            if (player00 != null) {
                player00.pause();
                player00.stop();
                player00 = null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void PauseMusic() {
        try {
            if (player00.isPlaying()) {
                player00.pause();
            } else if (player01.isPlaying()) {
                player01.pause();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void StartMusic(int i) {
        try {
            if (i == 0) {

                player00.start();

            } else if (i == 1) {
                player01.start();
                player01.setVolume(spVolumeMusic.getInt("volumemusic", 0) / 100f, spVolumeMusic.getInt("volumemusic", 0) / 100f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ClickSound() {
        try {
        if (player02.isPlaying()) {
            player02.seekTo(0);
        } else {
            player02.start();
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void ChangeVolume(int i) {
        speVolumeMusic.putInt("volumemusic", i).apply();
        player00.setVolume(spVolumeMusic.getInt("volumemusic", 0)/100f, spVolumeMusic.getInt("volumemusic", 0)/100f);
        player01.setVolume(spVolumeMusic.getInt("volumemusic", 0)/100f, spVolumeMusic.getInt("volumemusic", 0)/100f);
        player02.setVolume(spVolumeMusic.getInt("volumemusic", 0)/100f, spVolumeMusic.getInt("volumemusic", 0)/100f);
    }

    public static void SplashScreen(float i) {
        player00.setVolume(spVolumeMusic.getInt("volumemusic", 0)/i, spVolumeMusic.getInt("volumemusic", 0)/i);
        player01.setVolume(spVolumeMusic.getInt("volumemusic", 0)/i, spVolumeMusic.getInt("volumemusic", 0)/i);
        player02.setVolume(spVolumeMusic.getInt("volumemusic", 0)/i, spVolumeMusic.getInt("volumemusic", 0)/i);
    }

    public static void LevelGame(float i) {
        player01.setVolume(spVolumeMusic.getInt("volumemusic", 0)/i, spVolumeMusic.getInt("volumemusic", 0)/i);
    }

    @Override
    public void onLowMemory() {

    }
}