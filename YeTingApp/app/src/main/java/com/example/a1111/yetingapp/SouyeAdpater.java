package com.example.a1111.yetingapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Created by a1111 on 2017/10/27.
 */

public class SouyeAdpater extends BaseAdapter {

    private LinkedList<souyeModel> mData;

    private Context mContext;
   private int selectorpositon = 10000;


    public  SouyeAdpater(LinkedList<souyeModel>mData,Context mContext) {

        this.mData = mData;
        this.mContext = mContext;


    }
    //Override作用是表示重写，而且编译器会检测该方法是否是父类中所有，如果你写错了会提示你写错，如果不写如果写错了就不会报错会以为你重写了一个新的方法，还有方便阅读，
    @Override
    public  int getCount(){
        return  mData.size();


    }
    @Override
    public  Object getItem(int position){
        return  mData.get(position);


    }
    @Override
    public  long getItemId(int position){

        return  position;

    }

    //最重要
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //内部类 系统类，解决重复使用item而引起的卡顿问题
        ViewHolder holder = null;


        //优化利用服用减少性能小号
        if (convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.listv_item
                    ,parent,false);


            holder = new ViewHolder();


            holder.img_icon = (ImageView) convertView.findViewById(R.id.imageView);
            holder.txt_aName = (TextView) convertView.findViewById(R.id.name);
//            holder.txt_aspek = (TextView) convertView.findViewById(R.id.says);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
//加载网络图片picasso使用
       Picasso.with(mContext).load(Uri.parse(mData.get(position).getaicon())).into(holder.img_icon);



        holder.txt_aName.setText(mData.get(position).getaName());
        final souyeModel dataBean = mData.get(position);
        if (dataBean.isCheck == true){

         holder.txt_aName.setTextColor(Color.RED);

        }else {

            holder.txt_aName.setTextColor(Color.WHITE);

        }


//        holder.txt_aspek.setText(mData.get(position).getaspeak());
        return convertView;

    }



    //内部类
    static  class  ViewHolder{

        ImageView img_icon;
        TextView txt_aName;
//        TextView txt_aspek;


    }








}
