package com.example.a1111.yetingapp;

import android.animation.ObjectAnimator;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;


import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;

import android.media.MediaPlayer;
import android.net.Uri;

import android.os.IBinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.view.View;


import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class MusicPlayerActivity extends AppCompatActivity {

    private Context mContext;

    private MyService myservice;
    private Button playbtn, nextbtn, prebtn,backbtn,suijibtn,sharebtn;

    private  List<souyeModel> list = null;
    private int postion;
    private Intent intent;
    private  ImageView image,titlebackimageview,needimageview;
    private  YuanImageview yuanimage;
    private  TextView text,durntime,zongtime,titleTextview;
    private  SeekBar seekbar;
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private int issuijin;

    private int ForTwoVC;



//属性动画
    private ObjectAnimator mcircleanimator,needanimator;
//动画当前时间
   private long currentPlayTime;


    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myservice = ((MyService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myservice = null;
        }
    };

    private void bindServiceConnection() {
         intent = new Intent(MusicPlayerActivity.this, MyService.class);

        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_music_player);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();

        //传值逻辑
        Intent intent = this.getIntent();
       list = (List<souyeModel>) intent.getSerializableExtra("list");
        String d = intent.getStringExtra("position");
        postion = Integer.valueOf(d);

        String isfort = intent.getStringExtra("VCpage");
        ForTwoVC = Integer.valueOf(isfort);

        issuijin = 1;

        //判断是不是第一次播放和播放的是否是同一首歌逻辑
       firstPlayerORnotfirstPlayer();
        //控件毛玻璃效果和网络加载逻辑
        confithUI(postion);
        myservice.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                nextbtn();
            }
        });

//        musicM musicm = new musicM(null,list.get(postion).getaName(),list.get(postion).getaicon(),list.get(postion).getaspeak());
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"yeting.db",null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//
//        DaoSession daoSession = daoMaster.newSession();
//        musicMDao mDao = daoSession.getMusicMDao();
//        Log.i("tag", "结果：" + name);
//        getMusicMDao().insert(musicm);
//            List<musicM> m =getMusicMDao().loadAll();
//            for (int i = 0; i < m.size(); i++) {
//////
//                   String name = m.get(i).getName();
////                if (name.equals(list.get(postion).getaName())){
////
////
////                }else {
//                    getMusicMDao().insert(musicm);
////
////                }
//////
//                Log.i("tag", "结果：" + name);
//            }
        //存
//        musicM user = new musicM();
//

    }







    public android.os.Handler handler = new android.os.Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(myservice.mp.isPlaying()) {




                playbtn.setBackgroundResource(R.drawable.play_btn_pause);


            } else {
                playbtn.setBackgroundResource(R.drawable.play_btn_play);

            }
            zongtime.setText(time.format(myservice.mp.getDuration()));
            durntime.setText(time.format(myservice.mp.getCurrentPosition()));
            seekbar.setProgress(myservice.mp.getCurrentPosition());
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        myservice.mp.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            handler.postDelayed(runnable, 100);
        }
    };




    @Override
    protected void onResume() {
        if(myservice.mp.isPlaying()) {



        }

        seekbar.setProgress(myservice.mp.getCurrentPosition());
        seekbar.setMax(myservice.mp.getDuration());
        handler.post(runnable);
        super.onResume();
        Log.d("hint", "handler post runnable");
    }







//控件创建
    public  void  initView(){

        image = (ImageView) findViewById(R.id.imageView3);
        text = (TextView) findViewById(R.id.txt_topbar1);
        yuanimage = (YuanImageview) findViewById(R.id.imageView5);
        //图片旋转
         //动画

        mcircleanimator = ObjectAnimator.ofFloat(yuanimage,"rotation",0.0f,360.0f);
//        mcircleanimator = new RotateAnimation(0.0f,360.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        //动画执行周期时间
        mcircleanimator.setDuration(30000);
        //动画执行次数 -1位玄幻
        mcircleanimator.setRepeatCount(-1);

        // 匀速转动的代码:。LinearInterpolator为匀速效果，Accelerateinterpolator为加速效果、DecelerateInterpolator为减速效果;
        LinearInterpolator lin = new LinearInterpolator();
        mcircleanimator.setInterpolator(lin);
        mcircleanimator.setRepeatMode(ObjectAnimator.RESTART);
        //开始
        mcircleanimator.start();



        titleTextview = (TextView) findViewById(R.id.textView);
        playbtn = (Button) findViewById(R.id.playbtn);
        prebtn = (Button) findViewById(R.id.prebtn);
        nextbtn = (Button) findViewById(R.id.nextbtn);
        backbtn = (Button) findViewById(R.id.backbtn);
        zongtime = (TextView) findViewById(R.id.textView2);
        durntime = (TextView) findViewById(R.id.textView4);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        suijibtn = (Button) findViewById(R.id.suijibtn);
        sharebtn = (Button) findViewById(R.id.startbtn);






        needimageview = (ImageView) findViewById(R.id.imageView7);

        //偏好设置是否随机（0是随机，1不是）
        SharedPreferences sharedPreferences = getSharedPreferences("test2",0);
        int s = sharedPreferences.getInt("page1",1);

        if (s == 0){
            //随机
            suijibtn.setBackgroundResource(R.drawable.play_icn_shuffle_prs);
            suijibtn.setTag(0);
        }else {
            suijibtn.setBackgroundResource(R.drawable.play_icn_one);
            suijibtn.setTag(1);
        }

    }




//按钮点击事件
    public void click(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("test2",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (view.getId()) {
            case R.id.playbtn:
                myservice.playOrPause();

                if(myservice.mp.isPlaying()) {

                    mcircleanimator.start();
              mcircleanimator.setCurrentPlayTime(currentPlayTime);
                    needanimator = ObjectAnimator.ofFloat(needimageview, "rotation", -35, 0);

                } else {

                needanimator = ObjectAnimator.ofFloat(needimageview, "rotation", 0, -35);

                currentPlayTime = mcircleanimator.getCurrentPlayTime();
                mcircleanimator.cancel();

                }

                needanimator.setDuration(400);
                needimageview.setPivotX(30);
                needimageview.setPivotY(20);
                needanimator.setInterpolator(new LinearInterpolator());

                needanimator.start();


                break;
            case R.id.prebtn:

               prebtn();
               needimageviewAnimations();
                break;
            case R.id.nextbtn:

               nextbtn();
               needimageviewAnimations();
                break;
            case R.id.backbtn:

                Intent  intent1  = new Intent();
                intent1.putExtra("musicpostion",String.valueOf(postion));
                intent1.putExtra("musiclist",(Serializable) list);
                intent1.putExtra("musicVCpage", String.valueOf(ForTwoVC));
                setResult(1,intent1);
                finish();
              overridePendingTransition(R.layout.leftout, R.layout.left_outtow);
                 break;
            case R.id.suijibtn:
                int flag = (int)suijibtn.getTag();
                if (flag == 0){

                    suijibtn.setBackgroundResource(R.drawable.play_icn_shuffle_prs);
                    suijibtn.setTag(1);
                    issuijin = 0;

                    editor.putInt("page1",issuijin);
                    editor.commit();
                }else {

                    suijibtn.setBackgroundResource(R.drawable.play_icn_one);
                    suijibtn.setTag(0);
                    issuijin = 1;
                    editor.putInt("page1",issuijin);
                    editor.commit();
                }
                break;
            case R.id.startbtn:

                int flag1 = (int)sharebtn.getTag();
                 if (flag1 == 100){
                     sharebtn.setBackgroundResource(R.drawable.play_icn_love_prs);
                     sharebtn.setTag(101);
//                     musicM musicm = new musicM(null,list.get(postion).getaName(),list.get(postion).getaicon(),list.get(postion).getaspeak());
//                     getMusicMDao().deleteAll();

                    getMusicMDao().deleteByKey(list.get(postion).getAid());
                     Log.i("tag", "取消收藏" );

                 }else {

                    sharebtn.setBackgroundResource(R.drawable.play_icn_loved_prs);
                     sharebtn.setTag(100);

                    musicM musicm = new musicM(list.get(postion).getAid(),list.get(postion).getaName(),list.get(postion).getaicon(),list.get(postion).getaspeak());
                     getMusicMDao().insert(musicm);
                     Log.i("tag", "选中收藏" );
                 }

                break;
        }

    }

    //是不是第一次播放逻辑（偏好设置存起来）
    public void firstPlayerORnotfirstPlayer(){
        SharedPreferences sharedPreferences = getSharedPreferences("test1",0);
        int s = sharedPreferences.getInt("page",1000);
        if (s == 1000) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("page",postion+ForTwoVC);
            editor.commit();
            myservice = new MyService();
            myservice.Myservice2(list,postion);
            bindServiceConnection();


        }else {

            if (s == postion+ForTwoVC){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("page",postion+ForTwoVC);
                editor.commit();
                myservice = new MyService();
                myservice.MyService1(list,postion);
                bindServiceConnection();
            }else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("page",postion+ForTwoVC);
                editor.commit();
                myservice = new MyService();
                myservice.Myservice2(list,postion);


            }

        }


    }




//赋值
    public void confithUI(int page){

        text.setText(list.get(page).getaName());
        Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(list.get(page).getaicon(),2);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setImageBitmap(blurBitmap2);

        Picasso.with(mContext).load(Uri.parse(list.get(page).getCoverMiddle())).placeholder(R.drawable.placeholder_disk_play_song).into(yuanimage);

        titleTextview.setText(text.getText());



        sharebtn.setBackgroundResource(R.drawable.play_icn_love_prs);
        sharebtn.setTag(101);

            List<musicM> m = getMusicMDao().loadAll();
            //查询数据库中是否有这条数据如果有那么选中并设置tag,如果没有那么未选中并设置tag
            for (int i = 0; i < m.size(); i++) {

                String name = m.get(i).getName();

                if (name.equals(list.get(page).getaName())) {

                    sharebtn.setBackgroundResource(R.drawable.play_icn_loved_prs);
                    sharebtn.setTag(100);
                    Log.i("tag", "数据库里有----按钮为红");
                }

            }


    }



    //下一首播放逻辑(判断是否随机)
    public void nextbtn(){
        SharedPreferences sharedPreferences = getSharedPreferences("test2",0);
        int s = sharedPreferences.getInt("page1",1);
        if (s == 0){

            Random random = new Random();
            postion = random.nextInt(list.size());
            text.setText(list.get(postion).getaName());
            titleTextview.setText(text.getText());
            myservice.nextMusic(postion);
            confithUI(postion);

            jilubofangyuanshifouxiangtong();

        }else {
            text.setText(list.get(postion+1).getaName());
            titleTextview.setText(text.getText());
            postion++;
            myservice.nextMusic(postion);
            confithUI(postion);
            jilubofangyuanshifouxiangtong();

        }


    }
//上一首歌曲逻辑（）
    public void prebtn(){

        SharedPreferences sharedPreferences = getSharedPreferences("test2",0);
        int s = sharedPreferences.getInt("page1",1);
        if (s == 0){
            Random random = new Random();
            postion = random.nextInt(list.size());
            text.setText(list.get(postion).getaName());
            titleTextview.setText(text.getText());

            myservice.preMusic(postion);
            confithUI(postion);
            jilubofangyuanshifouxiangtong();

        }else {

        text.setText(list.get(postion-1).getaName());
        titleTextview.setText(text.getText());
        postion--;
        myservice.preMusic(postion);
        confithUI(postion);
        jilubofangyuanshifouxiangtong();
        }
    }







    public void jilubofangyuanshifouxiangtong(){

        SharedPreferences sharedPreferences = getSharedPreferences("test1",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("page",postion);
        editor.commit();
    }



//机械臂来回动画animtionset做
 public void needimageviewAnimations(){





            needanimator = ObjectAnimator.ofFloat(needimageview, "rotation", 0, -25);

                needanimator.setRepeatCount(1);
                needanimator.setDuration(400);
                needimageview.setPivotX(15);
                needimageview.setPivotY(15);
                needanimator.setInterpolator(new LinearInterpolator());
                needanimator.setRepeatMode(ObjectAnimator.REVERSE);
                needanimator.start();


 }

    private musicMDao getMusicMDao(){

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"yetingap.db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());

        DaoSession daoSession = daoMaster.newSession();
        musicMDao mDao = daoSession.getMusicMDao();



        return mDao;
    }




    public void onBackPressed(){

        Intent  intent1  = new Intent();
        intent1.putExtra("musicpostion",String.valueOf(postion));
        intent1.putExtra("musiclist",(Serializable) list);
        intent1.putExtra("musicVCpage", String.valueOf(ForTwoVC));
        setResult(1,intent1);


        super.onBackPressed();
    }


}