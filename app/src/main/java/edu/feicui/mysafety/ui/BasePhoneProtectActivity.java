package edu.feicui.mysafety.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.View;

/**
 * Created by Administrator on 2016/5/18.
 */
public abstract class BasePhoneProtectActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//        初始化数据
        initData();
//        初始化时间
        initEvent();

    }

    public abstract void initView();

    public void initEvent() {
    }

    public void initData() {
    }



    //抽取的按钮点击事件
    public void next(View view){
        nextActivity();
        nextAnimation();
    }

    //抽取的按钮点击事件
    public void prev(View view){
        prevActivity();
        nextAnimation();
    }

    public void nextAnimation(){
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    };
    public void start(Class activity){
        Intent intent=new Intent(this,activity);
        startActivity(intent);
        finish();
    }

    protected abstract void nextActivity() ;

    protected abstract void prevActivity() ;
}
