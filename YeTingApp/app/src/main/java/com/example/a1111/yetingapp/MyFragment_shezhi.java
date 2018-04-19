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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a1111 on 2017/11/16.
 * 设置界面，listview,数据,源itmes，适配器（要不要）待定，界面跳转，
 */

public class MyFragment_shezhi extends Fragment implements AdapterView.OnItemClickListener{


    private Context context;
    private String[] str_name = new String[] { "商务合作","分享给好友", "清除缓存(0.85M)", "我喜欢的歌曲"};
    private ListView listView;
    private  ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shezhi_listview, container, false);

      listView = (ListView) view.findViewById(R.id.shezhilistview);
//.D
//
//
     adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_activated_1,str_name);
       listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        Log.i("AAA-----", "SSS" + mData.get(position -).getaspeak());
//        Toast.makeText(getActivity(),"点击了" + position + "项", Toast.LENGTH_LONG).show();

        switch (position){
            case 0:
                Intent intent1 = new Intent(getActivity(), Hezuo.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.layout.leftin, R.layout.leftout);
                break;
            case 1:
                break;
            case 2:
                str_name = new String[] { "商务合作","分享给好友", "清除缓存(0 M)", "我喜欢的歌曲"};
                adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_activated_1,str_name);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                break;
            case 3:
            Intent intent = new Intent(getActivity(), MyShezhiActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.layout.leftin, R.layout.leftout);
                break;

        }

//        if (position ==3) {
//
//            Intent intent = new Intent(getActivity(), MyShezhiActivity.class);
//
//            startActivity(intent);
//            getActivity().overridePendingTransition(R.layout.leftin, R.layout.leftout);
//        }else if (position)

    }




}