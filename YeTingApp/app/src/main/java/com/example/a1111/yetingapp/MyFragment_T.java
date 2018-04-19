package com.example.a1111.yetingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a1111 on 2017/10/30.
 */

public class MyFragment_T extends Fragment implements AdapterView.OnItemClickListener{



    public static final String GET_URL = "http://mobile.ximalaya.com/mobile/v1/album/track/ts-1511095947974?albumId=198440&device=iPhone&isAsc=true&pageId=%d&pageSize=20&";

    private OkHttpClient client;

    private List<souyeModel> mData = null;
    //上下文，不知道深层用法
    private Context mContext;
    //自定义适配器类（继承与BaseAdapter）
    private SouyeAdpater mAdatpter = null;

    //上拉加载+下拉刷新第三方
   private PullToRefreshListView mPullToRefreshListView;
    //分页加载初始为1
    private  int page = 1;

    private ListView mlistview;
    private String content;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_content, container, false);


        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listview);
//mPullToRefreshListView 中的listview,final作用应该是只创建一次吧（不确定）,查过了它的作用是引用外部类的对象的时候
        mlistview = mPullToRefreshListView.getRefreshableView();
//这边是让mPullToRefreshListView支持上啦，下拉或上下都行，具体看他的官方文档，我这里是上拉加载和下拉刷新
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

//        mlistview = (ListView) view.findViewById(R.id.listview);
        mData = new LinkedList<souyeModel>();

        mContext = getActivity();
        initOkHttp(page);
        //创建适配器对象
        mAdatpter = new SouyeAdpater((LinkedList<souyeModel>) mData, mContext);
        //让listview和适配器绑定
        mlistview.setAdapter(mAdatpter);
        mAdatpter.notifyDataSetChanged();
        RefreshAndLoad();


        ////获取点击的行
       mlistview.setOnItemClickListener(this);


        return view;



    }

    public void initOkHttp(int pageidex) {
//创建对象
        client = new OkHttpClient();
//格式化字符串
        String urlstr = String.format(GET_URL,pageidex);
        //请求参数
        final Request request = new Request.Builder().url(urlstr).build();

        Call call = client.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //获得字符串
                String json = response.body().string();

                System.out.println(json);//2
//将字符串映射成对象
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                //根据json数据拿到我们要的对象
                JsonObject data = jsonObject.get("data").getAsJsonObject();

//对象中是值是一个数组
                JsonArray array1 = data.get("list").getAsJsonArray();
                //遍历数组
                for (int i = 0; i < array1.size(); i++) {
                    JsonObject temp = (JsonObject) array1.get(i);
                    String title = temp.get("title").getAsString();
                    String id1 = temp.get("coverSmall").getAsString();
                    String urlplay = temp.get("playPathAacv224").getAsString();
                    String over = temp.get("coverMiddle").getAsString();
                    String aid = temp.get("trackId").getAsString();

                    Log.i("AAA-----", "SSS" + title);
//                   Log.i("AAA-----", "SSS++++" + id1);
//                    Log.i("AAA-----", "SSS__________" + urlplay);
                    //将数据添加到全局变量List中
                    mData.add(new souyeModel(aid,title, urlplay, id1,over));

                }


            }

        });


    }
    //下拉刷新和上拉加载跟多方法
    private void RefreshAndLoad(){

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                if (refreshView.isHeaderShown()){

                    Toast.makeText(mContext,"下拉刷新",Toast.LENGTH_SHORT).show();
//                    initOkHttp();
                    page = 1;
                    initOkHttp(page);
                    //解决刷新完成刷新控件不隐藏问题（子线程中）
                    mlistview.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //这句话就是刷新完成隐藏刷新控件
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    },1000);

                }else {

                    Toast.makeText(mContext,"上拉加载等多",Toast.LENGTH_SHORT).show();
                    page ++;
                    initOkHttp(page);
                    mlistview.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //这句话是解决下拉加载更多以后listview显示的数据不是从加载处显示问题
                            mAdatpter.notifyDataSetChanged();
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    },1000);
                }


            }
        });



    }


    @Override
    public  void  onItemClick(AdapterView<?> parent, View view, int position, long id){

        Log.i("AAA-----", "SSS" + mData.get(position-1).getaspeak());

        Intent intent = new Intent(getActivity(),MusicPlayerActivity.class);
        intent.putExtra("list",(Serializable)mData);
        intent.putExtra("position",String.valueOf(position-1));
        intent.putExtra("VCpage",String.valueOf(200));

        startActivityForResult(intent, 0);
        getActivity().overridePendingTransition(R.layout.leftin,R.layout.leftout);

//        Toast.makeText(mContext,"点击了" + position + "项", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

        String d = data.getStringExtra("musicpostion");
        int p = Integer.valueOf(d);


        mData.get(p).isCheck = true;
        mAdatpter.notifyDataSetChanged();



//        come.setText(data.getExtras().get("back").toString());//获得返回信息，并刷新UI
    }


}
