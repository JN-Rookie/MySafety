package edu.feicui.mysafety.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.bean.Homebean;
import edu.feicui.mysafety.utils.Constants;
import edu.feicui.mysafety.utils.MD5utils;
import edu.feicui.mysafety.utils.SpUtils;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "HomeActivity";
    private boolean isFirstTouch=false;
    private GridView            mGridView;
    private ArrayList<Homebean> mDatas;
    private String[]desc={"手机防盗","通讯卫士","软件管理","进程管理","缓存清理","设置中心"};
    private int[]icons={R.mipmap.icon_phonemgr,R.mipmap.icon_telmgr,R.mipmap.icon_softmgr,
                            R.mipmap.icon_rocket,R.mipmap.icon_sdclean,R.mipmap.icon_filemgr};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        mGridView= (GridView) findViewById(R.id.gv_home);
        mGridView.setAdapter(new HomeAdapter());
        mGridView.setOnItemClickListener(this);
    }

    private void initData() {
        mDatas=new ArrayList<>();
        for (int i = 0; i <desc.length ; i++) {
            Homebean bean=new Homebean();
            bean.pic=icons[i];
            bean.desc=desc[i];
            mDatas.add(bean);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                clickSJFD();
                break;
            case 1:
                Intent intent1=new Intent(HomeActivity.this,TelManageActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2=new Intent(HomeActivity.this,SoftmanageActivity.class);
                startActivity(intent2);
                break;
            case 5:
                Intent intent5=new Intent(HomeActivity.this,PhonemgrActivity.class);
                startActivity(intent5);
                break;
        }
    }

    private void clickSJFD() {
//        取出来password
        String password = SpUtils.getString(this, Constants.SFJD_PWD);
        if(TextUtils.isEmpty(password)){
            showSetupDialog();

        }else{
            showEnterDialog();

        }
    }

    private void showEnterDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=View.inflate(getApplicationContext(),R.layout.item_submit,null);
        final EditText etPwdlogin= (EditText) view.findViewById(R.id.et_pwdlogin);

        Button btn_canclepwd= (Button) view.findViewById(R.id.btn_canclepwd);
        Button btn_login= (Button) view.findViewById(R.id.btn_login);
        builder.setView(view);
        final AlertDialog dialog=builder.show();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                校验文本框内容
                String pwd=etPwdlogin.getText().toString().trim();
                pwd=MD5utils.encode(pwd);
                Log.d(TAG, "onClick: pwd"+pwd);
//                是否为空
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                String savepwd=SpUtils.getString(getApplicationContext(),Constants.SFJD_PWD);
//                是否相同
                if(!pwd.equals(savepwd)){
                    Toast.makeText(getApplicationContext(),"输入密码有误",Toast.LENGTH_LONG).show();
                    return;
                }
//                保存edittext里的内容
                SpUtils.putString(getApplicationContext(), Constants.SFJD_PWD,pwd);
                dialog.dismiss();
                Intent intent=new Intent(HomeActivity.this,PhoneProectActivity.class);
                startActivity(intent);
            }
        });
        btn_canclepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showSetupDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=View.inflate(getApplicationContext(),R.layout.item_phonepot,null);
        final EditText etPwd= (EditText) view.findViewById(R.id.et_pwd);
        final EditText etconfirmPwd= (EditText) view.findViewById(R.id.et_pwd1);

        Button btn_cancle= (Button) view.findViewById(R.id.btn_cancle);
        Button btn_submit= (Button) view.findViewById(R.id.btn_submit);
        builder.setView(view);
        final AlertDialog dialog=builder.show();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                校验文本框内容
                String pwd=etPwd.getText().toString().trim();
                String confirmPwd=etconfirmPwd.getText().toString().trim();
//                是否为空
                if(TextUtils.isEmpty(pwd)||TextUtils.isEmpty(confirmPwd)){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
//                是否相同
                if(!pwd.equals(confirmPwd)){
                    Toast.makeText(getApplicationContext(),"两次输入的密码不一致",Toast.LENGTH_LONG).show();
                    return;
                }
                pwd=MD5utils.encode(pwd);
                Log.d(TAG, "onClick: +pwd"+pwd);
//                保存edittext里的内容
                SpUtils.putString(getApplicationContext(), Constants.SFJD_PWD,pwd);
                dialog.dismiss();
                Toast.makeText(HomeActivity.this,"密码设置成功",Toast.LENGTH_LONG).show();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    private class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(mDatas!=null){
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if(mDatas!=null){
                return mDatas.get(position);
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
                convertView=View.inflate(HomeActivity.this,R.layout.item_grid_list,null);
            }
            ImageView ivIcons= (ImageView) convertView.findViewById(R.id.iten_tv_pic);
            TextView tvDesc= (TextView) convertView.findViewById(R.id.item_tv_desc);
            Homebean bean=mDatas.get(position);
            ivIcons.setImageResource(bean.pic);
            tvDesc.setText(bean.desc);
            return convertView;
        }


    }
}
