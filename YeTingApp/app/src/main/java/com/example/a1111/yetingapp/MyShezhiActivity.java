package com.example.a1111.yetingapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MyShezhiActivity extends AppCompatActivity {


    private List<shareModel> mData ;
    //上下文，不知道深层用法
    private Context mContext;
    //自定义适配器类（继承与BaseAdapter）
    private ShaerAdpater mAdatpter;


    private ListView mlistview;

    private Button palyerallbtn,allselectorbtn,duoselectorbtn,leftbackbtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

       //不可旋转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_my_shezhi);


        duoselectorbtn = (Button) findViewById(R.id.button6);
        palyerallbtn = (Button) findViewById(R.id.button4);
        palyerallbtn.setTag(100);
        allselectorbtn = (Button) findViewById(R.id.button5);
        leftbackbtn = (Button) findViewById(R.id.backbtn1);

        mlistview = (ListView) findViewById(R.id.listview_soucang);
        mData = new LinkedList<shareModel>();
        mContext = getBaseContext();
        coreData();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,mData);
        mAdatpter = new ShaerAdpater(mContext,(List<shareModel>) mData);
        mlistview.setAdapter(mAdatpter);

        //拿到数据，和适配器绑定listview;



    }
    //数据源
    private void coreData(){

        List<musicM> m = getMusicMDao().loadAll();
        //查询数据库中是否有这条数据如果有那么选中并设置tag,如果没有那么未选中并设置tag
        for (int i = 0; i < m.size(); i++) {

            String title = m.get(i).getName();
            String id1 = m.get(i).getImagename();
            String urlplay = m.get(i).getMusicplayerurl();
            String cover = m.get(i).getImagename();
            String aid = m.get(i).getId();
                    Log.i("AAA-----", "SSS" + title);
                   Log.i("AAA-----", "SSS++++" + id1);
                    Log.i("AAA-----", "SSS__________" + urlplay);
            //将数据添加到全局变量List中
            mData.add(new shareModel(aid, title, urlplay, id1, cover));

        }


    }



    public void click(View view) {

        switch (view.getId()) {
            case R.id.button6:
                int datacontent = getMusicMDao().loadAll().size();
                if (datacontent > 0){
                mAdatpter.flage = !mAdatpter.flage;
                if (mAdatpter.flage) {
                    duoselectorbtn.setText("完成");
                    palyerallbtn.setAlpha(1);
                    allselectorbtn.setAlpha(1);
                } else {
                    duoselectorbtn.setText("多选");
                    palyerallbtn.setAlpha(0);
                    allselectorbtn.setAlpha(0);
                }

                mAdatpter.notifyDataSetChanged();
            }
                break;
            case R.id.button4:
                int tag = (int)palyerallbtn.getTag();
               if (tag == 100){

                   if (mAdatpter.flage) {
                       for (int i = 0; i < mData.size(); i++) {
                           mData.get(i).isCheck = true;
                       }
                       mAdatpter.notifyDataSetChanged();
                   }
                   allselectorbtn.setTextColor(Color.RED);
                   palyerallbtn.setTag(101);
               }else {

                   if (mAdatpter.flage) {
                       for (int i = 0; i < mData.size(); i++) {
                           mData.get(i).isCheck = false;
                       }
                       mAdatpter.notifyDataSetChanged();
                   }
                   allselectorbtn.setTextColor(Color.LTGRAY);
                   palyerallbtn.setTag(100);
               }

                break;
            case R.id.button5:

               for (int i=0;i<mData.size();i++){


                   if (mData.get(i).isCheck == true){
                       getMusicMDao().deleteByKey(mData.get(i).getAid());
                       mData.remove(i);
//                       getMusicMDao().deleteByKey(mData.get(i).getAid());
//                       Log.i("-------","-----" +mData.get(i).getAid());
                       mAdatpter.flage = false;
                       mAdatpter.notifyDataSetChanged();
                       allselectorbtn.setAlpha(0);
                       palyerallbtn.setAlpha(0);
                   }
               }
              break;
            case R.id.backbtn1:
                finish();
                overridePendingTransition(R.layout.leftout, R.layout.left_outtow);


        }

    }





    private musicMDao getMusicMDao(){

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"yetingap.db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());

        DaoSession daoSession = daoMaster.newSession();
        musicMDao mDao = daoSession.getMusicMDao();



        return mDao;
    }

}
