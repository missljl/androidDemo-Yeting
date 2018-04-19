package com.example.a1111.yetingapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

/**
 * Created by a1111 on 2017/11/3.
 */

public class MyService extends Service {

    public final IBinder binder = new MyBinder();


    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
    public  int musicIndex;

    private List<souyeModel> list = null;




    @Override
    public IBinder onBind(Intent intent) {

        return binder;

    }


    public static MediaPlayer mp = new MediaPlayer();


    public void MyService1(List<souyeModel> list2,int musc1) {
        musicIndex = musc1;
        list = list2;
        try {

            mp.setDataSource(list2.get(musc1).getaspeak());
            mp.prepare();
            mp.start();


        } catch (Exception e) {
            Log.d("hint", "can't get to the song");
            e.printStackTrace();
        }

    }
    public void  Myservice2(List<souyeModel> list1,int page) {

        musicIndex = page;
        list = list1;
        if (mp != null ) {

            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(list1.get(page).getaspeak());
                mp.prepare();
                mp.seekTo(0);
                mp.start();


            } catch (Exception e) {
                e.printStackTrace();

            }

        }else {


            try {

                mp.setDataSource(list1.get(page).getaspeak());
                mp.prepare();
                mp.start();


            } catch (Exception e) {
                Log.d("hint", "can't get to the song");
                e.printStackTrace();
            }
        }

    }


    public MyService() {







    }

    public void playOrPause() {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }
    }

    public void stop() {
        if (mp != null) {
            mp.stop();
            try {
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void nextMusic(int page) {


        if (mp != null && page < 15) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(list.get(page).getaspeak());

                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump next music");
                e.printStackTrace();
            }
        }
    }

    public void preMusic(int page) {
        if (mp != null && page > 0) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(list.get(page).getaspeak());

                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump pre music");
                e.printStackTrace();
            }
        }


    }






}