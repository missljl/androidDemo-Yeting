package com.example.a1111.yetingapp;


import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{


//    public static final String GET_URL = "http://mobile.ximalaya.com/mobile/v1/artist/tracks?device=iPhone&pageId=%d&pageSize=20&toUid=18463643&";
    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel,rb_1,rb_2,rb_3;

    private MyFragment fg1;
    private MyFragment_T fg2;
    private MyFragment_Three fg3;
    private MyFragment_shezhi fg4;
    private FragmentManager fManager;
    private TextView navbartextview;

    private ImageView animtionimageview;
    private  AnimationDrawable animtiaon;


    int musicplayercontent;
    int isForT;
    List<souyeModel> list;


private static final  String app_id = "wx88888888";
private IWXAPI iwxapi;
    private void regTowx(){

        iwxapi = WXAPIFactory.createWXAPI(this,app_id,true);
        iwxapi.registerApp(app_id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        animtionimageview = (ImageView) findViewById(R.id.imageView6);
        animtionimageview.setBackgroundResource(R.drawable.list_animation);
        animtiaon = (AnimationDrawable)animtionimageview.getBackground();
        animtiaon.setOneShot(false);
        animtionimageview.setAlpha(0.f);
        animtiaon.start();







        animtionimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MusicPlayerActivity.class);
                intent.putExtra("list", (Serializable) list);
                intent.putExtra("position", String.valueOf(musicplayercontent));
                intent.putExtra("VCpage", String.valueOf(isForT));

                startActivityForResult(intent, 0);

//        startActivity(intent);
                MainActivity.this.overridePendingTransition(R.layout.leftin, R.layout.leftout);
                Log.i("点了","点了"+"点了");
            }
        });


        navbartextview = (TextView) findViewById(R.id.txt_topbar);

       fManager = getSupportFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_1 = (RadioButton) findViewById(R.id.rb_message);
        rb_2 = (RadioButton) findViewById(R.id.rb_better);
        rb_3 = (RadioButton) findViewById(R.id.rb_setting);
        rb_channel.setChecked(true);



       initView();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_channel:
                navbartextview.setText("夜听");
                if(fg1 == null){
                    fg1 = new MyFragment();
                    fTransaction.add(R.id.ly_content,fg1);

                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.rb_message:
                navbartextview.setText("音乐");
                if(fg2 == null){
                    fg2 = new MyFragment_T();

                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.rb_better:
                navbartextview.setText("故事");
                if(fg3 == null){
                    fg3 = new MyFragment_Three();

                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
            case R.id.rb_setting:
                navbartextview.setText("设置");
                if(fg4 == null){
                    fg4 = new MyFragment_shezhi();

                    fTransaction.add(R.id.ly_content,fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
        }
        fTransaction.commit();
    }



    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }

    private void initView() {
        //定义底部标签图片大小和位置
        Drawable drawable_news = getResources().getDrawable(R.drawable.tab_home);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 48, 48);
        //设置图片在文字的哪个方向
        rb_channel.setCompoundDrawables(null, drawable_news, null, null);

        //定义底部标签图片大小和位置
        Drawable drawable_live = getResources().getDrawable(R.drawable.tab_tingyou);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_live.setBounds(0, 0, 50, 50);
        //设置图片在文字的哪个方向
        rb_1.setCompoundDrawables(null, drawable_live, null, null);

        //定义底部标签图片大小和位置
        Drawable drawable_tuijian = getResources().getDrawable(R.drawable.tab_wanan);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_tuijian.setBounds(0, 0, 50, 50);
        //设置图片在文字的哪个方向
        rb_2.setCompoundDrawables(null, drawable_tuijian, null, null);

        //定义底部标签图片大小和位置
        Drawable drawable_me = getResources().getDrawable(R.drawable.tab_shezhi);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_me.setBounds(0, 0, 48, 48);
        //设置图片在文字的哪个方向
        rb_3.setCompoundDrawables(null, drawable_me, null, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

        String d = data.getStringExtra("musicpostion");
        musicplayercontent = Integer.valueOf(d);
        String isfort = data.getStringExtra("musicVCpage");
       isForT = Integer.valueOf(isfort);
        list = (List<souyeModel>) data.getSerializableExtra("musiclist");
//        Log.i("MAIN","INTENT--------"+p + isfort);

        animtionimageview.setAlpha(1.f);
//        mData.get(p).isCheck = true;
//        mAdatpter.notifyDataSetChanged();



//        come.setText(data.getExtras().get("back").toString());//获得返回信息，并刷新UI
    }

}
