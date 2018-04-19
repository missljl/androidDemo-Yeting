package com.example.a1111.yetingapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by a1111 on 2017/11/16.
 */

public class ShaerAdpater extends BaseAdapter {

    private Context mContext;

    private List<shareModel> mDatas;

    private LayoutInflater mInflater;

    public boolean flage = false;


    public ShaerAdpater(Context mContext, List<shareModel> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;

        mInflater = LayoutInflater.from(this.mContext);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (convertView == null) {
            // 下拉项布局
            convertView = mInflater.inflate(R.layout.share_listview_item, null);

            holder = new ViewHolder();

            holder.checkboxOperateData = (CheckBox) convertView.findViewById(R.id.checkbox_operate_data);
            holder.textTitle = (TextView) convertView.findViewById(R.id.name_share);
           holder.textDesc = (ImageView) convertView.findViewById(R.id.imageView_share);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final shareModel dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.textTitle.setText(dataBean.getaName());
//            holder.textDesc.setText(dataBean.desc);

            Picasso.with(mContext).load(Uri.parse(mDatas.get(position).getaicon())).into(holder.textDesc);
            // 根据isSelected来设置checkbox的显示状况
            if (flage) {
                holder.checkboxOperateData.setVisibility(View.VISIBLE);
            } else {
                holder.checkboxOperateData.setVisibility(View.GONE);
            }

            holder.checkboxOperateData.setChecked(dataBean.isCheck);

            //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
            holder.checkboxOperateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataBean.isCheck) {
                        dataBean.isCheck = false;
//                        Log.i("aaaaa","aaa" + dataBean.getaName());
                    } else {
                        dataBean.isCheck = true;
//                        Log.i("ddddd","ddddd" + dataBean.getaName());
                    }
                }

            });
        }
        return convertView;
    }




    class ViewHolder {

        public CheckBox checkboxOperateData;

        public TextView textTitle;

        public ImageView textDesc;
    }


}
