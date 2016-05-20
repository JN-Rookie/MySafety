package edu.feicui.mysafety.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.adapter.SoftManageAdapter;

public class SoftmanageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "SoftmanageActivity";
    private ListView    mListView;
    private PackageManager mPm;
    private List<PackageInfo> mPInfo;
    private SoftManageAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softmanage);
        mListView= (ListView) findViewById(R.id.lv_soft);
        mPm = this.getPackageManager();
        mPInfo = mPm.getInstalledPackages(0);
        mAdapter=new SoftManageAdapter(this, mPInfo, mPm);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
//          使用内部类实现获取软件信息
//        mListView.setAdapter(new SofManageAdapter(pInfo,pm));
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       String name = mPInfo.get(position).packageName;
        showSoftDialog(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    public void showSoftDialog(final String name) {
        Intent intent=new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:"+name));
        startActivity(intent);
    }

    private class SofManageAdapter extends BaseAdapter {
        List<PackageInfo> mPackageInfos;
        PackageManager mPackageManager;
        public SofManageAdapter(List<PackageInfo> packageInfoList, PackageManager packageManager) {
            mPackageInfos=packageInfoList;
            mPackageManager=packageManager;
        }

        @Override
        public int getCount() {
            return mPackageInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return mPackageInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=View.inflate(SoftmanageActivity.this,R.layout.item_soft_list,null);
            }
            ImageView pic= (ImageView) convertView.findViewById(R.id.iv_pic);
            TextView tv_name= (TextView) convertView.findViewById(R.id.tv_appname);
            TextView tv_version= (TextView) convertView.findViewById(R.id.tv_version);
            pic.setImageDrawable(mPackageInfos.get(position).applicationInfo.loadIcon(mPackageManager));
            tv_name.setText(mPackageInfos.get(position).applicationInfo.loadLabel(mPackageManager));
            tv_version.setText(mPackageInfos.get(position).versionName);
            return convertView;
        }
    }
}
