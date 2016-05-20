package edu.feicui.mysafety.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.mysafety.R;

/**
 * Created by Administrator on 2016/5/13.
 */
public class PhoneAdapter extends BaseAdapter {
    private ArrayList<String> mdata=new ArrayList<>();
    private LayoutInflater       mInflater;
    private String[] mStrings={"获取手机卡相关信息","获取CPU相关信息","获取内存相关信息","是否Root","获取分辨率相关信息"};

    public PhoneAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        for (int i = 0; i <mStrings.length ; i++) {
            mdata.add(mStrings[i]);
        }
    }


    @Override
    public int getCount() {
        if(mdata!=null){
            return mdata.size();
        }
        return 0;
    }

    @Override
    public String getItem(int position) {
        if(mdata!=null){
            return mdata.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.item_phone,null);
        }
        TextView name= (TextView) convertView.findViewById(R.id.item_tv_desc);

        name.setText(getItem(position).toString());
        return convertView;
    }
}
