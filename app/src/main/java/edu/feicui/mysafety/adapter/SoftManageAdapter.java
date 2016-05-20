package edu.feicui.mysafety.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.feicui.mysafety.R;

/**
 * Created by Administrator on 2016/5/10.
 */
public class SoftManageAdapter extends BaseAdapter {
    public static final String TAG = "SoftManageAdapter";
    private List<PackageInfo> mDatas;
    private LayoutInflater    mInflater;
    private PackageManager    mPm;

    public SoftManageAdapter(Context context, List<PackageInfo> data, PackageManager pm) {
        this.mDatas = data;
        this.mPm = pm;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_soft_list, null);
        }
        ImageView iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_appname);
        TextView tv_version = (TextView) convertView.findViewById(R.id.tv_version);
        iv_pic.setImageDrawable(mDatas.get(position).applicationInfo.loadIcon(mPm));//获取图片
        tv_name.setText(mDatas.get(position).applicationInfo.loadLabel(mPm));//获取软件名
        tv_version.setText(mDatas.get(position).versionName);//获取版本号
        return convertView;
    }

}
